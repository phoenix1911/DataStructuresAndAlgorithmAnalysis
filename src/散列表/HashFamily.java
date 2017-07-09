package 散列表;

/**
 * Created by Tjl on 2017/7/9 15:25.
 */
public interface HashFamily<AnyType> {
    /**
     *实现布谷鸟散列需要一个散列函数的集合
     * 该接口可用于将散列函数族发送给布谷鸟散列
     */
    int hash(AnyType x, int which);

    int getNumberOfFunctions();

    void generateNewFunctions();


}
