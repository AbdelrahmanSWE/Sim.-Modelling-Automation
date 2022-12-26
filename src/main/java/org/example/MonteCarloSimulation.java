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
            commProbability[i]=this.probability[i]+commProbability[i-1];
        }
        return commProbability;
    }

    public int[][] calculateIntervals(){
        int[][] intervals=new int[commProbability.length][2];
        intervals[0][0]=1;
        intervals[0][1]=(int)(commProbability[0]*100);
        for (int i=1;i<commProbability.length;i++){
            intervals[i][0]=intervals[i-1][1]+1;
            intervals[i][1]=(int)(Math.round(commProbability[i]*100));
        }
        return intervals;
    }

    public void displayCalculatedTable(){
        System.out.println("|Time |Frequency|Probability|Comm Probability|Intervals |");
        for (int i=0;i<this.sampleTimeFrame.length;i++){
            System.out.printf("|%-5d|%-9.2f|%-11.2f|%-16.2f|%-3d to %-3d|\n",sampleTimeFrame[i],frequency[i],probability[i],commProbability[i],intervals[i][0],intervals[i][1]);
        }
        System.out.printf("      |%-9.2f|\n\n",frequencySum);
    }

    public void simulate(int[]randomNumbers){
        int timeIntervalNumber=randomNumbers.length;
        System.out.println("|Days(Sim)|RN |Sim Value|");
        double sumOfSimFrequencies=0;
        for (int i=0;i<timeIntervalNumber;i++){
            for (int j=0;j<this.sampleTimeFrame.length;j++){
                if (randomNumbers[i]>=intervals[j][0] && randomNumbers[i]<=intervals[j][1]){
                    sumOfSimFrequencies=sumOfSimFrequencies+this.frequency[j];
                    System.out.printf("|%-9d|%-3d|%-9.2f|\n",i+1,randomNumbers[i],this.frequency[j]);
                }
            }
        }
        System.out.printf("              |%-9.2f|\n",sumOfSimFrequencies);

    }
}
