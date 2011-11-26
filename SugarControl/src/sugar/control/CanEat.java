package sugar.control;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class CanEat extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.caneat);

        /*Button DrawGraph = (Button) findViewById(R.id.drawgraph);
        DrawGraph.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                startGraphActivity(SugarLevelGraph.class);
            }
        });*/

         Button DrawGraph = (Button) findViewById(R.id.drawgraph);
        DrawGraph.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Intent CanEatIntent = new Intent(CanEat.this,SugarLevelGraph.class);
                startActivity(CanEatIntent);
            }
        });


    }

    private void startGraphActivity(Class<? extends Activity> activity) {
		Intent intent = new Intent(CanEat.this, activity);
		/*if (((RadioButton) findViewById(R.id.radio_bar)).isChecked()) {
			if (activity == AdvancedMultipleSeriesGraph.class) {
				Toast.makeText(this, "Bar Charts are not implemented for multiple series, yet.", Toast.LENGTH_LONG).show();
				return;
			}
			intent.putExtra("type", "bar");
		} else {
			intent.putExtra("type", "line");
		}*/
                intent.putExtra("type", "line");
		startActivity(intent);
    }

}