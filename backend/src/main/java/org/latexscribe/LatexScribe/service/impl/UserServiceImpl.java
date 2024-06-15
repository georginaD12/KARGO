package org.latexscribe.LatexScribe.service.impl;

import lombok.RequiredArgsConstructor;
import org.latexscribe.LatexScribe.domain.dto.UsersDto;
import org.latexscribe.LatexScribe.domain.model.Order;
import org.latexscribe.LatexScribe.domain.model.User;
import org.latexscribe.LatexScribe.repository.UserRepository;
import org.latexscribe.LatexScribe.service.IUserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService  {
    private final UserRepository userRepository;

    @Override
    public List<UsersDto> listAll() {

        return userRepository
                .findAll()
                .stream()
                .map(item->
                        new UsersDto(item.getUsername(),
                                     item.getFullName(),
                                     item.getEmail(),
                                     item.getRole())).collect(Collectors.toList());
    }

    @Override
    public User save(User user) {
        if (user == null) {
            throw new IllegalArgumentException("provided user is null");
        }
        return userRepository.saveAndFlush(user);
    }

    @Override
    public void delete(User user) {
        if (user == null) {
            throw new IllegalArgumentException("provided user is null");
        }
        userRepository.delete(user);
    }

    @Override
    public void deleteByUsername(String username) {
        if (username == null) {
            throw new IllegalArgumentException(" username is null");

        }
        userRepository.deleteById(username);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public UsersDto getCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        System.out.println("this is username:"+username);
        System.out.println(SecurityContextHolder.getContext());

        User user = findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
        return new UsersDto(user.getUsername(),user.getFullName(),user.getEmail(),user.getRole());
    }
}
