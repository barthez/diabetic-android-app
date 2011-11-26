package sugar.control;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class ShowSet extends Activity{

	private double hour;
	private double hours;
	
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show);
        String in = "";
        hour=0.0;
        hours=0.0;
        Bundle b=getIntent().getExtras();
        TextView show = (TextView) findViewById(R.id.show);
        if (b.containsKey("name")) in=b.getString("name");
        if (b.containsKey("60")) hour = b.getDouble("60");
        if (b.containsKey("120")) hours = b.getDouble("120");
        
        
        		
		show.setText(in+"Poziom cukru po 60min.: "+hour+"\nPOziom cukru po 120min.: "+hours);
		 
		 
		 Button MainMenu = (Button) findViewById(R.id.backtomainmenu);
	        MainMenu.setOnClickListener(new OnClickListener() {
	            public void onClick(View v) {
	                Intent ShowIntent = new Intent(ShowSet.this,MainMenu.class);
	                startActivity(ShowIntent);
	            }
	        });
      
    }
	
	
	
}


