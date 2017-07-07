package 表_栈_队列;

import java.util.ListIterator;

/**
 * Created by Tjl on 2017/7/7 22:44.
 */
public interface List<AnyType> extends Collection {
    AnyType get(int idx);

    AnyType set(int idx, AnyType newVal);

    void add(int idx, AnyType x);

    void remove(int idx);

    ListIterator<AnyType> listIterator(int pos);
}
