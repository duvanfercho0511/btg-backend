package duvan_vargas.btg_backend.repository;

import duvan_vargas.btg_backend.model.Fondo;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FondoRepository extends MongoRepository<Fondo, String> {

    Boolean existsByNombre(String nombre);

}
