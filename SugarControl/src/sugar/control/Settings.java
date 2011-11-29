package sugar.control;


import sugar.control.core.BloodGlucoseEstimator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
//import android.widget.TextView;
import android.widget.Toast;
import android.content.Context;


public class Settings extends Activity {
	
	private double h0;
	private double h05;
	private double h1;
	private double h2;
	private double h3;
	private double h4;
	private double h5;
	private double h6;
	
public OnClickListener listenerOK = new OnClickListener(){

		
		public void onClick(View v) {
			

			        EditText a = (EditText)findViewById(R.id.name);	
			        EditText b = (EditText)findViewById(R.id.h0);	
			        EditText c = (EditText)findViewById(R.id.h05);
			        EditText d = (EditText)findViewById(R.id.h1);	
			        EditText e = (EditText)findViewById(R.id.h2);	
			        EditText f = (EditText)findViewById(R.id.h3);
			        EditText g = (EditText)findViewById(R.id.EditText01);	
			        EditText h = (EditText)findViewById(R.id.h5);
			        EditText i = (EditText)findViewById(R.id.h6);
		       
			        String name = a.getText().toString();
			        String bb = b.getText().toString();
			        String cc = c.getText().toString();
			        String dd = d.getText().toString();
			        String ee = e.getText().toString();
			        String ff = f.getText().toString();
			        String gg = g.getText().toString();
			        String hh = h.getText().toString();
			        String ii = i.getText().toString();
			               
			        
			        
			        
			        

			        if(name.equals(null) ||name.equals("") || bb.equals(null) ||bb.equals("") || cc.equals(null) ||cc.equals("") 
			        		|| dd.equals(null) ||dd.equals("") || ee.equals(null) ||ee.equals("") 
			        		|| ff.equals(null) ||ff.equals("") || gg.equals(null) ||gg.equals("") 
			        		|| hh.equals(null) ||hh.equals("") || ii.equals(null) ||ii.equals("") )
			        {
			        	CharSequence text = "Uzupełnij wszystkie pola";
			            Context context = getApplicationContext();
			            int duration = Toast.LENGTH_LONG;
			             
			            Toast toast = Toast.makeText(context,text,duration);
			            toast.show();
			        	
			        	
			        	//TextView control=(TextView)findViewById(R.id.cont);
			        	//control.setText("Uzupe�nij wszytkie pola!");
			        	return;
			        	}
		         
			        try {
				         h0 = Double.parseDouble(b.getText().toString());
				         h05 = Double.parseDouble(c.getText().toString());
				         h1 = Double.parseDouble(d.getText().toString());
				         h2 = Double.parseDouble(e.getText().toString());
				         h3 = Double.parseDouble(f.getText().toString());
				         h4 = Double.parseDouble(g.getText().toString());
				         h5 = Double.parseDouble(h.getText().toString());
				         h6 = Double.parseDouble(i.getText().toString());
			        } catch (Exception e1){
			        	CharSequence text = "Wpisz tylko wartości liczbowe";
			            Context context = getApplicationContext();
			            int duration = Toast.LENGTH_LONG;
			             
			            Toast toast = Toast.makeText(context,text,duration);
			            toast.show();
			        }
			        
		            Intent SettingsToShowIntent = new Intent(Settings.this,ShowSet.class);
	                startActivity(SettingsToShowIntent);
	                
	                	                
                    Intent ToShow = new Intent(Settings.this, ShowSet.class);
      	          
	                ToShow.putExtra("name","Imię: "+name+"\n");
	                ToShow.putExtra("0",h0);
	                ToShow.putExtra("05",h05);
	                ToShow.putExtra("1",h1);
	                ToShow.putExtra("2",h2);
	                ToShow.putExtra("3",h3);
	                ToShow.putExtra("4",h4);
	                ToShow.putExtra("5",h5);
	                ToShow.putExtra("6",h6);
	                startActivityForResult(ToShow, 1);
	                
	                BloodGlucoseEstimator bge = BloodGlucoseEstimator.getInstance();
	                double []data = {h0,h05,h1,h2,h3,h4,h5,h6};
	                bge.setGTTCurve(data, 50);
    
		}
	};
	
	
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
        

        Button OK = (Button)findViewById(R.id.ok) ;
        OK.setOnClickListener(listenerOK);
        
      //Cancel Button
        final Button cancel = (Button) findViewById(R.id.button1);
        cancel.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Intent SettingsIntent = new Intent(Settings.this,MainMenu.class);
                startActivity(SettingsIntent);
            }
        });
        
    }

}
