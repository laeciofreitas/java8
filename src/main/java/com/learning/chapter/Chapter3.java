package com.learning.chapter;

import com.learning.Validator;

public class Chapter3 {
    
    public static void main(String[] args) {
        int j = 10;
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                System.out.println(i+j);
            }
        }).start();

        Validator<String> validator = value -> value.matches("[0-9]{5}-[0-9]{3}]");
        System.out.println(validator.validate("64002-410"));

        Runnable runnable = () -> {
            for (int i = 0; i < 10; i++) {
                System.out.println(i);
            }
        };
        System.out.println(runnable);
        System.out.println(runnable.getClass());
    }
}
