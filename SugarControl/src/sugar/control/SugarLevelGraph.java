package sugar.control;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GraphView.GraphViewData;
import com.jjoe64.graphview.GraphView.GraphViewSeries;
import com.jjoe64.graphview.LineGraphView;

import sugar.control.core.BloodGlucoseEstimator;

public class SugarLevelGraph extends Activity {

  /** Called when the activity is first created. */
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.graph);

    double[] bloodG = {80, 90, 105, 90, 80, 80, 80, 80};

    BloodGlucoseEstimator bge = BloodGlucoseEstimator.getInstance();

    bge.setGTTCurve(bloodG, 50);
    bge.setGlucoseValue(80);

    double output[] = bge.estimate(100, 360);

    GraphViewData data[] = new GraphViewData[output.length];
    for (int i = 0; i < output.length; ++i) {
      data[i] = new GraphViewData(i, output[i]);
    }

    // init example series data
    GraphViewSeries exampleSeries = new GraphViewSeries(data);


    GraphView graphView = new LineGraphView(this, "Poziom cukru [mg/dl]");

    //((LineGraphView) graphView).setDrawBackground(true);

    graphView.setHorizontalLabels(new String[]{"0h", "1h", "2h", "3h", "4h", "5h", "6h"});
    graphView.addSeries(exampleSeries); // data

    graphView.setViewPort(0, 360);

    LinearLayout layout = (LinearLayout) findViewById(R.id.graph1);
    layout.addView(graphView);

  }
}