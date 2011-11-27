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

        public double getIGLevel() {
            return IGLevel;
        }

        public double getCarbonLevel() {
            return carbonLevel;
        }

        public String getFoodName() {
            return foodName;
        }

        public Food(String foodName, double IGLevel, double carbonLevel) {
            this.foodName = foodName;
            this.IGLevel = IGLevel;
            this.carbonLevel = carbonLevel;
        }
    }