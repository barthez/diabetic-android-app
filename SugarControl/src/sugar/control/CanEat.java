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

}