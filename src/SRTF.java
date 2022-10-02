/*The matrix proc stores all the data of the processes in the following code
        At index 0 -> Process ID
        At index 1 -> Arrival Time
        At index 2 -> Burst Time
        At index 3 -> finish or completion time
        At index 4 -> turn around time
        At index 5 -> waiting time
*/
import java.util.*;

public class SRTF {

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
        int t = 0;
        int i, min;
        int[] rt = new int[n];
        for(i = 0; i < n; i++)
        {
            rt[i] = proc[i][2];
        }
        int c = 0; int pos = -1;
        while(c < n)
        {
            min = Integer.MAX_VALUE;
            for(i = 0; i < n; i++)
            {
                if(proc[i][1] <= t && rt[i] < min && rt[i] > 0)
                {
                    min = rt[i];
                    pos = i;
                }
            }
            if(pos != -1) {
                rt[pos]--;
                min = rt[pos];
                if (rt[pos] == 0) {
                    proc[pos][3] = t+1;
                    proc[pos][4] = proc[pos][3] - proc[pos][1];
                    proc[pos][5] = proc[pos][4] - proc[pos][2];
                    min = Integer.MAX_VALUE;
                    pos = -1;
                    c++;
                }
            }
            t++;
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
        arrange(n, proc);
        System.out.println("\t\tShortest Remaining Time First\n");
        System.out.println("Process ID \t Arrival Time \t Burst Time \t Completion Time \t Turn Around Time \t Waiting Time\n");
        for(int i = 0; i < n; i++){
            System.out.println(" "+proc[i][0] +"\t\t\t"+proc[i][1]+"\t\t\t\t"+proc[i][2]+"\t\t\t\t"+proc[i][3]+"\t\t\t\t\t"+ proc[i][4]+"\t\t\t\t\t"+proc[i][5]);
        }
        average(n, proc);
    }
}
