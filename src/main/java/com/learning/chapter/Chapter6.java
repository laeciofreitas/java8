package com.learning.chapter;

import com.learning.model.User;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import static java.util.Comparator.comparing;
import static java.util.Comparator.comparingInt;

public class Chapter6 {

    public static void main(String[] args) {
        User user1 = new User("John", 150);
        User user2 = new User("Brandon", 300);

        Runnable block = user1::turnModerator;
        block.run();

        Runnable block2 = () -> user1.turnModerator();
        block2.run();


        Consumer<User> turnModerator = User::turnModerator;
        turnModerator.accept(user2);

        List<User> users = new ArrayList<>();
        users.add(user1);
        users.add(user2);

        Collections.sort(users, (u1, u2) -> u1.getName().compareTo(u2.getName()));
        users.forEach(System.out::println);

        users.sort((u1, u2) -> u1.getName().compareTo(u2.getName()));

        Consumer<User> consumer = User::getName;
        users.sort(Comparator.nullsLast(comparing(User::getName).thenComparing(User::getScore)));

        users.sort(comparingInt(User::getScore).reversed());

        Supplier<User> userSupplier = User::new;
        User user3 = userSupplier.get();

        Function<String, User> function = User::new;
        User summer = function.apply("Summer");

    }

}
