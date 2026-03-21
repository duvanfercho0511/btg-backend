package duvan_vargas.btg_backend.controller;


import duvan_vargas.btg_backend.dto.AuthResponseDTO;
import duvan_vargas.btg_backend.dto.LoginRequestDTO;
import duvan_vargas.btg_backend.dto.RegisterRequestDTO;
import duvan_vargas.btg_backend.mapper.AuthResponseMapper;
import duvan_vargas.btg_backend.mapper.LoginRequestMapper;
import duvan_vargas.btg_backend.mapper.RegisterRequestMapper;
import duvan_vargas.btg_backend.model.AuthResponse;
import duvan_vargas.btg_backend.model.Usuario;
import duvan_vargas.btg_backend.repository.UsuarioRepository;
import duvan_vargas.btg_backend.service.interfaces.IPasswordResetService;
import duvan_vargas.btg_backend.service.interfaces.IUsuarioService;
import duvan_vargas.btg_backend.service.interfaces.IAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    
    private final IAuthService IAuthService;

    private IPasswordResetService passwordResetService;

    private IUsuarioService usuarioService;

    private UsuarioRepository usuarioRepository;

    private final PasswordEncoder passwordEncoder;

    private final LoginRequestMapper loginRequestMapper;

    private final AuthResponseMapper authResponseMapper;

    private final RegisterRequestMapper registerRequestMapper;

    /**
     * Endpoint que retorna un accesToken y un refreshToken
     * @param loginRequestDTO
     * @return
     */
    @PostMapping(value = "/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody LoginRequestDTO loginRequestDTO)
    {
        AuthResponse response = this.IAuthService.login(loginRequestMapper.toLoginRequest(loginRequestDTO));

        if (response == null) {
            return new ResponseEntity<>(new AuthResponseDTO(), HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(authResponseMapper.toAuthResponseDTO(response), HttpStatus.OK);
    }

    /**
     * Endpoint que retorna un accesToken
     * @param registerRequestDTO
     * @return
     */
    @PostMapping(value = "/register")
    public ResponseEntity<AuthResponseDTO> register(@RequestBody RegisterRequestDTO registerRequestDTO)
    {
        AuthResponse response = this.IAuthService.register(registerRequestMapper.toRegisterRequest(registerRequestDTO));

        if (response == null) {
            return new ResponseEntity<>(new AuthResponseDTO(), HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(authResponseMapper.toAuthResponseDTO(response), HttpStatus.OK);
    }

    /**
     * Endpoint que envía un correo con un mensaje de recuperación de contraseña
     * @param email
     * @return
     */
    @PostMapping("/enviarCodigo")
    public ResponseEntity<String> resetPassword(@RequestParam(name = "email") String email) {
        Usuario user = usuarioService.findByEmail(email);
        if (user != null) {
            passwordResetService.sendPasswordResetCode(email);
            return ResponseEntity.ok("Código de recuperación enviado por correo electrónico.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Correo electrónico no encontrado.");
        }
    }

    /**
     * Endpoint para cambiar contraseña
     * @param email
     * @param codigo
     * @param password
     * @return
     */
    @PostMapping("/cambiarPassword")
    public ResponseEntity<String> changePassword(
            @RequestParam(name = "email") String email,
            @RequestParam(name = "codigo") String codigo,
            @RequestParam(name = "password") String password
    ) {
        Usuario user = usuarioService.findByEmail(email);
        var token = passwordResetService.isCodeValid(email, codigo);
        if (Boolean.TRUE.equals(token)) {
            if (user != null) {
                user.setPassword(passwordEncoder.encode(password));
                usuarioRepository.save(user);
                return ResponseEntity.ok("Contraseña actualizada correctamente.");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("El email proporcionado no se encuentra registrado en la base de datos.");
            }
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Código de recuperación no válido o ha expirado.");
        }
    }

    @Autowired
    public void setUsuarioService(IUsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @Autowired
    public void setUsuarioRepository(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Autowired
    public void setPasswordResetService(IPasswordResetService passwordResetService) {
        this.passwordResetService = passwordResetService;
    }


}
