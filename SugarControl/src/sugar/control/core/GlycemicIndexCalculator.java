package sugar.control.core;


/**
 * @author Marta Drabarczyk
 *
 */
public class GlycemicIndexCalculator {
	
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
	public double calculateIndex(double ig,double weight, double carbonPerGram ){
		return Math.round(((weight*carbonPerGram)/50)*ig);
	}
}