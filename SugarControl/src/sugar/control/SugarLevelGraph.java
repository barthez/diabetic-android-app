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

        double [] bloodG= {80, 90, 105, 90, 80, 80, 80, 80};
        BloodGlucoseEstimator bge = new BloodGlucoseEstimator();
        bge.setGTTCurve(bloodG, 50);
        
        double [] wynik = bge.estimate(100, 30);



               // init example series data
		GraphViewSeries exampleSeries = new GraphViewSeries(new GraphViewData[] {
				new GraphViewData(0, 5.0d)
                                , new GraphViewData(0.1, 5.0d)
				, new GraphViewData(0.2, 5.0d)
                                , new GraphViewData(0.3, 4.9d)
				, new GraphViewData(0.4, 4.8d)
                                , new GraphViewData(0.5, 4.7d)
				, new GraphViewData(0.6, 4.7d)
                                , new GraphViewData(0.7, 4.40d)
				, new GraphViewData(0.8, 4.5d)
                                , new GraphViewData(0.9, 4.4d)
                                , new GraphViewData(1, 4.3d)
                                , new GraphViewData(1.1, 4.2d)
				, new GraphViewData(1.2, 4.2d)
                                , new GraphViewData(1.3, 4.2d)
				, new GraphViewData(1.4, 4.3d)
                                , new GraphViewData(1.5, 4.3d)
				, new GraphViewData(1.6, 4.0d)
                                , new GraphViewData(1.7, 4.0d)
				, new GraphViewData(1.8, 4.0d)
                                , new GraphViewData(1.9, 4.0d)
                                , new GraphViewData(2, 3.9d)
                                , new GraphViewData(2.1, 3.8d)
				, new GraphViewData(2.2, 3.5d)
                                , new GraphViewData(2.3, 3.4d)
				, new GraphViewData(2.4, 3.2d)
                                , new GraphViewData(2.5, 3.1d)
				, new GraphViewData(2.6, 3.0d)
                                , new GraphViewData(2.7, 2.8d)
				, new GraphViewData(2.8, 2.8d)
                                , new GraphViewData(2.9, 2.7d)
                                , new GraphViewData(3, 2.5d)
                                , new GraphViewData(3.1, 2.3d)
				, new GraphViewData(3.2, 2.4d)
                                , new GraphViewData(3.3, 2.0d)
				, new GraphViewData(3.4, 1.7d)
                                , new GraphViewData(3.5, 1.5d)
				, new GraphViewData(3.6, 1.2d)
                                , new GraphViewData(3.7, 0.9d)
				, new GraphViewData(3.8, 0.4d)
                                , new GraphViewData(3.9, 0.2d)
                                , new GraphViewData(4, 0.1d)

		});


                GraphView graphView = new LineGraphView(this, "Poziom cukru [mg/dl]");

		//((LineGraphView) graphView).setDrawBackground(true);

		graphView.setHorizontalLabels(new String[] { "0h", "1h", "2h", "3h", "4h"});
		graphView.addSeries(exampleSeries); // data

                graphView.setViewPort(0, 4);

      		LinearLayout layout = (LinearLayout) findViewById(R.id.graph1);
		layout.addView(graphView);
              
    }

}