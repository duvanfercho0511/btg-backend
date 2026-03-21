package duvan_vargas.btg_backend.mapper;

import duvan_vargas.btg_backend.dto.AsociacionUsuarioFondoDTO;
import duvan_vargas.btg_backend.model.AsociacionUsuarioFondo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AsociacionUsuarioFondoMapper {

    AsociacionUsuarioFondo toAsociacionUsuarioFondo(AsociacionUsuarioFondoDTO asociacionUsuarioFondoDTO);

    AsociacionUsuarioFondoDTO toAsociacionUsuarioFondoDTO(AsociacionUsuarioFondo asociacionUsuarioFondo);
}
