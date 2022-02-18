package com.training;

import org.junit.Assert;
import org.junit.Test;
import org.junit.function.ThrowingRunnable;

import java.time.LocalDate;
import java.util.Optional;

/**
 * Created by Kartik on Jan 28, 2022.
 */

public class EvenOrOddTestSpec {

    @Test
    public void checkIfNumberIsEvenOrOdd() {

        boolean even = EvenOrOdd.isEven(10);

        Assert.assertEquals(true, even);

    }

    @Test
    public void checkUpperCase() {
        String uppercase = "reactore".toUpperCase();

        Assert.assertEquals("REACTORE", uppercase);
    }

    @Test
    public void checkOptional() {

        Optional<String> opt = Optional.ofNullable(null);
        Assert.assertEquals(false, opt.isPresent());
//        Assert.assertEquals(true, opt.isPresent());
        Assert.assertFalse(opt.isPresent()); // make sure condition is correct or satisfied


        LocalDate now = LocalDate.now();

        Assert.assertEquals(2022, now.getYear());


    }

    @Test(expected = Exception.class)
    public void checkException() throws Exception {

        EvenOrOdd.exceptionCase();
        //Assert.assertThrows(Exception.class, EvenOrOdd.exceptionCase());
    }

    @Test(expected = IllegalStateException.class)
    public void checkIllegalStateException() throws IllegalStateException {

//        EvenOrOdd.exceptionIllegalStateCase();
        Assert.assertThrows(IllegalStateException.class, EvenOrOdd.exceptionIllegalStateCase());
    }

    @Test(expected = ArithmeticException.class)
    public void checkDivideByZero() throws ArithmeticException {
        int i = 1 / 0;

    }

    @Test
    public void checkDivideBy2() {
        double i  = 1.0 / 2.0;
        double i3 = 1.0 / 3.0;

//        2.3 ~2.25 + -0.05
        Assert.assertEquals(0.5, i, 0.0);
        Assert.assertEquals(2.658, 2.6579, 0.0001);
        Assert.assertEquals(2.25, 2.3, 0.05);
    }


}

class EvenOrOdd {

    public static boolean isEven(int number) {
        return number % 2 == 0;
    }

    public static boolean isOdd(int number) {
        return !isEven(number);
    }

    public static ThrowingRunnable exceptionCase() throws Exception {
        throw new Exception("Some case");
    }

    public static ThrowingRunnable exceptionIllegalStateCase() throws IllegalStateException {
        throw new IllegalStateException("Illegal state");
    }
}