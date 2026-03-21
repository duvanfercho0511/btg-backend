package duvan_vargas.btg_backend.mapper;

import duvan_vargas.btg_backend.dto.RegisterRequestDTO;
import duvan_vargas.btg_backend.model.RegisterRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RegisterRequestMapper {

    //DTO TO ENTITY
    RegisterRequest toRegisterRequest(RegisterRequestDTO registerRequestDTO);

    //ENTITY TO DTO

    RegisterRequestDTO toRegisterRequestDTO(RegisterRequest registerRequest);


}
