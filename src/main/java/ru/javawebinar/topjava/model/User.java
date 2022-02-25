package ru.javawebinar.topjava.model;

import java.util.Date;
import java.util.EnumSet;
import java.util.Set;

import static ru.javawebinar.topjava.util.MealsUtil.DEFAULT_CALORIES_PER_DAY;

//класс хранит персональные данные отдельного юзера
public class User extends AbstractNamedEntity {

    /**
     * public abstract class AbstractNamedEntity extends AbstractBaseEntity {

    /**
     * public abstract class AbstractBaseEntity {
     *
     *     protected Integer id;
     *
     *     protected AbstractBaseEntity(Integer id) {
     *         this.id = id;
     *     }
     *
     *     public void setId(Integer id) {
     *         this.id = id;
     *     }
     *
     *     public Integer getId() {
     *         return id;
     *     }
     *
     *     public boolean isNew() {
     *         return this.id == null;
     *     }
     *
     *     @Override
     *     public String toString() {
     *         return getClass().getSimpleName() + ":" + id;
     *     }
     * }


    protected String name;

    protected AbstractNamedEntity(Integer id, String name) {
        super(id);
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public String toString() {
        return super.toString() + '(' + name + ')';
    }
}
     */

    private String email;

    private String password;

    private boolean enabled = true;

    private Date registered = new Date();

//    в енуме Role-2-е роли: ROLE_USER и ROLE_ADMIN
    private Set<Role> roles;

    private int caloriesPerDay = DEFAULT_CALORIES_PER_DAY;

//    низкоуровневый конструктор передает в основной след. параметры:
    public User(Integer id, String name, String email, String password, Role role, Role... roles) {
        this(id, name, email, password, DEFAULT_CALORIES_PER_DAY, true, EnumSet.of(role, roles));
    }
//    высокоуровневый конструктор:
    public User(Integer id, String name, String email, String password, int caloriesPerDay, boolean enabled, Set<Role> roles) {
        super(id, name);
        this.email = email;
        this.password = password;
        this.caloriesPerDay = caloriesPerDay;
        this.enabled = enabled;
        this.roles = roles;
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

    @Override
    public String toString() {
        return "User (" +
                "id=" + id +
                ", email=" + email +
                ", name=" + name +
                ", enabled=" + enabled +
                ", roles=" + roles +
                ", caloriesPerDay=" + caloriesPerDay +
                ')';
    }
}