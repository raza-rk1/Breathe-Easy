package breatheasy.identityservice.util;
import java.util.UUID;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Base64;
public class UUIDEncryptionUtil {

    private static final String ALGORITHM = "AES";
    private static final String KEY = "cgGRPeYGuI1bzxKIIwgt5Q==";

    public static String encryptUUID(String originalUuid) {
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            Key secretKey = new SecretKeySpec(KEY.getBytes(), ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] encryptedBytes = cipher.doFinal(originalUuid.toString().getBytes());
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String decryptUUID(String encryptedUUID) {
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            Key secretKey = new SecretKeySpec(KEY.getBytes(), ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] decodedBytes = Base64.getDecoder().decode(encryptedUUID);
            byte[] decryptedBytes = cipher.doFinal(decodedBytes);
            return new String(decryptedBytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
