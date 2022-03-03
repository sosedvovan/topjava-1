package ru.javawebinar.topjava.model;

//еще один промежуточный класс- скелетон на пути к Юзеру.  к  родительскому полю id добавляет protected String name;
public abstract class AbstractNamedEntity extends AbstractBaseEntity {

    //от родителя получаем два поля и методы обслуживающие id (геттеры, сеттеры...)
    //public static final int START_SEQ = 100000;
    // protected Integer id;


    ////продолжаем описание свойст Юзеров
    //у Юзера, кроме родительского id будет еще и name
    protected String name;

    //пустой конструктор для Спринга, для создания Бинов
    public AbstractNamedEntity() {
    }

    //конструктор
    protected AbstractNamedEntity(Integer id, String name) {
        super(id);
        this.name = name;
    }

    //сеттер для name
    public void setName(String name) {
        this.name = name;
    }

    //геттер для name
    public String getName() {
        return this.name;
    }

    //toString() для этого класса. к родительскому toString() конкатенирует name. Хороший фокус
    @Override
    public String toString() {
        return super.toString() + '(' + name + ')';
    }
}