package duvan_vargas.btg_backend.repository;

import duvan_vargas.btg_backend.model.Usuario;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UsuarioRepository extends MongoRepository<Usuario, String> {

    Optional<Usuario> findByUsername(String username);

    Boolean existsByUsernameOrEmail(String username, String email);

    Optional<Usuario> findByEmail(String email);

}