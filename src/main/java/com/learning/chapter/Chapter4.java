package com.learning.chapter;

import com.learning.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class Chapter4 {

    public static void main(String[] args) {
        User user1 = new User("John", 150);
        User user2 = new User("Brandon", 300);

        List<User> users = new ArrayList<>();
        users.add(user1);
        users.add(user2);
        Consumer<User> beforePrint = user -> System.out.println("Imprimir antes");
        Consumer<User> printName = user -> System.out.println(user.getName());
        users.forEach(beforePrint.andThen(printName));
        users.removeIf(user -> user.getScore() > 200);
        users.forEach(printName);
    }
}
