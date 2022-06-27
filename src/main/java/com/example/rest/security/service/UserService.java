package com.example.rest.security.service;

import com.example.rest.domain.movie.models.Movie;
import com.example.rest.security.models.Role;
import com.example.rest.security.models.User;

import java.util.List;

public interface UserService {

    User saveUser(User user);

    Role saveRole(Role role);

    void addRoleToUser(String username, String roleName);

    User getUser(String username);

    List<User> getUsers();

    void addMovieToWatchList(User user, Movie movie);

    void removeMovieFromWatchList(User user, Movie movie);

    void clearWatchList(User user);
}
