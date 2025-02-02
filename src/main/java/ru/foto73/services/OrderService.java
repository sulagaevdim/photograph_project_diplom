package ru.foto73.services;

import org.aspectj.weaver.ast.Or;
import org.springframework.stereotype.Service;
import ru.foto73.model.Order;
import ru.foto73.repository.OrderRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class OrderService {
    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public void createOrder(Order order){
        orderRepository.save(order);
    }

    public List<Order> getAllOrder(){
        return orderRepository.findAll();
    }

    public ArrayList<String> getState(){
        ArrayList<String> list = new ArrayList<>();
        List<Order> orderList = getAllOrder();
        list.add("Общее количество заказов: " + orderList.size());
        String moon = LocalDate.now().toString().split("-")[1];
        int count = 0;
        for (Order order : orderList){
            if(order.getDate().toString().split("-")[1].equals(moon)) count = count + 1;
        }
        list.add("Общее количество заказов в текущем месяце: " + count);
        String prevMoon;
        if (moon.equals("01")) prevMoon = "12";
        else {
            int moonInt = Integer.parseInt(moon);
            prevMoon = String.valueOf(moonInt-1);
            if(prevMoon.length() == 1) prevMoon = "0" + prevMoon;
        }
        count = 0;
        for (Order order : orderList){
            if(order.getDate().toString().split("-")[1].equals(prevMoon)) count++;
        }
        list.add("Общее количество заказов в предыдущем месяце: " + count);
        return list;
    }

    public Order getActiveOrderByCustomerId(Long customerId){
        List<Order> list = getAllOrder();
        for (Order order : list){
            if (order.getCustomerId() == customerId) {
                if (!order.getStatus().equals("Завершена")) return order;
            }
        }
        return new Order();
    }

    public Order getOrderById(Long id){
        return orderRepository.findById(id).get();
    }

    public List<Order> getTodayOrdersList(){
        List<Order> list = orderRepository.findAll();
        List<Order> todayOrdersList = new ArrayList<>();
        for (Order order : list){
            if(order.getDate().equals(LocalDate.now())) {
                if(!order.getStatus().equals("Завершена")) todayOrdersList.add(order);
            }
        }
        return todayOrdersList;
    }

    public List<Order> getOrdersListWithStatus(String status){
        List<Order> list = orderRepository.findAll();
        List<Order> activeOrdersList = new ArrayList<>();
        for (Order order : list){
            if(order.getStatus().equals(status)) {
                activeOrdersList.add(order);
            }
        }
        //сортировка по дате фотосессии
        activeOrdersList.sort(Comparator.comparing(Order::getDate));
        return activeOrdersList;
    }

    public List<Order> getOrderListForUserHistory(Long userId){
        List<Order> list = orderRepository.findAll();
        List<Order> newList = new ArrayList<>();
        for (Order order : list){
            if (order.getCustomerId().equals(userId)) newList.add(order);
        }
        newList.sort(Comparator.comparing(Order::getDate, Comparator.reverseOrder()));
        return newList;
    }

    public void updateOrder(Long id, String status){
        Order order = orderRepository.findById(id).get();
        order.setStatus(status);
        orderRepository.save(order);
    }

    public void deleteOrder(Long id){
        orderRepository.deleteById(id);
    }
}
