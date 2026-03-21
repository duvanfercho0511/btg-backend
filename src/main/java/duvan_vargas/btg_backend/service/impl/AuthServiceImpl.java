package duvan_vargas.btg_backend.service.impl;


import duvan_vargas.btg_backend.exception.DataNotFoundException;
import duvan_vargas.btg_backend.exception.ValidacionException;
import duvan_vargas.btg_backend.model.*;
import duvan_vargas.btg_backend.repository.UsuarioRepository;
import duvan_vargas.btg_backend.service.interfaces.IAuthService;
import duvan_vargas.btg_backend.util.Jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements IAuthService {

    private final UsuarioRepository usuarioRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;


    public AuthResponse login(LoginRequest request) {

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        UserDetails user = usuarioRepository.findByUsername(request.getUsername()).orElseThrow(DataNotFoundException::new);
        List<String> roles = user.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        String token = jwtService.getAccessToken(user, roles);
        return AuthResponse.builder()
                .accesToken(token)
                .refreshToken(jwtService.getRefreshToken(user, roles))
                .build();
    }



    @Transactional
    public AuthResponse register(RegisterRequest request) {


        this.validarRegistro(request);
        Usuario user = Usuario.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .nombreApellido(request.getNombreApellido())
                .numeroTelefono(request.getNumeroTelefono())
                .password(passwordEncoder.encode(request.getPassword()))
                .rol(Rol.USER)
                .montoInicial(500000L)
                .fechaHoraCreacion(new Date())
                .build();

        usuarioRepository.save(user);

        List<String> roles = user.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return AuthResponse.builder()
            .accesToken(jwtService.getAccessToken(user, roles ))
            .build();

    }

    private void validarRegistro(RegisterRequest request){

        var existsSimilar = this.usuarioRepository.existsByUsernameOrEmail(request.getUsername(), request.getEmail());
        if (Boolean.TRUE.equals(existsSimilar))
            throw new ValidacionException("Ya existe un usuario con este username o este email.");
    }

}
