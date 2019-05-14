/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.Serializable;

public class AvgTemperature implements Serializable {

    private static final long serialVersionUID = 1L;
    private int avg;
    private int max;
    private int min;

    public AvgTemperature() {
    }

    public AvgTemperature(int avg, int max, int min) {

        this.avg = avg;
        this.max = max;
        this.min = min;

    }

    public int getAvg() {
        return avg;
    }

    public void setAvg(int avg) {
        this.avg = avg;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

}
