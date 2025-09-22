package Modelo;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class PIId implements Serializable {
    @Column(name = "ID_PROGRAMADOR")
    private Integer idProgramador;

    @Column(name = "ID_LENGUAJE")
    private Integer idLenguaje;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PIId)) return false;
        PIId that = (PIId) o;
        return Objects.equals(idProgramador, that.idProgramador) &&
                Objects.equals(idLenguaje, that.idLenguaje);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idProgramador, idLenguaje);
    }

    public Integer getIdLenguaje() {
        return idLenguaje;
    }

    public Integer getIdProgramador() {
        return idProgramador;
    }

    public void setIdLenguaje(Integer idLenguaje) {
        this.idLenguaje = idLenguaje;
    }

    public void setIdProgramador(Integer idProgramador) {
        this.idProgramador = idProgramador;
    }
}
