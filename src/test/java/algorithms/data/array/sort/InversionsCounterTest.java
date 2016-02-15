package algorithms.data.array.sort;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static org.hamcrest.MatcherAssert.assertThat;

/**
 * User: Vasily Vlasov
 * Date: 03.02.13
 */
public class InversionsCounterTest {
    private InversionsCounter inversionCounter;

    @Before
    public void setup() {
        inversionCounter = new InversionsCounter();
    }

    @Test
    public void testTrivial() {
        assertThat(inversionCounter.calculateInversionsAndSort(new int[]{2, 1}), Matchers.equalTo(1l));
    }

    @Test
    public void testTrivial1() {
        assertThat(inversionCounter.calculateInversionsAndSort(new int[]{2, 3, 1, 4}), Matchers.equalTo(2l));
    }

    @Test
    public void testTrivial2() {
        assertThat(inversionCounter.calculateInversionsAndSort(new int[]{4, 3, 2, 1}), Matchers.equalTo(6l));
    }

    @Test
    public void testNonOdd() {
        assertThat(inversionCounter.calculateInversionsAndSort(new int[]{2, 3, 1}), Matchers.equalTo(2l));
    }

    @Test
    public void testSmall() {
        assertThat(inversionCounter.calculateInversionsAndSort(new int[]{3, 7, 10, 14, 18, 19, 2, 11, 16, 17, 23, 25}), Matchers.equalTo(13l));
    }

    @Test
    public void testSmall2() {
        assertThat(inversionCounter.calculateInversionsAndSort(new int[]{1, 3, 2, 4, 5, 6}), Matchers.equalTo(1l));


    }

    @Test
    public void testSmall3() {
        assertThat(inversionCounter.calculateInversionsAndSort(new int[]{1, 3, 2, 4, 5, 6, 7, 8, 9}), Matchers.equalTo(1l));
        assertThat(inversionCounter.calculateInversionsAndSort(new int[]{1, 3, 5, 2, 4, 6}), Matchers.equalTo(3l));

    }

    @Test
    public void testSmall4() {
        assertThat(inversionCounter.calculateInversionsAndSort(new int[]{1, 3, 5, 4, 2}), Matchers.equalTo(4l));
    }

    @Test
    @Ignore
    public void testMedium() throws IOException {
        assertThat(inversionCounter.calculateInversionsAndSort(readArray("/array/quiz1.txt", 100000)), Matchers.equalTo(2407905288L));
    }

    @Test
    @Ignore
    public void testMedium1() throws IOException {
        int[] array = readArray("/array/reverse.txt", 100000);
        assertThat(inversionCounter.calculateInversionsAndSort(array), Matchers.equalTo(2507223936L));

    }

    private int[] readArray(String resource, int length) throws IOException {
        int[] ints = new int[length];

        BufferedReader reader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(resource)));

        for (int i = 0; i < ints.length; i++) {
            ints[i] = Integer.parseInt(reader.readLine());

        }


        return ints;
    }


}
