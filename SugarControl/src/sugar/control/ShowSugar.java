package sugar.control;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ShowSugar  extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.showsugar);
        
        Button MainMenu = (Button) findViewById(R.id.backtomainmenu);
        MainMenu.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Intent ShowSugarIntent = new Intent(ShowSugar.this,MainMenu.class);
                startActivity(ShowSugarIntent);
            }
        });
    }

}
