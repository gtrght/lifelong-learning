package algorithms.misc;

import fj.Effect;
import fj.F;
import fj.data.Array;

import static fj.data.Array.array;
import static fj.data.Array.single;

/**
 * User: Vasily Vlasov
 * Date: 15.01.13
 */
public class Pattern1 {

    public static final F<Character, Character> cloneItem = new F<Character, Character>() {
        @Override
        public Character f(Character character) {
            return character;
        }
    };

    /**
     * suppose there is a pattern 1?0???1
     * print all the possible values
     */

    private Array<Array<Character>> calculate0(Array<Character> pattern, int index, Array<Array<Character>> resultHolder) {
        if (pattern.length() > index) {
            if (pattern.get(index) == '?') {
                Array<Character> copy = pattern.map(cloneItem);
                copy.set(index, '0');
                resultHolder = calculate0(copy, index + 1, resultHolder);
                copy.set(index, '1');
                resultHolder = calculate0(copy, index + 1, resultHolder);
            } else
                resultHolder = calculate0(pattern, index + 1, resultHolder);
        } else {
            resultHolder = resultHolder.append(single(pattern.map(cloneItem)));
        }
        return resultHolder;
    }

    public Array<Array<Character>> calculateAll(String pattern) {
        Character[] chars = new Character[pattern.length()];
        for (int i = 0; i < chars.length; i++) {
            chars[i] = pattern.charAt(i);
        }
        return calculate0(array(chars), 0, Array.<Array<Character>>empty());
    }

    public static void main(String[] args) {
        Array<Array<Character>> arrays = new Pattern1().calculateAll("1?0???10");
        arrays.foreach(new Effect<Array<Character>>() {
            @Override
            public void e(Array<Character> characters) {
                characters.foreach(new Effect<Character>() {
                    @Override
                    public void e(Character character) {
                        System.out.print(character);
                    }
                });
                System.out.println();
            }
        });
    }
}
