package com.parkit.parkingsystem;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import com.parkit.parkingsystem.util.InputReaderUtil;
import java.io.*;
import java.nio.charset.StandardCharsets;
import static org.junit.Assert.assertEquals;


public class InputReaderUtilTest {


    private  InputReaderUtil inputReaderUtil;
    private static final InputStream stdin = System.in;

    @AfterEach
    void restoreSystemInput() {
        System.setIn(stdin);
    }

    @Test
    public void readSelection_shouldReturnUserInput()  {


        String input = "1";
        System.out.println(input);
        InputStream in = new ByteArrayInputStream(input.getBytes());
        String testTest = new String(input.getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8);

        System.setIn(in);
        inputReaderUtil = new InputReaderUtil();


        assertEquals(1, inputReaderUtil.readSelection());

    }

    @Test
    public void readSelection_shouldReturnMinusOne_whenException() {

        String input = "exception";
        InputStream in = new ByteArrayInputStream(input.getBytes());

        System.setIn(in);
        inputReaderUtil = new InputReaderUtil();
        assertEquals(-1, inputReaderUtil.readSelection());

    }


}