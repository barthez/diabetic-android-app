package sugar.control.core;


public class GlycemicIndexCalculator {
	
	static GlycemicIndexCalculator instance = null;
	  public static GlycemicIndexCalculator getInstance() {
	    if (instance == null) {
	      instance = new GlycemicIndexCalculator();
	    }
	    return instance;
	  }
	
	
	public double calculateIndex(double ig,double weight, double carbonPerGram ){
		double index;
		index = Math.round(((weight*carbonPerGram)/50)*ig);
		return index;
	}
}