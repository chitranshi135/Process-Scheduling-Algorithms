/*The matrix proc stores all the data of the processes in the following code
        At index 0 -> Process ID
        At index 1 -> Arrival Time
        At index 2 -> Burst Time
        At index 3 -> finish or completion time
        At index 4 -> turn around time
        At index 5 -> waiting time
*/
import java.util.*;

public class RoundRobin {
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

    public static void findCompletionTime(int n, int q, int[][] proc)
    {
        int t = proc[0][1];
        int i;
        int f = 0;
        Queue<Integer> q1 = new LinkedList<Integer>();
        int c = 0;
        int[] rt = new int[n];
        for(i = 0; i < n; i++)
        {
            rt[i] = proc[i][2];
        }
//        q1.add(proc[0][0]);
//        while( c < n)
//        {
//                for(i = 0; i < n; i++)
//                {
//                    if(proc[i][1] == t)
//                    q1.add(proc[i][0]);
//                }
//                if(q1.size() > 0)
//                    continue;
//                if(f == q )
//                {
//                    int a = q1.remove();
//                    rt[a]--;
//                    if(rt[a] > 0)
//                        q1.add(a);
//                    else {
//                        proc[a][3] = t + 1;
//                        c++;
//                    }
//                    f = 0;
//                }
//                else
//                {
//                    int b = q1.peek();
//                    rt[b]--;
//                    if(rt[b] == 0)
//                    {
//                        proc[b][3] = t+1;
//                        c++;
//                        q1.remove();
//                        f = 0;
//                    }
//                    else
//                        f++;
//                }
//                t++;
//        }
        int j = 0; int val = -1;
        while(c != n) {

            while (j < n && proc[j][1] <= t) {
                q1.add(j);
                proc[j][5] = 0;
                j++;
            }
            if(val != -1)
                q1.add(val);
            if (q1.peek() != null) {

                if (rt[q1.peek()] <= q) {
                    t += rt[q1.peek()];
                    rt[q1.peek()] = -1;
                    c++;
                    proc[q1.peek()][3] = t;
                    q1.poll();
                } else {
                    rt[q1.peek()] = rt[q1.peek()] - q;
                    t += q;
                    val = q1.poll();
                }

            }
        }
        //turn around time
        for(i = 0; i < n; i++)
        {
            proc[i][4] = proc[i][3] - proc[i][1];
        }

        //wait time
        for(i = 0; i < n; i++)
        {
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
        System.out.println("Enter the time quantum ");
        int q = Sc.nextInt();
        int[][] proc = new int[n][6];
        for(int i = 0; i < n; i++){
            System.out.println("Enter Process ID ");
            proc[i][0] = Sc.nextInt();
            System.out.println("Enter Arrival Time ");
            proc[i][1] = Sc.nextInt();
            System.out.println("Enter Burst Time ");
            proc[i][2] = Sc.nextInt();
        }
        findCompletionTime(n, q, proc);
        arrange(n, proc);
        System.out.println("\t\tRound Robin\n");
        System.out.println("Process ID \t Arrival Time \t Burst Time \t Completion Time \t Turn Around Time \t Waiting Time\n");
        for(int i = 0; i < n; i++){
            System.out.println(" "+proc[i][0] +"\t\t\t"+proc[i][1]+"\t\t\t\t"+proc[i][2]+"\t\t\t\t"+proc[i][3]+"\t\t\t\t\t"+ proc[i][4]+"\t\t\t\t\t"+proc[i][5]);
        }
        average(n, proc);
    }
}
