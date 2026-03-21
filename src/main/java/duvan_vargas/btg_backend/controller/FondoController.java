package duvan_vargas.btg_backend.controller;

import duvan_vargas.btg_backend.dto.AuthResponseDTO;
import duvan_vargas.btg_backend.dto.FondoDTO;
import duvan_vargas.btg_backend.dto.LoginRequestDTO;
import duvan_vargas.btg_backend.mapper.FondoMapper;
import duvan_vargas.btg_backend.model.AuthResponse;
import duvan_vargas.btg_backend.model.Fondo;
import duvan_vargas.btg_backend.service.interfaces.IFondoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/fondo")
@RequiredArgsConstructor
public class FondoController {

    private IFondoService fondoService;

    private final FondoMapper fondoMapper;


    /**
     * Endpoint para crear fondos
     * @param fondoDTO
     * @return
     */
    @PostMapping()
    public ResponseEntity<FondoDTO> createFondo(
            @RequestBody FondoDTO fondoDTO) {
        Fondo fondo = this.fondoService.createFondo(fondoMapper.toFondo(fondoDTO));

        if (fondo == null) {
            return new ResponseEntity<>(new FondoDTO(), HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(fondoMapper.toFondoDTO(fondo), HttpStatus.OK);
    }

    /**
     * Retorna todos los registros de fondo
     *
     * @param
     * @return
     */
    @GetMapping("/findAll")
    public ResponseEntity<List<FondoDTO>> findAllRubroContableElementoServicio() {

        List<Fondo> fondoList = this.fondoService.findAllFondo();
        if (fondoList == null || fondoList.isEmpty()) {
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(fondoList.stream()
                .map(fondoMapper::toFondoDTO).toList(), HttpStatus.OK);
    }


    @Autowired
    public void setFondoService(IFondoService fondoService) {
        this.fondoService = fondoService;
    }
}
