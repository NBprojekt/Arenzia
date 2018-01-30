package NBprojekt.Arenzia.Main;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Random;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class Crypt {
	// Cryptkey
	private static SecretKeySpec secretKeySpec;
	
	// create a key of a string
	public static void createSecretKey(String s) { 
		MessageDigest messageDigest;
		try {
			messageDigest = MessageDigest.getInstance("SHA-256"); 
			// 128 bit
			byte[] key = Arrays.copyOf(messageDigest.digest(s.getBytes("UTF-8")), 16); 
			secretKeySpec = new SecretKeySpec(key, "AES"); 
		} catch (UnsupportedEncodingException e) { 
			e.printStackTrace();
		}catch (NoSuchAlgorithmException e) { 
			e.printStackTrace();
		}
	}
	
	//  Encrypt a text of a string or a string array
	public static String Encrypt(String s) { 
		String encrypted = "failed";
		if (secretKeySpec == null) {
			System.out.println("Please run first 'Crypt.createSecretKey()'");
			return "";
		}
		try {
			// Create a cipher
			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);		 
			// Returning string
			encrypted = new BASE64Encoder().encode(cipher.doFinal(s.getBytes()));
		} catch (NoSuchAlgorithmException e) { 
			e.printStackTrace();
		} catch (NoSuchPaddingException e) { 
			e.printStackTrace();
		} catch (InvalidKeyException e) { 
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) { 
			e.printStackTrace();
		} catch (BadPaddingException e) { 
			e.printStackTrace();
		} 
		return encrypted;
	}
	public static void Encrypt(String[] s) {
		// Convert array into a string
		String tmp = ""; 
		for (int i = 0; i < s.length; i++){
			tmp += s[i] + "\r\n";
		}
		Encrypt(tmp);
	}
	
	// Decode
	public static String Decode(String s) {
		String decoded = "failed"; 
		if (secretKeySpec == null) {
			System.out.println("Please run first 'Crypt.createSecretKey()'");
			return "";
		}
		try {
			// Create a cipher
			Cipher cipher = Cipher.getInstance("AES"); 
			cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
			// Convert string into a byte-array 
			byte[] decode = cipher.doFinal(new BASE64Decoder().decodeBuffer(s));
			decoded = new String(decode);
		} catch (NoSuchAlgorithmException e) { 
			e.printStackTrace();
		} catch (NoSuchPaddingException e) { 
			e.printStackTrace();
		} catch (InvalidKeyException e) { 
			e.printStackTrace();
		} catch (IOException e) { 
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) { 
			e.printStackTrace();
		} catch (BadPaddingException e) { 
			e.printStackTrace();
		} 
		return decoded;
	}
	public static String randomString(int length) {
		String randomText = "";
		int minLength = 10;
		if (length < minLength)
			length = minLength;
		String stringList = "abcdefghijklmnopqrstuvwxyz"
						  +	"ABCDEFGHIJKLMNOPQRSTUVWXYZ"
						  + "0123456789+#-*!:;_<>|§$%&/";
		
		
		for (int i = 0; i < length; i++) { 
			randomText += stringList.charAt(new Random().nextInt(stringList.length()));
		}		
		return randomText;
	}
}
