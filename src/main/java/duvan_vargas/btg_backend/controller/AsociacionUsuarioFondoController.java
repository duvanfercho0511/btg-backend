package duvan_vargas.btg_backend.controller;

import duvan_vargas.btg_backend.dto.AsociacionUsuarioFondoDTO;
import duvan_vargas.btg_backend.dto.FondoDTO;
import duvan_vargas.btg_backend.mapper.AsociacionUsuarioFondoMapper;
import duvan_vargas.btg_backend.model.AsociacionUsuarioFondo;
import duvan_vargas.btg_backend.model.Fondo;
import duvan_vargas.btg_backend.service.interfaces.IAsociacionUsuarioFondoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/asociacionUsuarioFondo")
@RequiredArgsConstructor
public class AsociacionUsuarioFondoController {

    private IAsociacionUsuarioFondoService asociacionUsuarioFondoService;

    private final AsociacionUsuarioFondoMapper asociacionUsuarioFondoMapper;


    /**
     * Endpoint para crear asociaciones de fondos y usuarios
     * @param asociacionUsuarioFondoDTO
     * @return
     */
    @PostMapping()
    public ResponseEntity<AsociacionUsuarioFondoDTO> createAsociacion(
            @RequestBody AsociacionUsuarioFondoDTO asociacionUsuarioFondoDTO) {
        AsociacionUsuarioFondo asociacionUsuarioFondo = this.asociacionUsuarioFondoService.createAsociacion(asociacionUsuarioFondoMapper.toAsociacionUsuarioFondo(asociacionUsuarioFondoDTO));

        if (asociacionUsuarioFondo == null) {
            return new ResponseEntity<>(new AsociacionUsuarioFondoDTO(), HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(asociacionUsuarioFondoMapper.toAsociacionUsuarioFondoDTO(asociacionUsuarioFondo), HttpStatus.OK);
    }

    /**
     * Retorna todos los registros de asociaciones de fondo para un usuario
     *
     * @param
     * @return
     */
    @GetMapping("/findAll")
    public ResponseEntity<List<AsociacionUsuarioFondoDTO>> findAllAsociacionByIdUsuario(
            @RequestParam(name = "idUsuario") String idUsuario
    ) {

        List<AsociacionUsuarioFondo> asociacionUsuarioFondoList = this.asociacionUsuarioFondoService.findAllAsociacionByIdUsuario(idUsuario);
        if (asociacionUsuarioFondoList == null || asociacionUsuarioFondoList.isEmpty()) {
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(asociacionUsuarioFondoList.stream()
                .map(asociacionUsuarioFondoMapper::toAsociacionUsuarioFondoDTO).toList(), HttpStatus.OK);
    }


    @Autowired
    public void setAsociacionUsuarioFondoService(IAsociacionUsuarioFondoService asociacionUsuarioFondoService) {
        this.asociacionUsuarioFondoService = asociacionUsuarioFondoService;
    }
}
