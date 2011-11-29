package sugar.control;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GraphView.GraphViewData;
import com.jjoe64.graphview.GraphView.GraphViewSeries;
import com.jjoe64.graphview.LineGraphView;

import sugar.control.core.BloodGlucoseEstimator;
import sugar.control.core.GlycemicIndexCalculator;

public class ShowSugar  extends Activity {
	BloodGlucoseEstimator estimator;
    /** Called when the activity is first created. */

     

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.showsugar);


   
    int exist = 1;  //

//    double ig = gic.calculateGlycemicIndex();

    estimator = BloodGlucoseEstimator.getInstance();
        TextView sugarLevel = (TextView) findViewById(R.id.sugarLevel);
        try {
        sugarLevel.setText(String.format("%.2f",estimator.getlastValue(0)));
        drawEstimationGraph();
        } catch (Exception e) {
        	sugarLevel.setText(e.getMessage());
                exist = 0;
		}
        
        Button refresh = (Button) findViewById(R.id.refresh);
        refresh.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				TextView sugarLevel = (TextView) findViewById(R.id.sugarLevel);
				try {
			        sugarLevel.setText(String.format("%.2f",estimator.getlastValue(0)));
                                drawEstimationGraph();
			        } catch (Exception e) {
			        	sugarLevel.setText(e.getMessage());
					}
				
			}
		});
    }
    
    private void drawEstimationGraph() {
		double output[] = estimator.getEstimataion(360);

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

                GraphViewSeries hyperglycemicSeries = new GraphViewSeries("Hiperglikemia", Color.rgb(200, 50, 00) ,data);

                data = new GraphViewData[output.length];
                for (int i = 0; i < output.length; ++i) {
                  data[i] = new GraphViewData(i, 50);
                }

                GraphViewSeries lowSeries = new GraphViewSeries("Dolna granica", Color.rgb(255, 215, 00) ,data);

                GraphView graphView = new LineGraphView(this, "Poziom cukru [mg/dl]");

                graphView.setHorizontalLabels(new String[]{"0h", "1h", "2h", "3h", "4h", "5h", "6h"});
                graphView.addSeries(exampleSeries); // data
                graphView.addSeries(hyperglycemicSeries);
                graphView.addSeries(lowSeries);

                graphView.setViewPort(0, 360);

                LinearLayout layout = (LinearLayout) findViewById(R.id.graph1);
                layout.addView(graphView);

	}
    

}
