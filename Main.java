import java.math.BigInteger;
import java.util.*;
import java.io.*;

public class Main {

    public static InputReader in = new InputReader(System.in);
    public static PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
    public static void main(String[] args)
    {
        String s,s2;
        long a,cas=0,ans,last;
        a=in.nextInt();
        while(a--!=0)
        {
            ans=0;
            last=0;
            cas++;
            s=in.next();
            s2=in.next();
            SAM1 part=new SAM1();
            part.init(s2.length()*2+10,26);
            for(int i=0;i<s2.length();++i)
            {
                if(s2.charAt(i)==s.charAt(0))
                    last=i+1;
                part.insert(s2.charAt(i)-'a');
                if(last<part.last.step-part.last.pre.step)
                    ans+=last;
                else
                    ans+=part.last.step-part.last.pre.step;
            }
            out.println("Case #"+cas+": "+ans);
        }
        out.flush();
        out.close();
    }
}
class SAM_node
{
    SAM_node pre,next[];
    int step,cnt,firstpos,ans;
    SAM_node(int sigma)
    {
        next=new SAM_node[sigma];
        step=0;
        cnt=0;
        pre=null;
        firstpos=0;
        ans=0;
    }
}
class SAM1
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
    public boolean hasNext() {  //处理EOF
        while (tokenizer == null || !tokenizer.hasMoreTokens()) {
            try {
                String line = in.readLine();
                if(line == null) return false;
                tokenizer = new StringTokenizer(line);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return true;
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