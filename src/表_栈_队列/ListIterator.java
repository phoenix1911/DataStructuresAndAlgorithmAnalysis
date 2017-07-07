package 表_栈_队列;

import java.util.Iterator;

/**
 * Created by Tjl on 2017/7/7 22:48.
 */
public interface ListIterator<AnyType> extends Iterator<AnyType> {
    boolean hasPrevious();
    AnyType previous();

    void add(AnyType x);
    void set(AnyType newVal);
}
