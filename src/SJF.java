/*The matrix proc stores all the data of the processes in the following code
        At index 0 -> Process ID
        At index 1 -> Arrival Time
        At index 2 -> Burst Time
        At index 3 -> finish or completion time
        At index 4 -> turn around time
        At index 5 -> waiting time
*/
import java.util.*;
public class SJF {

    public static void arrange(int n, int[][] proc) {
        int i, j, temp;
        for (i = 0; i < n; i++) {
            for (j = 0; j < n - i - 1; j++) {
                if (proc[j + 1][1] < proc[j][1]) {
                    for (int k = 0; k < 3; k++) {
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
        proc[0][3] = proc[0][1] + proc[0][2];
        proc[0][4] = proc[0][3] - proc[0][1];
        proc[0][5] = proc[0][4] - proc[0][2];
        int t;
        int i, min;
        int pos = -1;
        for(i = 1; i < n; i++)
        {
            t = proc[i-1][3];
            if(proc[i][1] > t)
                t = proc[i][1];

            min = proc[i][2];
            for(int j = i; j < n; j++)
            {
                if(proc[j][1] > t)
                    break;

                if(proc[j][1] <= t && proc[j][2] <= min)
                {
                    min = proc[j][2];
                    pos = j;
                }
            }

            for (int k = 0; k < 6; k++) {
                int temp = proc[pos][k];
                proc[pos][k] = proc[i][k];
                proc[i][k] = temp;
            }

            proc[i][3] = t + proc[i][2];
            proc[i][4] = proc[i][3] - proc[i][1];
            proc[i][5] = proc[i][4] - proc[i][2];
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
        int[][] proc = new int[n][6];
        for(int i = 0; i < n; i++){
            System.out.println("Enter Process ID ");
            proc[i][0] = Sc.nextInt();
            System.out.println("Enter Arrival Time ");
            proc[i][1] = Sc.nextInt();
            System.out.println("Enter Burst Time ");
            proc[i][2] = Sc.nextInt();
        }
        findCompletionTime(n, proc);
        System.out.println("\t\tShortest Job First\n");
        System.out.println("Process ID \t Arrival Time \t Burst Time \t Completion Time \t Turn Around Time \t Waiting Time\n");
        for(int i = 0; i < n; i++){
            System.out.println(" "+proc[i][0] +"\t\t\t"+proc[i][1]+"\t\t\t\t"+proc[i][2]+"\t\t\t\t"+proc[i][3]+"\t\t\t\t\t"+ proc[i][4]+"\t\t\t\t\t"+proc[i][5]);
        }
        average(n, proc);
    }
}
