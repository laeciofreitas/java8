package com.learning.model;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Group {

    private Set<User> users = new HashSet<>();
    public void add(User u) {
        users.add(u);
    }
    public Set<User> getUsers() {
        return Collections.unmodifiableSet(this.users);
    }
}
