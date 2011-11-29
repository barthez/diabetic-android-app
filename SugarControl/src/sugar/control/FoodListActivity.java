
package sugar.control;

import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import sugar.control.core.GlycemicIndexCalculator;
import sugar.control.database.FoodDatabaseAdapter;
import sugar.control.utils.Food;
import sugar.control.utils.FoodArrayAdapter;

/**
 *
 * @author rial
 */
public class FoodListActivity extends ListActivity {

    private List<Food> foodList;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        FoodDatabaseAdapter da = new FoodDatabaseAdapter(this);
        da.open();

        foodList = new ArrayList<Food>();
        Cursor allFoods = da.getAllEntries();
        for(int i=0; i<allFoods.getCount(); ++i){
            for(allFoods.moveToFirst(); !allFoods.isAfterLast(); allFoods.moveToNext()){
                Food f = new Food(allFoods.getString(allFoods.getColumnIndex(da.FOOD_NAME)), allFoods.getDouble(allFoods.getColumnIndex(da.FOOD_IG)), allFoods.getDouble(allFoods.getColumnIndex(da.FOOD_CARBON)));
                foodList.add(f);
            }            
        }
        da.close();

        ArrayAdapter<Food> adapter = new FoodArrayAdapter(this,foodList);
        this.setTitle("Wprowadź wagę w gramach");
		setListAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        super.onCreateOptionsMenu(menu);
        menu.add(0, 1, 1, "Dalej!");
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
            		if(foodList.get(i).getWeight()>0) {
            			g.addFood(foodList.get(i));
            			selected = true;
            		}
            	}
            		
            	if (selected==true) {
            		Intent MainMenuIntent = new Intent(FoodListActivity.this,SugarLevelGraph.class);
                    startActivityForResult(MainMenuIntent, 1);
                    return true;
            	}
            	else {
            		return false;
            	}         	
        }
        return false;
    }
   
}
