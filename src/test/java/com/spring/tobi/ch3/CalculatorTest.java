package com.spring.tobi.ch3;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;

public class CalculatorTest {
    @Autowired
    CalcClient calcClient;

    final String filepath = "C:/Users/KIM HYO EUN/Desktop/tobi/src/main/resources/static/numbers.txt";

    @Test
    public void calcSumTest() throws IOException {
        assertEquals(10, calcClient.calcSum1(filepath));
        assertEquals(10, calcClient.calcSum2(filepath));
    }

    @Test
    public void calcMultiplyTest() throws IOException {
        assertEquals(24, calcClient.calcMultiply1(filepath));
        assertEquals(24, calcClient.calcMultiply2(filepath));
    }

    @Test
    public void concatenateTest() throws IOException {
        System.out.println(calcClient.concatenate(filepath));
        assertEquals("1234", calcClient.concatenate(filepath));
    }

}
