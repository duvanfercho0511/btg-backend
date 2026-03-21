package duvan_vargas.btg_backend.mapper;

import duvan_vargas.btg_backend.dto.LoginRequestDTO;
import duvan_vargas.btg_backend.model.LoginRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LoginRequestMapper {

    //DTO TO ENTITY

    LoginRequest toLoginRequest(LoginRequestDTO loginRequestDTO);

    //ENTITY TO DTO

    LoginRequestDTO toLoginRequestDTO(LoginRequest loginRequest);


}
