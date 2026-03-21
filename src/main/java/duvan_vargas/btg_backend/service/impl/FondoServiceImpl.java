package duvan_vargas.btg_backend.service.impl;

import duvan_vargas.btg_backend.exception.ValidacionException;
import duvan_vargas.btg_backend.model.Fondo;
import duvan_vargas.btg_backend.repository.FondoRepository;
import duvan_vargas.btg_backend.service.interfaces.IFondoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FondoServiceImpl implements IFondoService {

    private FondoRepository fondoRepository;

    @Override
    public Fondo createFondo(Fondo fondo) {
        this.validarCreacion(fondo);
        fondo.setValorTotalVinculaciones(0L);
        return this.fondoRepository.save(fondo);
    }

    @Override
    public List<Fondo> findAllFondo() {
        return this.fondoRepository.findAll();
    }

    private void validarCreacion(Fondo fondo){
        Boolean exists = this.fondoRepository.existsByNombre(fondo.getNombre());
        if(Boolean.TRUE.equals(exists)) throw new ValidacionException("Ya existe un fondo creado con el mismo nombre.");
    }

    @Autowired
    public void setFondoRepository(FondoRepository fondoRepository) {
        this.fondoRepository = fondoRepository;
    }
}
