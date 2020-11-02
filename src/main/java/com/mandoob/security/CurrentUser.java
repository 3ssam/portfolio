package com.mandoob.security;

import com.mandoob.models.User;
import com.mandoob.repositories.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CurrentUser {
    private static UserRepository userRepository;

    private CurrentUser(UserRepository userRepository) {
        CurrentUser.userRepository = userRepository;
    }

    public static long getId() {
        return getPrincipal().map(MyUserDetails::getId).orElse(-1L);
    }

    public static User getUser() {
        return getPrincipal().map(MyUserDetails::getUser).orElse(null);
    }


    private static Optional<MyUserDetails> getPrincipal() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return Optional.ofNullable(authentication).map(auth -> ((MyUserDetails) auth.getPrincipal()));
    }
}
