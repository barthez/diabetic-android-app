/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sugar.control.utils;

import android.text.format.Time;

/**
 *
 * @author Bartłomiej Bułat <bartek.bulat at gmail.com>
 */
public class TimeDiff {

  private static long inMilis(Time t1, Time t2) {
    final long firstDateMilli = t1.toMillis(true);
    final long secondDateMilli = t2.toMillis(true);
    return firstDateMilli - secondDateMilli;
  }
  
  public static long inMinutes(Time t1, Time t2) {
    return inMilis(t1, t2)/(1000*60);
  }
  
  public static long inSeconds(Time t1, Time t2) {
    return inMilis(t1, t2)/1000;
  }
}
