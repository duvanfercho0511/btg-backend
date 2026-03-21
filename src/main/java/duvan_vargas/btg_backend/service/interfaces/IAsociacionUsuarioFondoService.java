package duvan_vargas.btg_backend.service.interfaces;

import duvan_vargas.btg_backend.model.AsociacionUsuarioFondo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IAsociacionUsuarioFondoService {

    List<AsociacionUsuarioFondo> findAllAsociacionByIdUsuario(String idUsuario);

    AsociacionUsuarioFondo createAsociacion(AsociacionUsuarioFondo asociacionUsuarioFondo);
}
