package 树;

/**
 * Created by Tjl on 2017/7/8 14:36.
 */
public class AvlTree<AnyType extends Comparable<? super AnyType>> {

    private static class AvlNode<AnyType> {
        AnyType element;
        AvlNode<AnyType> left;
        AvlNode<AnyType> right;
        int height;

        AvlNode(AnyType theElement) {
            this(theElement, null, null);
        }

        AvlNode(AnyType theElement, AvlNode<AnyType> lt, AvlNode<AnyType> rt) {
            element = theElement;
            left = lt;
            right = rt;
        }

    }

    private int height(AvlNode<AnyType> t) {
        return t == null ? -1 : t.height;
    }

    //向AVL树的插入历程
    private AvlNode<AnyType> insert(AnyType x, AvlNode<AnyType> t) {
        if (t == null) {
            return new AvlNode<AnyType>(x, null, null);
        }

        int compareResult = x.compareTo(t.element);

        if (compareResult < 0) {
            t.left = insert(x, t.left);
        } else if (compareResult > 0) {
            t.right = insert(x, t.right);
        } else ;

        return balance(t);
    }

    private static final int ALLOWED_IMBALANCE = 1;

    private AvlNode<AnyType> balance(AvlNode<AnyType> t) {
        if (t == null) {
            return t;
        }

        if (height(t.left) - height(t.right) > ALLOWED_IMBALANCE) {
            if (height(t.left.left) >= height(t.left.right)) {
                t = rotateWithLeftChild(t);
            } else {
                t = doubleWithLeftChild(t);
            }
        } else if (height(t.right) - height(t.left) > ALLOWED_IMBALANCE) {
            if (height(t.right) - height(t.left) > ALLOWED_IMBALANCE) {
                if (height(t.right.right) >= height(t.right.left)) {
                    t = rotateWithLeftChild(t);
                } else {
                    t = doubleWithLeftChild(t);
                }
            }
        }

        t.height = Math.max(height(t.left), height(t.right)) + 1;
        return t;
    }

    //执行单旋转的例程
    private AvlNode<AnyType> rotateWithLeftChild(AvlNode<AnyType> k2) {
        AvlNode<AnyType> k1 = k2.left;
        k2.left = k1.right;
        k1.right = k2;
        k2.height = Math.max(height(k2.left), height(k2.right)) + 1;
        k1.height = Math.max(height(k1.left), k2.height) + 1;
        return k1;
    }

    //执行双旋转的例程
    private AvlNode<AnyType> doubleWithLeftChild(AvlNode<AnyType> k3) {
        k3.left = rotateWithLeftChild(k3.left);
        return rotateWithLeftChild(k3);
    }

    private AvlNode<AnyType> remove(AnyType x, AvlNode<AnyType> t) {
        if (t == null) {
            return t;
        }

        int compareResult = x.compareTo(t.element);

        if (compareResult < 0) {
            t.left = remove(x, t.left);
        } else if (compareResult > 0) {
            t.right = remove(x, t.right);
        } else if (t.left != null && t.right != null) {
            t.element = findMin(t.right).element;
            t.right = remove(t.element, t.right);
        } else {
            if (t.left != null) {

                t = t.left;
            } else {
                t = t.right;
            }
        }
        return balance(t);
    }

    private AvlNode<AnyType> findMin( AvlNode<AnyType> t )
    {
        if( t == null )
            return t;

        while( t.left != null )
            t = t.left;
        return t;
    }

}
