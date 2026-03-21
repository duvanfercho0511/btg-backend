package duvan_vargas.btg_backend.service.impl;

import duvan_vargas.btg_backend.model.PasswordResetToken;
import duvan_vargas.btg_backend.repository.PasswordResetTokenRepository;
import duvan_vargas.btg_backend.service.interfaces.IPasswordResetService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class PasswordResetServiceImpl implements IPasswordResetService {


    private final JavaMailSender javaMailSender;


    private final PasswordResetTokenRepository tokenRepository;

    private String generateRandomCode() {
        Random random = new Random();
        int code = 1000 + random.nextInt(9000);
        return String.valueOf(code);
    }

    public void sendPasswordResetCode(String email) {
        String code = generateRandomCode();


        PasswordResetToken token = new PasswordResetToken();
        token.setEmail(email);
        token.setCodigo(code);

        token.setFechaExpiracion(new Date(System.currentTimeMillis() + 3600000));
        tokenRepository.save(token);


        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Código de recuperación de contraseña");
        message.setText("Tu código de recuperación de contraseña es: " + code);
        javaMailSender.send(message);
    }


    public boolean isCodeValid(String email, String code) {
        Optional<PasswordResetToken> tokenOptional = tokenRepository.findByEmailAndCodigo(email, code);

        if (tokenOptional.isPresent()) {
            PasswordResetToken token = tokenOptional.get();
            Date currentDate = new Date();
            return !token.getFechaExpiracion().before(currentDate);
        }else{
            return false;
        }

    }
}
