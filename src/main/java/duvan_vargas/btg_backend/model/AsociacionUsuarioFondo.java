package duvan_vargas.btg_backend.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "asociacion_usuarios_fondos")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AsociacionUsuarioFondo {

    @Id
    private String id;

    private String tipoVinculacion;

    private String idUsuario;

    private String idFondo;

    private String nombreFondo;

    private Long montoPagado;

    private Date fechaDesde;

    private Date fechaHasta;

    //Indicativo para tener más detalle del estado de la asociación
    private Boolean activo;

    private Date fechaHoraCreacion;

    private Boolean notifiacionEmail;
}
