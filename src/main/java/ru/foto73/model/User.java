package ru.foto73.model;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String login;
    private String password;
    private String firstName;
    private String lastName;
    private String role;
    private int saleLevel; //уровень скидки в процентах
    private String email;
    private String phoneNumber;
    private boolean limitReview = false; //указывает, исчерпан ли лимит по отзывам (доступен один отзыв)
    private boolean limitActiveOrder = false; //указывает, исчерпан ли лимит по заказам (доступен один активный заказ)

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getSaleLevel() {
        return saleLevel;
    }

    public void setSaleLevel(int saleLevel) {
        this.saleLevel = saleLevel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public boolean isLimitReview() {
        return limitReview;
    }

    public void setLimitReview(boolean limitReview) {
        this.limitReview = limitReview;
    }

    public boolean isLimitActiveOrder() {
        return limitActiveOrder;
    }

    public void setLimitActiveOrder(boolean limitActiveOrder) {
        this.limitActiveOrder = limitActiveOrder;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", role='" + role + '\'' +
                ", saleLevel=" + saleLevel +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", достигнут лимит по отзывам='" + limitReview + '\'' +
                ", достигнут лимит по активным заказам='" + limitActiveOrder + '\'' +
                '}';
    }
}
