
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
        byte[] bufferplano = leerLinea(System.in);
        
        //ciframos
        Cipher cifrado = Cipher.getInstance("RSA", "BC");
        
        //ciframos con publica
        cifrado.init(Cipher.ENCRYPT_MODE, clavePublica);
        
        System.out.println("3 ciframos con clave public: ");
        
        byte[] buffercifrado = cifrado.doFinal(bufferplano);
        
        System.out.println("txt cipher: ");
        //no tiene format
        mostrarBytes(buffercifrado);
        System.out.println("");
        
        //desciframos con privada
        cifrado.init(Cipher.DECRYPT_MODE, clavePrivada);
        
        System.out.println("4 descipher con clave priv");
        
        byte[] bufferdescifrado = cifrado.doFinal(buffercifrado);
        
        System.out.println("texto descifrado: ");
        mostrarBytes(bufferdescifrado);
        
        System.out.println("");
        
        //vamos a voltearlo
        
        cifrado.init(Cipher.ENCRYPT_MODE, clavePrivada);
        System.out.println("5 ciframos con la privada");
        buffercifrado = cifrado.doFinal(bufferplano);
        
        System.out.println("texto cipher");
        mostrarBytes(buffercifrado);
        
        System.out.println("");
        
        cifrado.init(Cipher.DECRYPT_MODE, clavePublica);
        
        System.out.println("6 descifrmos con publica: ");
        
        bufferdescifrado = cifrado.doFinal(buffercifrado);
        
        System.out.println("texto descipher");
        mostrarBytes(bufferdescifrado);
        
        System.out.println("");
    }

    private static byte[] leerLinea(InputStream in) throws Exception{
        //definir como leer
        byte[] buffer1 = new byte[1000];
        int i = 0;
        byte c;
        
        c = (byte)in.read();
        
        while((c != '\n')&&(i<1000)){
            buffer1[i] = c;
            c = (byte)in.read();
            i++;
        }
        
        byte[] buffer2 = new byte[i];
        for(int j = 0; j < i; j++){
            buffer2[j] = buffer1[j];
        }
        
        return buffer2;
    }

    private static void mostrarBytes(byte[] buffer) {
        System.out.write(buffer, 0, buffer.length);
    }
}