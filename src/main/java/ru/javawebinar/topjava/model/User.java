package ru.javawebinar.topjava.model;

import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.Date;
import java.util.EnumSet;
import java.util.Set;

import static ru.javawebinar.topjava.util.MealsUtil.DEFAULT_CALORIES_PER_DAY;
//класс, описывающий Юзеров. От родительского класса получает id юзера и name юзера
public class User extends AbstractNamedEntity {

    //от родительского класса получает:
    //id
    //name
    //геттеры сеттеры и др. для этих полей


    //кроме id и name из классов-скелетонов Юзеры будут обладать:

    //емейл Юзера
    private String email;

    //пароль Юзера
    private String password;

    //?
    private boolean enabled = true;

    //дата регистрации. поле инициализируется в момент создания объекта Юзера с пом системной даты
    private Date registered = new Date();

    //Сет с ролями, которыми может обладать Юзер
    private Set<Role> roles;

    //поле с ограничением дневных коллорий. инициализируем в момент создания объекта Юзера с пом константы объявленной в классе MealsUtil
    private int caloriesPerDay = DEFAULT_CALORIES_PER_DAY;

    //пустой конструктор для того, чтобы Спринг мог создать Бин
    public User() {
    }

    //низкоуровневый обратный разконструктор- принимает юзера и расскладывает на поля и отправляет в высокоуровневый. Зачем?
    public User(User u) {
        this(u.getId(), u.getName(), u.getEmail(), u.getPassword(), u.getCaloriesPerDay(), u.isEnabled(), u.getRegistered(), u.getRoles());
    }

    //низкоуровневый конструктор. Принимает аргументы (втч Роль и варАрг Ролей) и добавляет константу DEFAULT_CALORIES_PER_DAY, добавляет enabled = true,
    //и отправляет в высокоуровневый конструктор
    public User(Integer id, String name, String email, String password, Role role, Role... roles) {
        this(id, name, email, password, DEFAULT_CALORIES_PER_DAY, true, new Date(), EnumSet.of(role, roles));
    }

    //высокоуровневый конструктор (если вызван из второго низкоуровневого- Роль и варАрги Ролей принимает как Collection<Role> roles)
    public User(Integer id, String name, String email, String password, int caloriesPerDay, boolean enabled, Date registered, Collection<Role> roles) {
        super(id, name);
        this.email = email;
        this.password = password;
        this.caloriesPerDay = caloriesPerDay;
        this.enabled = enabled;
        this.registered = registered;
        //поле roles инициализируем с пом сеттера в который отправляем коллекцию Ролей. Хороший фокус.
        setRoles(roles);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getRegistered() {
        return registered;
    }

    public void setRegistered(Date registered) {
        this.registered = registered;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public int getCaloriesPerDay() {
        return caloriesPerDay;
    }

    public void setCaloriesPerDay(int caloriesPerDay) {
        this.caloriesPerDay = caloriesPerDay;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public String getPassword() {
        return password;
    }

    //сеттер для roles. принимает коллекцию с Ролями...
    public void setRoles(Collection<Role> roles) {
        //если подали пустую или null roles инициируем пустым енум-сетом- EnumSet.noneOf(Role.class),
        // иначе из коллекции, поступившей в аргументы, делаем енум-сет- EnumSet.copyOf(roles)
        this.roles = CollectionUtils.isEmpty(roles) ? EnumSet.noneOf(Role.class) : EnumSet.copyOf(roles);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email=" + email +
                ", name=" + name +
                ", enabled=" + enabled +
                ", roles=" + roles +
                ", caloriesPerDay=" + caloriesPerDay +
                '}';
    }
}