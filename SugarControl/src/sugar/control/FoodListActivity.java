
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
        //Cursor c = getContentResolver().query(People.CONTENT_URI, null, null, null, null);
        //startManagingCursor(c);
        //String[] cols = new String[]{People.NAME};

        
//        FoodDatabaseAdapter da = new FoodDatabaseAdapter(this);
//        da.open();
//        da.deleteAll();
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
            "mleko sojowe 3% tłuszczu, 120mg wapnia", "mleko zsiadle", "napój z mleka sojowego"};

        double[] foodIG = new double[]{73, 67, 54, 92, 55, 47, 38, 46, 67, 69,
            67, 85, 59, 76, 42, 87, 76, 57, 78, 59, 49, 51, 64, 77, 105, 19, 99, 
            46, 55, 7, 3, 75, 26, 87, 11, 68, 47, 47, 40, 64, 39, 42, 44, 61,
            38, 37, 27, 36, 61, 27, 27, 61, 44, 44, 36, 32, 32};

        double[] foodCarbo = new double[]{0.68, 0.58, 0.53, 0.36, 0.36, 0.48,
            0.47, 0.57, 0.46, 0.38, 0.54, 0.51, 0.46, 0.49, 0.52, 0.58, 0.37,
            0.6, 0.84, 0.64, 0.7, 0.72, 0.64, 0.72, 1, 1, 1, 1, 0.72, 0, 0, 0,
            0, 0, 0.8, 1, 0.22, 0.27, 0.22, 0.28, 0.21, 0.26, 0.27, 0.24, 0.27,
            0.23, 0.12, 0.05, 0.26, 0.05, 0.05, 0.54, 0.07, 0.07, 0.07, 0.05,
            0.09};

        foodList = new ArrayList<Food>();
        for(int i=0; i<foodNames.length; ++i){
            Food f = new Food(foodNames[i], foodIG[i], foodCarbo[i]);
//            da.insertFood(f);
            foodList.add(f);
        }
        
        ArrayAdapter<Food> adapter = new FoodArrayAdapter(this,foodList);
        this.setTitle("Wprowadź wagę w gramach");
		setListAdapter(adapter);



//        Food banan = new Food("banan", 1, 2);
//        Food truskawka = new Food("truskawka", 3, 4);
//        Food sliwka = new Food("sliwka", 5, 6);
//        Food czekolada = new Food("czekolada", 7, 8);
//        Food pomidor = new Food("pomidor", 9, 10);
//
//
//
//        da.deleteAll();
//        da.insertFood(banan);
//        da.insertFood(truskawka);
//        da.insertFood(sliwka);
//        da.insertFood(czekolada);
//        da.insertFood(pomidor);


//        Cursor c = da.getAllEntries();
//
//        String[] cols  = new String[]{FoodDatabaseAdapter.FOOD_NAME};
//
//        int[] names = new int[]{R.id.row_tv};
//        adapter = new SimpleCursorAdapter(this, R.layout.list_item, c, cols, names);
//        ListView lv = getListView();
//        lv.setChoiceMode(lv.CHOICE_MODE_MULTIPLE);
//        lv.setItemChecked(2, true);
//
//
//        this.setListAdapter(adapter);

//        da.close();
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
    public boolean onOptionsItemSelected(MenuItem item){

        switch(item.getItemId()){
            case 1:
                GlycemicIndexCalculator g = GlycemicIndexCalculator.getInstance();
                boolean selected = false;
            	for(int i=0;i<this.foodList.size();i++) {
            		if(foodList.get(i).isSelected()==true) {
            			g.addFood(foodList.get(i));
            			selected = true;
            		}
            	}
            		
            	if (selected==true) {
            		Intent MainMenuIntent = new Intent(FoodListActivity.this,SugarLevelGraph.class);
                    startActivity(MainMenuIntent);
                    return true;
            	}
            	else {
            		return false;
            	}
            	
        }

        return false;
    }


    
}
