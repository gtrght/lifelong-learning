package algorithms.misc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * User: Vasily Vlasov
 * Date: 15.01.13
 */
public class Brackets {

    public List<String> generate(int length) {
        if(length%2 == 1) throw new IllegalArgumentException("Length must be odd");
        if(length < 0) throw new IllegalArgumentException("Length must be non-negative");

        return generate0(length);
    }

    /**
     * Generates all possible brackets ordering
     * Dosn't work :) going to rewrite this
     */
    private List<String> generate0(int length) {

        if (length == 0) {
            return Collections.singletonList("");
        } else {
            List<String> results = new ArrayList<String>();

            List<String> all = generate(length - 2);

            for (String s : all) {
                results.add("()" + s);
                results.add("(" + s + ")");
            }
            return results;
        }
    }
}
