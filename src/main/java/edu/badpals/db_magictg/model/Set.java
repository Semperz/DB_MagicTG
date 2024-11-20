package edu.badpals.db_magictg.model;

public class Set {
    private int id_set;
    private String name_set;

    public Set(int id_set, String name_set) {
        this.id_set = id_set;
        this.name_set = name_set;
    }

    public Set() {}

    public int getId_set() {
        return id_set;
    }

    public void setId_set(int id_set) {
        this.id_set = id_set;
    }

    public String getName_set() {
        return name_set;
    }

    public void setName_set(String name_set) {
        this.name_set = name_set;
    }

    @Override
    public String toString() {
        return "set{" + "id_set=" + id_set + ", name_set=" + name_set + '}';
    }
}
