package com.learning;

import com.learning.model.User;

import java.util.function.Consumer;

public class Viewer implements Consumer<User> {

    public void accept(User user) {
        System.out.println(user.getName());
    }
}
