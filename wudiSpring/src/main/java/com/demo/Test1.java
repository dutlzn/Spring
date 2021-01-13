package com.demo;

import java.util.Random;

public class Test1 {
    public static void main(String[] args) {
        System.out.println("test");
        Random r1 = new Random();
        for (int i = 0; i < 10; i++) {
            System.out.print(r1.nextInt(100) + " ") ;
        }
        System.out.println();
        Random r2 = new Random(10);
        for (int i = 0; i < 10; i++) {
            System.out.print(r2.nextInt(100) + " ") ;
        }
    }
}
