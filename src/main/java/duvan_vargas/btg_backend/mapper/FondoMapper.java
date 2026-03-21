package duvan_vargas.btg_backend.mapper;

import duvan_vargas.btg_backend.dto.FondoDTO;
import duvan_vargas.btg_backend.model.Fondo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FondoMapper {

    Fondo toFondo(FondoDTO fondoDTO);

    FondoDTO toFondoDTO(Fondo fondo);
}
