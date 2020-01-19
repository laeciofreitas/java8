package com.learning.chapter;

import com.learning.model.Group;
import com.learning.model.User;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.IntBinaryOperator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.Comparator.comparingInt;

public class Chapter8 {

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

        users.stream().filter(u -> u.getScore() > 200).sorted(comparingInt(User::getScore)).collect(Collectors.toList());
//        users.stream().filter(u -> u.getScore() > 400).peek(System.out::println).findAny();
        users.stream().sorted(Comparator.comparing(User::getName)).peek(System.out::println).findAny();

        int sum = users.stream().mapToInt(User::getScore).sum();
        System.out.println(sum);

        int initValue = 0;
        IntBinaryOperator operator = (a, b) -> a + b;
        users.stream().mapToInt(User::getScore).reduce(initValue, operator);
        users.stream().mapToInt(User::getScore).reduce(initValue, Integer::sum);
        users.stream().reduce(0, (current, u) -> current + u.getScore(), Integer::sum);
        users.stream().iterator().forEachRemaining(System.out::println);

        boolean b = users.stream().anyMatch(User::isModerator);
        users.stream().skip(2);

        Stream<Object> empty = Stream.empty();
        Stream<User> user12 = Stream.of(user1, user2);

        IntStream.range(0, 10).forEach(System.out::println);

        Random random = new Random(0);
//        Stream<Integer> generate = Stream.generate(() -> random.nextInt());
//        generate.forEach(System.out::println);

        IntStream.generate(() -> random.nextInt()).limit(20).boxed().collect(Collectors.toList()).forEach(System.out::println);

        try {
            Files.list(Paths.get("/Users/laecio/Documents/Workspaces/Maps/java8/src/main/java/com/learning/chapter"))
                    .filter(p -> p.toString().endsWith(".java"))
                    .flatMap(p -> lines(p))
                    .flatMapToInt(s -> s.chars()).forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Group englishSpeakers = new Group();
        englishSpeakers.add(user1);
        englishSpeakers.add(user2);

        Group spanishSpeakers = new Group();
        spanishSpeakers.add(user2);
        spanishSpeakers.add(user3);

        List<Group> groups = Arrays.asList(englishSpeakers,
                spanishSpeakers);

        Stream<Stream<User>> streamStream = groups.stream().map(g -> g.getUsers().stream());
        groups.stream().flatMap(g -> g.getUsers().stream()).distinct().forEach(System.out::println);



    }

    static Stream<String> lines(Path p) {
        try {
            return Files.lines(p);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }

    }

}
