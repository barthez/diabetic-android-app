package sugar.control.database;

import sugar.control.utils.Food;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 *
 * @author rial
 */
public class FoodDatabaseAdapter {

    //public static final String AUTHORITY = "suger.control.provider.FoodProvider";
    public static final String DATABASE_NAME = "food.db";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "foods";
    // Glowna tabela
    //nazwa kolumny będącej indeksem naszej tabeli
    public static final String KEY_ID = "_id";
    public static final String ID_OPTIONS = "INTEGER PRIMARY KEY AUTOINCREMENT";
    // string
    public static final String FOOD_NAME = "name";
    public static final String NAME_OPTIONS = "TEXT NOT NULL";
    // int
    public static final String FOOD_IG = "IG";
    public static final String IG_OPTIONS = "DOUBLE";
    // int
    public static final String FOOD_CARBON = "carbo";
    public static final String CARBON_OPTIONS = "DOUBLE";

    //Stworzenie tabeli
    private static final String DB_CREATE = "create table "
            + TABLE_NAME + " ("
            + KEY_ID + " " + ID_OPTIONS + ", "
            + FOOD_NAME + " " + NAME_OPTIONS + ", "
            + FOOD_IG + " " + IG_OPTIONS + ", "
            + FOOD_CARBON + " " + CARBON_OPTIONS
            + ");";

    //Zmienna do przechowywania bazy danych
    private SQLiteDatabase db;

    //Kontekst aplikacji korzystającej z bazy
    private final Context context;

    //Helper od otwierania i aktualizacji bazy danych
    private DatabaseHelper myDatabaseHelper;

    //C-tor
    public FoodDatabaseAdapter(Context _context) {
        context = _context;
        myDatabaseHelper = new DatabaseHelper(_context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //Otwieramy połączenie z bazą danych
    public FoodDatabaseAdapter open() {
        db = myDatabaseHelper.getWritableDatabase();
        return this;
    }

    //Zamykamy połączenie z bazą danych
    public void close(){
        db.close();
    }

    public long insertFood(Food food) {
        //Tworzymy obiekt nowego "wiersza"
        ContentValues newFoodValues = new ContentValues();
        //Wypełniamy wszystkie pola wiersza
        newFoodValues.put(FOOD_NAME, food.getFoodName());
        newFoodValues.put(FOOD_IG, food.getIGLevel());
        newFoodValues.put(FOOD_CARBON, food.getCarbonLevel());
        //Wstawiamy wiersz do bazy
        return db.insert(TABLE_NAME, null, newFoodValues);
    }

    public boolean updateFood(long index, Food food) {
        //Warunek wstawiany do klauzuli WHERE
        String where = KEY_ID + "=" + index;
        //Tak samo jak przy metodzie insert
        ContentValues updateFoodValues = new ContentValues();
        updateFoodValues.put(FOOD_NAME, food.getFoodName());
        updateFoodValues.put(FOOD_IG, food.getIGLevel());
        updateFoodValues.put(FOOD_CARBON, food.getCarbonLevel());
        //Aktualizujemy dane wiersza zgodnego ze zmienną where
        return db.update(TABLE_NAME, updateFoodValues, where, null) > 0;
    }

    public Cursor getAllEntries() {
        String[] columns = {KEY_ID, FOOD_NAME, FOOD_IG, FOOD_CARBON};
        return db.query(TABLE_NAME, columns, null, null, null, null, null);
    }

    public Food getEntry(long index) {
        String[] columns = {KEY_ID, FOOD_NAME, FOOD_IG, FOOD_CARBON};
        String where = "KEY_ID=" + index;
        Cursor cursor = db.query(true, TABLE_NAME, columns, where, null, null, null, null, null);

        Food food = new Food(cursor.getString(cursor.getColumnIndex(FOOD_NAME)),
                        cursor.getDouble(cursor.getColumnIndex(FOOD_IG)),
                        cursor.getDouble(cursor.getColumnIndex(FOOD_CARBON)));

        return food;
    }

    public boolean deleteFood(long index) {
        String where = KEY_ID + "=" + index;
        return db.delete(TABLE_NAME, where , null) > 0;
    }

    public void deleteAll() {
        db.delete(TABLE_NAME, "1", null);
    }

    //Klasa helpera do otwierania i aktualizacji bazy danych
    private static class DatabaseHelper extends SQLiteOpenHelper {

        public DatabaseHelper(Context context, String name,
        CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase _db) {
            _db.execSQL(DB_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase _db, int oldVer, int newVer) {
            _db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(_db);

            Log.w("ListView DatabaseAdapter","Aktualizacja bazy z wersji " + oldVer +
                    " do " + newVer +
                    ". Wszystkie dane zostaną utracone.");
        }
    }

}
