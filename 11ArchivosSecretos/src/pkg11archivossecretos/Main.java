
package pkg11archivossecretos;

import java.io.*;
import java.security.*;
import java.security.spec.KeySpec;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.*;
public class Main {
    
    private static Cipher rsa;
    
    public static void main(String[] args) throws Exception{
        KeyPairGenerator generadorRSA = KeyPairGenerator.getInstance("RSA");
        
        KeyPair llavesRSA = generadorRSA.generateKeyPair();
        
        PublicKey llavePublica = llavesRSA.getPublic();
        
        PrivateKey llavePrivada = llavesRSA.getPrivate();
        
        //metodo para salvar y recuperar archivos
        save(llavePublica, "publickey.key");
        
        llavePublica = loadPublicKey("publickey.key");
        
        save(llavePrivada, "privadakey.key");
        
        llavePrivada = loadPrivateKey("privadakey.key");
        
        rsa = Cipher.getInstance("RSA/ECB/PCKS!Padding");
        
        String texto = "habia una vez un patito que decia miau miau y queria el god of war pero los malditos del internet le subieron el precio :c";
        
        //ciframos
        
        rsa.init(Cipher.ENCRYPT_MODE, llavePublica);
        
        byte[] encriptado = rsa.doFinal(texto.getBytes());
        
        //vamos a escribir eñ cifrado para que sea visible
        
        for(byte b : encriptado){
            System.out.print(Integer.toHexString(0xFF & b));
        }
        System.out.println("");
        
        //deciframos
        rsa.init(Cipher.DECRYPT_MODE, llavePrivada);
        System.out.println("");
        
        byte[] bytesdescifrado = rsa.doFinal();
        String textodescifrado = new String(bytesdescifrado);
        System.out.println(textodescifrado);
    }

    private static void save(Key key, String archivo) throws Exception{
        byte[] llavebytes = key.getEncoded();
        FileOutputStream fos = new FileOutputStream(archivo);
        fos.write(llavebytes);
        fos.close();
    }

    private static PublicKey loadPublicKey(String archivo) throws Exception{
        FileInputStream fis = new FileInputStream(archivo);
        int numBytes = fis.available();
        byte[] bytes = new byte[numBytes];
        fis.read(bytes);
        fis.close();
        
        KeyFactory llaveBytesPublico = KeyFactory.getInstance("RSA");
        KeySpec keySpec = new X509EncodedKeySpec(bytes);
        PublicKey nuevallavepublica = llaveBytesPublico.generatePublic(keySpec);
        return nuevallavepublica;
    }

    private static PrivateKey loadPrivateKey(String archivo) throws Exception{
        FileInputStream fis = new FileInputStream(archivo);
        int numBytes = fis.available();
        byte[] bytes = new byte[numBytes];
        fis.read(bytes);
        fis.close();
        
        KeyFactory llaveBytesPrivado = KeyFactory.getInstance("RSA");
        KeySpec keySpec = new X509EncodedKeySpec(bytes);
        PrivateKey nuevallaveprivada = llaveBytesPrivado.generatePrivate(keySpec);
        return nuevallaveprivada;
    }
    
}
