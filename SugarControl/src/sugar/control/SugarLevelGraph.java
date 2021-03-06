package sugar.control;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
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
import sugar.control.core.GlycemicIndexCalculator;

public class SugarLevelGraph extends Activity {
	
	double ig;
	
  /** Called when the activity is first created. */
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.graph);

    final BloodGlucoseEstimator bge = BloodGlucoseEstimator.getInstance();
    final GlycemicIndexCalculator gic = GlycemicIndexCalculator.getInstance();
    
    ig = gic.calculateIndex();
    double output[] = bge.estimate(ig, 360);

    GraphViewData data[] = new GraphViewData[output.length];
    for (int i = 0; i < output.length; ++i) {
      data[i] = new GraphViewData(i, output[i]);
    }

    // init example series data
    GraphViewSeries exampleSeries = new GraphViewSeries(data);

    //granica hiperglikemiczna
    data = new GraphViewData[output.length];
    for (int i = 0; i < output.length; ++i) {
      data[i] = new GraphViewData(i, 130);
    }

    GraphViewSeries hyperglycemicSeries1 = new GraphViewSeries("Hiperglikemia", Color.rgb(200, 50, 00) ,data);

    data = new GraphViewData[output.length];
    for (int i = 0; i < output.length; ++i) {
         data[i] = new GraphViewData(i, 180);
    }

    GraphViewSeries hyperglycemicSeries2 = new GraphViewSeries("Hiperglikemia 2", Color.rgb(220, 20, 60) ,data);

    data = new GraphViewData[output.length];
    for (int i = 0; i < output.length; ++i) {
      data[i] = new GraphViewData(i, 50);
    }

    GraphViewSeries lowSeries = new GraphViewSeries("Dolna granica", Color.rgb(255, 215, 00) ,data);

    GraphView graphView = new LineGraphView(this, "Poziom cukru [mg/dl]");

    //((LineGraphView) graphView).setDrawBackground(true);

    graphView.setHorizontalLabels(new String[]{"0h", "1h", "2h", "3h", "4h", "5h", "6h"});
    
    graphView.addSeries(hyperglycemicSeries1);
    graphView.addSeries(hyperglycemicSeries2);
    graphView.addSeries(lowSeries);
    graphView.addSeries(exampleSeries); // data

    graphView.setViewPort(0, 360);

    LinearLayout layout = (LinearLayout) findViewById(R.id.graph1);
    layout.addView(graphView);


    Button YesEat = (Button) findViewById(R.id.yeseat);
        YesEat.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                saveEstimation();
                
                 Intent result = new Intent();
                result.putExtra("result", "OK");
                setResult(0, result);
                
                finish();
            }
        });

    Button NoEat = (Button) findViewById(R.id.noeat);
        NoEat.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
              
                 Intent result = new Intent();
                result.putExtra("result", "NO");
                setResult(1, result);
                
                finish();
            }
        });

  }

  private void saveEstimation() {
		
		BloodGlucoseEstimator.getInstance().saveEstimatimation(ig, 360);
    
	}
}