package com.model;


/**
 * @author liufei
 */
public class HashTest {
    private static final Integer NUMBER = 20;

    public static void main(String[] args) {

        int i, j;
        for (i = 0, j = 0; i + j < NUMBER; ++i, j += i--) {
            System.out.println(i + j);
        }
        System.out.print("i = " + i);
    }
}
