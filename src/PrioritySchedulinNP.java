/*The matrix proc stores all the data of the processes in the following code
        At index 0 -> Process ID
        At index 1 -> Arrival Time
        At index 2 -> Burst Time
        At index 3 -> Priority (the lower the number, the higher the priority)
        At index 4 -> finish or completion time
        At index 5 -> turn around time
        At index 6 -> waiting time
*/
import java.util.*;

public class PrioritySchedulinNP {
    public static void arrange(int n, int[][] proc)
    {
        int i, j, temp;
        for(i = 0; i < n; i++)
        {
            for(j = 0; j < n-i-1; j++)
            {
                if(proc[j+1][1] < proc[j][1])
                {
                    for(int k = 0; k < 3; k++) {
                        temp = proc[j][k];
                        proc[j][k] = proc[j + 1][k];
                        proc[j + 1][k] = temp;
                    }
                }
                else if(proc[j+1][1] == proc[j][1])
                {
                    if(proc[j+1][2] < proc[j][2])
                    {
                        for(int k = 0; k < 3; k++) {
                            temp = proc[j][k];
                            proc[j][k] = proc[j + 1][k];
                            proc[j + 1][k] = temp;
                        }
                    }
                }
            }
        }
    }

    public static void findCompletionTime(int n, int[][] proc)
    {
        int t = proc[0][1];
        int min;
        int i, j, temp;
        int pos;
        for(i = 0; i < n; i++)
        {
            pos = i;
            min = proc[i][3];
            for(j = i; j < n; j++)
            {
                if(proc[j][1] <= t)
                {
                    if(proc[j][3] < min)
                    {
                        min = proc[j][3];
                        pos = j;
                    }
                }
            }
            proc[pos][4] = t + proc[pos][2];
            proc[pos][5] = proc[pos][4] - proc[pos][1];
            proc[pos][6] = proc[pos][5] - proc[pos][2];
            t = proc[pos][4];

            for (int k = 0; k < 6; k++) {
                temp = proc[pos][k];
                proc[pos][k] = proc[i][k];
                proc[i][k] = temp;
            }

        }
    }

    public static void average(int n, int[][] proc)
    {
        int totWait = 0;
        int totTAT = 0;
        for(int i = 0; i < n; i++)
        {
            totWait += proc[i][5];
            totTAT += proc[i][4];
        }

        double avgWait = (1.0 * totWait)/n;
        double avgTAT = (1.0 * totTAT)/n;
        System.out.println("Average Turn Around time : "+ avgTAT);
        System.out.println("Average Wait time : "+ avgWait);
    }

    public static void main(String args[])
    {
        Scanner Sc = new Scanner(System.in);
        System.out.println("Enter the number of processes ");
        int n = Sc.nextInt();
        int[][] proc = new int[n][7];
        for(int i = 0; i < n; i++){
            System.out.println("Enter Process ID ");
            proc[i][0] = Sc.nextInt();
            System.out.println("Enter Arrival Time ");
            proc[i][1] = Sc.nextInt();
            System.out.println("Enter Burst Time ");
            proc[i][2] = Sc.nextInt();
            System.out.println("Enter Priority ");
            proc[i][3] = Sc.nextInt();
        }
        arrange(n, proc);
        findCompletionTime(n, proc);
        arrange(n, proc);
        System.out.println("******************************************************************************");
        System.out.println("\t\t\t\t\tPriority Scheduling Non - Preemptive\n");
        System.out.println("******************************************************************************");
        System.out.println("Process ID \t Arrival Time \t Burst Time \t Priority \t Completion Time \t Turn Around Time \t Waiting Time\n");
        for(int i = 0; i < n; i++){
            System.out.println(" "+proc[i][0] +"\t\t\t"+proc[i][1]+"\t\t\t\t"+proc[i][2]+"\t\t\t\t"+proc[i][3]+"\t\t\t\t\t"+ proc[i][4]+"\t\t\t\t\t"+proc[i][5]+"\t\t\t\t\t"+proc[i][6]);
        }
        average(n, proc);
    }
}
