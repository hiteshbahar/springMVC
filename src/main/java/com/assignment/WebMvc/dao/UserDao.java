package com.assignment.WebMvc.dao;


import com.assignment.WebMvc.model.User;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

@Repository
public class UserDao implements Dao<User> {
    public static final String USER = "User:";
    public static Map<String, User> userMap = new HashMap<>();

    @Override
    public Optional<User> get(long id) {
        User user = userMap.get(USER + id);
        return Optional.ofNullable(user);
    }

    @Override
    public List<User> getAll(User user) {
        return null;
    }

    @Override
    public User save(User user) {
        userMap.put(USER+user.getId(), user);
        return get(user.getId()).orElse(null);
    }

    @Override
    public User update(User user) {
        userMap.replace(USER+user.getId(), user);
        return userMap.get(USER+user.getId());
    }

    @Override
    public boolean delete(long id) {
        if(userMap.containsKey(USER+id)) {
            userMap.remove(USER+id);
            return true;
        }
        return false;
    }

    public User findByEmail(String email) {
        return new ArrayList<>(userMap.values())
                .stream()
                .filter(Objects::nonNull)
                .filter(user -> user.getEmail().equals(email))
                .findFirst()
                .orElse(null);
    }

    public List<User> findByName(String name) {
        return new ArrayList<>(userMap.values())
                .stream()
                .filter(user -> user.getName().contains(name))
                .collect(Collectors.toList());
    }
}
