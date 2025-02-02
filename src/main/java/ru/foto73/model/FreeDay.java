package ru.foto73.model;

import jakarta.persistence.Entity;
import jakarta.persistence.*;

import java.time.LocalDate;


@Entity
@Table(name = "free-days")
public class FreeDay {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id; // primary key

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "8:00")
    private boolean eight = true;

    @Column(name = "9:00")
    private boolean nine = true;

    @Column(name = "10:00")
    private boolean ten = true;

    @Column(name = "11:00")
    private boolean eleven = true;

    @Column(name = "12:00")
    private boolean twelve = true;

    @Column(name = "13:00")
    private boolean thirteen = true;

    @Column(name = "14:00")
    private boolean fourteen = true;

    @Column(name = "15:00")
    private boolean fifteen = true;

    @Column(name = "16:00")
    private boolean sixteen = true;

    @Column(name = "17:00")
    private boolean seventeen = true;

    @Column(name = "18:00")
    private boolean eighteen = true;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public boolean isEight() {
        return eight;
    }

    public void setEight(boolean eight) {
        this.eight = eight;
    }

    public boolean isNine() {
        return nine;
    }

    public void setNine(boolean nine) {
        this.nine = nine;
    }

    public boolean isTen() {
        return ten;
    }

    public void setTen(boolean ten) {
        this.ten = ten;
    }

    public boolean isEleven() {
        return eleven;
    }

    public void setEleven(boolean eleven) {
        this.eleven = eleven;
    }

    public boolean isTwelve() {
        return twelve;
    }

    public void setTwelve(boolean twelve) {
        this.twelve = twelve;
    }

    public boolean isThirteen() {
        return thirteen;
    }

    public void setThirteen(boolean thirteen) {
        this.thirteen = thirteen;
    }

    public boolean isFourteen() {
        return fourteen;
    }

    public void setFourteen(boolean fourteen) {
        this.fourteen = fourteen;
    }

    public boolean isFifteen() {
        return fifteen;
    }

    public void setFifteen(boolean fifteen) {
        this.fifteen = fifteen;
    }

    public boolean isSixteen() {
        return sixteen;
    }

    public void setSixteen(boolean sixteen) {
        this.sixteen = sixteen;
    }

    public boolean isSeventeen() {
        return seventeen;
    }

    public void setSeventeen(boolean seventeen) {
        this.seventeen = seventeen;
    }

    public boolean isEighteen() {
        return eighteen;
    }

    public void setEighteen(boolean eighteen) {
        this.eighteen = eighteen;
    }

    public void updateTimeFromNumber(String time, boolean status) {
        switch (time) {
            case "8:00":
                setEight(status);
                break;
            case "9:00":
                setNine(status);
                break;
            case "10:00":
                setTen(status);
                break;
            case "11:00":
                setEleven(status);
                break;
            case "12:00":
                setTwelve(status);
                break;
            case "13:00":
                setThirteen(status);
                break;
            case "14:00":
                setFourteen(status);
                break;
            case "15:00":
                setFifteen(status);
                break;
            case "16:00":
                setSixteen(status);
                break;
            case "17:00":
                setSeventeen(status);
                break;
            case "18:00":
                setEighteen(status);
                break;
            default:
                System.out.println("Такого времени нет");
                break;
        }
    }
    
    public void updateTimeFromString(String time, boolean status) {
        switch (time) {
            case "setEight":
                setEight(status);
                break;
            case "setNine":
                setNine(status);
                break;
            case "setTen":
                setTen(status);
                break;
            case "setEleven":
                setEleven(status);
                break;
            case "setTwelve":
                setTwelve(status);
                break;
            case "setThirteen":
                setThirteen(status);
                break;
            case "setFourteen":
                setFourteen(status);
                break;
            case "setFifteen":
                setFifteen(status);
                break;
            case "setSixteen":
                setSixteen(status);
                break;
            case "setSeventeen":
                setSeventeen(status);
                break;
            case "setEighteen":
                setEighteen(status);
                break;
            default:
                System.out.println("Такого времени нет");
                break;
        }
    }
}