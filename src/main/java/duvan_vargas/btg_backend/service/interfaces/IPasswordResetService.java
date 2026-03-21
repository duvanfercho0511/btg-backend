package duvan_vargas.btg_backend.service.interfaces;

import org.springframework.stereotype.Service;

@Service
public interface IPasswordResetService {

    void sendPasswordResetCode(String email);

    boolean isCodeValid(String email, String code);
}
