package ru.javawebinar.topjava.model;

//абстрактный Base класс- скелетон. описывает поле id и работу с этим полем.
public abstract class AbstractBaseEntity {
    //объявим константу, от которой будем отсчитывать id-шники
    //в дб объявленна: global_seq START WITH 100000, с таким же значением
    public static final int START_SEQ = 100000;

    //каждая сущность (еда и юзер) будут иметь id
    protected Integer id;

    //пустой конструктор в супере, чтобы Спринг мог создавать Бины
    public AbstractBaseEntity() {
    }

    //конструктор для поля id
    protected AbstractBaseEntity(Integer id) {
        this.id = id;
    }

    //сеттер для поля id
    public void setId(Integer id) {
        this.id = id;
    }

    //геттер для поля id
    public Integer getId() {
        return id;
    }

    //проверочный метод: будет возвращать true, если id == null
    public boolean isNew() {
        return this.id == null;
    }

    //toString() переопределяем с пом отражения. хороший фокус.
    @Override
    public String toString() {
        return getClass().getSimpleName() + ":" + id;
    }

    //переопределяем equals()
    @Override
    public boolean equals(Object o) {
        //если ссылки this и о равны, значит они указывают на один объект
        if (this == o) {
            return true;
        }
        //если o == null или у this и о разные классы, тогда по-любому не равны
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        //кастуем о к AbstractBaseEntity(стираем его дочерний тип)
        AbstractBaseEntity that = (AbstractBaseEntity) o;
        //сравнивем поля id внутри двух объектов(текущего-на кот вызван equals и переданного в аргументы)
        return id != null && id.equals(that.id);
    }

    //хеш код объекта работает как геттер с проверкой- возвращает id.
    @Override
    public int hashCode() {
        return id == null ? 0 : id;
    }
}