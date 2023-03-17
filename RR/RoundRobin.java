package hi;
import java.util.LinkedList;
import java.util.Scanner;

public class RoundRobin {

    public void rr(Process[] processes , int quantum){
        LinkedQueue<Process> queue = new LinkedQueue<>();
        sort(processes);
        //to keep track with the index of the next process should be added to queue
        int pointer= 1;

        double wait=0;
        double turn =0;
        int totalTime = 0;

        // enqueue p1 as it is the first arrived process
        queue.enqueue(processes[0]);

        //to add all process with the same arrival time of p1
        for (int i = 1 ; i< processes.length; i++){
            if (processes[i].arrival==processes[0].arrival) {
                queue.enqueue(processes[i]);
                pointer++;
            }else
                break;
        }

        while (!queue.isEmpty()){
            //check if it is the fist time for a process to be processed or not
            if(queue.first().remain == queue.first().burst){
                // set waiting time
                queue.first().setWaiting(totalTime-queue.first().arrival);
            }else
                queue.first().setWaiting(queue.first().waiting+(totalTime-queue.first().lastProcessTime));

            if ( queue.first().remain > quantum) {
                queue.first().setRemain(queue.first().remain-=quantum);
                totalTime+=quantum;
                queue.first().setLastProcessTime(totalTime);
                Process p = queue.dequeue();


                System.out.println(p.num + " process "+(p.burst - p.remain) + " and remain "+ p.remain);
                for (int i = pointer ; i< processes.length; i++){
                    if (processes[i].arrival<= totalTime) {
                        queue.enqueue(processes[i]);
                        pointer++;
                    }else
                        break;
                }
                queue.enqueue(p);
            }else if(queue.first().remain <= quantum){
                totalTime+=queue.first().remain;
                Process p = queue.dequeue();
                System.out.println(p.num + " has been processed with burst time "+ p.burst +" waiting Time "+ p.waiting + " Turn around time "+p.getTurnAround());
                for (int i = pointer ; i< processes.length; i++){
                    if (processes[i].arrival<= totalTime) {
                        queue.enqueue(processes[i]);
                        pointer++;
                    }else
                        break;
                }
            }
        }
        // get total waiting and turnaround time
        for (Process process : processes) {
            wait += process.waiting;
            turn += process.getTurnAround();
        }

        System.out.println("Total processing time: "+totalTime);
        System.out.println("Average waiting time: "+(wait/ processes.length));
        System.out.println("Average TurnAround Time: "+(turn/processes.length));
    }

    // this method sorting the processes according to it's arrival time
    public void sort(Process[] processes){
        for (int i=0; i< processes.length-1 ; i++){

            for (int j=0 ; j< processes.length- i-1 ; j++){

                if (processes[j].arrival > processes[j + 1].arrival) {
                    Process temp = processes[j];
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
        Process[] processes = new Process[n];

        System.out.println("Enter Quantum Time");
        int q = input.nextInt();

        for (int i = 0 ; i<n ; i++){
            System.out.println("Enter processes "+ (i+1) +" burst and arrival time");
            int burst = input.nextInt();
            int arr = input.nextInt();
            //add this process to the array
            processes[i] = new Process("P"+(i+1) , arr, burst);
        }
        RoundRobin r = new RoundRobin();
        r.rr(processes, q);

    }
}
// this class represents each process and hold it's number, arrival, waiting, burst and remaining time
class Process{
    String num ;
    int arrival;
    int burst;
    int remain;
    int waiting;
    int lastProcessTime;
    public Process(String n , int arr, int b ){
        num = n;
        arrival = arr;
        burst = b;
        remain = burst;

    }
    // last processing time used to calculate waiting time
    public void setLastProcessTime(int lastProcessTime) {
        this.lastProcessTime = lastProcessTime;
    }

    public void setWaiting(int waiting) {
        this.waiting = waiting;
    }

    public void setRemain(int remain) {
        this.remain = remain;
    }

    public int getTurnAround() {
        return waiting+ burst;
    }
}

// a queue data structure based on linked list instead of array to be with variable length
class LinkedQueue<E> {
    LinkedList<Object> qu = new LinkedList<>();
    public int size() {
        return qu.size();
    }

    public boolean isEmpty() {
        return getSize()==0;
    }
    public int getSize(){
        return qu.size();
    }


    public void enqueue(E e) {
        qu.addLast(e);
    }


    public E first() {
        return (E) qu.getFirst();
    }


    public E dequeue() {
        return (E) qu.removeFirst();
    }


}

