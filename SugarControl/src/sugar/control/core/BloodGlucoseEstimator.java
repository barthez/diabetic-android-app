package sugar.control.core;

import android.text.format.Time;
import sugar.control.utils.Spline;

/**
 * !ATTENTIONE! API może ulegać zmianie
 * @author Bartłomiej Bułat <bartek.bulat at gmail.com>
 */
public class BloodGlucoseEstimator {

  private static final double time[] = {0, 30, 60, 120, 180, 240, 300, 360};
  
  private double coeff; // Stosunek ilości glukozy przy pomiarze do 50g
  private Spline GTTCurve = null;
  private double lastValues[];
  private Time estimationStart = new Time();

  protected BloodGlucoseEstimator(){
    loadData();
  }
  
  private void loadData() {
    
  }
  
  public void saveData() {
    
  }
  
  static BloodGlucoseEstimator instance = null;
  public static BloodGlucoseEstimator getInstance() {
    if (instance == null) {
      instance = new BloodGlucoseEstimator();
    }
    return instance;
  }
  

  public void setGTTCurve(double[] data, double sW) {
    if (data.length != 8) {
      throw new IllegalArgumentException("Dane musza mieć 8 pomiarów w czasie 0, 0.5, 1, 2, 3, 4, 5, 6 godziny po podaniu glukozy.");
    }
    GTTCurve = new Spline(time, data);
    coeff = sW/50;
  }
  
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
    double c = coeff*ig/100;    
    for(int i = 0; i < howlong; ++i) {
      output[i] = getlastValue(i, tt) + (GTTCurve.val(i) - first)*c;
    }

    return output;
  }
  
  public void saveEstimatimation(double ig, int howlong) {
    double output[] = estimate(ig, howlong);    
    
    estimationStart.setToNow();
    
    lastValues = new double[howlong];
    for(int i = 0; i < howlong; ++i) {
      lastValues[i] = output[i];
    }
  }

  private double getlastValue(int i) {
    
    Time tt = new Time();
    tt.setToNow();
    return getlastValue(i, tt);
  }
  
  private double getlastValue(int i, Time t) {
    i = i + (t.hour - estimationStart.hour)*60 + t.minute - estimationStart.minute;
    if (i >= lastValues.length) {
      return lastValues[lastValues.length -1];
    } else {
      return lastValues[i];
    }
  }
}
