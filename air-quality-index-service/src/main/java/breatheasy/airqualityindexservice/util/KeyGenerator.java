package breatheasy.airqualityindexservice.util;

import java.security.SecureRandom;
import java.util.Base64;

public class KeyGenerator {

    public static void generateSecureKey() {
        try {
            SecureRandom secureRandom = new SecureRandom();
            byte[] keyBytes = new byte[16]; // 128 bits
            secureRandom.nextBytes(keyBytes);
            String base64Key = Base64.getEncoder().encodeToString(keyBytes);
            System.out.println("Secure Secret Key: " + base64Key);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

