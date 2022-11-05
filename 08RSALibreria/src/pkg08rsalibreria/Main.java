
package pkg08rsalibreria;

import java.io.*;
import java.nio.channels.Channel;
import java.security.*;

import javax.crypto.*;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
//la libreria de bouncycastle nos permite generar numeros de p q n fi mas grandes, pero no maneja correctameente el modo ECB
//, por lo que el tamaño de la llave debe de ser de 512 y realizar el bloque a mano

public class Main {

    public static void main(String[] args) throws Exception {
        //primero tenemos que añadir el nuevo proveedor de algoritmo debido a que securityno tiene soporte para BC
        
        Security.addProvider(new BouncyCastleProvider());
        
        //vamos a la generacion de las claves
        System.out.println("1 crear las llaves publica y privada");
        
        KeyPairGenerator keygen = KeyPairGenerator.getInstance("RSA", "BC");
        
        //inicializar llave
        keygen.initialize(512);
        
        //vamos a asignar la llave publica y privada
        KeyPair clavesRSA=keygen.generateKeyPair();
        
        //definimos la llave privada
        PrivateKey clavePrivada = clavesRSA.getPrivate();
        PublicKey clavePublica = clavesRSA.getPublic();
        
        System.out.println("2 introducir el txt plano que desea cifrar max 64 chars");
        
        //almacenar el txt en un array de bytes
        byte[] bufferplano = leerLinea(System.inheritedChannel());
        
        //ciframos
        Cipher cifrado = Cipher.getInstance("RSA", "BC");
        
        //ciframos con publica
        cifrado.init(Cipher.ENCRYPT_MODE, clavePublica);
    }

    private static byte[] leerLinea(Channel inheritedChannel) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}