package ru.javawebinar.topjava.model;

public abstract class AbstractNamedEntity extends AbstractBaseEntity {

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
     */

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