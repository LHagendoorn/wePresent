package wepresent.wepresent;

/**
 * Created by s124006 on 23-3-2015.
 */
public class Pair extends android.util.Pair {
    private Object key;
    private Object value;

    /**
     * Constructor for a Pair.
     *
     * @param first  the first object in the Pair
     * @param second the second object in the pair
     */
    public Pair(Object first, Object second) {
        super(first, second);

        this.key = first;
        this.value = second;
    }

    public Object getKey() {
        return key;
    }

    public Object getValue() {
        return value;
    }
}
