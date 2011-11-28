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
import sugar.control.core.GlycemicIndexCalculator;

public class SugarLevelGraph extends Activity {
	
	double ig;
	
  /** Called when the activity is first created. */
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.graph);

    double[] bloodG = {80, 90, 105, 90, 80, 80, 80, 80};

    final BloodGlucoseEstimator bge = BloodGlucoseEstimator.getInstance();
    final GlycemicIndexCalculator gic = GlycemicIndexCalculator.getInstance();
    
    bge.setGTTCurve(bloodG, 50);
    bge.setGlucoseValue(80);
    
//    double ig = gic.calculateGlycemicIndex();
    ig = 10;
    double output[] = bge.estimate(ig, 360);

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


    Button YesEat = (Button) findViewById(R.id.yeseat);
        YesEat.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                startYesEatActivity(MainMenu.class,bge);
            }
        });

    Button NoEat = (Button) findViewById(R.id.noeat);
        NoEat.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Intent GraphIntent = new Intent(SugarLevelGraph.this,CanEat.class);
                startActivity(GraphIntent);
            }
        });

  }

  private void startYesEatActivity(Class<? extends Activity> activity, BloodGlucoseEstimator bge) {
		Intent intent = new Intent(SugarLevelGraph.this, activity);
		bge.saveEstimatimation(ig, 360);
                
		startActivity(intent);
	}
}