package edu.badpals.db_magictg.model;

public class Card {
    private int id_card;
    private String nombre;
    private String mana_cost;
    private int cmc;
    private String color;
    private String color_identity;
    private int poder;
    private int resistencia;
    private Type tipo;
    private Rarity rareza;
    private Set card_set;
    private float precio;

    public Card(int id_card, String nombre, String mana_cost, int cmc, String color, String color_identity, int poder, int resistencia, Type tipo, Rarity rareza, Set card_set, float precio) {
        this.id_card = id_card;
        this.nombre = nombre;
        this.mana_cost = mana_cost;
        this.cmc = cmc;
        this.color = color;
        this.color_identity = color_identity;
        this.poder = poder;
        this.resistencia = resistencia;
        this.tipo = tipo;
        this.rareza = rareza;
        this.card_set = card_set;
        this.precio = precio;
    }

    public Card() {}

    public int getId_card() {
        return id_card;
    }

    public void setId_card(int id_card) {
        this.id_card = id_card;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getMana_cost() {
        return mana_cost;
    }

    public void setMana_cost(String mana_cost) {
        this.mana_cost = mana_cost;
    }

    public int getCmc() {
        return cmc;
    }

    public void setCmc(int cmc) {
        this.cmc = cmc;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getColor_identity() {
        return color_identity;
    }

    public void setColor_identity(String color_identity) {
        this.color_identity = color_identity;
    }

    public int getPoder() {
        return poder;
    }

    public void setPoder(int poder) {
        this.poder = poder;
    }

    public int getResistencia() {
        return resistencia;
    }

    public void setResistencia(int resistencia) {
        this.resistencia = resistencia;
    }

    public Type getTipo() {
        return tipo;
    }

    public void setTipo(Type tipo) {
        this.tipo = tipo;
    }

    public Rarity getRareza() {
        return rareza;
    }

    public void setRareza(Rarity rareza) {
        this.rareza = rareza;
    }

    public Set getCard_set() {
        return card_set;
    }

    public void setCard_set(Set card_set) {
        this.card_set = card_set;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    @Override
    public String toString() {
        return "card{" + "id_card=" + id_card + ", nombre=" + nombre + ", mana_cost=" + mana_cost + ", cmc=" + cmc + ", color=" + color + ", color_identity=" + color_identity + ", poder=" + poder + ", resistencia=" + resistencia + ", tipo=" + tipo + ", rareza=" + rareza + ", card_set=" + card_set + ", precio=" + precio + '}';
    }
}
