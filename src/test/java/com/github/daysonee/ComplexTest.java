package com.github.daysonee;

import static org.junit.jupiter.api.Assertions.*;

import java.io.EOFException;

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

    @Test
    void checkAdd() {
        Complex a = new Complex(1, 2);
        Complex b = new Complex(3, 4);
        Complex c = a.add(b);
        assertEquals(4, c.getRe(), EPS);
        assertEquals(6, c.getIm(), EPS);
    }

    @Test
    void checkSubtract() {
        Complex a = new Complex(1, 2);
        Complex b = new Complex(3, 4);
        Complex c = a.subtract(b);
        assertEquals(-2, c.getRe(), EPS);
        assertEquals(-2, c.getIm(), EPS);
    }

    @Test 
    void checkMultiply(){
        Complex a = new Complex(1, 2);
        Complex b = new Complex(3, 4);
        Complex c = a.multiply(b);
        
        assertEquals(-5, c.getRe(), EPS);
        assertEquals(10, c.getIm(), EPS);

        Complex d = Complex.I.multiply(Complex.I);
        assertEquals(-1, d.getRe(), EPS);
        assertEquals(0, d.getIm(), EPS);

        Complex e = a.multiply(Complex.ONE);
        assertEquals(a.getRe(), e.getRe(), EPS);
        assertEquals(a.getIm(), e.getIm(), EPS);

        Complex f = a.multiply(Complex.ZERO);
        assertEquals(0, f.getRe(), EPS);
        assertEquals(0, f.getIm(), EPS);
    }

        @Test
    void checkConjugate() {
        Complex a = new Complex(3, 4);
        Complex b = a.conjugate();

        assertEquals(3, b.getRe(), EPS);
        assertEquals(-4, b.getIm(), EPS);
    }

    @Test
    void checkNegate() {
        Complex a = new Complex(3, -4);
        Complex b = a.negate();

        assertEquals(-3, b.getRe(), EPS);
        assertEquals(4, b.getIm(), EPS);
    }

    @Test
    void checkAbs() {
        Complex a = new Complex(3, 4);

        assertEquals(5, a.abs(), EPS);
    }

    @Test
    void checkIsZero() {
        assertTrue(Complex.ZERO.isZero());
        assertFalse(Complex.ONE.isZero());
    }

    @Test
    void checkDivide() {
        Complex a = new Complex(-5, 10);
        Complex b = new Complex(3, 4);
        Complex c = a.divide(b);

        assertEquals(1, c.getRe(), EPS);
        assertEquals(2, c.getIm(), EPS);
    }

    @Test
    void checkDivideByZero() {
        Complex a = new Complex(1, 2);

        assertThrows(ArithmeticException.class, () -> a.divide(Complex.ZERO));
    }

        @Test
    void checkToString() {
        assertEquals("3+4i", new Complex(3, 4).toString());
        assertEquals("3-4i", new Complex(3, -4).toString());
        assertEquals("-2i", new Complex(0, -2).toString());
        assertEquals("5", new Complex(5, 0).toString());
        assertEquals("0", Complex.ZERO.toString());
        assertEquals("1.5-2.5i", new Complex(1.5, -2.5).toString());

        assertEquals("i", Complex.I.toString());
        assertEquals("-i", new Complex(0, -1).toString());
        assertEquals("3+i", new Complex(3, 1).toString());
        assertEquals("3-i", new Complex(3, -1).toString());

        assertEquals("0.3", new Complex(0.1 + 0.2, 0).toString());
        assertEquals("5", new Complex(5, -1e-12).toString());
        assertEquals("0", new Complex(-0.0, 0).toString());
    }

        @Test
    void checkParse() {
        Complex a = Complex.parse("3+4i");
        assertEquals(3, a.getRe(), EPS);
        assertEquals(4, a.getIm(), EPS);

        Complex b = Complex.parse("3-4i");
        assertEquals(3, b.getRe(), EPS);
        assertEquals(-4, b.getIm(), EPS);

        Complex c = Complex.parse("-5");
        assertEquals(-5, c.getRe(), EPS);
        assertEquals(0, c.getIm(), EPS);

        Complex d = Complex.parse("1.5-2.5i");
        assertEquals(1.5, d.getRe(), EPS);
        assertEquals(-2.5, d.getIm(), EPS);
    }

    @Test
    void checkParseSpecialCases() {
        Complex a = Complex.parse("i");
        assertEquals(0, a.getRe(), EPS);
        assertEquals(1, a.getIm(), EPS);

        Complex b = Complex.parse("-i");
        assertEquals(0, b.getRe(), EPS);
        assertEquals(-1, b.getIm(), EPS);

        Complex c = Complex.parse("3-i");
        assertEquals(3, c.getRe(), EPS);
        assertEquals(-1, c.getIm(), EPS);

        Complex d = Complex.parse("-2i");
        assertEquals(0, d.getRe(), EPS);
        assertEquals(-2, d.getIm(), EPS);
    }

}
