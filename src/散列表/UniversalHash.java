package 散列表;

/**
 * Created by Tjl on 2017/7/10 20:56.
 */
public class UniversalHash {
    //通用散列法

    //通用散列的简单实现
    public static int universalHash(int x, int A, int B, int P, int M) {
        return (int) ((((long ) A * x) + B) % P) % M;

    }

    //通用散列的简单实现2
    //卡特-韦格曼技巧
    public static final int DIGS = 31;
    public static final int mersennep = (1<<DIGS) - 1;

    public static int uninersalHash(int x, int A, int B, int M) {
        long hashVal = (long) A * x + B;

        hashVal = ((hashVal >> DIGS) + (hashVal & mersennep));

        if (hashVal >= mersennep) {
            hashVal -= mersennep;
        }

        return (int) hashVal % M;
    }
}
