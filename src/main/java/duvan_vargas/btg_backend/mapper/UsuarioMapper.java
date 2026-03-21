package duvan_vargas.btg_backend.mapper;

import duvan_vargas.btg_backend.dto.UsuarioDTO;
import duvan_vargas.btg_backend.model.Usuario;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    Usuario toUsuario(UsuarioDTO usuarioDTO);

    UsuarioDTO toUsuarioDTO (Usuario usuario);
}
