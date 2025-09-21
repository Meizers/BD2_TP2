package db4o.ex2;

import java.io.Serializable;

public class Detalle implements Serializable {
    private Factura factura;   // referencia a factura (nro)
    private Producto producto; // referencia a producto (id)
    private int cantidad;
    private double precioUnit;

    public Detalle() {}
    public Detalle(Factura factura, Producto producto, int cantidad, double precioUnit) {
        this.factura = factura;
        this.producto = producto;
        this.cantidad = cantidad;
        this.precioUnit = precioUnit;
    }

    public Factura getFactura() { return factura; }
    public void setFactura(Factura factura) { this.factura = factura; }

    public Producto getProducto() { return producto; }
    public void setProducto(Producto producto) { this.producto = producto; }

    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }

    public double getPrecioUnit() { return precioUnit; }
    public void setPrecioUnit(double precioUnit) { this.precioUnit = precioUnit; }

    public double subtotal() { return cantidad * precioUnit; }

    @Override
    public String toString() {
        String f = factura == null ? "null" : "nro=" + factura.getNro();
        String p = producto == null ? "null" : producto.getDescr() + "(id=" + producto.getId() + ")";
        return "Detalle{" + f + ", producto=" + p + ", cantidad=" + cantidad +
                ", pu=" + precioUnit + ", subtotal=" + subtotal() + "}";
    }
}