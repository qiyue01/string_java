public class hashstring
{
    class string_hash
    {
        public static final long prime2 = 131;
        public static final long prime1 = 267;
        public static final long mod1 = (long)9e8+11;
        public static final long mod2  = 19260817;
        long prime_n[],hash[];
        long mod,prime;
        int lcp2,N;
        string_hash(long Mod,long Prime,int n)
        {
            mod=Mod;
            prime=Prime;
            N=n;
            prime_n=new long[n];
            hash=new long[n];
        }
        void init1()//切记初始化
        {
            prime_n[0] = 0;
            prime_n[1] = prime;
            for (int i = 2; i < N; ++i)
                prime_n[i] = (prime_n[i - 1] * prime) % mod;
        }
        void trans1(String str)
        {
            hash[0] = 0;
            for (int i = 0; i < str.length(); ++i)
                hash[i + 1] = (hash[i] * prime + str.charAt(i)) % mod;
        }
        long substring1(int l, int r)
        {
            return ((hash[r] - hash[l - 1] * prime_n[r - l + 1]) % mod + mod) % mod;
        }
        int lcp(int L1, int R1, int L2, int R2)
        {
            lcp2 = 0;
            Lcp(L1, L2, 1, Math.min(R1 - L1+1, R2 - L2+1));
            return lcp2;
        }
        int lsp(int L1,int R1,int L2,int R2)
        {
            lcp2 = 0;
            Lsp(L1, L2, 1, Math.min(R1 - L1 + 1, R2 - L2 + 1));
            return lcp2;
        }
        void Lsp(int i1, int i2, int L, int R)
        {
            int mid = (L + R) / 2;
            if (L <= R)
            {
                if (substring1(i1 - mid + 1, i1) == substring1(i2 - mid + 1, i2))
                {
                    Lsp(i1, i2, mid + 1, R);
                    lcp2 = Math.max(lcp2, mid);
                }
                else
                    Lsp(i1, i2, L, mid - 1);
            }
        }
        void Lcp(int i1, int i2, int L, int R)
        {
            int mid = (L + R) / 2;
            if (L <= R)
            {
                if (substring1(i1, i1+mid-1) == substring1(i2, i2+mid-1))
                {
                    Lcp(i1, i2, mid + 1, R);
                    lcp2 = Math.max(lcp2, mid);
                }
                else
                    Lcp(i1, i2, L, mid - 1);
            }
        }
    }
}
