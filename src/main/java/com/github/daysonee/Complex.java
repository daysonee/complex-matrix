package com.github.daysonee;

import java.math.BigDecimal;
import java.math.RoundingMode;

public final class Complex {

    public static final double EPS = 1e-9;

    public static final Complex ZERO = new Complex(0, 0);
    public static final Complex ONE = new Complex(1, 0);
    public static final Complex I = new Complex(0, 1);

    private final double re;
    private final double im;

    public Complex(double re, double im) {
        this.re = re;
        this.im = im;
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

    public Complex add(Complex x) {
        return new Complex(this.re + x.re, this.im + x.im);
    }

    public Complex subtract(Complex x) {
        return new Complex(this.re - x.re, this.im - x.im);
    }

    public Complex multiply(Complex x) {
        double real = this.re * x.re - this.im * x.im;
        double image = this.re * x.im + this.im * x.re;

        return new Complex(real, image);
    }

    public boolean isZero() {
        if (Math.abs(this.re) < EPS && Math.abs(this.im) < EPS) {
            return true;
        }
        return false;
    }

    public Complex divide(Complex x) {
        double denominator = x.re * x.re + x.im * x.im;
        if (x.isZero()) {
            throw new ArithmeticException("Деление на нулевое комплексное");
        }
        double real = (this.re * x.re + this.im * x.im) / denominator;
        double image = (this.im * x.re - this.re * x.im) / denominator;

        return new Complex(real, image);
    }

    public Complex conjugate() {
        return new Complex(re, -im);
    }

    public Complex negate() {
        return new Complex(-re, -im);
    }

    public double abs() {
        return (Math.sqrt(re * re + im * im));
    }

    private static String format(double num) {
        String s = BigDecimal.valueOf(num).setScale(6, RoundingMode.HALF_UP).stripTrailingZeros().toPlainString();
        return s;

    }

    @Override
    public String toString() {
        String sign = this.im > 0 ? "+" : "-";
        String realString = format(re);
        String imageString = format(Math.abs(im));
        if (imageString.equals("0") == true) {
            return realString;
        }
        imageString = imageString.equals("1") == true ? "i" : imageString + "i";
        if (realString.equals("0") == true) {
            return sign.equals("+") == true ? imageString : sign + imageString;
        }
        return realString + sign + imageString;

    }

    private static double parseCoefficient(String s) {
        if (s.equals("+") || s.isEmpty()) {
            return 1;
        }
        if (s.equals("-")) {
            return -1;
        }
        return Double.parseDouble(s);
    }

    public static Complex parse(String s) {
        if (s == null || s.isEmpty()) {
            throw new IllegalArgumentException("Строка пустая");
        }
        if (s.charAt(s.length() - 1) != 'i') {
            return new Complex(Double.parseDouble(s), 0);
        }
        s = s.substring(0, s.length() - 1);
        int plusIndex = s.lastIndexOf("+");
        int minusIndex = s.lastIndexOf("-");
        int signIndex = Math.max(plusIndex, minusIndex);
        if (signIndex == 0 || signIndex == -1) {
            return new Complex(0, parseCoefficient(s));
        }
        return new Complex(Double.parseDouble(s.substring(0, signIndex)), parseCoefficient(s.substring(signIndex)));
    }

}
