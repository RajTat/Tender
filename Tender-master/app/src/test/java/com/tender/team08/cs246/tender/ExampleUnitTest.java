package com.tender.team08.cs246.tender;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    Patient ryan = new Patient("weird@hotmail.com", "Ryan", "P", "7", "1 streety", "Pocky", "ID", "83204", "0000", "0000","");

    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void test_name() throws Exception {
        assertEquals("Ryan P", ryan.getFullName());
    }

    @Test
    public void test_addr() throws Exception {
        assertEquals("1 streety\nPocky, ID 83204", ryan.getAddress());
    }

    @Test
    public void test_contact() throws Exception {
        assertEquals("7 weird@hotmail.com", ryan.getContact());
    }
}