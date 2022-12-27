package org.example;

public class InventoryControlSimulation {
    private final int[] demand;
    private final double[] probability;
    private final double[] commProbability;
    private final int[][] intervals;
    private final int[] leadTime;
    private final double[] leadProbability;
    private final double[] leadCommProbability;
    private final int[][] leadIntervals;
    private final int orderQuantity;
    private final double holdingCost;
    private final double stockoutCost;
    private final int reorderPoint;
    public InventoryControlSimulation(int[] demand, double[] probability, int[] leadTime, double[] leadProbability, int orderQuantity, double holdingCost, double stockoutCost, int reorderPoint) {
        this.demand = demand;
        this.probability=probability;
        this.commProbability=calculateComProbability(this.probability);
        this.intervals=calculateIntervals(this.commProbability);

        this.leadTime=leadTime;
        this.leadProbability=leadProbability;
        this.leadCommProbability=calculateComProbability(this.leadProbability);
        this.leadIntervals=calculateIntervals(this.leadCommProbability);

        this.orderQuantity = orderQuantity;
        this.holdingCost = holdingCost;
        this.stockoutCost = stockoutCost;
        this.reorderPoint = reorderPoint;
    }
    public double[] calculateComProbability(double[] probability){
        double[] commProbability=new double[probability.length];
        commProbability[0]=probability[0];
        for (int i=1;i<probability.length;i++){
            commProbability[i]=probability[i]+commProbability[i-1];
        }
        return commProbability;
    }
    public int[][] calculateIntervals(double[] commProbability){
        int[][] intervals=new int[commProbability.length][2];
        intervals[0][0]=1;
        intervals[0][1]=(int)(commProbability[0]*100);
        for (int i=1;i<commProbability.length;i++){
            intervals[i][0]=intervals[i-1][1]+1;
            intervals[i][1]=(int)(Math.round(commProbability[i]*100));
        }
        return intervals;
    }


}
