package com.learning.chapter;

import java.math.BigDecimal;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Arrays.asList;

class Product {
    private String name;
    private Path file;
    private BigDecimal price;

    public Product(String name, Path file, BigDecimal price) {
        this.name = name;
        this.file = file;
        this.price = price;
    }

    public String getName() {
        return this.name;
    }

    public Path getFile() {
        return this.file;
    }

    public BigDecimal getPrice() {
        return this.price;
    }

    public String toString() {
        return this.name;
    }
}

class Customer {
    private String name;

    public Customer(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public String toString() {
        return this.name;
    }
}

class Payment {
    private List<Product> products;
    private LocalDateTime date;
    private Customer customer;

    public Payment(List<Product> products,
                   LocalDateTime date,
                   Customer customer) {
        this.products =
                Collections.unmodifiableList(products);
        this.date = date;
        this.customer = customer;
    }

    public List<Product> getProducts() {
        return this.products;
    }

    public LocalDateTime getDate() {
        return this.date;
    }

    public Customer getCustomer() {
        return this.customer;
    }

    public String toString() {
        return "[Payment: " +
                date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) +
                " " + customer + " " + products + "]";
    }
}

class Subscription {
    private BigDecimal monthlyFee;
    private LocalDateTime begin;
    private Optional<LocalDateTime> end;
    private Customer customer;

    public Subscription(BigDecimal monthlyFee, LocalDateTime begin,
                        Customer customer) {
        this.monthlyFee = monthlyFee;
        this.begin = begin;
        this.end = Optional.empty();
        this.customer = customer;
    }

    public Subscription(BigDecimal monthlyFee, LocalDateTime begin,
                        LocalDateTime end, Customer customer) {
        this.monthlyFee = monthlyFee;
        this.begin = begin;
        this.end = Optional.of(end);
        this.customer = customer;
    }

    public BigDecimal getMonthlyFee() {
        return monthlyFee;
    }

    public LocalDateTime getBegin() {
        return begin;
    }

    public Optional<LocalDateTime> getEnd() {
        return end;
    }

    public Customer getCustomer() {
        return customer;
    }

    public BigDecimal getTotalPaid() {
        return getMonthlyFee()
                .multiply(new BigDecimal(ChronoUnit.MONTHS
                        .between(getBegin(),
                                getEnd().orElse(LocalDateTime.now()))));
    }
}


public class Chapter11 {

    public static void main(String... args) throws Exception {

        Customer paulo = new Customer("Paulo Silveira");
        Customer rodrigo = new Customer("Rodrigo Turini");
        Customer guilherme = new Customer("Guilherme Silveira");
        Customer adriano = new Customer("Adriano Almeida");

        Product bach = new Product("Bach Completo",
                Paths.get("/music/bach.mp3"), new BigDecimal(100));
        Product poderosas = new Product("Poderosas Anita",
                Paths.get("/music/poderosas.mp3"), new BigDecimal(90));
        Product bandeira = new Product("Bandeira Brasil",
                Paths.get("/images/brasil.jpg"), new BigDecimal(50));
        Product beauty = new Product("Beleza Americana",
                Paths.get("beauty.mov"), new BigDecimal(150));
        Product vingadores = new Product("Os Vingadores",
                Paths.get("/movies/vingadores.mov"), new BigDecimal(200));
        Product amelie = new Product("Amelie Poulain",
                Paths.get("/movies/amelie.mov"), new BigDecimal(100));

        LocalDateTime today = LocalDateTime.now();
        LocalDateTime yesterday = today.minusDays(1);
        LocalDateTime lastMonth = today.minusMonths(1);

        Payment payment1 =
                new Payment(asList(bach, poderosas), today, paulo);
        Payment payment2 =
                new Payment(asList(bach, bandeira, amelie), yesterday, rodrigo);
        Payment payment3 =
                new Payment(asList(beauty, vingadores, bach), today, adriano);
        Payment payment4 =
                new Payment(asList(bach, poderosas, amelie), lastMonth, guilherme);
        Payment payment5 =
                new Payment(asList(beauty, amelie), yesterday, paulo);

        List<Payment> payments = asList(payment1, payment2,
                payment3, payment4, payment5);

        payments.stream().sorted(Comparator.comparing(Payment::getDate))
                .forEach(System.out::println);

        payment1.getProducts().stream().map(Product::getPrice).reduce(BigDecimal::add).ifPresent(System.out::println);

        Stream<BigDecimal> bigDecimalStream = payments.stream()
                .map(p -> p.getProducts().stream().map(Product::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add));

        BigDecimal total = payments.stream()
                .map(p -> p.getProducts().stream().map(Product::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Stream<Stream<BigDecimal>> streamStream = payments.stream().map(p -> p.getProducts().stream().map(Product::getPrice));
        BigDecimal totalFlat = payments.stream().flatMap(p -> p.getProducts().stream().map(Product::getPrice)).reduce(BigDecimal.ZERO, BigDecimal::add);


        Stream<List<Product>> listStream = payments.stream().map(Payment::getProducts);
        Stream<Product> productStream = payments.stream().map(Payment::getProducts).flatMap(p -> p.stream());
        Stream<Product> productStream1 = payments.stream().flatMap(p -> p.getProducts().stream());

        Map<Product, Long> topProducts = payments.stream().flatMap(p -> p.getProducts().stream())
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        topProducts.entrySet().stream().forEach(System.out::println);

        topProducts.entrySet().stream().max(Comparator.comparing(Map.Entry::getValue))
                .ifPresent(System.out::println);

        Map<Product, BigDecimal> totalValuePerProduct = payments.stream().flatMap(p -> p.getProducts().stream())
                .collect(Collectors.groupingBy(Function.identity(), Collectors.reducing(BigDecimal.ZERO, Product::getPrice, BigDecimal::add)));

        totalValuePerProduct.entrySet().stream().sorted(Comparator.comparing(Map.Entry::getValue))
                .forEach(System.out::println);

        payments.stream().flatMap(p -> p.getProducts().stream())
                .collect(Collectors.toMap(Function.identity(), Product::getPrice, BigDecimal::add));

        Map<Customer, List<Payment>> collect = payments.stream().collect(Collectors.groupingBy(Payment::getCustomer));

        System.out.println("=======================");

        Map<Customer, List<List<Product>>> customerToProductsList = payments.stream()
                .collect(Collectors.groupingBy(Payment::getCustomer, Collectors.mapping(Payment::getProducts, Collectors.toList())));
        customerToProductsList.entrySet().stream()
                .sorted(Comparator.comparing(e -> e.getKey().getName()))
                .forEach(System.out::println);

        System.out.println("=======================");

        Map<Customer, List<Product>> customerToProductsList2 = payments.stream()
                .collect(Collectors.groupingBy(Payment::getCustomer, Collectors.mapping(Payment::getProducts, Collectors.toList())))
                .entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().stream()
                        .flatMap(List::stream)
                        .collect(Collectors.toList())));

        Map<Customer, List<Product>> customerToProducts = payments.stream().collect(Collectors.groupingBy(Payment::getCustomer,
                Collectors.reducing(Collections.emptyList(), Payment::getProducts,
                (l1, l2) -> {
                    List<Product> l = new ArrayList<>();
                    l.addAll(l1);
                    l.addAll(l2);
                    return  l;
        })));

       Function<Payment, BigDecimal> paymentToTotal = p -> p.getProducts().stream()
               .map(Product::getPrice)
               .reduce(BigDecimal.ZERO, BigDecimal::add);

        Map<Customer, BigDecimal> totalValuePerCustomer = payments.stream()
                .collect(
                        Collectors.groupingBy(Payment::getCustomer, Collectors.reducing(BigDecimal.ZERO, paymentToTotal, BigDecimal::add)));
    }
}