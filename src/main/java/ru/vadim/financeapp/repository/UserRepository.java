package ru.vadim.financeapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.vadim.financeapp.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
