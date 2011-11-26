package sugar.control.database;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataHelper extends SQLiteOpenHelper {

    private static String DATABASE_DIR = "/data/data/suger.control/databases/"; //Ścieżka do katalogu gdzie przechowywane są bazy danych
    private static String DATABASE_NAME = "foods.sqlite"; //Nazwa pliku z bazą danych
    private static String DATABASE_ZIP = "foods.sqlite.zip"; //Nazwa pliku zip w którym znajduje się skompresowany plik z bazą danych
    private SQLiteDatabase db;
    private final Context context;

    public DataHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase arg0) {
//Implementację tej metody można pominąć
    }

    @Override
    public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
//Implementację tej metody można pominąć
    }

    /**
     * Zadaniem metody jest sprawdzenie czy istnieje plik z bazą danych
     * @return true jeżeli plik w katalogu istnieje,
     * false gdy nie istnieje - nie został jeszcze skopiowany z assets do katalogu w którym android przechowuje pliki baz danych
     */
    public boolean dataBaseExists() {
        File f = new File(DATABASE_DIR);
        if (!f.exists()) {
            f.mkdir();
        }

        boolean result = true;
        SQLiteDatabase db_test = null;

        try {
            db_test = SQLiteDatabase.openDatabase(DATABASE_DIR + DATABASE_NAME, null, SQLiteDatabase.OPEN_READONLY);
        } catch (Exception ex) {
            result = false;
        }

        if (db_test != null) {
            db_test.close();
        }


        return result;
    }

    /**
     * Metoda rozpakowuje plik z assets do katalogu gdzie Android trzyma bazy danych pod warunkiem, że już nie zostało to zrobione
     * @throws IOException
     */
    public void copyDataBase() throws IOException {
        if (!this.dataBaseExists()) {
            File f = new File(DATABASE_DIR);
            if (!f.exists()) {
                f.mkdir();
            }

            ZipInputStream zis = new ZipInputStream(context.getAssets().open(DATABASE_ZIP));
            ZipEntry entry;

            entry = zis.getNextEntry();

            int BUFFER = 2048;
            FileOutputStream fos = new FileOutputStream(DATABASE_DIR + DATABASE_NAME);
            BufferedOutputStream dest = new BufferedOutputStream(fos, BUFFER);
            int count;
            byte data[] = new byte[BUFFER];

            while ((count = zis.read(data, 0, BUFFER)) != -1) {
                dest.write(data, 0, count);
            }


            dest.flush();
            dest.close();
            zis.close();
        }
    }

    /**
     * Metoda otwiera bazę danych, po wywołaniu tej metody można wykonywać polecenie SQL
     * @return - true gdy otwarcie się powiodło
     */
    public boolean open() {
        boolean result = false;

        try {
            String myPath = DATABASE_DIR + DATABASE_NAME;
            db = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
            result = true;
        } catch (Exception ex) {
//Otwarcie bazy danych nie powiodło się}
        }

        return result;
    }

    public synchronized void close() {
        if (db != null) {
            db.close();
        }
        super.close();

    }

    /**
     * Wykonuje zapytanie SQL
     * @param query - zapytanie SQL
     * @return zwraca Stringa z rezultatem
     */
    public String executeQuery(String query) {
        String result = "";
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                result += cursor.getString(0) + ". " + cursor.getString(1) + " " + cursor.getString(2) + "\n";
            } while (cursor.moveToNext());
        }

        return result;
    }
}
