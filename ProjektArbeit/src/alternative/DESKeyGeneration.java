package alternative;

import java.security.SecureRandom;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.util.Random;

class DESKeyGeneration {
	byte[] skey = new byte[1000];
	String skeyString;
	static byte[] raw;

	public DESKeyGeneration() {
		generateSymmetricKey();
	}

	void generateSymmetricKey() {
		try {
			Random r = new Random();
			int num = r.nextInt(10000);
			String knum = String.valueOf(num);
			byte[] knumb = knum.getBytes();
			skey = getRawKey(knumb);
			skeyString = new String(skey);
			System.out.println("DES Symmetric key = " + skeyString);
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	private static byte[] getRawKey(byte[] seed) throws Exception {
		KeyGenerator kgen = KeyGenerator.getInstance("DES");
		SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
		sr.setSeed(seed);
		kgen.init(56, sr);
		SecretKey skey = kgen.generateKey();
		raw = skey.getEncoded();
		return raw;
	}

	public byte[] getSessionKey() {
		return raw;
	}

	@SuppressWarnings("unused")
	public static void main(String args[]) {
		DESKeyGeneration aeskey = new DESKeyGeneration();
	}
}