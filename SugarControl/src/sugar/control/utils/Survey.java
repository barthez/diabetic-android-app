/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package sugar.control.utils;

import android.text.format.Time;
import java.util.Date;

/**
 *
 * @author rial
 */
public class Survey {

    private long time;
    private double[] results;

    public double[] getResults() {
        return results;
    }

    public long getTime() {
        return time;
    }

    public Survey(long time, double[] results) {
        this.time = time;
        this.results = results;
    }
}
