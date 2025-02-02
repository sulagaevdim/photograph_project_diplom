package ru.foto73.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.foto73.model.Message;
import ru.foto73.model.User;
import ru.foto73.services.MessageService;
import ru.foto73.services.ReviewService;
import ru.foto73.services.UserService;

@Controller
@RequestMapping("/")
public class MainController {
    private final MessageService messageService;
    private final UserService service;
    private final ReviewService reviewService;

    public MainController(MessageService messageService, UserService service, ReviewService reviewService) {
        this.messageService = messageService;
        this.service = service;
        this.reviewService = reviewService;
    }

    @GetMapping("/")
    public String index(Model model){
        model.addAttribute("title", "Фотограф Наталья Сулагаева: Главная страница");
        return "index";
    }
    @GetMapping("/gallery")
    public String galerry(Model model){
        return "gallery.html";
    }
    @GetMapping("/price")
    public String price(Model model){
        return "price.html";
    }
    @GetMapping("/online-record")
    public String entry(Model model){
        return "online-record.html";
    }

    @GetMapping("/contacts")
    public String contact(Model model){
        return "contacts.html";
    }

    @PostMapping("/contacts")
    public String sendMessage(@ModelAttribute("message") Message message){
        messageService.saveMessage(message);
        return "redirect:/send-success";
    }

    @GetMapping("/reviews")
    public String reviews(Model model){
        model.addAttribute("averageRate", reviewService.calcAverageRating());
        model.addAttribute("reviews", reviewService.findAllReview());
        return "reviews.html";
    }

    @GetMapping("/login")
    public String login(){
        return "login-form.html";
    }

    @GetMapping("/registration")
    public String registration(Model model){
        model.addAttribute("user", new User());
        return "registration.html";}

    @PostMapping("/registration")
    public String registerUser(@ModelAttribute("user") User user){
        System.out.println(user.getLogin() + "успешно зарегистрирован в системе");
        return service.registerUser(user);
    }

    @GetMapping("/success")
    public String successRegistration(){
        return "success.html";}

    @GetMapping("/errorRegistration")
    public String errorRegistration(){
        return "errorRegistration.html";
    }

    @GetMapping("/send-success")
    public String successSendMessage(){
        return "send-success.html";}
}
