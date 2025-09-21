package db4o.ex2;

import java.io.Serializable;

public class Factura implements Serializable {
    private int nro;
    private double importe;
    private Cliente cliente; // referencia al cliente

    public Factura() {}
    public Factura(int nro, Cliente cliente, double importe) {
        this.nro = nro; this.cliente = cliente; this.importe = importe;
    }

    public int getNro() { return nro; }
    public void setNro(int nro) { this.nro = nro; }

    public double getImporte() { return importe; }
    public void setImporte(double importe) { this.importe = importe; }

    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }

    @Override
    public String toString() {
        String c = cliente == null ? "null" : cliente.getDescr() + "(id=" + cliente.getId() + ")";
        return "Factura{nro=" + nro + ", importe=" + importe + ", cliente=" + c + "}";
    }
}