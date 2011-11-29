package sugar.control.core;

import android.text.format.Time;
import sugar.control.database.SurveyDatabaseAdapter;
import sugar.control.utils.Spline;
import sugar.control.utils.TimeDiff;

/**
 * !ATTENTIONE! API może ulegać zmianie
 * @author Bartłomiej Bułat <bartek.bulat at gmail.com>
 */
public class BloodGlucoseEstimator {

  private static final double time[] = {0, 30, 60, 120, 180, 240, 300, 360};
  private double coeff = 1; // Stosunek ilości glukozy przy pomiarze do 50g
  private double activCoff = 1; // Wspólczynnik aktuwności
  private Spline GTTCurve = null;
  private double lastValues[];
  private Time estimationStart = new Time();
  
  
    SurveyDatabaseAdapter dba;

  /**
   * Ustawia współczynnik wysiłku fizycznego
   * @param activCoff Współczynnik wysiłku, im większy tym szybciej rośnie/spada cukier
   */
  public void setActivCoff(double activCoff) {
    this.activCoff = activCoff;
  }

  protected BloodGlucoseEstimator() {
    dba = new SurveyDatabaseAdapter(null);
    loadData();
  }

  private void loadData() {
    try {
      estimationStart = dba.getTimeOfLastSurvey();
      lastValues = dba.getResultOfLastSurvey();    
    } catch(Exception ex) {
      
    }
    
  }

  public void saveData() {
    try {
      dba.saveSurvey(estimationStart, lastValues);
    } catch (Exception ex) {
      
    }
    
  }
  
  static BloodGlucoseEstimator instance = null;

  /**
   * Zwraca instancję Singletonu klasy BloodGlucoseEstimator
   * @return Instancja Singletonu
   */
  public static BloodGlucoseEstimator getInstance() {
    if (instance == null) {
      instance = new BloodGlucoseEstimator();
    }
    return instance;
  }

  /**
   * Ustawia krzywą glikemiczną
   * @param data tablica double z wartościami pomiarów w czasie 0 30 60 120 180 240 300 300 min od spożycia preparatu z glukozy
   * @param sW Ilość glukozy w preparacie w gramach
   */
  public void setGTTCurve(double[] data, double sW) throws IllegalArgumentException {
    if (data.length != 8) {
      throw new IllegalArgumentException("Dane musza mieć 8 pomiarów w czasie 0, 0.5, 1, 2, 3, 4, 5, 6 godziny po podaniu glukozy.");
    }
    GTTCurve = new Spline(time, data);
    coeff = sW / 50;
  }

  /**
   * Ustawia aktualną, zmierzoną wartość glukozy we krwi
   * @param value Wartość pomiaru w mg/dl
   */
  public void setGlucoseValue(double value) {
    if (lastValues == null) {
      estimationStart.setToNow();
      lastValues = new double[1];
      lastValues[0] = value;
    } else {
      double c = value / getlastValue(0);
      for (int i = 0; i < lastValues.length; ++i) {
        lastValues[i] = c * lastValues[i];
      }
    }
  }

  /**
   * Pobiera tablicę wartości 
   * @param minutes Czas estymacji. Mniejszy lub równy 360 minut.
   * @return Tablica wartości co minutę
   * @throws RuntimeException Gdy nie podano wcześniej początkowej wartości poziomu cukru.
   * @throws IllegalArgumentException Gdy parametr jest z poza zakresu.
   */
  public double[] getEstimataion(int minutes) throws RuntimeException {
    if (minutes > 360) throw new IllegalArgumentException("Nie można pobrać estymacji dłuższej niż 360 minut.");
    if (minutes<=0) throw new IllegalArgumentException("Bardzo śmieszne.");
    double output[] = new double[minutes];
    Time t = new Time();
    t.setToNow();
    int diff = (int) TimeDiff.inMinutes(t, estimationStart);
    try {
      for (int i = 0; i < minutes; ++i) {
        if (i + diff >= lastValues.length) {
          output[i] = lastValues[lastValues.length - 1];
        } else {
          output[i] = lastValues[diff + i];
        }

      }
    } catch (NullPointerException ex) {
      throw new RuntimeException("Dodaj wcześniej początkową wartość poziomu cukru.");
    }
    return output;
  }

  /**
   * Pobiera tablicę estymacji od teraz do końca
   * @return Tablicę estymacji co minutę
   * @throws RuntimeException Kiedy nie podano wzceśniej początkowej wartości poziomu cukru
   */
  public double[] getEstimataion() throws RuntimeException {

    Time t = new Time();
    t.setToNow();
    int diff = (int) TimeDiff.inMinutes(t, estimationStart);
    try {
      int n = lastValues.length - diff;
      if (n <= 0) {
        double output[] = new double[1];
        output[0] = lastValues[lastValues.length - 1];
        return output;
      }
      double output[] = new double[n];
      for (int i = 0; i < n; ++i) {
        output[i] = lastValues[diff + i];
      }
      return output;
    } catch (NullPointerException ex) {
      throw new RuntimeException("Dodaj wcześniej początkową wartość poziomu cukru.");
    }
  }

  /**
   * Oblicza estymowane wartości poziomu cukru we krwi przez zadany czas
   * @param ig Indeks glikemiczny potrawy
   * @param howlong Długość estymaczji w minutach
   * @return Tablica wartości poziomu cukru co minutę
   */
  public double[] estimate(double ig, int howlong) throws RuntimeException, IllegalArgumentException {
    if (GTTCurve == null) {
      throw new RuntimeException("Wczytaj najpierw dane krzywej glikemicznej.");
    }
    if (howlong > GTTCurve.lastValue()) {
      throw new IllegalArgumentException("Nie można estymować poziomu cukru dłużej niż zakres krzywej glikemicznej.");
    }
    Time tt = new Time();
    tt.setToNow();
    double[] output = new double[howlong];
    double first = GTTCurve.val(0);
    double c = activCoff * coeff * ig / 100;
    for (int i = 0; i < howlong; ++i) {
      double cv = GTTCurve.val(i);
      output[i] = getlastValue(i, tt) + (cv - first) * c;
    }

    return output;
  }

  /**
   * Tak jak estimate ale wynik jest zapisywany
   * @param ig Indeks glikemiczny potrawy
   * @param howlong Długość estymacji w minutach
   */
  public void saveEstimatimation(double ig, int howlong) throws RuntimeException, IllegalArgumentException {
    if (GTTCurve == null) {
      throw new RuntimeException("Wczytaj najpierw dane krzywej glikemicznej.");
    }
    if (howlong > GTTCurve.lastValue()) {
      throw new IllegalArgumentException("Nie można estymować poziomu cukru dłużej niż zakres krzywej glikemicznej.");
    }
    Time tt = new Time();
    tt.setToNow();
    double[] output = new double[howlong];
    double first = GTTCurve.val(0);
    double c = activCoff * coeff * ig / 100;
    for (int i = 0; i < howlong; ++i) {
      double cv = GTTCurve.val(i);
      output[i] = getlastValue(i, tt) + (cv - first) * c;
    }


    estimationStart.setToNow();

    lastValues = new double[howlong];
    for (int i = 0; i < howlong; ++i) {
      lastValues[i] = output[i];
    }
  }

  /**
   * Pobiera wartość poziomu cukru w i minut od teraz
   * @param i Ilość minut od teraz
   * @return Wartość poziomu cukru w mg/dl
   */
  public double getlastValue(int i) throws RuntimeException {

    Time tt = new Time();
    tt.setToNow();
    return getlastValue(i, tt);
  }

  /**
   * Pobiera wartość poziomu cukru w i minut od czasu t
   * @param i Ilość minut od czasu t
   * @param t Czas od którego liczymy przesunięcie czasowe
   * @return Wartość poziomu cukru w mg/dl
   */
  private double getlastValue(int i, Time t) throws RuntimeException {
    i = i + (int) TimeDiff.inMinutes(t, estimationStart);
    try {
      if (i >= lastValues.length) {
        return lastValues[lastValues.length - 1];
      } else {
        return lastValues[i];
      }
    } catch (NullPointerException ex) {
      throw new RuntimeException("Dodaj wcześniej początkową wartość poziomu cukru.");
    }
  }
}
