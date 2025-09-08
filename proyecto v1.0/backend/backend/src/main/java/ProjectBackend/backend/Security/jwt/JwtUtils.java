package ProjectBackend.backend.Security.jwt;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.security.Key;
import java.util.Date;
import java.util.function.Function;

/**
 * JwtUtils es una clase de utilidad para trabajar con JSON Web Tokens (JWT) en Spring Boot.
 *
 * JSON Web Token (JWT) es un estándar abierto (RFC 7519) que define un formato compacto y seguro
 * para transmitir información entre partes como un objeto JSON.
 *
 * Un JWT consta de tres partes separadas por puntos:
 * 1. Header (cabecera): contiene el tipo de token (JWT) y el algoritmo de firma (por ejemplo HS256)
 * 2. Payload (carga útil): contiene los claims o datos que quieres transmitir, como el username o roles
 * 3. Signature (firma): permite verificar que el token no fue modificado. Se genera con un algoritmo
 *    (HS256, RS256, etc.) usando un secret key.
 *
 * Ejemplo de un JWT:
 *   eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9
 *   .
 *   eyJzdWIiOiJ1c2VybmFtZSIsImlhdCI6MTY2Mjk0NzYyMywiZXhwIjoxNjYyOTUxMjIzfQ
 *   .
 *   dBjftJeZ4CVP-mB92K27uhbUJU1p1r_wW1gFWFOEjXk
 *
 * Esta clase permite:
 * - Generar tokens de acceso (generateAccesToken)
 * - Validar si un token es válido (isTokenValid)
 * - Extraer información del token, como el username (getUsernameFromToken)
 * - Obtener claims individuales o todos los claims del token
 * - Obtener la clave de firma (getSignatureKey)
 */

@Component
@Slf4j
public class JwtUtils {

    // Secret key usada para firmar y validar los tokens. Debe ser segura y larga.
    @Value("${jwt.secret.key}")
    private String secretKey;

    // Tiempo de expiración del token en milisegundos (por ejemplo, 86400000 = 1 día)
    @Value("${jwt.time.expiration}")
    private String timeExpiration;

    /**
     * Genera un token JWT de acceso para un username dado.
     *
     * @param username nombre del usuario que se guardará en el claim "sub" (subject)
     * @return token JWT firmado en formato String
     */
    public String generateAccesToken(String username){
        return Jwts.builder()
                .setSubject(username) // Claim "sub" = username
                .setIssuedAt(new Date(System.currentTimeMillis())) // Fecha de creación
                .setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(timeExpiration))) // Expiración
                .signWith(getSignatureKey(), SignatureAlgorithm.HS256) // Firma con HS256
                .compact(); // Genera el token en formato String
    }

    /**
     * Valida si un token JWT es válido.
     *
     * Intenta parsear el token con la clave secreta. Si no hay errores, es válido.
     *
     * @param token token JWT
     * @return true si el token es válido, false si es inválido o expirado
     */
    public boolean isTokenValid(String token){
        try {
            Jwts.parser()
                    .setSigningKey(getSignatureKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return true;
        } catch (Exception e) {
            log.error("invalid token, error: ".concat(e.getMessage())); // Log de error
            return false;
        }
    }

    /**
     * Obtiene el username (claim "sub") de un token.
     *
     * @param token token JWT
     * @return username contenido en el token
     */
    public String getUsernameFromToken(String token){
        return getClaim(token, Claims::getSubject);
    }

    /**
     * Obtiene un claim específico del token usando una función de transformación.
     *
     * @param token token JWT
     * @param claimsTFunction función que extrae un claim específico
     * @param <T> tipo de dato del claim
     * @return el claim solicitado
     */
    public <T> T getClaim(String token, Function<Claims, T> claimsTFunction){
        Claims claims = extractAllClaims(token);
        return claimsTFunction.apply(claims);
    }

    /**
     * Obtiene todos los claims (payload) de un token JWT.
     *
     * @param token token JWT
     * @return Claims con toda la información del token
     */
    public Claims extractAllClaims(String token){
        return Jwts.parser()
                .setSigningKey(getSignatureKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Obtiene la clave secreta (Key) que se utiliza para firmar/verificar el token.
     *
     * Convierte la cadena Base64 del secretKey a bytes y genera la Key para HS256.
     *
     * @return Key usada para firmar y validar tokens
     */
    public Key getSignatureKey(){
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}


