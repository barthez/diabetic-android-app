package sugar.control.core;

import java.util.ArrayList;

import sugar.control.utils.Food;


/**
 * @author Marta Drabarczyk
 *
 */
public class GlycemicIndexCalculator {
	
	private ArrayList<Food> foodList;
	
	public GlycemicIndexCalculator(){
		foodList = new ArrayList<Food>();
	}
	
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
	 * Oblicza indeks glikemiczny podanej ilości danego produktu
	 * @param ig indeks glikemiczny produktu
	 * @param weight waga produktu (w gramach)
	 * @param carbonPerGram ilość węglowodanów na gram produktu
	 * @return indeks glikemiczny podanej ilości danego produktu
	 */
//	public double calculateIndex(){
//		return Math.round(((weight*carbonPerGram)/50)*ig);
//	}
	
	public void addFood(Food f){
		foodList.add(f);
	}
	
	public void removeFood(Food f){
		foodList.remove(f);
	}
}