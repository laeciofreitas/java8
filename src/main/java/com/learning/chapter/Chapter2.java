package com.learning.chapter;

import com.learning.Viewer;
import com.learning.model.User;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class Chapter2 {

    public static void main(String[] args) {
        User user1 = new User("John", 150);
        User user2 = new User("Brandon", 300);

        List<User> users = Arrays.asList(user1, user2);
        for (User user : users) {
            System.out.println(user.getName());
        }

        Viewer viewer = new Viewer();
        users.forEach(viewer);

        users.forEach(new Consumer<User>() {
            @Override
            public void accept(User user) {
                System.out.println(user.getName());
            }
        });

        Consumer<User> consumer = (User user) -> {System.out.println(user.getName());};
        users.forEach(consumer);

        Consumer<User> consumer2 = u -> {System.out.println(u.getName());};
        users.forEach(consumer2);

        users.forEach(u -> System.out.println(u.getName()));

        users.forEach(user -> user.turnModerator());
    }
}
