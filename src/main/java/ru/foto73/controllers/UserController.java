package ru.foto73.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.foto73.model.FreeDay;
import ru.foto73.model.Message;
import ru.foto73.model.Order;
import ru.foto73.model.Review;
import ru.foto73.services.*;

import java.lang.reflect.InvocationTargetException;
import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/")
public class UserController {
    private final FreeDayService freeDayService;
    private final UserService userService;
    private final OrderService orderService;
    private final MessageService messageService;
    private final ReviewService reviewService;

    public UserController(FreeDayService freeDayService, UserService userService, OrderService orderService, MessageService messageService, ReviewService reviewService) {
        this.freeDayService = freeDayService;
        this.userService = userService;
        this.orderService = orderService;
        this.messageService = messageService;
        this.reviewService = reviewService;
    }

    @GetMapping("/user")
    public String getUserIndexPage(Model model, Principal user) {
        model.addAttribute("name", userService.getUser(user.getName()).getFirstName());
        return "userPages/userIndexPage.html";
    }

    @GetMapping("/user/gallery")
    public String getGalleryPage(Model model, Principal user) {
        model.addAttribute("name", userService.getUser(user.getName()).getFirstName());
        return "userPages/gallery.html";
    }
    @GetMapping("/user/price")
    public String getPricePage(Model model, Principal user) {
        model.addAttribute("name", userService.getUser(user.getName()).getFirstName());
        return "userPages/price.html";
    }

    @GetMapping("/user/online-record")
    public String getOnlineRecordPage(Model model, Principal user) {
        model.addAttribute("isActiveOrder", userService.getUser(user.getName()).isLimitActiveOrder());
        model.addAttribute("name", userService.getUser(user.getName()).getFirstName());
        List<FreeDay> list = freeDayService.findAllFreeDays();
        model.addAttribute("freeDays", list);
        return "userPages/online-record.html";
    }
    @GetMapping("/user/online-record/{time}/{id}")
    public String confirmRecordPage(@PathVariable String time,
                                    @PathVariable Long id,
                                    Principal user,
                                    Model model) {
        model.addAttribute("name", userService.getUser(user.getName()).getFirstName());
        model.addAttribute("lastName", userService.getUser(user.getName()).getLastName());
        model.addAttribute("date", freeDayService.findFreeDay(id).getDate());
        model.addAttribute("time", convertTime(time));

        return "userPages/confirm-order.html";
    }

    @PostMapping("/user/online-record/{time}/{id}")
    public String confirmRecord(@ModelAttribute Order order,
                                @PathVariable String time,
                                @PathVariable Long id,
                                Principal user,
                                Model model) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        model.addAttribute("name", userService.getUser(user.getName()).getFirstName());
        FreeDay freeDay = freeDayService.findFreeDay(id);
        freeDay.updateTimeFromString(time, false);
        Order order1 = new Order();
        order1.setTime(convertTime(time));
        order1.setDate(freeDay.getDate());
        order1.setCustomerId(userService.getUser(user.getName()).getId());
        order1.setFullName(userService.getUser(user.getName()).getFirstName() + " "
                + userService.getUser(user.getName()).getLastName());
        order1.setPhoneNumber(order.getPhoneNumber());
        order1.setLocation(order.getLocation());
        order1.setWishes((order.getWishes() == null)?" ":order.getWishes());
        freeDayService.updateFreeDay(freeDay);
        userService.updateLimitOrderUser(user.getName(), true);
        orderService.createOrder(order1);
        return "userPages/confirm-successfull.html";
    }

    @GetMapping("/user/contacts")
    public String getContactsPage(Model model, Principal user) {
        model.addAttribute("name", userService.getUser(user.getName()).getFirstName());
        return "userPages/contacts.html";
    }

    @PostMapping("/user/contacts")
    public String sendMessage(@ModelAttribute("message") Message message){
        messageService.saveMessage(message);
        return "redirect:/user/send-success";
    }

    @GetMapping("/user/send-success")
    public String successSendMessage(Model model, Principal user){
        model.addAttribute("name", userService.getUser(user.getName()).getFirstName());
        return "userPages/send-success.html";}

    @GetMapping("/user/reviews")
    public String getReviewsPage(Model model, Principal user) {
        model.addAttribute("averageRate", reviewService.calcAverageRating());
        model.addAttribute("reviews", reviewService.findAllReview());
        model.addAttribute("name", userService.getUser(user.getName()).getFirstName());
        model.addAttribute("limitReview", userService.getUser(user.getName()).isLimitReview());
        return "userPages/reviews.html";
    }

    @PostMapping("/user/reviews")
    public String sendReview(@ModelAttribute("review") Review review, Model model, Principal user) {
        model.addAttribute("name", userService.getUser(user.getName()).getFirstName());
        Review review1 = new Review();
        review1.setName(userService.getUser(user.getName()).getFirstName());
        review1.setExellent(review.getExellent());
        review1.setComment(review.getComment());
        if (reviewService.saveReview(review1)) userService.updateLimitReviewUser(user.getName(), true);
        return "redirect:/user/reviews";
    }

    @GetMapping("/user/active-order")
    public String showActiveOrder(Model model, Principal user){
        model.addAttribute("order", orderService.getActiveOrderByCustomerId(
                                                userService.getUser(user.getName()).getId()));
        model.addAttribute("name", userService.getUser(user.getName()).getFirstName());
        model.addAttribute("blocked", userService.getUser(user.getName()).isLimitActiveOrder());
        return "userPages/active-order.html";
    }

    @GetMapping("/user/history")
    public String getHistoryPage(Model model, Principal user) {
        model.addAttribute("name", userService.getUser(user.getName()).getFirstName());
        model.addAttribute("orders", orderService.getOrderListForUserHistory(userService.getUser(user.getName()).getId()));
        return "userPages/history.html";
    }

    @GetMapping("/user/user-data")
    public String getUserDataPage(Model model, Principal user) {
        model.addAttribute("name", userService.getUser(user.getName()).getFirstName());
        model.addAttribute("user", userService.getUser(user.getName()));
        return "userPages/user-data.html";
    }


    private String convertTime(String time){
        switch(time) {
            case "setEight":
                return "8:00";
            case "setNine":
                return "9:00";
            case "setTen":
                return "10:00";
            case "setEleven":
                return "11:00";
            case "setTwelve":
                return "12:00";
            case "setThirteen":
                return "13:00";
            case "setFourteen":
                return "14:00";
            case "setFifteen":
                return "15:00";
            case "setSixteen":
                return "16:00";
            case "setSeventeen":
                return "17:00";
            default:
                return "18:00";
        }
    }
    private String convertReview(int exellent){
        switch(exellent) {
            case 1:
                return "★☆☆☆☆";
            case 2:
                return "★★☆☆☆";
            case 3:
                return "★★★☆☆";
            case 4:
                return "★★★★☆";
            default:
                return "★★★★★";
        }
    }
}
