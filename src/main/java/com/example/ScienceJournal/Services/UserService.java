package com.example.ScienceJournal.Services;

import com.example.ScienceJournal.Entities.Role;
import com.example.ScienceJournal.Entities.User;
import com.example.ScienceJournal.Repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class UserService {
    @Autowired
    UserRepo uRepo;

    public User findByUsername(String username) {return uRepo.findByUsername(username);}

    public List<User> findAll() {return uRepo.findAll();}

    public void registration(User user)
    {
        user.setRoles(Collections.singleton(Role.USER));
        uRepo.save(user);
    }
}
