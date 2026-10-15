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
                { Complex.parse("1+2i"), Complex.parse("3"), Complex.parse("-i") },
                { Complex.parse("0"), Complex.parse("2-i"), Complex.parse("4i") }
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
        assertThrows(IllegalArgumentException.class, () -> new ComplexMatrix(new Complex[][] { null }));
        assertThrows(IllegalArgumentException.class, () -> new ComplexMatrix(new Complex[][] { {} }));
        assertThrows(IllegalArgumentException.class, () -> new ComplexMatrix(new Complex[][] {
                { Complex.ONE, Complex.ONE },
                { Complex.ONE }
        }));
        assertThrows(IllegalArgumentException.class, () -> new ComplexMatrix(new Complex[][] {
                { Complex.ONE, null }
        }));
    }

    @Test
    void checkGetOutOfBounds() {
        ComplexMatrix m = new ComplexMatrix(new Complex[][] {
                { Complex.ONE, Complex.ONE, Complex.ONE },
                { Complex.ONE, Complex.ONE, Complex.ONE }
        });

        assertThrows(IndexOutOfBoundsException.class, () -> m.get(2, 0));
        assertThrows(IndexOutOfBoundsException.class, () -> m.get(0, 3));
        assertThrows(IndexOutOfBoundsException.class, () -> m.get(-1, 0));
    }

    @Test
    void checkToString() {
        ComplexMatrix m = new ComplexMatrix(new Complex[][] {
                { Complex.parse("1+2i"), Complex.parse("3") },
                { Complex.parse("0"), Complex.parse("-i") }
        });

        assertEquals("1+2i 3\n0 -i", m.toString());
    }

    private static void assertMatrix(ComplexMatrix expected, ComplexMatrix actual) {
        assertEquals(expected.getRows(), actual.getRows());
        assertEquals(expected.getCols(), actual.getCols());
        for (int i = 0; i < expected.getRows(); ++i) {
            for (int j = 0; j < expected.getCols(); ++j) {
                assertCell(expected.get(i, j).getRe(), expected.get(i, j).getIm(), actual.get(i, j));
            }
        }
    }

    @Test
    void checkDefensiveCopy() {
        Complex[][] source = {
                { Complex.parse("1+2i"), Complex.parse("3") }
        };
        ComplexMatrix m = new ComplexMatrix(source);

        source[0][0] = Complex.ZERO;

        assertCell(1, 2, m.get(0, 0));
    }

    @Test
    void checkAdd() {
        ComplexMatrix a = new ComplexMatrix(new Complex[][] {
                { Complex.parse("1+2i"), Complex.parse("3"), Complex.parse("-i") },
                { Complex.parse("0"), Complex.parse("2-i"), Complex.parse("4i") }
        });
        ComplexMatrix b = new ComplexMatrix(new Complex[][] {
                { Complex.parse("1-i"), Complex.parse("-3"), Complex.parse("2i") },
                { Complex.parse("5"), Complex.parse("1+i"), Complex.parse("1") }
        });
        ComplexMatrix expected = new ComplexMatrix(new Complex[][] {
                { Complex.parse("2+i"), Complex.parse("0"), Complex.parse("i") },
                { Complex.parse("5"), Complex.parse("3"), Complex.parse("1+4i") }
        });

        assertMatrix(expected, a.add(b));

        assertCell(1, 2, a.get(0, 0));
        assertCell(1, -1, b.get(0, 0));

        ComplexMatrix c = new ComplexMatrix(new Complex[][] { { Complex.ONE, Complex.ONE } });
        ComplexMatrix d = new ComplexMatrix(new Complex[][] { { Complex.ONE }, { Complex.ONE } });
        assertThrows(IllegalArgumentException.class, () -> c.add(d));
    }

    @Test
    void checkSubtract() {
        ComplexMatrix a = new ComplexMatrix(new Complex[][] {
                { Complex.parse("3+2i"), Complex.parse("1"), Complex.parse("5i") },
                { Complex.parse("0"), Complex.parse("2-i"), Complex.parse("4") }
        });
        ComplexMatrix b = new ComplexMatrix(new Complex[][] {
                { Complex.parse("1+i"), Complex.parse("1"), Complex.parse("2i") },
                { Complex.parse("2"), Complex.parse("1+i"), Complex.parse("1") }
        });
        ComplexMatrix expected = new ComplexMatrix(new Complex[][] {
                { Complex.parse("2+i"), Complex.parse("0"), Complex.parse("3i") },
                { Complex.parse("-2"), Complex.parse("1-2i"), Complex.parse("3") }
        });

        assertMatrix(expected, a.subtract(b));

        ComplexMatrix c = new ComplexMatrix(new Complex[][] { { Complex.ONE, Complex.ONE } });
        ComplexMatrix d = new ComplexMatrix(new Complex[][] { { Complex.ONE }, { Complex.ONE } });
        assertThrows(IllegalArgumentException.class, () -> c.subtract(d));
    }

    @Test
    void checkMultiply() {
        ComplexMatrix a = new ComplexMatrix(new Complex[][] {
                { Complex.parse("1"), Complex.parse("2"), Complex.parse("i") },
                { Complex.parse("0"), Complex.parse("1+i"), Complex.parse("3") }
        });
        ComplexMatrix b = new ComplexMatrix(new Complex[][] {
                { Complex.parse("1"), Complex.parse("i") },
                { Complex.parse("2"), Complex.parse("0") },
                { Complex.parse("1"), Complex.parse("1") }
        });
        ComplexMatrix expected = new ComplexMatrix(new Complex[][] {
                { Complex.parse("5+i"), Complex.parse("2i") },
                { Complex.parse("5+2i"), Complex.parse("3") }
        });

        assertMatrix(expected, a.multiply(b));

        assertThrows(IllegalArgumentException.class, () -> a.multiply(a));
    }

    @Test
    void checkTranspose() {
        ComplexMatrix a = new ComplexMatrix(new Complex[][] {
                { Complex.parse("1+2i"), Complex.parse("3"), Complex.parse("-i") },
                { Complex.parse("0"), Complex.parse("2-i"), Complex.parse("4i") }
        });
        ComplexMatrix expected = new ComplexMatrix(new Complex[][] {
                { Complex.parse("1+2i"), Complex.parse("0") },
                { Complex.parse("3"), Complex.parse("2-i") },
                { Complex.parse("-i"), Complex.parse("4i") }
        });

        assertMatrix(expected, a.transpose());
    }

    @Test
    void checkDeterminant() {
        ComplexMatrix a = new ComplexMatrix(new Complex[][] {
                { Complex.parse("1"), Complex.parse("2") },
                { Complex.parse("3"), Complex.parse("4") }
        });
        assertCell(-2, 0, a.determinant());

        ComplexMatrix b = new ComplexMatrix(new Complex[][] {
                { Complex.parse("i"), Complex.parse("1") },
                { Complex.parse("1"), Complex.parse("i") }
        });
        assertCell(-2, 0, b.determinant());

        ComplexMatrix c = new ComplexMatrix(new Complex[][] {
                { Complex.parse("1+i"), Complex.parse("2") },
                { Complex.parse("0"), Complex.parse("1-i") }
        });
        assertCell(2, 0, c.determinant());

        ComplexMatrix d = new ComplexMatrix(new Complex[][] {
                { Complex.parse("2"), Complex.parse("1"), Complex.parse("1") },
                { Complex.parse("1"), Complex.parse("3"), Complex.parse("2") },
                { Complex.parse("1"), Complex.parse("0"), Complex.parse("0") }
        });
        assertCell(-1, 0, d.determinant());

        ComplexMatrix e = new ComplexMatrix(new Complex[][] {
                { Complex.parse("0"), Complex.parse("1") },
                { Complex.parse("1"), Complex.parse("0") }
        });
        assertCell(-1, 0, e.determinant());

        assertCell(1, 0, ComplexMatrix.I(4).determinant());
    }

    @Test
    void checkDeterminantSingularAndErrors() {
        ComplexMatrix singular = new ComplexMatrix(new Complex[][] {
                { Complex.parse("1"), Complex.parse("2") },
                { Complex.parse("2"), Complex.parse("4") }
        });
        assertCell(0, 0, singular.determinant());

        ComplexMatrix notSquare = new ComplexMatrix(new Complex[][] { { Complex.ONE, Complex.ONE } });
        assertThrows(IllegalArgumentException.class, () -> notSquare.determinant());

        ComplexMatrix a = new ComplexMatrix(new Complex[][] {
                { Complex.parse("1"), Complex.parse("2") },
                { Complex.parse("3"), Complex.parse("4") }
        });
        a.determinant();
        assertCell(1, 0, a.get(0, 0));
        assertCell(3, 0, a.get(1, 0));
        assertCell(4, 0, a.get(1, 1));
    }

    @Test
    void checkIdentity() {
        ComplexMatrix m = ComplexMatrix.I(3);

        assertEquals(3, m.getRows());
        assertEquals(3, m.getCols());
        assertCell(1, 0, m.get(0, 0));
        assertCell(1, 0, m.get(2, 2));
        assertCell(0, 0, m.get(0, 1));
        assertCell(0, 0, m.get(2, 0));

        assertThrows(IllegalArgumentException.class, () -> ComplexMatrix.I(0));
        assertThrows(IllegalArgumentException.class, () -> ComplexMatrix.I(-1));
    }

    @Test
    void checkMultiplyByIdentity() {
        ComplexMatrix a = new ComplexMatrix(new Complex[][] {
                { Complex.parse("1+2i"), Complex.parse("3"), Complex.parse("-i") },
                { Complex.parse("0"), Complex.parse("2-i"), Complex.parse("4i") }
        });

        assertMatrix(a, a.multiply(ComplexMatrix.I(3)));
        assertMatrix(a, ComplexMatrix.I(2).multiply(a));
    }

    @Test
    void checkInverse() {
        ComplexMatrix a = new ComplexMatrix(new Complex[][] {
                { Complex.parse("1"), Complex.parse("2") },
                { Complex.parse("3"), Complex.parse("4") }
        });
        ComplexMatrix expected = new ComplexMatrix(new Complex[][] {
                { Complex.parse("-2"), Complex.parse("1") },
                { Complex.parse("1.5"), Complex.parse("-0.5") }
        });
        assertMatrix(expected, a.inverse());

        // комплексный случай: 1/i = -i
        ComplexMatrix b = new ComplexMatrix(new Complex[][] {
                { Complex.parse("i"), Complex.parse("0") },
                { Complex.parse("0"), Complex.parse("i") }
        });
        ComplexMatrix expectedB = new ComplexMatrix(new Complex[][] {
                { Complex.parse("-i"), Complex.parse("0") },
                { Complex.parse("0"), Complex.parse("-i") }
        });
        assertMatrix(expectedB, b.inverse());

        ComplexMatrix c = new ComplexMatrix(new Complex[][] {
                { Complex.parse("2"), Complex.parse("1"), Complex.parse("1") },
                { Complex.parse("1"), Complex.parse("3"), Complex.parse("2") },
                { Complex.parse("1"), Complex.parse("0"), Complex.parse("0") }
        });
        assertMatrix(ComplexMatrix.I(3), c.multiply(c.inverse()));
        assertMatrix(ComplexMatrix.I(3), c.inverse().multiply(c));

        assertMatrix(ComplexMatrix.I(3), ComplexMatrix.I(3).inverse());

        assertCell(1, 0, a.get(0, 0));
        assertCell(2, 0, a.get(0, 1));
        assertCell(3, 0, a.get(1, 0));
        assertCell(4, 0, a.get(1, 1));
    }

    @Test
    void checkInverseErrors() {
        ComplexMatrix singular = new ComplexMatrix(new Complex[][] {
                { Complex.parse("1"), Complex.parse("2") },
                { Complex.parse("2"), Complex.parse("4") }
        });
        assertThrows(ArithmeticException.class, () -> singular.inverse());

        ComplexMatrix notSquare = new ComplexMatrix(new Complex[][] { { Complex.ONE, Complex.ONE } });
        assertThrows(IllegalArgumentException.class, () -> notSquare.inverse());
    }

    @Test
    void checkDivide() {
        ComplexMatrix a = new ComplexMatrix(new Complex[][] {
                { Complex.parse("1"), Complex.parse("i") },
                { Complex.parse("0"), Complex.parse("2") }
        });
        ComplexMatrix b = new ComplexMatrix(new Complex[][] {
                { Complex.parse("1"), Complex.parse("2") },
                { Complex.parse("3"), Complex.parse("4") }
        });

        assertMatrix(a, a.multiply(b).divide(b));
        assertMatrix(ComplexMatrix.I(2), b.divide(b));
        assertMatrix(b, b.divide(ComplexMatrix.I(2)));

        // I / B = B^-1
        ComplexMatrix expected = new ComplexMatrix(new Complex[][] {
                { Complex.parse("-2"), Complex.parse("1") },
                { Complex.parse("1.5"), Complex.parse("-0.5") }
        });
        assertMatrix(expected, ComplexMatrix.I(2).divide(b));
    }

    @Test
    void checkDivideErrors() {
        ComplexMatrix singular = new ComplexMatrix(new Complex[][] {
                { Complex.parse("1"), Complex.parse("2") },
                { Complex.parse("2"), Complex.parse("4") }
        });
        assertThrows(ArithmeticException.class, () -> ComplexMatrix.I(2).divide(singular));

        ComplexMatrix row = new ComplexMatrix(new Complex[][] { { Complex.ONE, Complex.ONE, Complex.ONE } });
        assertThrows(IllegalArgumentException.class, () -> row.divide(ComplexMatrix.I(2)));
    }
}