package duvan_vargas.btg_backend.service.interfaces;

import duvan_vargas.btg_backend.model.Fondo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IFondoService {

    Fondo createFondo(Fondo fondo);

    List<Fondo> findAllFondo();
}
