package duvan_vargas.btg_backend.util;

import duvan_vargas.btg_backend.exception.ValidacionException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Set;

@Service
public class VerificationServiceImpl {

    /**
     * Metodo para verificar los roles autorizados, obteniendo los roles del token de jwt, luego lo verifica y de no tener ninguno, aparece el mensaje de error
     * @param userDetails
     * @param rolesAutorizados
     * @return
     */
    public void verificarRolesAutorizados(@AuthenticationPrincipal UserDetails userDetails, Set<String> rolesAutorizados) {

        Collection<? extends GrantedAuthority> roles = userDetails.getAuthorities();

        boolean tieneRolAutorizado = roles.stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(rolesAutorizados::contains);

        if (Boolean.FALSE.equals(tieneRolAutorizado))
            throw new ValidacionException("No tienes permiso para acceder a este recurso");
    }

}
