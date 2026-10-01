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

    
}
