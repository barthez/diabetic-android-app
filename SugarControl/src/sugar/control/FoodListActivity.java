
package sugar.control;

import android.app.ListActivity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Contacts.People;
import android.widget.SimpleCursorAdapter;
import sugar.control.database.DatabaseAdapter;

/**
 *
 * @author rial
 */
public class FoodListActivity extends ListActivity {

    private SimpleCursorAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        //Cursor c = getContentResolver().query(People.CONTENT_URI, null, null, null, null);
        //startManagingCursor(c);
        //String[] cols = new String[]{People.NAME};
        DatabaseAdapter dba = new DatabaseAdapter(this);
        dba.open();
        Cursor c = dba.getAllEntries();
        startManagingCursor(c);
        dba.close();
        String[] cols = null;
        //if(c.getColumnCount() != 0){
        //    cols = new String[c.getColumnCount()];
        //}
        cols = new String[2];
        cols[0] = "dupa";
        cols[1] = "DUPA";
        //int i=0;
        //for(c.moveToFirst();c.moveToNext();c.isAfterLast()){

            //cols[i++] = c.getString(c.getColumnIndex(DatabaseAdapter.FOOD_NAME));
        //}

        int[] names = new int[]{R.id.row_tv};
        adapter = new SimpleCursorAdapter(this, R.layout.list_item, c, cols, names);
        this.setListAdapter(adapter);
    }
}
