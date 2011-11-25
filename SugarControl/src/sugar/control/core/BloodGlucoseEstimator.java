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
  private int mH, mM;
  private Time tt = new Time();

  public BloodGlucoseEstimator() {
  }

  public void setGTTCurve(double[] data, double sW) {
    if (data.length != 8) {
      throw new IllegalArgumentException("Dane musza mieć 8 pomiarów w czasie 0, 0.5, 1, 2, 3, 4, 5, 6 godziny po podaniu glukozy.");
    }
    GTTCurve = new Spline(time, data);
    coeff = sW/50;
  }

  public double[] estimate(double ig, int howlong) {
    if( howlong > 360) {
      throw new IllegalArgumentException("Nie można estymować poziomu cukru dłużej niż 360 minut");
    }
    if (GTTCurve == null) {
      throw new RuntimeException("Wczytaj najpierw dane krzywej glikemicznej.");
    }
    tt.setToNow();
    int _mH = tt.hour;
    int _mM = tt.minute;
    
    
    double[] output = new double[howlong];
    double first = GTTCurve.val(0);
    double c = coeff*ig/100;    
    for(int i = 0; i < howlong; ++i) {
      output[i] = getlastValue(i,_mH, _mM) + (GTTCurve.val(i) - first)*c;
    }

    return output;
  }
  
  public void saveEstimatimation(double ig, int howlong) {
    if( howlong > 360) {
      throw new IllegalArgumentException("Nie można estymować poziomu cukru dłużej niż 360 minut");
    }
    if (GTTCurve == null) {
      throw new RuntimeException("Wczytaj najpierw dane krzywej glikemicznej.");
    }
    tt.setToNow();
    int _mH = tt.hour;
    int _mM = tt.minute;
    
    
    double[] output = new double[howlong];
    double first = GTTCurve.val(0);
    double c = coeff*ig/100;    
    for(int i = 0; i < howlong; ++i) {
      output[i] = getlastValue(i,_mH, _mM) + (GTTCurve.val(i) - first)*c;
    }
    
    lastValues = new double[howlong];
    for(int i = 0; i < howlong; ++i) {
      lastValues[i] = output[i];
    }
  }

  private double getlastValue(int i) {
    tt.setToNow();
    int _H = tt.hour;
    int _M = tt.minute;
    return getlastValue(i, _H, _M);
  }
  
  private double getlastValue(int i, int _H, int _M) {
    i = i + (_H - mH)*60 + mM - _M;
    if (i >= lastValues.length) {
      return lastValues[lastValues.length -1];
    } else {
      return lastValues[i];
    }
  }
}
