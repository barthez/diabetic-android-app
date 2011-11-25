package sugar.control;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import sugar.control.core.BloodGlucoseEstimator;

public class InsertSugar extends Activity {

  /** Called when the activity is first created. */
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.insertsugar);

//    Button MainMenu = (Button) findViewById(R.id.backtomainmenu);
//    MainMenu.setOnClickListener(new OnClickListener() {
//
//      public void onClick(View v) {
//        Intent InsertSugarIntent = new Intent(InsertSugar.this, MainMenu.class);
//        startActivity(InsertSugarIntent);
//      }
//    });
    
    
    Button estimation = (Button) findViewById(R.id.estimation);
    estimation.setOnClickListener(new OnClickListener() {

      public void onClick(View v) {
        TextView test = (TextView) findViewById(R.id.insertView);
        try {
          double [] bloodG= {80, 90, 105, 90, 80, 80, 80, 80};

          BloodGlucoseEstimator bge = new BloodGlucoseEstimator();

          bge.setGTTCurve(bloodG, 50);
          
          double [] wynik = bge.estimate(100, 30);
          test.setText(" ");
          
          for (int i = 0; i< wynik.length; i++) {
            test.append(String.valueOf(wynik[i]) + ", ");
          }
          
          test.append("OK!");
        } catch ( Exception ex ) {
          
          test.setText( (CharSequence) ex.toString() + ": " + ex.getMessage() );
        }
        
        
      }
    });
  }
}