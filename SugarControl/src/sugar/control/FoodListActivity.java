
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
        
        Food banan = new Food("banan", 1, 2);
        Food truskawka = new Food("truskawka", 3, 4);
        Food sliwka = new Food("sliwka", 5, 6);
        Food czekolada = new Food("czekolada", 7, 8);
        Food pomidor = new Food("pomidor", 9, 10);

        da.open();
        
        da.deleteAll();
        da.insertFood(banan);
        da.insertFood(truskawka);
        da.insertFood(sliwka);
        da.insertFood(czekolada);
        da.insertFood(pomidor);


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

                if(ids.length != 0){
                    Intent MainMenuIntent = new Intent(FoodListActivity.this,ShowSugar.class);  // tu ma przechodzic gdzie indziej ostatecznie!
                    startActivity(MainMenuIntent);
                }
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
