package com.example.userservice.Utils;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.userservice.Repositories.UserRepository;

@Component
public class RandomUsername {

    @Autowired
    UserRepository userRepository;

    public String generateUniqueUsername(String fisrtName, String lastName) {
        long userCount = userRepository.count();
        String randomString = generateRandomString(4);

        return (fisrtName + lastName + userCount + randomString).toLowerCase().replaceAll("\\s+", "");
    }

    private String generateRandomString(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

        Random random = new Random();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < length; i++) {
            sb.append(characters.charAt(random.nextInt(characters.length())));
        }

        return sb.toString();
    }
}
