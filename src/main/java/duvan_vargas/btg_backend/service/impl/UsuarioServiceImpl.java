package duvan_vargas.btg_backend.service.impl;


import duvan_vargas.btg_backend.exception.ValidacionException;
import duvan_vargas.btg_backend.model.Usuario;
import duvan_vargas.btg_backend.repository.UsuarioRepository;
import duvan_vargas.btg_backend.service.interfaces.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioServiceImpl implements IUsuarioService {

    private UsuarioRepository usuarioRepository;

    @Override
    public Usuario findByEmail(String email) {
        var usuario = this.usuarioRepository.findByEmail(email);
        if(usuario.isPresent()){
            return usuario.get();
        }else{
            throw new ValidacionException("El email proporcionado no se encuentra registrado en la base de datos.");
        }
    }

    @Override
    public Usuario findByUsername(String userName) {
        var usuario = this.usuarioRepository.findByUsername(userName);
        if(usuario.isPresent()){
            return usuario.get();
        }else{
            throw new ValidacionException("El username proporcionado no se encuentra registrado en la base de datos.");
        }
    }

    @Autowired
    public void setUsuarioRepository(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }
}
