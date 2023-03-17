package hi;



import java.util.Comparator;
import java.util.PriorityQueue;

import java.util.Scanner;

public class SJF {
    public void sjf(Proces[] processes){
        NodePriorityComparator nodePriorityComparator = new NodePriorityComparator();

        PriorityQueue<Proces> queue = new PriorityQueue<Proces>(10, nodePriorityComparator);
        sort(processes);
        int pointer = 1;
        int totalTime = 0;
        double wait =0;
        double turn=0;
        queue.add(processes[0]);

        //to add all process with the same arrival time of p1
        for (int i = 1 ; i< processes.length; i++){
            if (processes[i].arrival==processes[0].arrival) {
                queue.add(processes[i]);
                pointer++;
            }else
                break;
        }
        while (!queue.isEmpty()){
            Proces p = queue.poll();
            p.setWaiting(totalTime-p.arrival);
            totalTime+=p.burst;

            System.out.println(p.num + " has been processed with burst time "+ p.burst +" waiting Time "+ p.waiting + " Turn around time "+p.getTurnAround());
            wait += p.waiting;
            turn += p.getTurnAround();
            for (int i = pointer ; i< processes.length; i++){
                if (processes[i].arrival<= totalTime) {
                    queue.add(processes[i]);
                    pointer++;
                }else
                    break;
            }
        }
        System.out.println("Total processing time: "+totalTime);
        System.out.println("Average waiting time: "+(wait/ processes.length));
        System.out.println("Average TurnAround Time: "+(turn/processes.length));

    }
    public void sort(Proces[] processes){
        for (int i=0; i< processes.length-1 ; i++){

            for (int j=0 ; j< processes.length- i-1 ; j++){

                if (processes[j].arrival > processes[j + 1].arrival) {
                    Proces temp = processes[j];
                    processes[j] = processes[j + 1];
                    processes[j + 1] = temp;
                }
            }
        }
    }

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);
        System.out.println("Enter number of processes");
        int n = input.nextInt();
        //declaration of the array
        Proces[] processes = new Proces[n];

        for (int i = 0 ; i<n ; i++){
            System.out.println("Enter processes "+ (i+1) +" arrival and burst time");
            int arr = input.nextInt();
            int burst = input.nextInt();
            //add this process to the array
            processes[i] = new Proces("P"+(i+1) , arr, burst);
        }
        SJF s = new SJF();
        s.sjf(processes);

    }
}


// this class represents each process and hold it's number, arrival, waiting and burst
class Proces{
    String num ;
    int arrival;
    int burst;
    int waiting;

    public Proces(String n , int arr, int b ){
        num = n;
        arrival = arr;
        burst = b;
    }

    public void setWaiting(int waiting) {
        this.waiting = waiting;
    }

    public int getTurnAround() {
        return waiting+ burst;
    }
}
class NodePriorityComparator implements Comparator<Proces> {

    @Override
    public int compare(Proces x, Proces y) {
        if (x.burst < y.burst) {
            return -1;
        }
        if (x.burst > y.burst) {
            return 1;
        }
        return 0;
    }
}
