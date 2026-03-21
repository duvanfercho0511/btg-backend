package duvan_vargas.btg_backend.service.interfaces;


import duvan_vargas.btg_backend.model.Usuario;

import org.springframework.stereotype.Service;

@Service
public interface IUsuarioService {

    Usuario findByEmail(String email);

    Usuario findByUsername(String userName);

}
