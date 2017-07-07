/**
 * Created by Tjl on 2017/7/7 23:06.
 */
public interface Iterator<AnyType> {
    boolean hasNext();

    AnyType next();

    void remove();
}
