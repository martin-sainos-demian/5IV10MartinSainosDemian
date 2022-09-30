/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aes;

import java.security.*;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
//para poder generar una clave secreta de mayor longitud

import sun.misc.*;

public class AES {
    //vamos a generar una llave
    public static final byte[] keyvalue = new byte[]{
        /* puede ser de 3 tamaños
        128 -> 16 caracteres -> 9 rondas
        192 -> 24 caracteres -> 11 rondas
        256 -> 32 caracteres -> 13 rondas
        */
        'i', ' ', 'f', 'o', 'r', 'g', 'o', 'r',
        ' ', ':', 's', 'k', 'u', 'l', 'l', ':'
    };
    
    private static final String instancia = "AES";
    //vamoa s crear los metodos para cifrar
    public static String cifrar(String Data) throws Exception{
        //para poder cifrar debemos de generar las llaves, pero vamos a crear un metodo para esa generacion
        // de las subllaves
        
        //vamos a ocupar un objeto Key
        Key llave = generarLlave();
        
        //inicializamos el cifrado
        Cipher cifrado = Cipher.getInstance(instancia);
        
        cifrado.init(Cipher.ENCRYPT_MODE, llave);
        
        //vamos a obtener el mensaje y hay que transformarlo en bytes
        byte[] encValores = cifrado.doFinal(Data.getBytes());
        
        //tenemos un problema el cual es el formato para poder leerlo
        //tenemos que aplicar un formato BASE64
        System.out.println("Valores sin formato: " + encValores);
        
        //aplicamos formato
        String valoresEncriptados = new BASE64Encoder().encode(encValores);
        
        System.out.println("Valores con formato: " + valoresEncriptados);
        
        return valoresEncriptados;
    }
    
    public static String descifrar(String valoresEncriptados) throws Exception{
        //para poder cifrar debemos de generar las llaves, pero vamos a crear un metodo para esa generacion
        // de las subllaves

        //vamos a ocupar un objeto Key
        Key llave = generarLlave();

        //inicializamos el cifrado
        Cipher cifrado = Cipher.getInstance(instancia);

        cifrado.init(Cipher.DECRYPT_MODE, llave);

        //vamos a obtener el mensaje y hay que decodificarlo en bytes
        byte[] decodificadosValores = new BASE64Decoder().decodeBuffer(valoresEncriptados);
        
        //aqui hay que transformarlos
        byte[] decValores = cifrado.doFinal(decodificadosValores);

        //tenemos un problema el cual es el formato para poder leerlo
        //tenemos que aplicar un formato BASE64
        System.out.println("Valores sin formato: " + decValores);
        
        //aplico un formato de cadena
        String valoresDecifrados = new String(decValores);

        return valoresDecifrados;
    }

    private static Key generarLlave() throws Exception{
        //vamos a ocupar las llaves de AES de acuerdo a la clase SecretKeySpec,
        // esta nos genera todas las subllaves de ronda
        
        Key llave = new SecretKeySpec(keyvalue, instancia);
        return llave;
    }
}