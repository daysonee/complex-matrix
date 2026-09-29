package com.github.daysonee;

public final class Complex {
    
    public static final Complex ZERO = new Complex(0, 0);
    public static final Complex ONE = new Complex(1, 0);
    public static final Complex I = new Complex(0, 1);

    private final double re;
    private final double im;


    public Complex(double re, double im) {
        this.re = re;
        this.im = im;
    }
    public Complex(){
        this.re = 0;
        this.im = 0;
    }

    public static Complex real(double re) {
        return new Complex(re, 0);
    }

    public double getRe() {
        return this.re;
    }

    public double getIm() {
        return this.im;
    }
}
