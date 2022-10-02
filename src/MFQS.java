/*The procrix proc stores all the data of the processes in the following code
        At index 0 -> Process ID
        At index 1 -> Arrival Time
        At index 2 -> Burst Time
        At index 3 -> finish or completion time
        At index 4 -> turn around time
        At index 5 -> waiting time
*/
import java.util.*;

public class MFQS{

    public static void arrangeArrival(int num, int[][] proc){
        for (int i = 0; i < num; i++) {
            for (int j = 0; j < num - i - 1; j++) {
                if (proc[j][1] > proc[j + 1][1]) {
                    for (int k = 0; k < 3; k++) {
                        int temp = proc[j][k];
                        proc[j][k] = proc[j + 1][k];
                        proc[j + 1][k] = temp;
                    }
                }
            }
        }
    }

    public static void completionTime(int num, int[][] proc, int quantumQ1, int quantumQ2){
        Queue<Integer> q1 = new PriorityQueue<>();
        Queue<Integer> q2 = new PriorityQueue<>();
        Queue<Integer> q3 = new PriorityQueue<>();

        int[] rem = new int[num];
        for(int i = 0; i < num; i++) rem[i] = proc[i][2];

        int currTime = proc[0][1];
        int j = 0;
        int completed = 0;

        while(completed != num){

            while(j < num && proc[j][1] <= currTime){
                q1.add(j);
                proc[j][5] = 0;
                j++;
            }

            if(q1.peek() != null){

                if(rem[q1.peek()] <= quantumQ1){
                    currTime += rem[q1.peek()];
                    rem[q1.peek()] = -1;
                    completed++;
                    proc[q1.peek()][3] = currTime;
                    q1.poll();
                } else {
                    rem[q1.peek()] = rem[q1.peek()] - quantumQ1;
                    currTime += quantumQ1;
                    q2.add(q1.poll());
                }

            } else if(q1.peek() == null && q2.peek() != null){

                if(j < num && proc[j][1] - currTime >= quantumQ2){
                    if(rem[q2.peek()] <= quantumQ2){
                        currTime += rem[q2.peek()];
                        rem[q2.peek()] = -1;
                        completed++;
                        proc[q2.peek()][3] = currTime;
                        q2.poll();
                    } else {
                        rem[q2.peek()] = rem[q2.peek()] - quantumQ2;
                        currTime += quantumQ2;
                        q3.add(q2.poll());
                    }
                } else if(j < num && proc[j][1] - currTime < quantumQ2){
                    if(rem[q2.peek()] <= proc[j][1] - currTime){
                        currTime += rem[q2.peek()];
                        rem[q2.peek()] = -1;
                        completed++;
                        proc[q2.peek()][3] = currTime;
                        q2.poll();
                    } else {
                        rem[q2.peek()] = rem[q2.peek()] - (proc[j][1] - currTime);
                        currTime += (proc[j][1] - currTime);
                        q3.add(q2.poll());
                    }
                } else{
                    if(rem[q2.peek()] <= quantumQ2){
                        currTime += rem[q2.peek()];
                        rem[q2.peek()] = -1;
                        completed++;
                        proc[q2.peek()][3] = currTime;
                        q2.poll();
                    } else {
                        rem[q2.peek()] = rem[q2.peek()] - quantumQ2;
                        currTime += quantumQ2;
                        q3.add(q2.poll());
                    }
                }
            } else if (q1.peek() == null && q2.peek() == null){
                if(q3.peek() != null){
                    if(j < num && proc[j][1] - currTime >= rem[q3.peek()]){
                        currTime += rem[q3.peek()];
                        rem[q3.peek()] = -1;
                        completed++;
                        proc[q3.peek()][3] = currTime;
                        q3.poll();
                    } else if(j < num && proc[j][1] - currTime < rem[q3.peek()]){
                        rem[q3.peek()] = rem[q3.peek()] - (proc[j][1] - currTime);
                        currTime += proc[j][1] - currTime;
                    } else{
                        currTime += rem[q3.peek()];
                        rem[q3.peek()] = -1;
                        completed++;
                        proc[q3.peek()][3] = currTime;
                        q3.poll();
                    }
                }
                if(j < num && j - completed < 1 && proc[j][1] > currTime){
                    currTime = proc[j][1];
                }
            }
        }

        turnAroundTime(num, proc);
        waitingTime(num, proc);
        printResult(num, proc);
    }

    public static void turnAroundTime(int num, int[][] proc){
        for(int i = 0; i < num; i++) proc[i][4] = proc[i][3] - proc[i][1];
    }

    public static void waitingTime(int num, int[][] proc){
        for(int i = 0; i < num; i++) proc[i][5] = proc[i][4] - proc[i][2];
    }

    public static double avgTAT(int num, int[][] proc){
        int sum = 0;
        for(int i = 0; i < num; i++) sum += proc[i][4];
        return (double)sum/num;
    }

    public static double avgWT(int num, int[][] proc){
        int sum = 0;
        for(int i = 0; i < num; i++) sum += proc[i][5];
        return (double)sum/num;
    }

    public static void MFQSScheduling(int num, int[][] proc, int quantumQ1, int quantumQ2){
        arrangeArrival(num, proc);
        completionTime(num, proc, quantumQ1, quantumQ2);
    }

    public static void printResult(int num, int proc[][]){
        System.out.println("******************************************************************************");
        System.out.println("\t\t\t\t\tmultilevel Feedback Queue Scheduling");
        System.out.println("******************************************************************************");
        System.out.println("Process ID \t Arrival Time \t Burst Time \t Completion Time \t Turn Around Time \t Waiting Time");
        for(int i = 0; i < num; i++){
            System.out.println(" "+proc[i][0] +"\t\t\t"+proc[i][1]+"\t\t\t\t"+proc[i][2]+"\t\t\t\t"+proc[i][3]+"\t\t\t\t\t"+ proc[i][4]+"\t\t\t\t\t"+proc[i][5]);
        }
        System.out.println("Average Turn Around Time : " + avgTAT(num, proc));
        System.out.println("Average Waiting Time : " + avgWT(num, proc));
    }

    public static void main(String[] args){
        System.out.println("******************************************************************************");
        System.out.println("\t\t\t\t\tMultilevel Feedback Queue Scheduling");
        System.out.println("******************************************************************************");
        System.out.println("Uses Three Queues, Q1 with RR (quantum = quantumQ1), Q2 with RR (quantum = quantumQ2) and Q3 with FCFS)");
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the number of processes : ");
        int n = sc.nextInt();
        System.out.println();
        int[][] proc = new int[n][6];

        System.out.print("Enter 0 to put custom process ID's or -1 to give default 1 to N as process ID's : ");
        int id = sc.nextInt();
        System.out.println();

        if(id == -1){
            for(int i = 0; i < n; i++) proc[i][0] = i + 1;
        } else {
            System.out.println("Enter Process ID's(separated by spaces) : ");
            for(int i = 0; i < n; i++) proc[i][0] = sc.nextInt();
        }

        System.out.print("Enter Arrival Times(separated by spaces) : ");
        for(int i = 0; i < n; i++) proc[i][1] = sc.nextInt();

        System.out.println();

        System.out.print("Enter Burst Times(separated by spaces) : ");
        for(int i = 0; i < n; i++) proc[i][2] = sc.nextInt();

        System.out.println();

        System.out.print("Enter quantum time for Queue1 : ");
        int quantQ1 = sc.nextInt();
        System.out.println();

        System.out.print("Enter quantum time for Queue2 : ");
        int quantQ2 = sc.nextInt();
        System.out.println();

        MFQSScheduling(n, proc, quantQ1, quantQ2);

    }
}