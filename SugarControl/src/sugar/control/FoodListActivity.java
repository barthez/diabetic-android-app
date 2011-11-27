
package sugar.control;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Contacts.People;
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
import sugar.control.database.FoodDatabaseAdapter;
import sugar.control.utils.Food;

/**
 *
 * @author rial
 */
public class FoodListActivity extends ListActivity {

    private SimpleCursorAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.foodlist);
        final Context cx = getApplicationContext();
        //Cursor c = getContentResolver().query(People.CONTENT_URI, null, null, null, null);
        //startManagingCursor(c);
        //String[] cols = new String[]{People.NAME};

        
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
            "wafle ryżowe", "wafle waniliowe"};
        double[] foodIG = new double[]{73, 67, 54, 92, 55, 47, 38, 46, 67, 69,
            67, 85, 59, 76, 42, 87, 76, 57, 78, 59, 49, 51, 64, 77};
        double[] foodCarbo = new double[]{0.68, 0.58, 0.53, 0.36, 0.36, 0.48,
            0.47, 0.57, 0.46, 0.38, 0.54, 0.51, 0.46, 0.49, 0.52, 0.58, 0.37,
            0.6, 0.84, 0.64, 0.7, 0.72, 0.64, 0.72};

        for(int i=0; i<foodNames.length; ++i){
            Food f = new Food(foodNames[i], foodIG[i], foodCarbo[i]);
            da.insertFood(f);
        }


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


        Cursor c = da.getAllEntries();

        String[] cols  = new String[]{FoodDatabaseAdapter.FOOD_NAME};

        int[] names = new int[]{R.id.row_tv};
        adapter = new SimpleCursorAdapter(this, R.layout.list_item, c, cols, names);
        ListView lv = getListView();
        lv.setChoiceMode(lv.CHOICE_MODE_MULTIPLE);
        lv.setItemChecked(2, true);


        this.setListAdapter(adapter);

        da.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){

        super.onCreateOptionsMenu(menu);



        menu.add(0, 1, 1, "Zjedz!");            // to menu tez bedzie przeniesione!
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

                
                
                ListView lv = getListView();    
                             
                long[] ids = lv.getCheckItemIds();
                for(long l: ids){
                   Food pomidor = new Food(Long.toString(l), l, l);
                   FoodDatabaseAdapter da = new FoodDatabaseAdapter(this);
                   da.open();
                   da.insertFood(pomidor);
                   da.close();

                }

//                if(ids.length != 0){
//                    Intent MainMenuIntent = new Intent(FoodListActivity.this,ShowSugar.class);  // tu ma przechodzic gdzie indziej ostatecznie!
//                    startActivity(MainMenuIntent);
//                }
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
