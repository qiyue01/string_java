import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

public class poj1743
{

    public static InputReader in = new InputReader(System.in);
    public static PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));

    public static void main(String[] args)
    {
        int a;
        while (true)
        {
            a=in.nextInt();
            if(a==0)
                break;
            int p[]=new int[a];
            for(int i=0;i<a;++i)
                p[i]=in.nextInt();

            if(a<10)
            {
                out.println(0);
                continue;
            }
            int p2[]=new int[a-1];
            for(int i=0;i<a-1;++i)
                p2[i]=(p[i+1]-p[i])+88;
            SAM part=new SAM();
            part.init(2*a+2,180);
            for(int i=0;i<a-1;++i)
            {
                part.insert(p2[i]);
                part.last.ans=i+1;
            }
            part.topo();
            int ans=0;
            for(int i=part.cur-1;i>0;--i)
            {
                if(part.pool[i].ans-part.pool[i].step>=part.pool[i].step && part.pool[i].step>=4)
                    ans=Math.max(ans,part.pool[i].step);
                part.pool[i].pre.ans=part.pool[i].ans;
            }
            out.println(ans+1);
            out.flush();
        }
        out.flush();
        out.close();
    }
}



