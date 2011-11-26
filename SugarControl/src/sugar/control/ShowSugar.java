package sugar.control;

import sugar.control.core.BloodGlucoseEstimator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class ShowSugar  extends Activity {
	BloodGlucoseEstimator estimator;
    /** Called when the activity is first created. */
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.showsugar);
        
        estimator = BloodGlucoseEstimator.getInstance();
        TextView sugarLevel = (TextView) findViewById(R.id.sugarLevel);
        try {
        sugarLevel.setText(String.format("%.2f",estimator.getlastValue(0)));
        } catch (Exception e) {
        	sugarLevel.setText(e.getMessage());
		}
        
        Button refresh = (Button) findViewById(R.id.refresh);
        refresh.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				TextView sugarLevel = (TextView) findViewById(R.id.sugarLevel);
				try {
			        sugarLevel.setText(String.format("%.2f",estimator.getlastValue(0)));
			        } catch (Exception e) {
			        	sugarLevel.setText(e.getMessage());
					}
				
			}
		});
    }
    
    
    

}
