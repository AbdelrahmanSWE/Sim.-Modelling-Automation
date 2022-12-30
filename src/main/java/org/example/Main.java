package org.example;

import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int simNum, length,leadLength, simLength,orderQuantity,reorderPoint;
        double stockoutCost,holdingCost,orderCost;
        boolean mainSwitchFlag = true,exitSecondarySwitch=false;
        while (mainSwitchFlag == true) {
            System.out.println("Enter the wanted simulation\n" +
                    "1.Monte Carlo\n" +
                    "2.Inventory Control");
            simNum = input.nextInt();

            switch (simNum) {
                case 1:{
                    System.out.println("What is the size of the sample Demand");
                    length = input.nextInt();
                    int[] demand = new int[length];
                    double[] freq = new double[length];
                    for (int i = 0; i < length; i++) {
                        System.out.print("Demand: ");
                        demand[i] = input.nextInt();
                        System.out.print("it's Frequency: ");
                        freq[i] = input.nextDouble();
                    }
                    MonteCarloSimulation sim1 = new MonteCarloSimulation(demand, freq);
                    System.out.println("How many days is the simulation?");
                    simLength = input.nextInt();

                    while (exitSecondarySwitch == false) {
                        System.out.println("1.Display Demand Table\n" +
                                "2.Display Simulated table\n" +
                                "3.Leave Monte Carlo Simulation");
                        switch (input.nextInt()) {
                            case 1:
                                sim1.displayCalculatedTable();
                                break;
                            case 2:
                                sim1.simulate(randomArrayGenerator(simLength));
                                break;
                            case 3:
                                exitSecondarySwitch = true;
                        }
                    }
                    exitSecondarySwitch = false;
                    System.out.println("Press Any Button To Be redirected to the start of the program");
                    input.nextLine();
                    break;
                }
                case 2: {
                    System.out.println("Input the sample Demand Length");
                    length=input.nextInt();
                    System.out.println("Input the sample lead time Length");
                    leadLength=input.nextInt();
                    int[] demand=new int[length],lead=new int[leadLength];
                    double[] probability=new double[length],leadProbability=new double[leadLength];
                    for (int i = 0; i < length; i++) {
                        System.out.print("Demand: ");
                        demand[i] = input.nextInt();
                        System.out.print("it's probability(in decimal): ");
                        probability[i] = input.nextDouble();
                    }
                    for (int i = 0; i < length; i++) {
                        System.out.print("Lead Time: ");
                        lead[i] = input.nextInt();
                        System.out.print("it's probability(in decimal): ");
                        leadProbability[i] = input.nextDouble();
                    }
                    System.out.println("Order Quantity:");
                    orderQuantity=input.nextInt();
                    System.out.println("Reorder Point:");
                    reorderPoint=input.nextInt();
                    System.out.println("Holding cost: ");
                    holdingCost=input.nextInt();
                    System.out.println("StockOut Cost: ");
                    stockoutCost=input.nextInt();
                    System.out.println("Order Cost: ");
                    orderCost=input.nextInt();
                    InventoryControlSimulation sim2=new InventoryControlSimulation(demand,probability,lead,leadProbability,orderQuantity,holdingCost,stockoutCost,
                            orderCost,reorderPoint);

                    System.out.println("How many days is the simulation?");
                    simLength = input.nextInt();

                    while (exitSecondarySwitch == false) {
                        System.out.println("1.Display Demand Table\n" +
                                "2.Display Lead time table\n" +
                                "3.Simulate\n" +
                                "4.exit Inventory Control Simulation");
                        switch (input.nextInt()) {
                            case 1:
                                sim2.displayDemandTable();
                                break;
                            case 2:
                                sim2.displayLeadTable();
                                break;
                            case 3:
                                sim2.simulate(randomArrayGenerator(simLength),randomArrayGenerator(simLength));
                                break;
                            case 4:
                                exitSecondarySwitch = true;
                        }
                    }
                    exitSecondarySwitch=false;
                }
            }
        }
    }



    public static int[] randomArrayGenerator(int length){
        int[]RN=new int[length];
        Random r=new Random();
        for (int i=0;i<length;i++){
            RN[i]= r.nextInt(1,101);
        }
        return RN;
    }

}