package sugar.control;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import sugar.control.core.BloodGlucoseEstimator;
import sugar.control.database.FoodDatabaseAdapter;
import sugar.control.utils.Food;

public class MainMenu extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        Button ShowSugarButton = (Button) findViewById(R.id.showsugar);
        ShowSugarButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Intent MainMenuIntent = new Intent(MainMenu.this,ShowSugar.class);
                startActivity(MainMenuIntent);
            }
        });
        
        Button InsertSugarButton = (Button) findViewById(R.id.insertsugar);
        InsertSugarButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Intent MainMenuIntent = new Intent(MainMenu.this,InsertSugar.class);
                startActivity(MainMenuIntent);
            }
        });
        
        Button CanEatButton = (Button) findViewById(R.id.caneat);
        CanEatButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
            	Intent MainMenuIntent = new Intent(MainMenu.this, FoodListActivity.class);
                startActivity(MainMenuIntent);
            }
        });
        
        
        Button SettingsButton = (Button) findViewById(R.id.settings);
        SettingsButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Intent MainMenuIntent = new Intent(MainMenu.this,Settings.class);
                startActivity(MainMenuIntent);
            }
        });
    }
}