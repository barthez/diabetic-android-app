/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package sugar.control.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.format.Time;
import sugar.control.utils.Survey;

/**
 *
 * @author rial
 */
public class SurveyDatabaseAdapter {

    public static final String DATABASE_NAME = "surveys.db";
    public static final int DATABASE_VERSION = 1;

    public static final String SURVEY_TABLE_NAME = "survey";
    // Glowna tabela pomiarów
    //nazwa kolumny będącej indeksem naszej tabeli
    public static final String SURVEY_KEY_ID = "_id";
    public static final String SURVEY_KEY_ID_OPTIONS = "INTEGER PRIMARY KEY AUTOINCREMENT";
    // long
    public static final String SURVEY_DATE = "survey_date";
    public static final String SURVEY_DATE_OPTIONS = "NUMERIC";

    //Stworzenie tabeli
    private static final String CREATE_SURVEY_TABLE = "create table "
            + SURVEY_TABLE_NAME + " ("
            + SURVEY_KEY_ID + " " + SURVEY_KEY_ID_OPTIONS + ", "
            + SURVEY_DATE + " " + SURVEY_DATE_OPTIONS + ", "
            + ");";

    public static final String RESULTS_TABLE_NAME = "results";
    // Glowna tabela pomiarów
    //nazwa kolumny będącej indeksem naszej tabeli
    public static final String RESULTS_SURVEY_ID = "_id";
    public static final String RESULTS_SURVEY_ID_OPTIONS = "INTEGER";
    // int
    public static final String RESULT_NUMBER = "result_number";
    public static final String RESULT_NUMBER_OPTIONS = "INTEGER";
    // double
    public static final String RESULT_VALUE = "result_value";
    public static final String RESULT_VALUE_OPTIONS = "DOUBLE";

    //Stworzenie tabeli
    private static final String CREATE_RESULTS_TABLE = "create table "
            + RESULTS_TABLE_NAME + " ("
            + RESULTS_SURVEY_ID + " " + RESULTS_SURVEY_ID_OPTIONS + ", "
            + RESULT_NUMBER + " " + RESULT_NUMBER_OPTIONS + ", "
            + RESULT_VALUE + " " + RESULT_VALUE_OPTIONS + ", "
            + ");";

    //Zmienna do przechowywania bazy danych
    private SQLiteDatabase db;

    //Kontekst aplikacji korzystającej z bazy
    private final Context context;

    //Helper od otwierania i aktualizacji bazy danych
    private DatabaseHelper myDatabaseHelper;

    //C-tor
    public SurveyDatabaseAdapter(Context _context) {
        context = _context;
        myDatabaseHelper = new DatabaseHelper(_context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //Otwieramy połączenie z bazą danych
    public SurveyDatabaseAdapter open() {
        db = myDatabaseHelper.getWritableDatabase();
        return this;
    }

    //Zamykamy połączenie z bazą danych
    public void close(){
        db.close();
    }

    public void saveSurvey(Time time, double[] results){
        long date = time.toMillis(true);
        Survey survey = new Survey(date, results);
        insertSurvey(survey);
    }

    public Time getTimeOfLastSurvey(){
        Cursor surveysCursor = getAllSurveyEntries();
        surveysCursor.moveToLast();
        
        Time time = null;
        time.set(surveysCursor.getLong(surveysCursor.getColumnIndex(SURVEY_DATE)));
        
        return time;
    }

    public double[] getResultOfLastSurvey(){
        Cursor surveysCursor = getAllSurveyEntries();
        surveysCursor.moveToLast();
        int lastSurveyID = surveysCursor.getInt(surveysCursor.getColumnIndex(SURVEY_KEY_ID));

        String[] resultsColumns = {RESULTS_SURVEY_ID, RESULT_NUMBER, RESULT_VALUE};
        String resultsWhere = RESULTS_SURVEY_ID + "=" + lastSurveyID;
        Cursor resultsCursor = db.query(true, RESULTS_TABLE_NAME, resultsColumns, resultsWhere, null, null, null, null, null);

        double[] results = new double[resultsCursor.getCount()];
        int i=0;
        for(resultsCursor.moveToFirst(); !resultsCursor.isAfterLast(); resultsCursor.moveToNext()){
            results[i++] = resultsCursor.getDouble(resultsCursor.getColumnIndex(RESULT_VALUE));
        }

        return results;

    }

    public double[] getResultByTime(Time time){
        long date = time.toMillis(true);
        int setSurveyID = 0;
        Cursor surveysCursor = getAllSurveyEntries();
        for(surveysCursor.moveToFirst(); !surveysCursor.isAfterLast(); surveysCursor.moveToNext()){
            if(surveysCursor.getLong(surveysCursor.getColumnIndex(SURVEY_DATE)) == date){
                setSurveyID = surveysCursor.getInt(surveysCursor.getColumnIndex(SURVEY_KEY_ID));
            }
        }

        String[] resultsColumns = {RESULTS_SURVEY_ID, RESULT_NUMBER, RESULT_VALUE};
        String resultsWhere = RESULTS_SURVEY_ID + "=" + setSurveyID;
        Cursor resultsCursor = db.query(true, RESULTS_TABLE_NAME, resultsColumns, resultsWhere, null, null, null, null, null);

        double[] results = new double[resultsCursor.getCount()];
        int i=0;

        for(resultsCursor.moveToFirst(); !resultsCursor.isAfterLast(); resultsCursor.moveToNext()){
            results[i++] = resultsCursor.getDouble(resultsCursor.getColumnIndex(RESULT_VALUE));
        }
        
        return results;
    }

    private long insertSurvey(Survey survey) {
        ContentValues newSurvey = new ContentValues();
        newSurvey.put(SURVEY_DATE, survey.getTime());
        long newSurveyID = db.insert(SURVEY_TABLE_NAME, null, newSurvey);
        double[] results = survey.getResults();
        for(int i=0; i<survey.getResults().length; i++){
            ContentValues newResult = new ContentValues();
            newResult.put(RESULTS_SURVEY_ID, newSurveyID);
            newResult.put(RESULT_NUMBER, (i+1));
            newResult.put(RESULT_VALUE, results[i]);
            db.insert(RESULTS_TABLE_NAME, null, newResult);
        }
        return newSurveyID;
    }


// mozna przerobic na updateSurvey
//
//    public boolean updateFood(long index, ChosenFood food) {
//        //Warunek wstawiany do klauzuli WHERE
//        String where = KEY_ID + "=" + index;
//        //Tak samo jak przy metodzie insert
//        ContentValues updateFoodValues = new ContentValues();
//        updateFoodValues.put(FOOD_NAME, food.getFoodName());
//        updateFoodValues.put(FOOD_IG, food.getIGLevel());
//        updateFoodValues.put(FOOD_CARBON, food.getCarbonLevel());
//        //Aktualizujemy dane wiersza zgodnego ze zmienną where
//        return db.update(TABLE_NAME, updateFoodValues, where, null) > 0;
//    }


    private Cursor getAllSurveyEntries() {
        String[] columns = {SURVEY_KEY_ID, SURVEY_DATE};
        return db.query(SURVEY_TABLE_NAME, columns, null, null, null, null, null);
    }

    private Cursor getAllResultsEntries() {
        String[] columns = {RESULTS_SURVEY_ID, RESULT_NUMBER, RESULT_VALUE};
        return db.query(RESULTS_TABLE_NAME, columns, null, null, null, null, null);
    }

    private Survey getSurveyEntry(long index) {
        String[] surveyColumns = {SURVEY_KEY_ID, SURVEY_DATE};
        String[] resultsColumns = {RESULTS_SURVEY_ID, RESULT_NUMBER, RESULT_VALUE};
        String surveyWhere = SURVEY_KEY_ID + "=" + index;
        String resultsWhere = RESULTS_SURVEY_ID + "=" + index;
        Cursor surveyCursor = db.query(true, SURVEY_TABLE_NAME, surveyColumns, surveyWhere, null, null, null, null, null);
        Cursor resultsCursor = db.query(true, RESULTS_TABLE_NAME, resultsColumns, resultsWhere, null, null, null, null, null);

        long date = new Long(surveyCursor.getLong(surveyCursor.getColumnIndex(SURVEY_DATE)));

        double[] results = new double[resultsCursor.getCount()];
        int i=0;
        for(resultsCursor.moveToFirst(); !resultsCursor.isAfterLast(); resultsCursor.moveToNext()){
            results[i++] = resultsCursor.getDouble(resultsCursor.getColumnIndex(RESULT_VALUE));
        }

        Survey survey = new Survey(date, results);

        return survey;
    }



    private boolean deleteSurvey(long index) {
        String whereSurvey = SURVEY_KEY_ID + "=" + index;
        String whereResults = RESULTS_SURVEY_ID + "=" + index;
        db.delete(RESULTS_TABLE_NAME, whereResults, null);
        return db.delete(SURVEY_TABLE_NAME, whereSurvey , null) > 0;
    }

    public void deleteAll() {
        db.delete(RESULTS_TABLE_NAME, "1", null);
        db.delete(SURVEY_TABLE_NAME, "1", null);
    }

    //Klasa helpera do otwierania i aktualizacji bazy danych
    private static class DatabaseHelper extends SQLiteOpenHelper {

        public DatabaseHelper(Context context, String name,
        CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase _db) {
            _db.execSQL(CREATE_SURVEY_TABLE);
            _db.execSQL(CREATE_RESULTS_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase _db, int oldVer, int newVer) {
            _db.execSQL("DROP TABLE IF EXISTS " + RESULTS_TABLE_NAME);
            _db.execSQL("DROP TABLE IF EXISTS " + SURVEY_TABLE_NAME);
            onCreate(_db);
        }
    }

}
