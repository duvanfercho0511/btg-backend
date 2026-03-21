package duvan_vargas.btg_backend.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class FondoDTO implements Serializable {

    private String id;
    @NotNull(message = "Campo no puede ser nulo")
    private String nombre;
    @NotNull(message = "Campo no puede ser nulo")
    private String categoria;
    @NotNull(message = "Campo no puede ser nulo")
    private Long montoMinimo;

    private Long valorTotalVinculaciones;
}
