package controlador;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
/**
 * Esta Clase permite cifrar la contraseña que se le introduzca y también descifrarla. 
 * Emplea un algoritmo que siempre se repite con cada cifrado y descifrado, el cual se modifica en función de una palabra clave,
 * que se le puede modificar de modo estático, pero que siempre ha de ser la misma (o su resultado será distinto).
 * Quiero añadir que casi todo el método está creado por ChatGPT, salvo pequeñas modificaciones que hice a posteriori para que case con el resto del proyecto.
 * @author ChatGPT
 * @version 1.1
 */
public class Cifrado {

	    private static final String ALGORITHM = "AES"; 
	    private static final String KEY = "AFThj929inWrMb0q"; //16 caracteres a voluntad 
/**
 * Este método recibe un String, lo encripta y devuelve el resultado de su encriptación.
 * @param valueToEncrypt String que encriptará el método.
 * @return Devuelve la encriptación del String recibido.
 */
	    public static String encriptar(String valueToEncrypt) {
	    	byte[] encryptedValue = null;
	    	try {
	            SecretKeySpec secretKey = new SecretKeySpec(KEY.getBytes(), ALGORITHM);
	            Cipher cipher = Cipher.getInstance(ALGORITHM);
	            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
	            encryptedValue = cipher.doFinal(valueToEncrypt.getBytes());
	            
	        } catch (Exception ex) {
	            ex.printStackTrace();
	        }
	        return Base64.getEncoder().encodeToString(encryptedValue);
	    }
/**
 * Este método recibe un String y devuelve el resultado de su desencriptación.
 * @param encryptedValue
 * @return Devuelve la desencriptación del String recibido.
 */
	    public static String desencriptar(String encryptedValue) {
	    	byte[] decryptedValue =null;
	    	 
	    	try {
	            SecretKeySpec secretKey = new SecretKeySpec(KEY.getBytes(), ALGORITHM);
	            Cipher cipher = Cipher.getInstance(ALGORITHM);
	            cipher.init(Cipher.DECRYPT_MODE, secretKey);
	            decryptedValue = cipher.doFinal(Base64.getDecoder().decode(encryptedValue));
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return new String(decryptedValue);
	    }
	    //Prueba de su funcionamiento
//	    public static void main(String[] args) {
//	    	
//	        String originalString = "INTRODUCE UN TEXTO";
//	        String encryptedString = encriptar(originalString);
//	        String decryptedString = desencriptar(encryptedString);
//
//	        System.out.println("Original String: " + originalString);
//	        System.out.println("Encrypted String: " + encryptedString);
//	        System.out.println("Decrypted String: " + decryptedString);
//	    }
	}
