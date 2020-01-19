package com.learning.chapter;

import com.learning.model.User;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Chapter7 {

    public static void main(String[] args) {
        User user1 = new User("John", 150);
        User user2 = new User("Brandon", 300);
        User user3 = new User("Sommer", 400);
        User user4 = new User("Paul", 200);
        User user5 = new User("Suzane", 600);
        User user6 = new User("Carol", 100);

        List<User> users = new ArrayList<>();
        users.add(user1);
        users.add(user2);
        users.add(user3);
        users.add(user4);
        users.add(user5);
        users.add(user6);

        users.sort(Comparator.comparing(User::getScore).reversed());
        users.subList(0, 4).forEach(u -> System.out.println(u.getName()));

        users.forEach(u -> System.out.println(u.getName()));
        Stream<User> userStream = users.stream().filter(u -> u.getScore() > 400);
        System.out.println("==========");
        users.forEach(u -> System.out.println(u.getName()));
        System.out.println("==========");
        userStream.forEach(u -> System.out.println(u.getName()));

        System.out.println("==========!!!");
        List<User> moreThan400 = new ArrayList<>();
        users.stream().filter(u -> u.getScore() > 300).forEach(u -> moreThan400.add(u));
        moreThan400.forEach(u -> System.out.println(u.getName()));

        System.out.println("==========!!!");

        Supplier<ArrayList<User>> supplier = ArrayList::new;
        BiConsumer<ArrayList<User>, User> biConsumer = ArrayList::add;
        BiConsumer<ArrayList<User>, ArrayList<User>> biConsumerAll = ArrayList::addAll;

        List<User> newList = users.stream().filter(u -> u.getScore() > 300).collect(supplier, biConsumer, biConsumerAll);
        newList.forEach(u -> System.out.println(u.getName()));
        System.out.println("==========!!!");
        ArrayList<User> collect = users.stream().filter(u -> u.getScore() > 300).collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
        collect.forEach(u -> System.out.println(u.getName()));

        List<User> collect1 = users.stream().filter(u -> u.getScore() > 300).collect(Collectors.toList());
        collect1.forEach(System.out::println);

        users.stream().filter(u -> u.getScore() > 300).collect(Collectors.toCollection(LinkedList::new)).forEach(System.out::println);

        users.stream().filter(u -> u.getScore() > 300).toArray(User[]::new);

        List<Integer> scores = users.stream().filter(u -> u.getScore() > 300).map(User::getScore).collect(Collectors.toList());

        double asDouble = users.stream().mapToInt(User::getScore).average().orElse(0.0);
        new ArrayList<User>().stream().mapToInt(User::getScore).average().orElseThrow(IllegalArgumentException::new);

        users.stream().max(Comparator.comparingInt(User::getScore)).map(User::getName).orElse("");
    }

}
