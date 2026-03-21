package duvan_vargas.btg_backend.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@Document(collection = "usuarios")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Usuario implements UserDetails {

    @Id
    private String id;

    private String username;
    private String password;
    private String email;

    private String nombreApellido;

    private String numeroTelefono;

    private Long montoInicial;

    private Rol rol;

    private Date fechaHoraCreacion;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + rol.name()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isAccountNonLocked() { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled() { return true; }
}
