
package sugar.control;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import suger.control.database.DataHelper;

public class Main extends Activity {
/** Called when the activity is first created. */
@Override
public void onCreate(Bundle savedInstanceState)
{
super.onCreate(savedInstanceState);
setContentView(R.layout.main);

String out ="";

TextView tv = (TextView) this.findViewById(R.id.estimation);
DataHelper dh = new DataHelper(this);

out += "Czy istnieje baza danych? " + dh.dataBaseExists()+"\n";
try
{
dh.copyDataBase();
out += "Czy istnieje baza danych po skopiowaniu? " + dh.dataBaseExists()+"\n";
out +="Rezultat otworzenia bazy danych: " + dh.open()+"\n";
out += "Wynik zapytania \n" + dh.executeQuery("SELECT * FROM books limit 3;");
    System.out.println(out);
} catch (Exception ex)
{
out += ex.toString() +"\n";
}

tv.setText(out);

}
}