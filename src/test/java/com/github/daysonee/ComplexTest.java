package com.github.daysonee;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class ComplexTest {

    private static final double EPS = 1e-9;

    @Test
    void checkGettersAndConstructor() {
        Complex a = new Complex(3, -5);

        assertEquals(3, a.getRe(), EPS);
        assertEquals(-5, a.getIm(), EPS);
    }

    @Test
    void checkOne() {
        assertEquals(1, Complex.ONE.getRe(), EPS);
        assertEquals(0, Complex.ONE.getIm(), EPS);
    }

    @Test
    void checkI() {
        assertEquals(0, Complex.I.getRe(), EPS);
        assertEquals(1, Complex.I.getIm(), EPS);
    }

    @Test
    void checkZero() {
        assertEquals(0, Complex.ZERO.getRe(), EPS);
        assertEquals(0, Complex.ZERO.getIm(), EPS);
    }

    @Test
    void checkReal() {
        Complex a = Complex.real(2);
        assertEquals(2, a.getRe(), EPS);
        assertEquals(0, a.getIm(), EPS);
    }
}
