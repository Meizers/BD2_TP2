package Modelo;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "LENGUAJE")
public class Lenguaje {
    @Id
    @Column(name = "ID_LENGUAJE")
    private Integer id;

    @Column(name = "NOMBRE_LENGUAJE", length = 40, nullable = false)
    private String nombre;

    @OneToMany(mappedBy = "lenguaje")
    private List<PI> programadores;

    public void setId(Integer id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public Integer getId() {
        return id;
    }



    public List<PI> getProgramadores() {
        return programadores;
    }
}