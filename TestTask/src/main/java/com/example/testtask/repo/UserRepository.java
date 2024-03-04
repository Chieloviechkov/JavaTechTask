package com.example.testtask.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.example.testtask.model.User;


public interface UserRepository extends MongoRepository<User, String> {
    User findByUsername(String username);
}
