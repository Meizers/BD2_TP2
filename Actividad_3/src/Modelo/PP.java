package Modelo;

import javax.persistence.*;

@Entity
@Table(name = "P_P")
public class PP {
    @EmbeddedId
    private PPId id;

    @ManyToOne
    @MapsId("idProgramador")
    @JoinColumn(name = "ID_PROGRAMADOR")
    private Programador programador;

    @ManyToOne
    @MapsId("idProyecto")
    @JoinColumn(name = "ID_PROYECTO")
    private Proyecto proyecto;

    @Column(name = "HORAS_SEMANALES", nullable = false)
    private Integer horasSemanales;

    public Integer getHorasSemanales() {
        return horasSemanales;
    }

    public PPId getId() {
        return id;
    }

    public Programador getProgramador() {
        return programador;
    }

    public Proyecto getProyecto() {
        return proyecto;
    }

    public void setProgramador(Programador programador) {
        this.programador = programador;
    }

    public void setId(PPId id) {
        this.id = id;
    }

    public void setHorasSemanales(Integer horasSemanales) {
        this.horasSemanales = horasSemanales;
    }

    public void setProyecto(Proyecto proyecto) {
        this.proyecto = proyecto;
    }
}
