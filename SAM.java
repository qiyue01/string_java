import java.util.Arrays;

public class SAM
{
    class EXSAM_node
    {
        EXSAM_node next[],pre;
        int step,cnt[];
        EXSAM_node(int sigma,int sum)
        {
            next=new EXSAM_node[sigma];
            Arrays.fill(next,null);
            step=0;
            cnt=new int[sum];
            Arrays.fill(cnt,0);
        }
    }
    class EXSAM
    {
        EXSAM_node root,last,EXSAM_pool[],pool[];
        int d[],cur,sigma,sum;
        void init(int a,int b,int c)
        {
            d=new int[a];
            pool=new EXSAM_node[a];
            EXSAM_pool=new EXSAM_node[a];
            EXSAM_pool[0]=new EXSAM_node(b,c);
            sigma=b;
            root=last=EXSAM_pool[0];
            cur=1;
            sum=c;
        }
        void insert(int w,int k)
        {
            EXSAM_node p = last;
            if (p.next[w]!=null && p.next[w].step == p.step + 1)
            {
                last = p.next[w];
                last.cnt[k]++;
                //last->cnt[i]++;
                //last->size++;
                return;
            }
            EXSAM_pool[cur]=new EXSAM_node(sigma,sum);
            EXSAM_node np=EXSAM_pool[cur];
            cur++;
            last=np;
            np.step=p.step+1;
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
                EXSAM_node q=p.next[w];
                if(p.step+1==q.step)
                    np.pre=q;
                else {
                    EXSAM_node nq = EXSAM_pool[cur++] = new EXSAM_node(sigma,sum);
                    nq.next = Arrays.copyOf(q.next, sigma);
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
            //last.cnt[k]++;
        }
        void topo() {
            // 求出parent树的拓扑序
            int cnt = cur;
            int maxVal = 0;
            Arrays.fill(d, 0);
            for (int i = 1; i < cnt; i++) {
                maxVal = Math.max(maxVal, EXSAM_pool[i].step);

                d[EXSAM_pool[i].step]++;
            }
            for (int i = 1; i <= maxVal; i++)
                d[i] += d[i - 1];
            for (int i = 1; i < cnt; i++)
                pool[d[EXSAM_pool[i].step]--] = EXSAM_pool[i];
            pool[0] = root;
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
}
