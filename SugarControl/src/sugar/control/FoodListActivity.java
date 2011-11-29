
package sugar.control;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Contacts.People;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;
import sugar.control.core.GlycemicIndexCalculator;
import sugar.control.database.FoodDatabaseAdapter;
import sugar.control.utils.Food;
import sugar.control.utils.FoodArrayAdapter;

/**
 *
 * @author rial
 */
public class FoodListActivity extends ListActivity {

//    private SimpleCursorAdapter adapter;
    private List<Food> foodList;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.foodlist);
//        final Context cx = getApplicationContext();


/////////////////////////////////////////////////////////////////////////////////////////////
//////////   Wazna informacja dla osoby ktora bedzie sprzatac w ostecznej wersji,  //////////
//////////   po uruchomieniu po raz ostatni testowo, wyrzucic caly kod, ktory ma   //////////
//////////   cokolwiek wspolnego FoodDatabaseAdapter, czyli odtad -->>             //////////
/////////////////////////////////////////////////////////////////////////////////////////////


        FoodDatabaseAdapter da = new FoodDatabaseAdapter(this);
        da.open();
        da.deleteAll();
        String[] foodNames = new String[]{"babka z polewą truskawkową",
            "bezy", "biszkopt", "bułeczki drożdżowe", "ciasto bananowe bez cukru",
            "ciasto bananowe z cukrem", "ciasto czekoladowe z polewą czekoladową",
            "ciasto waniliowe z polewą waniliową", "crumpet (rodzaj naleśnika)",
            "donaty i pączki", "mufinki (średnio)", "naleśniki", "paszteciki",
            "placek z owocami", "rogalik", "tort", "wafle", "ciasteczka owsiane",
            "ciateczka ryżowe", "Digestives", "LU petitki", "Petit Beurre (LU)",
            "wafle ryżowe", "wafle waniliowe", "50g maltozy", "fruktoza", "glukoza", 
            "laktoza", "miód", "xylitol", "stevia", "syrop kukurydziany", "maltitol", 
            "cukier trzcinowy", "nektar z kaktusa niebieskiej agawy 90% fruktozy", 
            "sacharoza", "makaron imstant (zalewany wrzątkiem)", "makaron rurki",
            "makaron ryżowy", "makaron z serem", "Ravioli z mięsem", "Spaghetti z mąki białej",
            "Spaghetti z mąki białej gotowane 10-15 min", "Spaghetti z mąki białej gotowane 20 min",
            "Spaghetti z mąki białej gotowane 5 min", "Spaghetti z mąki pełnowartościowej (razowe)",
            "jogurt 0% tłuszczu", "jogurt naturalny", "lody", "mleko pełne (3% tłuszczu)",
            "mleko pełne z otrębami pszennymi (20g)", "mleko skondensowane, słodzone",
            "mleko sojowe 1.5% tłuszczu, 120mg wapnia", "mleko sojowe 3% tłuszczu, 0mg wapnia",
            "mleko sojowe 3% tłuszczu, 120mg wapnia", "mleko zsiadle", "napój z mleka sojowego",
            "Coca Cola", "Fanta orange", "Isostar", "ananas", "arbuz", "banany",
            "brzoskwinie z puszki w lekkim syropie", "brzoskwinie z puszki w lekkim syropie świeże",
            "brzoskwinie z puszki w mocnym syropie", "brzoskwinie z puszki w naturalnym syropie",
            "daktyle suszone", "dżem truskawowy", "grejpfrut", "gruszki",
            "gruszki w puszce w sosie własnym", "jabłka", "jablka suszone", "kiwi", "mango",
            "marmolada z pomarańczy", "morele", "morele suszone", "morele w puszce w lekkim syropie",
            "owoc chlebowca", "papaja", "pomarańcze", "rodzynki", "rodzynki sultanki", "śliwki",
            "śliwki suszone", "truskawki", "winogrona", "wiśnie", "bagietka", "bajgiel",
            "biały chleb turecki", "chleb gryczany", "chleb owsiany otrębowy", "chleb pszenny",
            "gruboziarniste pieczywo jęczmienne (50% ziarna)",
            "gruboziarniste pieczywo jęczmienne (70% ziarna)",
            "gruboziarnisty chleb jęzmienno -słonecznikowy", "pełnoziarnisty chleb turecki",
            "pełnoziarnisty chleb żytni", "pumpernikiel pełnoziarnisty"};

        double[] foodIG = new double[]{73, 67, 54, 92, 55, 47, 38, 46, 67, 69,
            67, 85, 59, 76, 42, 87, 76, 57, 78, 59, 49, 51, 64, 77, 105, 19, 99, 
            46, 55, 7, 3, 75, 26, 87, 11, 68, 47, 47, 40, 64, 39, 42, 44, 61,
            38, 37, 27, 36, 61, 27, 27, 61, 44, 44, 36, 32, 32, 58, 68, 70, 59,
            72, 52, 52, 42, 58, 38, 103, 51, 25, 38, 44, 38, 29, 53, 51, 48, 57,
            31, 64, 68, 59, 42, 64, 56, 39, 29, 40, 46, 22, 95, 72, 87, 47, 47,
            70, 46, 34, 57, 49, 58, 46};

        double[] foodCarbo = new double[]{0.68, 0.58, 0.53, 0.36, 0.36, 0.48,
            0.47, 0.57, 0.46, 0.38, 0.54, 0.51, 0.46, 0.49, 0.52, 0.58, 0.37,
            0.6, 0.84, 0.64, 0.7, 0.72, 0.64, 0.72, 1, 1, 1, 1, 0.72, 0, 0, 0,
            0, 0, 0.8, 1, 0.22, 0.27, 0.22, 0.28, 0.21, 0.26, 0.27, 0.24, 0.27,
            0.23, 0.12, 0.05, 0.26, 0.05, 0.05, 0.54, 0.07, 0.07, 0.07, 0.05,
            0.09, 0.1, 0.14, 0.07, 0.11, 0.05, 0.2, 0.15, 0.09, 0.13, 0.09, 0.67,
            0.67, 0.09, 0.09, 0.09, 0.13, 0.57, 0.1, 0.14, 0.67, 0.08, 0.47, 0.16,
            0.23, 0.14, 0.09, 0.73, 0.75, 0.1, 0.55, 0.03, 0.15, 0.1, 0.5, 0.5,
            0.57, 0.7, 0.6, 0.47, 0.67, 0.67, 0.37, 0.53, 0.47, 0.37};

/////////////////////////////////////////////////////////////////////////////////////////////
//////////   -->> Dotad! (jednak nie, bo wtedy sie wszystko posypie...)                                                //////////
/////////////////////////////////////////////////////////////////////////////////////////////

        foodList = new ArrayList<Food>();
        for(int i=0; i<foodNames.length; ++i){
            Food f = new Food(foodNames[i], foodIG[i], foodCarbo[i]);
            da.insertFood(f);
            foodList.add(f);
        }
 
        da.close();

        ArrayAdapter<Food> adapter = new FoodArrayAdapter(this,foodList);
        this.setTitle("Wprowadź wagę w gramach");
		setListAdapter(adapter);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){

        super.onCreateOptionsMenu(menu);



        menu.add(0, 1, 1, "Dalej!");            // to menu tez bedzie przeniesione!
        menu.add(0, 2, 2,  "Stworz posilek!");


        return true;
    }

    
    public View getView(final int position, View convertView, ViewGroup parent){
        
        return null;
    }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (requestCode == 1) {
      if (resultCode == 0) {
        
      } else {
        Toast.makeText(getApplicationContext(), "Wybierz mniej słodkie danie.", Toast.LENGTH_SHORT).show();
      }
    }
  }
    
    
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        switch(item.getItemId()){
            case 1:
                GlycemicIndexCalculator g = GlycemicIndexCalculator.getInstance();
                boolean selected = false;
            	for(int i=0;i<this.foodList.size();i++) {
            		if(foodList.get(i).getWeight()>0) {
            			g.addFood(foodList.get(i));
            			selected = true;
            		}
            	}
            		
            	if (selected==true) {
            		Intent MainMenuIntent = new Intent(FoodListActivity.this,SugarLevelGraph.class);
                    startActivityForResult(MainMenuIntent, 1);
                    return true;
            	}
            	else {
            		return false;
            	}
            	
        }

        return false;
    }


    
}
