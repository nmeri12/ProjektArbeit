package alternative;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.util.Formatter;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.JOptionPane;

/**
 * Java Swing
 * Hash of input message is generated using the HmacSHA1 algorithm that is
 * available in javax. crypto.Mac package. Generated hash is not irreversible.
 * This code can be used in the process of integrity verification process of
 * message (checking for alteration of message) while transmitting data over the
 * network which is vulnerable to various attacks.
 * 
 */
class FindHash {

	private static final String HMAC_SHA1_ALGORITHM = "HmacSHA1";

	public FindHash() {
		try {
			String inputMessage = JOptionPane.showInputDialog(null, "Enter message to hash");
			String hmac_message = calculateRFC2104HMAC(inputMessage, "key1");
			JOptionPane.showMessageDialog(null, "Hash of the message: " + hmac_message);
		} catch (Exception e) {
			System.out.println(e);
		}
	}

//************* Coding for Hashing *******************

	private static String toHexString(byte[] bytes) {
		@SuppressWarnings("resource")
		Formatter formatter = new Formatter();
		for (byte b : bytes) {
			formatter.format("%02x", b);

		}
		return formatter.toString();
	}

	public static String calculateRFC2104HMAC(String data, String key)
			throws SignatureException, NoSuchAlgorithmException, InvalidKeyException {
		SecretKeySpec signingKey = new SecretKeySpec(key.getBytes(), HMAC_SHA1_ALGORITHM);
		Mac mac = Mac.getInstance(HMAC_SHA1_ALGORITHM);
		mac.init(signingKey);
		return toHexString(mac.doFinal(data.getBytes()));
	}

	@SuppressWarnings("unused")
	public static void main(String args[]) {
		FindHash fh = new FindHash();
	}

}