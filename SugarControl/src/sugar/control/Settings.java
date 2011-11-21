package sugar.control;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Settings extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
        
//        Button MainMenu = (Button) findViewById(R.id.backtomainmenu);
//        MainMenu.setOnClickListener(new OnClickListener() {
//            public void onClick(View v) {
//                Intent SettingsIntent = new Intent(Settings.this,MainMenu.class);
//                startActivity(SettingsIntent);
//            }
//        });
    }

}