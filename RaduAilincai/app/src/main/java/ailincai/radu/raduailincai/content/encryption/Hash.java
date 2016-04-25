package ailincai.radu.raduailincai.content.encryption;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Hash {

    public static final String MD5_ALGORITHM = "MD5";

    private final String preHash;

    public Hash(String originalString) {
        this.preHash = originalString;
    }

    public String generateHash(String algorithm) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
        messageDigest.reset();
        messageDigest.update(preHash.getBytes());
        byte[] hashBytes = messageDigest.digest();
        BigInteger hashAsBigInteger = new BigInteger(1, hashBytes);
        return hashAsBigInteger.toString(16);
    }

}
