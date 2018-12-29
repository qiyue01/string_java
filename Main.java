import java.math.BigInteger;
import java.util.*;
import java.io.*;

public class Main {

    public static InputReader in = new InputReader(System.in);
    public static PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
    public static void main(String[] args)
    {
        String s;
        out.flush();
        out.close();
    }
}
class Palindromic_Tree
{
    int len[]; //以节点i为结尾的回文串的长度
    int str[];//第i次添加的字符
    int last;//
    int point;//
    int n;
    int Next[][];//
    int fail[];//类似于AC自动机的fail指针，指向失配后需要跳转到的节点（即为i的最长回文后缀且不为i）
    int count[];//节点i表示的回文串在S中出现的次数（建树时求出的不是完全的，count()加上子节点以后才是正确的）
    int num[];//以节点i回文串的末尾字符结尾的但不包含本条路径上的回文串的数目。(也就是fail指针路径的深度)
    int trans[];
    Palindromic_Tree(int N)
    {
        n=N;
        len=new int[N];
        str=new int[N];
        fail=new int[N];
        count=new int[N];
        num=new int[N];
        Next=new int[N][26];
        trans=new int[N];
    }
    int newnode(int l)
    {
        for (int i = 0; i < 26; ++i)
            Next[point][i] = 0;
        count[point] = 0;
        num[point] = 0;
        len[point] = l;
        return point++;
    }
    void init()
    {
        Arrays.fill(len,0);
        Arrays.fill(str,0);
        Arrays.fill(fail,0);
        Arrays.fill(count,0);
        Arrays.fill(num,0);
        Arrays.fill(trans,0);
        point = 0;
        newnode(0);
        newnode(-1);
        last = 0;
        n = 0;
        str[n] = -1;
        fail[0] = 1;
    }
    int get_fail(int x)
    {
        while (str[n - len[x] - 1] != str[n])
            x = fail[x];
        return x;
    }
    void add(int c)
    {
        c -= 'a';
        str[++n] = c;
        int cur = get_fail(last);
        if (Next[cur][c]==0)
        {
            int now = newnode(len[cur] + 2);
            fail[now] = Next[get_fail(fail[cur])][c];
            Next[cur][c] = now;
            num[now] = num[fail[now]] + 1;
        }
        last = Next[cur][c];
        count[last]++;
        trans[n]=last;
    }
    void counting()//统计本质相同的回文串的出现次数
    {
        for (int i = point-1; i >= 0; i--)//逆序累加，保证每个点都会比它的父亲节点先算完，于是父亲节点能加到所有子孙
            count[fail[i]] += count[i];
    }
}
class manacher
{
    int cnt, len, ans = 0;
    char ss[],s[];
    int p[];
    manacher(String str)
    {
        s=str.toCharArray();
        ss=new char[s.length*2+10];
        p=new int[s.length*2+10];
    }
    void init() {//将每两个字符中插入一个字符
        len = s.length;
        cnt = 1;
        ss[0] = '!';
        ss[cnt] = '#';
        for (int i = 0; i < len; i++)
        {
            ss[++cnt] = s[i];
            ss[++cnt] = '#';
        }
        ans = 0;
    }

    void manacher1() {
        int pos = 0, mx = 0;
        for (int i = 1; i <= cnt; i++) {
            if (i < mx) p[i] = Math.min(p[pos * 2 - i], mx - i);
            else p[i] = 1;
            while (ss[i + p[i]] == ss[i - p[i]]) p[i]++;
            if (mx < i + p[i])
            {
                mx = i + p[i];
                pos = i;
            }
            ans = Math.max(ans, p[i] - 1);
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