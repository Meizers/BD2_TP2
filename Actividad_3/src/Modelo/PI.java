package Modelo;

import javax.persistence.*;

@Entity
@Table(name = "P_I")
public class PI {
    @EmbeddedId
    private PIId id;

    @ManyToOne
    @MapsId("idProgramador")
    @JoinColumn(name = "ID_PROGRAMADOR")
    private Programador programador;

    @ManyToOne
    @MapsId("idLenguaje")
    @JoinColumn(name = "ID_LENGUAJE")
    private Lenguaje lenguaje;

    @Column(name = "NIVEL", nullable = false)
    private Integer nivel;

    public PIId getId() {
        return id;
    }

    public Integer getNivel() {
        return nivel;
    }

    public Programador getProgramador() {
        return programador;
    }

    public Lenguaje getLenguaje() {
        return lenguaje;
    }

    public void setId(PIId id) {
        this.id = id;
    }

    public void setLenguaje(Lenguaje lenguaje) {
        this.lenguaje = lenguaje;
    }

    public void setNivel(Integer nivel) {
        this.nivel = nivel;
    }

    public void setProgramador(Programador programador) {
        this.programador = programador;
    }
}

