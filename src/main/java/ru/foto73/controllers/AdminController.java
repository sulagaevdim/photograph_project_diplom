package ru.foto73.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.foto73.model.FreeDay;
import ru.foto73.model.Order;
import ru.foto73.model.User;
import ru.foto73.services.FreeDayService;
import ru.foto73.services.MessageService;
import ru.foto73.services.OrderService;
import ru.foto73.services.UserService;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

import static java.util.Arrays.stream;

@Controller
@RequestMapping("/")
public class AdminController {
    private final UserService userService;
    private final FreeDayService freeDayService;
    private final OrderService orderService;
    private final MessageService messageService;

    public AdminController(UserService userService, FreeDayService freeDayService, OrderService orderService, MessageService messageService) {
        this.userService = userService;
        this.freeDayService = freeDayService;
        this.orderService = orderService;
        this.messageService = messageService;
    }

    @GetMapping("/admin")
    public String setAdminIndexPage(Model model) {
        model.addAttribute("orders", orderService.getTodayOrdersList());
        return "adminPages/adminIndexPage.html";
    }

    @GetMapping("/admin/free-days")
    public String openFreeDaysPage(Model model) {
        model.addAttribute("freeDays", freeDayService.findAllFreeDays());
        return "adminPages/free-days.html";
    }

    @PostMapping("/admin/free-days")
    public String addFreeDay(@ModelAttribute("date") LocalDate date) {
        FreeDay day = new FreeDay();
        day.setDate(date);
        freeDayService.createFreeDay(day);
        return "redirect:/admin/free-days";
    }

    @GetMapping("/admin/free-days/{id}")
    public String deleteFreeDay(@PathVariable Long id) {
        freeDayService.deleteFreeDay(id);
        return "redirect:/admin/free-days";
    }

    @GetMapping("/admin/new-orders")
    public String getNewOrdersPage(Model model) {
        model.addAttribute("orders", orderService.getOrdersListWithStatus("Зарегистрирована, не подтверждена"));
        return "adminPages/new-orders.html";
    }

    @GetMapping("/admin/new-orders/{id}")
    public String confirmNewOrder(@PathVariable Long id) {
        orderService.updateOrder(id, "Подтверждена");
        return "redirect:/admin/new-orders";
    }

    @GetMapping("/admin/new-orders/del/{id}")
    public String deleteNewOrder(@PathVariable Long id) {
        userService.updateLimitOrderUser(userService.getUserById(orderService.getOrderById(id).getCustomerId()).getLogin(), false);
        freeDayService.liberationDayOfOrderId(id);
        orderService.deleteOrder(id);
        return "redirect:/admin/new-orders";
    }

    @GetMapping("/admin/active-orders")
    public String getActiveOrdersPage(Model model) {
        model.addAttribute("orders", orderService.getOrdersListWithStatus("Подтверждена"));
        return "adminPages/active-orders.html";
    }

    @GetMapping("/admin/active-orders/{id}")
    public String endActiveOrder(@PathVariable Long id) {
        userService.updateLimitOrderUser(userService.getUserById(orderService.getOrderById(id).getCustomerId()).getLogin(), false);
        userService.levelUpUser(orderService.getOrderById(id).getCustomerId());
        orderService.updateOrder(id, "Завершена");
        return "redirect:/admin/active-orders";
    }

    @GetMapping("/admin/active-orders/del/{id}")
    public String deleteActiveOrder(@PathVariable Long id) {
        userService.updateLimitOrderUser(userService.getUserById(orderService.getOrderById(id).getCustomerId()).getLogin(), false);
        freeDayService.liberationDayOfOrderId(id);
        orderService.deleteOrder(id);
        return "redirect:/admin/active-orders";
    }

    @GetMapping("/admin/end-orders")
    public String getEndOrdersPage(Model model) {
        List<Order> list = orderService.getOrdersListWithStatus("Завершена");
        list.sort(Comparator.comparing(Order::getDate, Comparator.reverseOrder()));
        model.addAttribute("orders", list);
        return "adminPages/end-orders.html";
    }

    @GetMapping("/admin/state")
    public String getStatePage(Model model) {
        model.addAttribute("states", orderService.getState());
        return "adminPages/state.html";
    }

    @GetMapping("/admin/messages")
    public String getMessages(Model model) {
        model.addAttribute("messages", messageService.findAllMessage());
        return "adminPages/messages.html";
    }
    @GetMapping("/admin/messages/del/{id}")
    public String deleteMessage(@PathVariable Long id) {
        messageService.deleteMessage(id);
        return "redirect:/admin/messages";
    }

}
