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
    public void displayDemandTable(){
        double expectedValue=0;
        System.out.println("|Demand|Probability|Comm Probability|Intervals |");
        for (int i = 0; i<this.demand.length; i++){
            System.out.printf("|%-6d|%-11.2f|%-16.2f|%-3d to %-3d|\n", demand[i],probability[i],commProbability[i],intervals[i][0],intervals[i][1]);
        }
    }
    public void displayLeadTable(){
        double expectedValue=0;
        System.out.println("|Lead Time|Probability|Comm Probability|Intervals |");
        for (int i = 0; i<this.leadTime.length; i++){
            System.out.printf("|%-9d|%-11.2f|%-16.2f|%-3d to %-3d|\n", leadTime[i],leadProbability[i],leadCommProbability[i],leadIntervals[i][0],leadIntervals[i][1]);
        }
    }
    public void simulate(int[]randomNumbers,int[]leadRN){
        System.out.println("|Time|New Units|Beginning Inventory|RN |Simulated Demand|Ending Inventory|Lost Sales|Created Orders|ORN|Simulated Lead Time|");
        int c=0, begInv=this.orderQuantity,newUnits=0,endInv=begInv,simDemand=0,lostSales=0,simLeadTime=0;
        boolean createdOrder=false,orderArrivedFlag=false;
        for (int i=0;i<randomNumbers.length;i++){
            if (simLeadTime>0){
                simLeadTime=simLeadTime-1;
                if (simLeadTime==0){
                    orderArrivedFlag=true;
                }
            }
            lostSales=0;
            System.out.printf("|%-4d|%-9d|%-19d|"
                    ,i+1,newUnits,begInv);
            for (int j=0;j<demand.length;j++){
                if (randomNumbers[i]<=intervals[j][1]&&randomNumbers[i]>=intervals[j][0]){
                    simDemand=demand[j];
                    endInv=begInv-simDemand;
                    if (endInv<0) {
                        lostSales+=(-1*endInv);
                        endInv = 0;
                    }
                    System.out.printf("%-3d|%-16d|%-16d|%-10d|",randomNumbers[i],simDemand,endInv,lostSales);
                }
            }
            if (endInv<=this.reorderPoint&&simLeadTime==0&&orderArrivedFlag!=true){
                createdOrder=true;
                System.out.printf("%-14b|",createdOrder);
                for (int k=0;k<leadTime.length;k++){
                    if (leadRN[c]<=leadIntervals[k][1]&&leadRN[c]>=leadIntervals[k][0]){
                        simLeadTime=leadTime[k];
                        System.out.printf("%-3d|%-19d|\n",leadRN[c],simLeadTime);
                    }
                }
                createdOrder=false;
                c++;
            }
            else{
                System.out.printf("%-14b|%-3d|%-19d|\n",false,0,0);
            }
            if(orderArrivedFlag==true){
                newUnits=this.orderQuantity;
                begInv=newUnits;
                orderArrivedFlag=false;
            }else {
                begInv = endInv;
                newUnits = 0;
            }
        }
    }
}
