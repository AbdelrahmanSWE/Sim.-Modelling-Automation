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
        this.commProbability=calculateComProbability();
        this.intervals=calculateIntervals();
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
        commProbability[0]=this.probability[0];
        for (int i=1;i<this.probability.length;i++){
            commProbability[0]=this.probability[i]+commProbability[i-1];
        }
        return commProbability;
    }

    public int[][] calculateIntervals(){
        int[][] intervals=new int[commProbability.length][2];
        intervals[0][0]=1;
        intervals[0][1]=(int)commProbability[0];
        for (int i=1;i<commProbability.length;i++){
            intervals[i][0]=(int)(commProbability[i-1]*100)+1;
            intervals[i][1]=(int)commProbability[i];
        }
        return intervals;
    }


}
