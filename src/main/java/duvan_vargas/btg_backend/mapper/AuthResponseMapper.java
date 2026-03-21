package duvan_vargas.btg_backend.mapper;


import duvan_vargas.btg_backend.dto.AuthResponseDTO;
import duvan_vargas.btg_backend.model.AuthResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuthResponseMapper {


    //DTO TO ENTITY
    AuthResponse toAuthResponse(AuthResponseDTO personaDTO);

    //ENTITY TO DTO

    AuthResponseDTO toAuthResponseDTO(AuthResponse persona);

}
