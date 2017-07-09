package 散列表;

import java.util.Random;

/**
 * Created by Tjl on 2017/7/9 15:30.
 */
public class CuckooHashTable<AnyType> {

    private static final double MAX_LOAD = 0.4;
    private static final int ALLOWED_REHASHES = 1;
    private static final int DEFAULT_TABLE_SIZE = 101;

    private final HashFamily<? super AnyType> hashFunctions;
    private final int numHashFunctions;
    private AnyType [ ] array;
    private int currentSize;
    private int reshashes = 0;
    private Random r = new Random();


    public CuckooHashTable(HashFamily<? super AnyType> hf) {
        this(hf, DEFAULT_TABLE_SIZE);
    }

    public CuckooHashTable(HashFamily<? super AnyType> hf, int size) {
        allocateArray(nextPrime(size));
        doClear();
        hashFunctions = hf;
        numHashFunctions = hf.getNumberOfFunctions();
    }

    public void makeEmpty() {
        doClear();
    }

    public boolean contains(AnyType x) {
        return findPos(x) != -1;
    }

    //用于选择合适的散列函数，然后将它的值按比例对应到一个有效的数组下标
    public int myhash(AnyType x, int which) {
        int hashVal = hashFunctions.hash(x, which);

        hashVal %= array.length;
        if (hashVal < 0) {
            hashVal += array.length;
        }

        return hashVal;
    }

    //去查所有散列函数，返回包含x项的数组下表，如果找不到x就返回-1；
    public int findPos(AnyType x) {
        for (int i = 0; i < numHashFunctions; i++) {
            int pos = myhash(x, i);
            if (array[pos] != null && array[pos].equals(x)) {
                return pos;
            }

        }
        return -1;
    }

    public boolean remove(AnyType x) {
        int pos = findPos(x);

        if (pos != -1) {
            array[pos] = null;
            currentSize--;
        }

        return pos != -1;
    }

    public boolean insert(AnyType x) {
        if (contains(x)) {
            return false;
        }
        if (currentSize >= array.length * MAX_LOAD) {
            expand();
        }

        return insertHelper1(x);
    }

    private void expand( )
    {
        rehash( (int) ( array.length / MAX_LOAD ) );
    }

    private void rehash( )
    {
        System.out.println( "NEW HASH FUNCTIONS " + array.length );
        hashFunctions.generateNewFunctions( );
        rehash( array.length );
    }

    private void rehash( int newLength )
    {
        System.out.println( "REHASH: " + array.length + " " + newLength + " " + currentSize );
        AnyType [ ] oldArray = array;

        allocateArray( nextPrime( newLength ) );

        currentSize = 0;


        for( AnyType str : oldArray )
            if( str != null )
                insert( str );
    }

    public void doClear() {
        currentSize = 0;
        for (int i = 0; i < array.length; i++) {
            array[i] = null;
        }
    }

    private void allocateArray(int arraySize) {
        array = (AnyType[]) new Object[arraySize];
    }

    private boolean insertHelper1(AnyType x) {

        final int COUNT_LIMIT = 100;

        while (true) {
            int lastPos = -1;
            int pos;


            for (int count = 0; count < COUNT_LIMIT; count++) {
                for (int i = 0; i < numHashFunctions; i++) {
                    pos = myhash(x, i);

                    if (array[pos] == null) {
                        array[pos] = x;
                        currentSize++;
                        return true;
                    }
                }

                int i = 0;
                do {
                    pos = myhash(x, r.nextInt(numHashFunctions));
                } while (pos == lastPos && i++ < 5);

                AnyType tmp = array[lastPos = pos];
                array[pos] = x;
                x = tmp;
            }

            if (++reshashes > ALLOWED_REHASHES) {
                expand();
                reshashes = 0;
            } else {
                rehash();
            }
        }
    }

    protected static int nextPrime( int n )
    {
        if( n % 2 == 0 )
            n++;

        for( ; !isPrime( n ); n += 2 )
            ;

        return n;
    }

    private static boolean isPrime( int n )
    {
        if( n == 2 || n == 3 )
            return true;

        if( n == 1 || n % 2 == 0 )
            return false;

        for( int i = 3; i * i <= n; i += 2 )
            if( n % i == 0 )
                return false;

        return true;
    }

    public int capacity( )
    {
        return array.length;
    }

    public static void main( String [ ] args )
    {
        long cumulative = 0;

        final int NUMS = 2000000;
        final int GAP  =   37;
        final int ATTEMPTS = 10;

        System.out.println( "Checking... (no more output means success)" );

        for( int att  = 0; att < ATTEMPTS; att++ )
        {
            System.out.println( "ATTEMPT: " + att );

            CuckooHashTable<String> H = new CuckooHashTable<>( new StringHashFamily( 3 ) );
            //QuadraticProbingHashTable<String> H = new QuadraticProbingHashTable<>( );

            long startTime = System.currentTimeMillis( );

            for( int i = GAP; i != 0; i = ( i + GAP ) % NUMS )
                H.insert( ""+i );
            for( int i = GAP; i != 0; i = ( i + GAP ) % NUMS )
                if( H.insert( ""+i ) )
                    System.out.println( "OOPS!!! " + i );
            for( int i = 1; i < NUMS; i+= 2 )
                H.remove( ""+i );

            for( int i = 2; i < NUMS; i+=2 )
                if( !H.contains( ""+i ) )
                    System.out.println( "Find fails " + i );

            for( int i = 1; i < NUMS; i+=2 )
            {
                if( H.contains( ""+i ) )
                    System.out.println( "OOPS!!! " +  i  );
            }


            long endTime = System.currentTimeMillis( );

            cumulative += endTime - startTime;

            if( H.capacity( ) > NUMS * 4 )
                System.out.println( "LARGE CAPACITY " + H.capacity( ) );
        }

        System.out.println( "Total elapsed time is: " + cumulative );
    }


}

