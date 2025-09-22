package Modelo;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "PROGRAMADOR")
public class Programador {
    @Id
    @Column(name = "ID_PROGRAMADOR")
    private Integer id;

    @Column(name = "SALDO", nullable = false)
    private Integer saldo;

    @Column(name = "NOMBRE", length = 40, nullable = false)
    private String nombre;

    @OneToMany(mappedBy = "programador")
    private List<PP> proyectos; // relación con proyectos

    @OneToMany(mappedBy = "programador")
    private List<PI> lenguajes; // relación con lenguajes

    public Integer getId() {
        return id;
    }

    public Integer getSaldo() {
        return saldo;
    }

    public List<PI> getLenguajes() {
        return lenguajes;
    }

    public List<PP> getProyectos() {
        return proyectos;
    }

    public String getNombre() {
        return nombre;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setLenguajes(List<PI> lenguajes) {
        this.lenguajes = lenguajes;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setProyectos(List<PP> proyectos) {
        this.proyectos = proyectos;
    }

    public void setSaldo(Integer saldo) {
        this.saldo = saldo;
    }

}
