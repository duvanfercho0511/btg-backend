package duvan_vargas.btg_backend.repository;

import duvan_vargas.btg_backend.model.PasswordResetToken;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface PasswordResetTokenRepository extends MongoRepository<PasswordResetToken, String> {

    Optional<PasswordResetToken> findByEmailAndCodigo(String email, String code);
}
