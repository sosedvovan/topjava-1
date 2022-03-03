package ru.javawebinar.topjava.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

//Класс- модель еды. От своего родителя получает поле id  и методы для работы с этим полем. и так же константу START_SEQ = 100000;
public class Meal extends AbstractBaseEntity {

    //от родителя получаем два поля и методы обслуживающие id (геттеры, сеттеры...)
    //public static final int START_SEQ = 100000;
   // protected Integer id;

    //продолжаем описание свойст еды(приема пищи)
    //время приема пищи
    private LocalDateTime dateTime;

    //будет хранить анотацию к приему пищи- завтрак, обед, ужин
    private String description;

    //кол-во употребленных калорий за данный прием пищи
    private int calories;

    //пустой конструктор, чтобы Сприн мог создавать Бины
    public Meal() {
    }

    //низкоуровневый конструктор, не принимает id, а вместо id отправляет в высокоуровневый null
    public Meal(LocalDateTime dateTime, String description, int calories) {
        this(null, dateTime, description, calories);
    }

    //высокоуровневый конструктор
    public Meal(Integer id, LocalDateTime dateTime, String description, int calories) {
        super(id);
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
    }

    //геттеры и сеттеры
    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getDescription() {
        return description;
    }

    public int getCalories() {
        return calories;
    }

    //возвращает только дату, кот берет из dateTime
    public LocalDate getDate() {
        return dateTime.toLocalDate();
    }

    //возвращает только время, кот берет из dateTime
    public LocalTime getTime() {
        return dateTime.toLocalTime();
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    //toString() для объектов Meal
    @Override
    public String toString() {
        return "Meal{" +
                "id=" + id +
                ", dateTime=" + dateTime +
                ", description='" + description + '\'' +
                ", calories=" + calories +
                '}';
    }
}
