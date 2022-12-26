package org.example;

public class MonteCarloSimulation {

    private int[] sampleTimeFrame;
    private double[] frequency;
    public double frequencySum=0;
    private double[] probability;
    private double[] commProbability;
    private int[][] intervals;

    public MonteCarloSimulation(int[] sampleTimeFrame, double[] frequency) {
        this.sampleTimeFrame = sampleTimeFrame;
        this.frequency = frequency;
        for (int i = 0; i < frequency.length; i++) {
            this.frequencySum = this.frequencySum + frequency[i];
        }
        this.probability=calculateProbability();
    }

    public double[] calculateProbability(){
        double[]probability=new double[this.sampleTimeFrame.length];
        for (int i=0;i<this.sampleTimeFrame.length;i++){
            probability[i]=frequency[i]/frequencySum;
        }
        return probability;
    }

    public double[] calculateComProbability(){
        double[] commProbability=new double[this.probability.length];
        for (int i=0;i<this.probability.length;i++){

        }
        return commProbability;
    }

}
