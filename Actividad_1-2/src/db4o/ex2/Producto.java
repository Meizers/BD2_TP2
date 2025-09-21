package db4o.ex2;

import java.io.Serializable;

public class Producto implements Serializable {
    private int id;
    private String descr;
    private int stock;

    public Producto() {}
    public Producto(int id, String descr, int stock) {
        this.id = id; this.descr = descr; this.stock = stock;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getDescr() { return descr; }
    public void setDescr(String descr) { this.descr = descr; }

    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }

    @Override
    public String toString() {
        return "Producto{id=" + id + ", descr='" + descr + "', stock=" + stock + "}";
    }
}