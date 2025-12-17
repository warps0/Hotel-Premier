package edu.utn.tp_disenyo.hotel_premier.dto;

import edu.utn.tp_disenyo.hotel_premier.model.Huesped;
import edu.utn.tp_disenyo.hotel_premier.util.TipoDoc;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class HuespedDTO {
    private Long id;
    private String nombre;
    private String apellido;
    private String docIdentidad;
    private TipoDoc tipoDoc;

    public HuespedDTO(Huesped h) {
        setId(h.getId());
        setNombre(h.getNombre());
        setApellido(h.getApellido());
        setDocIdentidad(h.getDocIdentidad());
        setTipoDoc(h.getTipoDoc());
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        HuespedDTO that = (HuespedDTO) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
