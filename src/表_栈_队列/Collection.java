package 表_栈_队列;

/**
 * Created by Administrator on 2017/7/6.
 */
public interface Collection<AnyType> extends Iterable<AnyType> {

    /*collection接口扩展了iterable接口*/
    int size();

    boolean isEmpty();

    void clear();

    boolean contains(AnyType x);

    boolean add(AnyType x);

    boolean remove(AnyType x);

    java.util.Iterator<AnyType> iterator();

    }


