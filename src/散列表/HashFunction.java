package 散列表;

/**
 * Created by Tjl on 2017/7/9 10:42.
 */
public class HashFunction {
    /*
    *每个关键字被映射到从0到TableSize-1这个范围中的某个数，并且被放到适当的单元中，这个映射就叫做散列函数
    */

    //一个简单的散列函数
    public static int hash1(String key, int tableSize) {
        int hashVal = 0;

        for (int i = 0; i < key.length(); i++) {
            hashVal += key.charAt(i);
        }

        return hashVal % tableSize;
    }

    //另一个可能的散列函数，不是太好
    public static int hash2(String key, int tableSize) {
        return (key.charAt(0) + 27 * key.charAt(1) + 729 * key.charAt(2)) % tableSize;
    }

    //一个好的散列函数
    public static int hash3(String key, int tableSize) {
        int hashVal = 0;

        for (int i = 0; i < key.length(); i++) {
            hashVal = 37 * hashVal + key.charAt(i);

        }

        hashVal %= tableSize;
        if (hashVal < 0) {
            hashVal += tableSize;
        }

        return hashVal;




    }






}
