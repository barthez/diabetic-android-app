package sugar.control;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class ShowSet extends Activity{

	private double h_0;
	private double h_1;
	private double h_05;
	private double h_2;
	private double h_3;
	private double h_4;
	private double h_5;
	private double h_6;
	
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show);
        String in = "";
        h_0=0.0;
        h_1=0.0;
        h_05=0.0;
        h_2=0.0;
        h_3=0.0;
        h_4=0.0;
        h_5=0.0;
        h_6=0.0;
        Bundle b=getIntent().getExtras();
        TextView show = (TextView) findViewById(R.id.show);
        if (b.containsKey("name")) in=b.getString("name");
        if (b.containsKey("0")) h_0 = b.getDouble("0");
        if (b.containsKey("1")) h_1 = b.getDouble("1");
        if (b.containsKey("05")) h_05 = b.getDouble("05");
        if (b.containsKey("2")) h_2 = b.getDouble("2");
        if (b.containsKey("3")) h_3 = b.getDouble("3");
        if (b.containsKey("4")) h_4 = b.getDouble("4");
        if (b.containsKey("5")) h_5 = b.getDouble("5");
        if (b.containsKey("6")) h_6 = b.getDouble("6");
        
        		
		show.setText(in+"Poziom cukru po:\n0min: "+h_0+"\n30min: "+h_05+"\n1godz.: "+h_1+"\n2godz: "+h_2+"\n3godz: "+h_3+"\n4godz: "+h_4+"\n5godz: "+h_5+"\n6godz: "+h_6);
		 
		 
		 Button MainMenu = (Button) findViewById(R.id.backtomainmenu);
	        MainMenu.setOnClickListener(new OnClickListener() {
	            public void onClick(View v) {
	                Intent ShowIntent = new Intent(ShowSet.this,MainMenu.class);
	                startActivity(ShowIntent);
	            }
	        });
      
    }
	
	
	
}


