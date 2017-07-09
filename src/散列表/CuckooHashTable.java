package 散列表;

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
            expend();
        }

        return insertHelper1(x);
    }

    public void expend() {

    }

    public void rehash() {

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

}

