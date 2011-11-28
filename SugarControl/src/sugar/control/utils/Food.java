/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package sugar.control.utils;

/**
 *
 * @author rial
 */
    public class Food {

        private String foodName;
        private double IGLevel;
        private double carbonLevel;
        private boolean selected;
        private double weight;

        public double getIGLevel() {
            return IGLevel;
        }

        public double getCarbonLevel() {
            return carbonLevel;
        }

        public String getFoodName() {
            return foodName;
        }
        
        public boolean isSelected(){
        	return selected;
        }
        
        public void setSelected(boolean s){
        	this.selected = s;
        }
        
        public void setWeight(double w){
        	weight = w;
        }
        
        public double getWeight(){
        	return weight;
        }

        public Food(String foodName, double IGLevel, double carbonLevel) {
            this.foodName = foodName;
            this.IGLevel = IGLevel;
            this.carbonLevel = carbonLevel;
            this.selected = false;
            this.weight = 0;
        }
    }