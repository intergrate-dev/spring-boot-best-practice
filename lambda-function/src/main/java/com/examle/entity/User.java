package com.example.entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * User
 *
 * @author xxx
 * @since 2023/9/21 10:42
 */
public class User {
    private final String name;
    private final List<User> friends = new ArrayList<>();

    public void addFriend(User user) {
        this.friends.add(user);
    }

    public void addFriends(User... users) {
        this.friends.addAll(Arrays.asList(users));
    }

    public User(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "User{" + "name='" + name + '\'' + '}';
    }

    public List<User> getFriends() {
        return Collections.unmodifiableList(this.friends);
    }
}
