package TestAll;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.util.Base64;

import RSA.RSA;
import RSA.RSA_Read_Write_Key;
import RSA.StringToTxt;
import usb.Usb;

/**
 * Steps:
 * 1- Run Programm
 * 2- Insert USB stick (IsUsbStickInPort())
 * 
 */

public class test {

	@SuppressWarnings("resource")
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		//check if USB in Port
		//boolean isUsbThere=Usb.IsUsbStickInPort();
		System.out.println("Please Insert USB Stick!");
		System.out.println("---------------------------------------------");
		//get USB stick Drive (letter)
		String d= Usb.FindUsbDrive();
		//get USB serial Number
		
		//String sn = Usb.getSerialKey(d);
		String sn = Usb.getSerialNumber(d);
		System.out.println("USB Serial Number is: "+sn);
		System.out.println("---------------------------------------------");
		System.out.println("USB Serial Number 2 is: "+Usb.getSerialKey(d));
		System.out.println("---------------------------------------------");
		System.out.println();
		  System.out.println("----------------- Start Test 1 (RSA_Read_Write_Key class) ----------------------------");
		 // Get an instance of the RSA key generator
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(4096);

        // Generate the KeyPair
        KeyPair keyPair = keyPairGenerator.generateKeyPair();

        // Get the public and private key
        PublicKey publicKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();
        
        // Get the RSAPublicKeySpec and RSAPrivateKeySpec
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        RSAPublicKeySpec publicKeySpec = keyFactory.getKeySpec(publicKey, RSAPublicKeySpec.class);
        RSAPrivateKeySpec privateKeySpec = keyFactory.getKeySpec(privateKey, RSAPrivateKeySpec.class);
        
        // Saving the Key to the file
        RSA_Read_Write_Key.saveKeyToFile("public.key", publicKeySpec.getModulus(), publicKeySpec.getPublicExponent());
        RSA_Read_Write_Key.saveKeyToFile("private.key", privateKeySpec.getModulus(), privateKeySpec.getPrivateExponent());
        String plainText=sn;
        System.out.println("Original Text  : " + plainText);

        // Encryption
        byte[] cipherTextArray = RSA_Read_Write_Key.encrypt(sn, "D:\\sts-3.8.3.RELEASE\\Workspace\\Encryption\\public.key");
        String encryptedText = Base64.getEncoder().encodeToString(cipherTextArray);
        System.out.println("Encrypted Text : " + encryptedText);
        //save encryptedText to Txt File
        StringToTxt.SaveToTxt(encryptedText);
        //load encryptedText from txt file
      // String enc= StringToTxt.ReadFromTxt();
        // Decryption
        String decryptedText = RSA_Read_Write_Key.decrypt(cipherTextArray, "D:\\sts-3.8.3.RELEASE\\Workspace\\Encryption\\private.key");
        System.out.println("DeCrypted Text : " + decryptedText);
        //check if the Serial number is the same as the Origenal in the USB stick
        if(plainText.equals(decryptedText));
        System.out.println("OK! You can Use the Software! ");
        System.out.println("----------------- end of Test 1 (RSA_Read_Write_Key class) ----------------------------");
        
        
        try {

		      // Check if the pair of keys are present else generate those.
		      if (!RSA.areKeysPresent()) {
		        // Method generates a pair of keys using the RSA algorithm and stores it
		        // in their respective files
		        RSA.generateKey();
		      }
		      System.out.println();
		      System.out.println("-----------------Start Test 2 (RSA class)----------------------------");
		      System.out.println();
		      final String originalText = Usb.getSerialKey(d);
		      ObjectInputStream inputStream = null;

		      // Encrypt the string using the public key
		      inputStream = new ObjectInputStream(new FileInputStream(RSA.PUBLIC_KEY_FILE));
		      final PublicKey puKey = (PublicKey) inputStream.readObject();
		      final byte[] cipherText = RSA.encrypt(originalText, puKey);

		      // Decrypt the cipher text using the private key.
		      inputStream = new ObjectInputStream(new FileInputStream(RSA.PRIVATE_KEY_FILE));
		      final PrivateKey pk = (PrivateKey) inputStream.readObject();
		      final String plainText1 = RSA.decrypt(cipherText, pk);

		      // Printing the Original, Encrypted and Decrypted Text
		      System.out.println("Original Serial Number: " + originalText);
		      System.out.println("---------------------------------------------");
		      System.out.println("Encrypted: " +cipherText.toString());
		      System.out.println("---------------------------------------------");
		      System.out.println("Decrypted: " + plainText1);
		      System.out.println("---------------------------------------------");
		      System.out.println("----------------- end of Test 2 (RSA class) ----------------------------");
		      
		      //check if the Serial number is the same as the Origenal in the USB stick
		   //   if (originalText==plainText1)
		      // if(plainText.equals(originalText));
		    //	  System.out.println("OK,You can use the Software");
		      //if (riginalText)

		    } catch (Exception e) {
		      e.printStackTrace();
		    }
		  }
	

/**
 * bei Übereinstimmung meldet die Prüfmethode "OK" zurück
 * 
 */
	
	public static String isequal(String a,String b) {
		
	    if(a.equals(b));
		return "OK";
	}
	}


