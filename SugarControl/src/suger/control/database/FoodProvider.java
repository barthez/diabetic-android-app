package suger.control.database;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import java.util.HashMap;

/**
 *
 * @author rial
 */
public class FoodProvider extends ContentProvider{

    private static HashMap<String, String> sFoodsProjectionMap;
    static{
        sFoodsProjectionMap = new HashMap<String, String>();
        String put = sFoodsProjectionMap.put(FoodProvaiderMetaData.FoodTableMetaData._ID, FoodProvaiderMetaData.FoodTableMetaData._ID);

    }

    @Override
    public boolean onCreate() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Cursor query(Uri arg0, String[] arg1, String arg2, String[] arg3, String arg4) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getType(Uri arg0) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Uri insert(Uri arg0, ContentValues arg1) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int delete(Uri arg0, String arg1, String[] arg2) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int update(Uri arg0, ContentValues arg1, String arg2, String[] arg3) {
        throw new UnsupportedOperationException("Not supported yet.");
    }


}
