package duvan_vargas.btg_backend.controller;

import duvan_vargas.btg_backend.dto.UsuarioDTO;
import duvan_vargas.btg_backend.mapper.UsuarioMapper;
import duvan_vargas.btg_backend.model.Usuario;
import duvan_vargas.btg_backend.service.interfaces.IUsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usuario")
@RequiredArgsConstructor
public class UsuarioController {

    private IUsuarioService usuarioService;

    private final UsuarioMapper usuarioMapper;

    /**
     * Retorna todos los registros de fondo
     *
     * @param
     * @return
     */
    @GetMapping("/findByUsername")
    public ResponseEntity<UsuarioDTO> findAllRubroContableElementoServicio(
            @RequestParam(name = "username") String username
    ) {
        Usuario usuario = this.usuarioService.findByUsername(username);
        if (usuario == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(usuarioMapper.toUsuarioDTO(usuario), HttpStatus.OK);
    }

    @Autowired
    public void setUsuarioService(IUsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }
}
