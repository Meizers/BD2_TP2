package Modelo;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "PROYECTO")
public class Proyecto {
    @Id
    @Column(name = "ID_PROYECTO")
    private Integer id;

    @Column(name = "DESCRIPCION", length = 40, nullable = false)
    private String descripcion;

    @Column(name = "PRESUPUESTO", nullable = false)
    private Integer presupuesto;

    @OneToMany(mappedBy = "proyecto")
    private List<PP> programadores;

    public Integer getId() {
        return id;
    }

    public Integer getPresupuesto() {
        return presupuesto;
    }

    public List<PP> getProgramadores() {
        return programadores;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setPresupuesto(Integer presupuesto) {
        this.presupuesto = presupuesto;
    }

    public void setProgramadores(List<PP> programadores) {
        this.programadores = programadores;
    }

    // getters y setters
}