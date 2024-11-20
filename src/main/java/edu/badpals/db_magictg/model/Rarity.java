package edu.badpals.db_magictg.model;

public class Rarity {
    private int id_rarity;
    private String name_rarity;

    public Rarity(int id_rarity, String name_rarity) {
        this.id_rarity = id_rarity;
        this.name_rarity = name_rarity;
    }

    public Rarity() {}

    public int getId_rarity() {
        return id_rarity;
    }

    public void setId_rarity(int id_rarity) {
        this.id_rarity = id_rarity;
    }

    public String getName_rarity() {
        return name_rarity;
    }

    public void setName_rarity(String name_rarity) {
        this.name_rarity = name_rarity;
    }

    @Override
    public String toString() {
        return "rarity{" + "id_rarity=" + id_rarity + ", name_rarity=" + name_rarity + '}';
    }
}
