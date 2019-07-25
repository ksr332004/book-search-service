package com.search.book.service;

import com.search.book.dto.UserRequest;
import com.search.book.entity.User;
import com.search.book.exception.BusinessException;
import com.search.book.exception.ErrorCode;
import com.search.book.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public User save(UserRequest userRequest) {
        userRepository.findByEmail(userRequest.getEmail())
                .ifPresent(u -> {throw new BusinessException(ErrorCode.EMAIL_DUPLICATION);});
        User user = User.builder()
                .email(userRequest.getEmail())
                .password(passwordEncoder.encode(userRequest.getPassword()))
                .name(userRequest.getName())
                .roles(Stream.of(User.UserRole.USER).collect(Collectors.toSet()))
                .build();
        return userRepository.save(user);
    }

}
