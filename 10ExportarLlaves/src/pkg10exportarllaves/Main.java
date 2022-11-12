
package pkg10exportarllaves;

import java.io.*;
import java.security.*;
import java.security.spec.*;
import javax.crypto.*;
import javax.crypto.spec.DESKeySpec;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class Main {

    public static void main(String[] args) throws Exception {
        //verificar si hay un archivo
        if(args.length !=1 ){
            mensajeAyuda();
            System.exit(0);
        }
        
        //creamos los archivos
        System.out.println("crea los archivos : "+args[0]+".secreta, "
        +args[0]+".pulblica, "+args[0]+".privada");
        
        //añadimos el proveedor
        Security.addProvider(new BouncyCastleProvider());
        
        //generamos la llave rsa
        KeyPairGenerator generadorRSA= KeyPairGenerator.getInstance("RSA", "BC");
        
        //inicializamos la llave
        generadorRSA.initialize(4096);
        
        //generamos llave publica y privada
        KeyPair clavesRSA = generadorRSA.genKeyPair();
        
        //definimos publica
        PublicKey clavePublica = clavesRSA.getPublic();
        
        //definimos privada
        PrivateKey clavePrivada = clavesRSA.getPrivate();
        
        //generamos las llaves para la firma con KeyFactory
        KeyFactory llavesRSAfirma = KeyFactory.getInstance("RSA", "BC");
        
        //tenemos que establecer el protocolo para la extraccion para la firma digital
        //para ello ocuparemos el PKCS8
        
        PKCS8EncodedKeySpec pkcs8llave = new PKCS8EncodedKeySpec(clavePrivada.getEncoded());
        
        //vamos a escribirlo en un archivo
        FileOutputStream archivosalida = new FileOutputStream(args[0]+"privada");
        archivosalida.write(pkcs8llave.getEncoded());
        archivosalida.close();
        
        //vamos a recuperar la clave privada
        byte[] bufferprivado = new byte[5000];
        FileInputStream in = new FileInputStream(args[0]+".privada");
        in.read(bufferprivado, 0, 5000);
        in.close();
        
        //recuperamos la clave privada desde los datos codificados por el formato PKCS8
        
        PKCS8EncodedKeySpec clavePrivadaSpec = new PKCS8EncodedKeySpec(bufferprivado);
        
        //vemos la clave privada para la firma
        PrivateKey clavePrivada2 = llavesRSAfirma.generatePrivate(clavePrivadaSpec);
        
        //validar la privada de la clave
        if(clavePrivada.equals(clavePrivada2)){
            System.out.println("ok , la clave privada ha sido guardada y recuperada");
        }else{
            System.out.println("ya te hakiaron");
        }
        
        X509EncodedKeySpec llavex509 = new X509EncodedKeySpec(clavePublica.getEncoded());
        
        //vamos a escribir la publica
        archivosalida = new FileOutputStream(args[0]+".publica");
        
        //tenemos que hacer uso del estandar x.509 para la generacion de llaves publicas con rsa para firma digital
        archivosalida.write(llavex509.getEncoded());
        archivosalida.close();
        
        //recuperar el archivo publico
        byte[] bufferpublic = new byte[5000];
        in = new FileInputStream(args[0]+".publica");
        in.read(bufferpublic, 0 , 5000);
        in.close();
        
        //recuperamos y validamos la llave que sea correcta
        X509EncodedKeySpec clavePublicaSpec = new X509EncodedKeySpec(bufferpublic);
        
        //creamos otra llave para comparar
        PublicKey clavePublic2 = llavesRSAfirma.generatePublic(clavePublicaSpec);
        
        if(clavePublica.equals(clavePublic2)){
            System.out.println("ok , la clave publica ha sido guardada y verificada");
        }else{
            System.out.println("ya te hakiaron");
        }
        
        //te entrego una por defecto
        
        KeyGenerator generadorDES = KeyGenerator.getInstance("DES");
        
        generadorDES.init(56);
        
        SecretKey claveSecretaDES = generadorDES.generateKey();
        
        SecretKeyFactory llaveDES = SecretKeyFactory.getInstance("DES");
        
        //realizamos el volcado de las llaves
        FileOutputStream out = new FileOutputStream(args[0]+".secreta");
        out.write(claveSecretaDES.getEncoded());
        out.close();
        
        //recuperamos la clave secreta
        byte[] buffersecret = new byte[5000];
        FileInputStream entrada = new FileInputStream(args[0]+".secreta");
        
        entrada.read(buffersecret, 0, 5000);
        entrada.close();
        
        //comparo las llaves
        DESKeySpec llavedes = new DESKeySpec(buffersecret);
        
        SecretKey clavesecreta2 = llaveDES.generateSecret(llavedes);
        
        if(claveSecretaDES.equals(clavesecreta2)){
            System.out.println("ok, la llave secreta ha sido guardada y verificada");
        }else{
            System.out.println("ya te hakiaron");
        }
    }

    private static void mensajeAyuda() {
        System.out.println("ejemplo de generacion de llaves");
    }
}