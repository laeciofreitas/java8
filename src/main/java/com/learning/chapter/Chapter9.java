package com.learning.chapter;

import com.learning.model.Group;
import com.learning.model.User;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Function;
import java.util.function.IntBinaryOperator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import static java.util.Comparator.comparingInt;

public class Chapter9 {

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


        try {
            Files.list(Paths.get("/Users/laecio/Documents/Workspaces/Maps/java8/src/main/java/com/learning/chapter"))
                    .filter(p -> p.toString().endsWith(".java"))
                    .mapToLong(p -> lines(p).count());

            Files.list(Paths.get("/Users/laecio/Documents/Workspaces/Maps/java8/src/main/java/com/learning/chapter"))
                    .filter(p -> p.toString().endsWith(".java"))
                    .map(p -> lines(p).count());

            Map<Path, Long> linesPerFile = new HashMap<>();

            Files.list(Paths.get("/Users/laecio/Documents/Workspaces/Maps/java8/src/main/java/com/learning/chapter"))
                    .filter(p -> p.toString().endsWith(".java"))
                    .forEach(p -> linesPerFile.put(p, lines(p).count()));

            System.out.println(linesPerFile);

            Map<Path, Long> collect = Files.list(Paths.get("/Users/laecio/Documents/Workspaces/Maps/java8/src/main/java/com/learning/chapter"))
                    .filter(p -> p.toString().endsWith(".java"))
                    .collect(Collectors.toMap(p -> p, p -> lines(p).count()));
            System.out.println(collect);

            Map<Path, List<String>> collect1 = Files.list(Paths.get("/Users/laecio/Documents/Workspaces/Maps/java8/src/main/java/com/learning/chapter"))
                    .filter(p -> p.toString().endsWith(".java"))
                    .collect(Collectors.toMap(Function.identity(), p -> lines(p).collect(Collectors.toList())));

            Map<String, User> collect2 = users.stream().collect(Collectors.toMap(User::getName, Function.identity()));

            Map<Integer, List<User>> scores = new HashMap<>();

            for (User user : users) {
                scores.computeIfAbsent(user.getScore(), u -> new ArrayList<>()).add(user);
            }

            System.out.println(scores);

            Map<Integer, List<User>> scores2 = users.stream().collect(Collectors.groupingBy(User::getScore));

            System.out.println(scores);

            Map<Boolean, List<User>> moderators = users.stream().collect(Collectors.partitioningBy(User::isModerator));
            System.out.println(moderators);

            Map<Boolean, List<String>> collect3 = users.stream().collect(Collectors.partitioningBy(User::isModerator, Collectors.mapping(User::getName, Collectors.toList())));
            System.out.println(collect3);

            Map<Boolean, Integer> collect4 = users.stream().collect(Collectors.partitioningBy(User::isModerator, Collectors.summingInt(User::getScore)));
            System.out.println(collect4);

            String collect5 = users.stream().map(User::getName).collect(Collectors.joining(", "));
            System.out.println(collect5);

            List<User> collect6 = users.stream().filter(u -> u.getScore() > 300).sorted(Comparator.comparing(User::getScore))
                    .collect(Collectors.toList());
            System.out.println(collect6);

            List<User> collect7 = users.parallelStream().filter(u -> u.getScore() > 300).sorted(Comparator.comparing(User::getScore))
                    .collect(Collectors.toList());
            System.out.println(collect7);


            long sum = LongStream.range(0, 1_000_000_000).parallel().filter(x -> x % 2 == 0).sum();
        } catch (IOException e) {
            e.printStackTrace();
        }




    }

    static Stream<String> lines(Path p) {
        try {
            return Files.lines(p);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }

    }

}
