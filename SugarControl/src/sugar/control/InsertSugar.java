package sugar.control;

import sugar.control.core.BloodGlucoseEstimator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.RadioGroup;

public class InsertSugar  extends Activity {

	
public OnClickListener listenerOK = new OnClickListener(){
		
		public void onClick(View v) {
			
					RadioGroup phys = (RadioGroup)findViewById(R.id.physicalactivity);
					String physAnswer="nie wyszło";
					double physAnswerDouble = 0;
					switch(phys.getCheckedRadioButtonId())
					{case R.id.low: 
						physAnswer="niska";
						physAnswerDouble=1.1;
						break;
					case R.id.normal: 
						physAnswer="normalna";
						physAnswerDouble = 1.0;
						break;
					case R.id.high: 
						physAnswer="wysoka";
						physAnswerDouble=0.9;
						break;
					default:break;
					}
					EditText a = (EditText)findViewById(R.id.sugar);
					String control=(a.getText().toString());
			        if(control.equals(null) || control.equals("") || physAnswer=="nie wyszło" ){
			        	TextView con=(TextView)findViewById(R.id.cont);
			        	con.setText("Uzupełnij wszytkie pola!");
			        	return;
			        	}
			        
			        double sugarLev=0;
			        try{
			        	sugarLev = Double.parseDouble(a.getText().toString());
			        } catch(Exception e){
			        	TextView con=(TextView)findViewById(R.id.cont);
			        	con.setText("Poziom cukru musi być liczbą");
			        }
		           
			        BloodGlucoseEstimator bge = BloodGlucoseEstimator.getInstance();
			        bge.setActivCoff(physAnswerDouble);
			        bge.setGlucoseValue(sugarLev);
			        
	                
		            Intent InsertToShowIntent = new Intent(InsertSugar.this,ShowInsert.class);
	                startActivity(InsertToShowIntent);
	                
                    Intent InToShow = new Intent(InsertSugar.this, ShowInsert.class);
	          
	                InToShow.putExtra("ph","\nAktywność fizyczna: "+physAnswer);
	                InToShow.putExtra("sug",sugarLev);
	                
	                startActivityForResult(InToShow, 1);    
	                
					
			
		}
	};
	

	
	
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.insertsugar);
        
        
       Button OK = (Button)findViewById(R.id.ok) ;
       OK.setOnClickListener(listenerOK);
       
       
     //Cancel Button
       final Button cancel = (Button) findViewById(R.id.cancel);
       cancel.setOnClickListener(new View.OnClickListener() {
       public void onClick(View v) {
       // Perform action on click
    	   Intent SettingsIntent = new Intent(InsertSugar.this,MainMenu.class);
           startActivity(SettingsIntent);
       }
        
    });

}}
