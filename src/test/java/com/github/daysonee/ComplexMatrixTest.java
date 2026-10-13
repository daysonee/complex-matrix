package com.github.daysonee;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class ComplexMatrixTest {

    private static final double EPS = 1e-9;

    private static void assertCell(double re, double im, Complex actual) {
        assertEquals(re, actual.getRe(), EPS);
        assertEquals(im, actual.getIm(), EPS);
    }

    @Test
    void checkConstructorAndGetters() {
        Complex[][] source = {
            {Complex.parse("1+2i"), Complex.parse("3"), Complex.parse("-i")},
            {Complex.parse("0"), Complex.parse("2-i"), Complex.parse("4i")}
        };
        ComplexMatrix m = new ComplexMatrix(source);

        assertEquals(2, m.getRows());
        assertEquals(3, m.getCols());
        assertCell(1, 2, m.get(0, 0));
        assertCell(0, -1, m.get(0, 2));
        assertCell(2, -1, m.get(1, 1));
        assertCell(0, 4, m.get(1, 2));

        assertThrows(IllegalArgumentException.class, () -> new ComplexMatrix(null));
        assertThrows(IllegalArgumentException.class, () -> new ComplexMatrix(new Complex[0][0]));
        assertThrows(IllegalArgumentException.class, () -> new ComplexMatrix(new Complex[][] {null}));
        assertThrows(IllegalArgumentException.class, () -> new ComplexMatrix(new Complex[][] {{}}));
        assertThrows(IllegalArgumentException.class, () -> new ComplexMatrix(new Complex[][] {
            {Complex.ONE, Complex.ONE},
            {Complex.ONE}
        }));
        assertThrows(IllegalArgumentException.class, () -> new ComplexMatrix(new Complex[][] {
            {Complex.ONE, null}
        }));
    }

    @Test
    void checkGetOutOfBounds() {
        ComplexMatrix m = new ComplexMatrix(new Complex[][] {
            {Complex.ONE, Complex.ONE, Complex.ONE},
            {Complex.ONE, Complex.ONE, Complex.ONE}
        });

        assertThrows(IndexOutOfBoundsException.class, () -> m.get(2, 0));
        assertThrows(IndexOutOfBoundsException.class, () -> m.get(0, 3));
        assertThrows(IndexOutOfBoundsException.class, () -> m.get(-1, 0));
    }

    @Test
    void checkToString() {
        ComplexMatrix m = new ComplexMatrix(new Complex[][] {
            {Complex.parse("1+2i"), Complex.parse("3")},
            {Complex.parse("0"), Complex.parse("-i")}
        });

        assertEquals("1+2i 3\n0 -i", m.toString());
    }
}