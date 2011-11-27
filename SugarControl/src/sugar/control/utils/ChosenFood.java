/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package sugar.control.utils;

/**
 *
 * @author rial
 */
public class ChosenFood {

        private int foodID;
        private String foodName;
        private double IGLevel;
        private double carbonLevel;
        private double weight;

    public double getIGLevel() {
        return IGLevel;
    }

    public double getCarbonLevel() {
        return carbonLevel;
    }

    public int getFoodID() {
        return foodID;
    }

    public String getFoodName() {
        return foodName;
    }

    public double getWeight() {
        return weight;
    }

    public ChosenFood(int foodID, String foodName, double IGLevel, double carbonLevel, double weight) {
        this.foodID = foodID;
        this.foodName = foodName;
        this.IGLevel = IGLevel;
        this.carbonLevel = carbonLevel;
        this.weight = weight;
    }


}
