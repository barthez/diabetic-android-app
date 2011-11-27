/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package sugar.control;

/**
 *
 * @author rial
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;


public class SetWeight extends Activity {

public OnClickListener listenerOK = new OnClickListener(){

		public void onClick(View v) {


			        EditText a = (EditText)findViewById(R.id.name);
			        EditText b = (EditText)findViewById(R.id.h);
			        EditText c = (EditText)findViewById(R.id.hh);

			        String name = a.getText().toString();
			        double h = Double.parseDouble(b.getText().toString());
			        double hh = Double.parseDouble(c.getText().toString());



		            Intent SettingsToShowIntent = new Intent(SetWeight.this,ShowSet.class);
	                startActivity(SettingsToShowIntent);


                    Intent ToShow = new Intent(SetWeight.this, ShowSet.class);

	                ToShow.putExtra("name","Imi�: "+name+"\n");
	                ToShow.putExtra("60",h);
	                ToShow.putExtra("120",hh);
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
                Intent SettingsIntent = new Intent(SetWeight.this,MainMenu.class);
                startActivity(SettingsIntent);
            }
        });

        Button OK = (Button)findViewById(R.id.ok) ;
        OK.setOnClickListener(listenerOK);
    }

}