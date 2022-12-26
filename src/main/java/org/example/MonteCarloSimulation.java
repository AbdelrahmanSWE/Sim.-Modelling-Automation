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
    }
}
