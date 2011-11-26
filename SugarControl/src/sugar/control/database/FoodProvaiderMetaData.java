package sugar.control.database;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 *
 * @author rial
 */
public class FoodProvaiderMetaData {

    public static final String AUTHORITY = "suger.control.provider.FoodProvider";
    public static final String DATABASE_NAME = "food.db";
    public static final int DATABASE_VERSION = 1;
    public static final String FOODS_TABLE_NAME = "foods";

    private FoodProvaiderMetaData() {}

    public static final class FoodTableMetaData implements BaseColumns{

        private FoodTableMetaData() {}
        public static final String TABLE_NAME = "foods";

        public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/foods");
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.suger.control.food";
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.suger.control.food";

        public static final String DEFAULT_SORT_ORDER = "modified DESC";

        // Glowna tabela
        // string
        public static final String FOOD_NAME = "name";

        // int
        public static final String FOOD_QUANTITY = "quantity";

    }

}
