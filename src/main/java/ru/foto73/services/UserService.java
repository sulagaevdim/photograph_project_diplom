package ru.foto73.services;


import org.springframework.stereotype.Service;
import ru.foto73.model.User;
import ru.foto73.repository.UserRepository;
import ru.foto73.security.MyPasswordEncoder;

import java.util.*;

@Service
public class UserService {
    private final OrderService orderService;
    private final FreeDayService freeDayService;
    private final UserRepository repository;
    private final MyPasswordEncoder encoder;

    public UserService(OrderService orderService, FreeDayService freeDayService, UserRepository repository, MyPasswordEncoder encoder) {
        this.orderService = orderService;
        this.freeDayService = freeDayService;
        this.repository = repository;
        this.encoder = encoder;
    }

    public String registerUser(User user){
        for (User u : repository.findAll()) {
            if (user.getLogin().equals(u.getLogin())) return "redirect:/errorRegistration";
            if (user.getEmail().equals(u.getEmail())) return "redirect:/errorRegistration";
        }
        user.setRole("USER");
        user.setPassword(encoder.encode(user.getPassword()));
        user.setSaleLevel(5);
        repository.save(user);
        return "redirect:/success";
    }

    public void updateLimitOrderUser(String login, boolean blocked){
        User user = repository.findByLogin(login).get();
        user.setLimitActiveOrder(blocked);
        repository.save(user);
    }

    public void updateLimitReviewUser(String login, boolean blocked){
        User user = repository.findByLogin(login).get();
        user.setLimitReview(blocked);
        repository.save(user);
    }

    public User getUser(String login){
        return repository.findByLogin(login).get();
    }

    public User getUserById(Long id){
        return repository.findById(id).get();
    }

    public List<User> getAllUsers(){
        return repository.findAll();
    }

    public void levelUpUser(Long id){
        User user = repository.findById(id).get();
        user.setSaleLevel(10);
        repository.save(user);
    }



}
