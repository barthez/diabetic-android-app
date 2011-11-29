package sugar.control.core;

import java.util.ArrayList;

import sugar.control.utils.Food;


/**
 * @author Marta Drabarczyk
 *
 */
public class GlycemicIndexCalculator {
	
	private ArrayList<Food> foodList = new ArrayList<Food>();

	
	static GlycemicIndexCalculator instance = null;
		/**
	   * Zwraca instancję Singletonu klasy GlycemicIndexCalculator
	   * @return Instancja Singletonu
	   */
	public static GlycemicIndexCalculator getInstance() {
	    if (instance == null) {
	      instance = new GlycemicIndexCalculator();
	    }
	    return instance;
	  }
	
	
	/**
	 * Oblicza indeks glikemiczny wybranych produktów
	 * @return indeks glikemiczny wszystkich artykułów
	 */
	public double calculateIndex(){

		double resultIG = 0;
		for(int i=0;i<foodList.size();i++){
			Food f = foodList.get(i);
			resultIG+=((f.getWeight()*f.getCarbonLevel())/50)*f.getIGLevel();
		}
		
		foodList.clear();
		return resultIG;
	}
	
	public void addFood(Food f){
		foodList.add(f);
	}
	
	public void removeFood(Food f){
		foodList.remove(f);
	}
}