package sugar.control;


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
		         
			        
			        double h0 = Double.parseDouble(b.getText().toString());
			        double h05 = Double.parseDouble(c.getText().toString());
			        double h1 = Double.parseDouble(d.getText().toString());
			        double h2 = Double.parseDouble(e.getText().toString());
			        double h3 = Double.parseDouble(f.getText().toString());
			        double h4 = Double.parseDouble(g.getText().toString());
			        double h5 = Double.parseDouble(h.getText().toString());
			        double h6 = Double.parseDouble(i.getText().toString());
			        
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
    
		
			
		}
	};
	
	
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
        
        Button MainMenu = (Button) findViewById(R.id.backtomainmenu);
        MainMenu.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Intent SettingsIntent = new Intent(Settings.this,MainMenu.class);
                startActivity(SettingsIntent);
            }
        });
        
        Button OK = (Button)findViewById(R.id.ok) ;
        OK.setOnClickListener(listenerOK);
        
      //Cancel Button
        final Button cancel = (Button) findViewById(R.id.button1);
        cancel.setOnClickListener(new View.OnClickListener() {
        public void onClick(View v) {
        // Perform action on click
     	   EditText clear1 = (EditText)findViewById(R.id.name);
     	   EditText clear2 = (EditText)findViewById(R.id.h0);
     	   EditText clear3 = (EditText)findViewById(R.id.h05);
     	   EditText clear4 = (EditText)findViewById(R.id.h1);
     	   EditText clear5 = (EditText)findViewById(R.id.h2);
     	   EditText clear6 = (EditText)findViewById(R.id.h3);
     	   EditText clear7 = (EditText)findViewById(R.id.EditText01);
     	   EditText clear8 = (EditText)findViewById(R.id.h5);
     	   EditText clear9 = (EditText)findViewById(R.id.h6);            
     	   
     	   clear1.setText("");
     	   clear2.setText("");
     	   clear3.setText("");
     	   clear4.setText("");
     	   clear5.setText("");
     	   clear6.setText("");
     	   clear7.setText("");
     	   clear8.setText("");
     	   clear9.setText("");
            
        }
         
     });
        
    }

}
