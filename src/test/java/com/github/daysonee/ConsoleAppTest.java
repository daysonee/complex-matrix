package com.github.daysonee;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class ConsoleAppTest {

    private static final double EPS = 1e-9;

    @Test
    void checkParseRow() {
        Complex[] row = ConsoleApp.parseRow("1+2i 3 -i", 3);

        assertEquals(3, row.length);
        assertEquals(1, row[0].getRe(), EPS);
        assertEquals(2, row[0].getIm(), EPS);
        assertEquals(3, row[1].getRe(), EPS);
        assertEquals(0, row[1].getIm(), EPS);
        assertEquals(0, row[2].getRe(), EPS);
        assertEquals(-1, row[2].getIm(), EPS);
    }

    @Test
    void checkParseRowExtraSpaces() {
        Complex[] row = ConsoleApp.parseRow("   1    2i  ", 2);

        assertEquals(2, row.length);
        assertEquals(1, row[0].getRe(), EPS);
        assertEquals(0, row[0].getIm(), EPS);
        assertEquals(0, row[1].getRe(), EPS);
        assertEquals(2, row[1].getIm(), EPS);
    }

    @Test
    void checkParseRowInvalid() {
        assertThrows(IllegalArgumentException.class, () -> ConsoleApp.parseRow(null, 2));
        assertThrows(IllegalArgumentException.class, () -> ConsoleApp.parseRow("", 2));
        assertThrows(IllegalArgumentException.class, () -> ConsoleApp.parseRow("   ", 2));
        assertThrows(IllegalArgumentException.class, () -> ConsoleApp.parseRow("1", 2));
        assertThrows(IllegalArgumentException.class, () -> ConsoleApp.parseRow("1 2 3", 2));
        assertThrows(IllegalArgumentException.class, () -> ConsoleApp.parseRow("1 abc", 2));
    }
}
