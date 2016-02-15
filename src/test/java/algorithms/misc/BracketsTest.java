package algorithms.misc;

import org.hamcrest.Matchers;
import org.junit.Test;

import static org.junit.Assert.assertThat;

/**
 * User: Vasily Vlasov
 * Date: 16.01.13
 */
public class BracketsTest {
    @Test
    public void testGeneration() {
        assertThat(new Brackets().generate(2).size(), Matchers.equalTo(1));

        assertThat(new Brackets().generate(4).size(), Matchers.equalTo(2));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNonOddArgument() {
        new Brackets().generate(3);
    }
    @Test(expected = IllegalArgumentException.class)
    public void testNegativeArgument() {
        new Brackets().generate(-1);
    }
}
