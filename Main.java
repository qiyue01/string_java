import java.math.BigInteger;
import java.util.*;
import java.io.*;

public class Main {

    public static InputReader in = new InputReader(System.in);
    public static PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));

    public static void main(String[] args)
    {
        int a;
        a=in.nextInt();
        String p;
        int cas=0;
        while(a--!=0)
        {
            cas++;
            p=in.next();
            int len=0;
            SAM part=new SAM();
            part.init(2*p.length()+2,26);
            SAM_node cur=part.root;
            out.println("Case #"+cas+":");
            for(int i=0;i<p.length();++i)
            {
                if (cur.next[p.charAt(i) - 'a'] == null)
                {
                    if (cur != part.root)
                    {
                        out.println(len+" "+(cur.firstpos-len+1));
                        len = 0;
                        cur = part.root;
                    }
                    if (part.root.next[p.charAt(i) - 'a'] == null)
                    {
                        out.println(-1+" "+(int)p.charAt(i));
                        part.insert(p.charAt(i) - 'a');
                        len = 0;
                        continue;
                    }

                }
                part.insert(p.charAt(i) - 'a');
                cur = cur.next[p.charAt(i) - 'a'];
                len++;
            }
            if (cur != part.root)
                out.println(len+" "+(cur.firstpos-len+1));
        }
        out.flush();
        out.close();
    }
}
class SAM_node
{
    SAM_node pre,next[];
    int step,cnt,firstpos;
    SAM_node(int sigma)
    {
        next=new SAM_node[sigma];
        Arrays.fill(next,null);
        step=0;
        cnt=0;
        pre=null;
        firstpos=0;
    }
}
class SAM
{
    SAM_node SAM_pool[],root,last;
    int d[];
    SAM_node pool[];
    int cur;
    int sigma;
    void topo() {
        // 求出parent树的拓扑序
        int cnt = cur;
        int maxVal = 0;
        Arrays.fill(d, 0);
        for (int i = 1; i < cnt; i++) {
            maxVal = Math.max(maxVal, SAM_pool[i].step);

            d[SAM_pool[i].step]++;
        }
        for (int i = 1; i <= maxVal; i++)
            d[i] += d[i - 1];
        for (int i = 1; i < cnt; i++)
            pool[d[SAM_pool[i].step]--] = SAM_pool[i];
        pool[0] = root;
    }
    void init(int a,int b)
    {
        d=new int[a];
        pool=new SAM_node[a];
        SAM_pool=new SAM_node[a];
        SAM_pool[0]=new SAM_node(b);
        sigma=b;
        root=last=SAM_pool[0];
        cur=1;
    }
    void insert(int w)
    {
        SAM_node p=last;
        SAM_pool[cur]=new SAM_node(sigma);
        SAM_node np=SAM_pool[cur];
        last=np;
        cur++;
        np.step=p.step+1;
        np.firstpos = np.step - 1; //确定原串初始位置
        while (p!=null && p.next[w]==null)
        {
            p.next[w]=np;
            p = p.pre;
        }
        if(p==null)
        {
            np.pre=root;
        }
        else
        {
            SAM_node q=p.next[w];
            if(p.step+1==q.step)
                np.pre=q;
            else {
                SAM_node nq = SAM_pool[cur++] = new SAM_node(sigma);
                nq.next = Arrays.copyOf(q.next, sigma);
                nq.firstpos = q.firstpos;
                nq.step = p.step + 1;
                nq.pre = q.pre;
                q.pre = nq;
                np.pre = nq;
                while (p != null && p.next[w]==(q)) {
                    p.next[w] = nq;
                    p = p.pre;
                }
            }
        }
    }
}

class InputReader{
    private final static int BUF_SZ = 65536;
    BufferedReader in;
    StringTokenizer tokenizer;
    public InputReader(InputStream in) {
        super();
        this.in = new BufferedReader(new InputStreamReader(in),BUF_SZ);
        tokenizer = new StringTokenizer("");
    }
    public String next() {
        while (!tokenizer.hasMoreTokens()) {
            try {
                tokenizer = new StringTokenizer(in.readLine());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return tokenizer.nextToken();
    }
    public int nextInt() {
        return Integer.parseInt(next());
    }
    public long nextLong()
    {
        return Long.parseLong(next());
    }
}