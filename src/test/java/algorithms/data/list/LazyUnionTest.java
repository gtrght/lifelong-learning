package algorithms.data.list;

import algorithms.data.LazyUnion;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.hamcrest.MatcherAssert.assertThat;

/**
 * User: Vasily Vlasov
 * Date: 26.01.13
 */
public class LazyUnionTest {

    @Test
    public void testLazyUnionCreate() {
        LazyUnion<String> union = new LazyUnion<String>(Arrays.asList("1", "2", "3"));

        assertThat(union.findRoot("1"), Matchers.equalTo(0));
        assertThat(union.findRoot("2"), Matchers.equalTo(1));
        assertThat(union.findRoot("3"), Matchers.equalTo(2));
    }


    @Test
    public void testLazyUnionMerge() {
        LazyUnion<String> union = new LazyUnion<String>(Arrays.asList("1", "2", "3", "4", "5", "6"));

        union.merge("1", "2");
        assertThat(union.findRoot("1"), Matchers.equalTo(0));
        assertThat(union.findRoot("2"), Matchers.equalTo(0));
        assertThat(union.rank(union.findRoot("1")), Matchers.equalTo(1));
        assertThat(union.rank(1), Matchers.equalTo(0));

        union.merge("2", "3");
        assertThat(union.rank(union.findRoot("1")), Matchers.equalTo(1));
        assertThat(union.findRoot("3"), Matchers.equalTo(0));

        union.merge("5", "6");
        assertThat(union.findRoot("6"), Matchers.equalTo(union.findRoot("5")));
        assertThat(union.rank(union.findRoot("6")), Matchers.equalTo(1));


        assertThat(union.same("1", "3"), Matchers.equalTo(true));
        assertThat(union.same("1", "6"), Matchers.equalTo(false));
    }
}
