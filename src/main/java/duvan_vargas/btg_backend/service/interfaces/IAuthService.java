package duvan_vargas.btg_backend.service.interfaces;


import duvan_vargas.btg_backend.model.AuthResponse;
import duvan_vargas.btg_backend.model.LoginRequest;
import duvan_vargas.btg_backend.model.RegisterRequest;
import org.springframework.stereotype.Service;

@Service
public interface IAuthService {

    AuthResponse login(LoginRequest request);

    AuthResponse register(RegisterRequest request);
}
