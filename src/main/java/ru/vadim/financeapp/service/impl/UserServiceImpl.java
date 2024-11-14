package ru.vadim.financeapp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.vadim.financeapp.entity.User;
import ru.vadim.financeapp.repository.UserRepository;
import ru.vadim.financeapp.service.UserService;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;

    @Override
    public void addUser(User user) {
        userRepository.save(user);
    }
}
