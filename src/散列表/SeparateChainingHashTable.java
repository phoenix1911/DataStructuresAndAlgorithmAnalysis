package 散列表;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Tjl on 2017/7/9 10:55.
 */
public class SeparateChainingHashTable<AnyType> {
    /**
     * 如果当一个元素被插入是与一个已经插入的元素散列到相同的只，那么就产生一个冲突
     * 解决冲突的第一个方法通常叫做 分离链接法 separete chaining
     * 其做法是将散列到同一个值的所有元素保留到一个表中；
     */
    private static final int DEFAULT_TABLE_SIZE = 101;

    private List<AnyType> [] theLists;
    private int currentSize;

    public SeparateChainingHashTable() {
        this(DEFAULT_TABLE_SIZE);
    }

    public SeparateChainingHashTable(int size) {
        theLists = new LinkedList[nextPrime(size)];
        for (int i = 0; i < theLists.length; i++) {
            theLists[i] = new LinkedList<>();
        }
    }

    public void insert(AnyType x) {
        List<AnyType> whichList = theLists[myhash(x)];
        if (!whichList.contains(x)) {
            whichList.add(x);

            if (++currentSize > theLists.length) {
                rehash();
            }
        }
    }

    public void remove(AnyType x) {
        List<AnyType> whichList = theLists[myhash(x)];
        if (whichList.contains(x)) {
            whichList.remove(x);
            currentSize--;
        }
    }

    public boolean contains(AnyType x) {
        List<AnyType> whichList = theLists[myhash(x)];
        return whichList.contains(x);
    }

    public void makeEmpty() {
        for (int i = 0; i < theLists.length; i++) {
            theLists[i].clear();

        }
        currentSize = 0;
    }




    private void rehash() {
        List<AnyType> [ ]  oldLists = theLists;

        // Create new double-sized, empty table
        theLists = new List[ nextPrime( 2 * theLists.length ) ];
        for( int j = 0; j < theLists.length; j++ )
            theLists[ j ] = new LinkedList<>( );

        // Copy table over
        currentSize = 0;
        for( List<AnyType> list : oldLists )
            for( AnyType item : list )
                insert( item );
    }

    private int myhash(AnyType x) {

        int hashVal = x.hashCode();

        hashVal %= theLists.length;
        if (hashVal < 0) {
            hashVal += theLists.length;
        }

        return hashVal;

    }

    private static int nextPrime(int n) {
        if( n % 2 == 0 )
            n++;

        for( ; !isPrime( n ); n += 2 )
            ;

        return n;
    }

    private static boolean isPrime( int n) {
        if( n == 2 || n == 3 )
            return true;

        if( n == 1 || n % 2 == 0 )
            return false;

        for( int i = 3; i * i <= n; i += 2 )
            if( n % i == 0 )
                return false;

        return true;
    }



    public static void main( String [ ] args )
    {
        SeparateChainingHashTable<Integer> H = new SeparateChainingHashTable<>( );

        long startTime = System.currentTimeMillis( );

        final int NUMS = 2000000;
        final int GAP  =   37;

        System.out.println( "Checking... (no more output means success)" );

        for( int i = GAP; i != 0; i = ( i + GAP ) % NUMS )
            H.insert( i );
        for( int i = 1; i < NUMS; i+= 2 )
            H.remove( i );

        for( int i = 2; i < NUMS; i+=2 )
            if( !H.contains( i ) )
                System.out.println( "Find fails " + i );

        for( int i = 1; i < NUMS; i+=2 )
        {
            if( H.contains( i ) )
                System.out.println( "OOPS!!! " +  i  );
        }

        long endTime = System.currentTimeMillis( );

        System.out.println( "Elapsed time: " + (endTime - startTime) );
    }



}
