package sugar.control.core;

import android.text.format.Time;
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
  
  /**
   * Ustaw współczynnik wysiłku fizycznego
   * @param activCoff Współczynnik wysiłku, im większy tym szybciej rośnie/spada cukier
   */
  public void setActivCoff(double activCoff) {
    this.activCoff = activCoff;
  } 
  
  protected BloodGlucoseEstimator(){
    loadData();
  }
  
  private void loadData() {
    
  }
  
  public void saveData() {
    
  }
  
  static BloodGlucoseEstimator instance = null;
  
  /**
   * Pobierz instancję Singletonu klasy BloodGlucoseEstimator
   * @return Instancja Singletonu
   */
  public static BloodGlucoseEstimator getInstance() {
    if (instance == null) {
      instance = new BloodGlucoseEstimator();
    }
    return instance;
  }
  
  /**
   * Ustaw krzywą glikemiczną
   * @param data tablica double z wartościami pomiarów w czasie 0 30 60 120 180 240 300 300 min od spożycia preparatu z glukozy
   * @param sW Ilość glukozy w preparacie w gramach
   */
  public void setGTTCurve(double[] data, double sW) {
    if (data.length != 8) {
      throw new IllegalArgumentException("Dane musza mieć 8 pomiarów w czasie 0, 0.5, 1, 2, 3, 4, 5, 6 godziny po podaniu glukozy.");
    }
    GTTCurve = new Spline(time, data);
    coeff = sW/50;
  }
  
  /**
   * Ustaw aktualną, zmierzoną wartość glukozy we krwi
   * @param value Wartość pomiaru w mg/dl
   */
  public void setGlucoseValue(double value) {
    if (lastValues == null) {
      lastValues = new double[1];
      lastValues[0] = value;
    } else {
      double c = value/getlastValue(0);
      for(int i=0; i< lastValues.length; ++i) {
        lastValues[i] = c*lastValues[i];
      }
    }
  }

  /**
   * Oblicz estymowane wartości poziomu cukru we krwi przez zadany czas
   * @param ig Indeks glikemiczny potrawy
   * @param howlong Długość estymaczji w minutach
   * @return Tablica wartości poziomu cukru co minutę
   */
  public double[] estimate(double ig, int howlong) {
    if (GTTCurve == null) {
      throw new RuntimeException("Wczytaj najpierw dane krzywej glikemicznej.");
    }
    if( howlong > GTTCurve.lastValue()) {
      throw new IllegalArgumentException("Nie można estymować poziomu cukru dłużej niż zakres krzywej glikemicznej.");
    }
    Time tt = new Time();
    tt.setToNow();    
    double[] output = new double[howlong];
    double first = GTTCurve.val(0);
    double c = activCoff*coeff*ig/100;    
    for(int i = 0; i < howlong; ++i) {
      double cv =GTTCurve.val(i);
      output[i] = getlastValue(i, tt) + (cv - first)*c;
    }

    return output;
  }
  
  /**
   * Tak jak estimate ale wynik jest zapisywany
   * @param ig Indeks glikemiczny potrawy
   * @param howlong Długość estymaczji w minutach
   */
  public void saveEstimatimation(double ig, int howlong) {
    double output[] = estimate(ig, howlong);    
    
    estimationStart.setToNow();
    
    lastValues = new double[howlong];
    for(int i = 0; i < howlong; ++i) {
      lastValues[i] = output[i];
    }
  }

  /**
   * Pobiera wartość poziomu cukru w i minut od teraz
   * @param i Ilość minut od teraz
   * @return Wartość poziomu cukru w mg/dl
   */
  public double getlastValue(int i) {
    
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
  private double getlastValue(int i, Time t) {
    i = i + (int) TimeDiff.inMinutes(t, estimationStart);
    try {
      if (i >= lastValues.length) {
        return lastValues[lastValues.length -1];
      } else {
        return lastValues[i];
      }
    } catch (NullPointerException ex) {
      throw new RuntimeException("Dodaj wcześniej początkową wartość poziomu cukru.");
    }
  }
}
