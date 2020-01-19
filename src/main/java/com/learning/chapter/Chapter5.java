package com.learning.chapter;

import com.learning.model.User;

import java.util.*;

import static java.util.Comparator.comparing;

public class Chapter5 {

    public static void main(String[] args) {
        User user1 = new User("John", 150);
        User user2 = new User("Brandon", 300);

        List<User> users = new ArrayList<>();
        users.add(user1);
        users.add(user2);

        Collections.sort(users, (u1, u2) -> u1.getName().compareTo(u2.getName()));
        users.forEach(u -> System.out.println(u.getName()));

        users.sort((u1, u2) -> u1.getName().compareTo(u2.getName()));

        users.sort(comparing(u -> u.getName()));

        users.sort(Comparator.comparingInt(u -> u.getScore()));

        List<String> names = Arrays.asList("Casa do Código", "Alura", "Caelum");
        names.sort(Comparator.naturalOrder());


    }

}
