
package firmarsa;

import javax.crypto.*;
import java.security.*;

//libreria para formatos de la firma
import sun.misc.BASE64Encoder;

public class FirmaRSA {
    
    public static void main(String[] args) throws Exception{
        //vamos a realizar la instancia correspondiente del algoritmo
        KeyPairGenerator generadorRSA = KeyPairGenerator.getInstance("RSA");
        
        //inicializamos llave
        generadorRSA.initialize(4096);
        
        //crear las llaves para la firma
        KeyPair llaves = generadorRSA.generateKeyPair();
        
        //lo vamo a hacer con sha y jimmy con md5 (*w*)
        
        //texto
        
        byte[] dato = "habia una vez un patito que decia miau miau que queria mimir y mucho chocolate, y 2 novias que lo mantengan, un ps5 porque no alcanzamos el god of war, y por eso necesitamos mas cositas lindas y kawaii".getBytes("UTF8");
        
        //preparamos la firma
        
        Signature firma = Signature.getInstance("MD5WithRSA");
        
        //inicializo la llave privada
        firma.initSign(llaves.getPrivate());
        
        //actualizamos el documento
        firma.update(dato);
        
        //ahora firmamos el documento
        byte[] firmadocumento = firma.sign();
        System.out.println("la firma digital es: "+new BASE64Encoder().encode(firmadocumento));
                
        //proceso de verificacion de la firma que deberia de ir en otro lugar pero meh
        firma.initVerify(llaves.getPublic());
        
        //vamos a actualizar el edo del documento
        firma.update(dato);
        
        //vamos a ver si está bien el doc
        System.out.println("el documento es valido?");
        System.out.println(firma.verify(firmadocumento));
    }
    
}