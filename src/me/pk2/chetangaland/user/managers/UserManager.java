package me.pk2.chetangaland.user.managers;

import me.pk2.chetangaland.user.User;

import java.util.HashMap;

public class UserManager {
    public final HashMap<String, User> users;
    public UserManager() {
        this.users = new HashMap<>();
    }
}