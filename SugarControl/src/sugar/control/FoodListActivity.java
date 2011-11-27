
package sugar.control;

import android.app.ListActivity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Contacts.People;
import android.widget.SimpleCursorAdapter;
import sugar.control.database.DatabaseAdapter;
import sugar.control.database.Food;

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
        //Cursor c = getContentResolver().query(People.CONTENT_URI, null, null, null, null);
        //startManagingCursor(c);
        //String[] cols = new String[]{People.NAME};
        DatabaseAdapter da = new DatabaseAdapter(this);
        
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

        
        da.open();


        String[] cols  = new String[]{DatabaseAdapter.FOOD_NAME};

        int[] names = new int[]{R.id.row_tv};
        adapter = new SimpleCursorAdapter(this, R.layout.list_item, c, cols, names);
        this.setListAdapter(adapter);
        da.close();
    }
}
