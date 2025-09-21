package db4o.ex1;

import java.io.Serializable;

public class Factura implements Serializable {
    private int nro;
    private Cliente cliente; // referencia a Cliente
    private double importe;

    public Factura() {}

    public Factura(int nro, Cliente cliente, double importe) {
        this.nro = nro;
        this.cliente = cliente;
        this.importe = importe;
    }

    public int getNro() { return nro; }
    public void setNro(int nro) { this.nro = nro; }

    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }

    public double getImporte() { return importe; }
    public void setImporte(double importe) { this.importe = importe; }

    @Override
    public String toString() {
        String c = (cliente == null) ? "null" : cliente.getDescr() + "(id=" + cliente.getId() + ")";
        return "Factura{nro=" + nro + ", cliente=" + c + ", importe=" + importe + "}";
    }
}

