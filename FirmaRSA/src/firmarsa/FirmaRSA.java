
package firmarsa;

import javax.crypto.*;
import java.security.*;

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
        
        Signature firma = Signature.getInstance("MD%WithRSA");
                
    }
    
}