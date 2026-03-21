package duvan_vargas.btg_backend.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "fondos")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Fondo {

    @Id
    private String id;
    private String nombre;
    private String categoria;
    private Long montoMinimo;
    private Long valorTotalVinculaciones;

}
