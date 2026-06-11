package com.raven.birdmail.Controller;

import com.raven.birdmail.Models.User;
import com.raven.birdmail.Repository.CrudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private CrudRepository<User> userCrudRepository;

    @RequestMapping(value = "birdmail/users", method = RequestMethod.POST)
    public void create(@RequestBody User user) {
        System.out.println("Im being called!!!");
        userCrudRepository.create(user);
    }
}
