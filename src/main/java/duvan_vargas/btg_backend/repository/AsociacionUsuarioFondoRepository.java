package duvan_vargas.btg_backend.repository;

import duvan_vargas.btg_backend.model.AsociacionUsuarioFondo;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface AsociacionUsuarioFondoRepository extends MongoRepository<AsociacionUsuarioFondo, String> {

    List<AsociacionUsuarioFondo> findAllByIdUsuarioOrderByFechaHoraCreacionAsc(String idUsuario);

    Boolean existsByIdUsuarioAndIdFondoAndActivo(String idUsuario, String idFondo, Boolean activo);

    AsociacionUsuarioFondo findFirstByIdUsuarioAndIdFondoAndActivoOrderByFechaHoraCreacionDesc(String idUsuario, String idFondo, Boolean activo);
}
