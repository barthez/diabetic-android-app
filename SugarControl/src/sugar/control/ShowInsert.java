package sugar.control;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class ShowInsert extends Activity{

	private double sugarLevel;

	
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show);
        String in = "";
        sugarLevel=0.0;
      
        Bundle b=getIntent().getExtras();
        TextView show = (TextView) findViewById(R.id.show);
        if (b.containsKey("ph")) in=b.getString("ph");
        if (b.containsKey("sug")) sugarLevel = b.getDouble("sug");
            
        
		
		show.setText("Aktualny poziom cukru: "+sugarLevel+in);
		 
		 
		 Button MainMenu = (Button) findViewById(R.id.backtomainmenu);
	        MainMenu.setOnClickListener(new OnClickListener() {
	            public void onClick(View v) {
	                Intent ShowInsertIntent = new Intent(ShowInsert.this,MainMenu.class);
	                startActivity(ShowInsertIntent);
	            }
	        });
      
    }
	
	
	
}


