package Modelo;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class PPId implements Serializable {
    @Column(name = "ID_PROGRAMADOR")
    private Integer idProgramador;

    @Column(name = "ID_PROYECTO")
    private Integer idProyecto;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PPId)) return false;
        PPId that = (PPId) o;
        return Objects.equals(idProgramador, that.idProgramador) &&
                Objects.equals(idProyecto, that.idProyecto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idProgramador, idProyecto);
    }

    public Integer getIdProgramador() {
        return idProgramador;
    }

    public Integer getIdProyecto() {
        return idProyecto;
    }

    public void setIdProgramador(Integer idProgramador) {
        this.idProgramador = idProgramador;
    }

    public void setIdProyecto(Integer idProyecto) {
        this.idProyecto = idProyecto;
    }
}
