package algorithms.data.array.sort;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

/**
 * User: Vasily Vlasov
 * Date: 13.12.12
 */
public abstract class BaseSortTester {
    protected abstract Sorter getSorter();

    @Test
    public void testOneItem() {
        Assert.assertThat(getSorter().sort(new int[]{3}), Matchers.equalTo(new int[]{3}));
    }

    @Test
    public void testEmpty() {
        Assert.assertThat(getSorter().sort(new int[]{}), Matchers.equalTo(new int[]{}));
    }

    @Test
    public void testTwoItems() {
        Assert.assertThat(getSorter().sort(new int[]{3, 1}), Matchers.equalTo(new int[]{1, 3}));
    }

    @Test
    public void testThreeItems() {
        Assert.assertThat(getSorter().sort(new int[]{3, 1, 2}), Matchers.equalTo(new int[]{1, 2, 3}));
    }

    @Test
    public void testSeveralItems() {
        int[] input = {3, 1, -12, 323, Integer.MIN_VALUE, Integer.MAX_VALUE};
        int[] copy = Arrays.copyOf(input, input.length);
        Arrays.sort(copy);
        Assert.assertThat(getSorter().sort(input), Matchers.equalTo(copy));
    }

}
