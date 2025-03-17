package med.voll.api.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import med.voll.api.domain.usuario.Usuario;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.sql.SQLOutput;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;

import static com.auth0.jwt.JWT.*;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;

    public String gerarToken(Usuario usuario) {
        try {
            var algoritmo = Algorithm.HMAC256(secret);
            return  JWT.create().
                    withIssuer("VollMed API").
                    withSubject(usuario.getLogin()).
                    withExpiresAt(dataExpiracao()).
                    sign(algoritmo);
        } catch (JWTVerificationException exception){
            throw new RuntimeException("Erro ao gerar token JWT.");
        }
    }

    public String getSubject(String tokenJWT) {
        var algoritmo = Algorithm.HMAC256(secret);
        try {
            return JWT.require(algoritmo)
                    .withIssuer("VollMed API")
                    .build()
                    .verify(tokenJWT)
                    .getSubject();
        } catch (JWTVerificationException exception){
            throw new RuntimeException("Erro ao validar token JWT.");
        }
    }

    private Instant dataExpiracao() {
        return LocalDateTime.now().plusHours(2).toInstant(
                java.time.ZoneOffset.of("-03:00"));
    }
}
