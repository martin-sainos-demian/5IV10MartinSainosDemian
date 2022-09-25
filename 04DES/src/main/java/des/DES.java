package des;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author alumno
 * vamos a trabajar bajo un modo de cifrado por bloques de 64 bits
 * vamos a manejar una llave privada de 64 bits
 * se debe de tomar para la inizializacion de la llave 56 bits
 * exactamente tal y como nos dice la teoria porque es para poder formar 16 subllaves
 * las tablas de permutacion y cajas de sustitucion
 * se van a cargar por parte del proveedor de servicios
 * para poder hacer todo esto vamos a usar librerias
 * crypto y security
 */

import java.security.*;
//es para poer generar las claves y subllaves
import javax.crypto.spec.*;
import javax.crypto.*;

import java.io.*;

public class DES {
    public static void main(String args[])throws Exception{
        //vamos a comprobar la entrada de un archivo o fichero para cifrar
        if(args.length!=1){
            //no hay archivos cargados
            mensajeAyuda();
            System.exit(1);
            
            /*lo primero que tenemos que hacer es cargar un ainstancia del proveedor del tipo de cifrado
            ára eso está la parte de las librerias*/
            
            System.out.println("1.generar la clave DES: ");
            
            //vamos a usar la clase KeyGenerator
            KeyGenerator generadorDes = KeyGenerator.getInstance("DES");
            //inicializamo la llave
            generadorDes.init(56);
            
            //vamos a generar las claves
            SecretKey clave = generadorDes.generateKey();
            //estamos creando las 16 subllaves
            
            System.out.println("la clave es: "+clave);
            
            //recordemos que en la criptografía moderna solo se ocupan bits o bytes
            
            //la llave que se ha generado no tiene formato
            mostrarBytes(clave.getEncoded());
            
            System.out.println("");
            
            /*vamos a cifrar, para ello debemos de aplicar los estandares vistos en clase PKCS
            para ello tenemos que crear una instancia del algoritmo DES en el modo de cifrado
            
            ALGORITMO DES
            MODO ECB(ELECTRONIC CODE BOOK)
            RELLENO PKCS5
            */
            
            System.out.println("2.cifrar con DES y el archivo :"+args[0]
                    +" , dejar el resultado en: "+args[0]+".cifrado");
            
            //instancia
            Cipher cifrado = Cipher.getInstance("DES/ECB/PKCS5Padding");
            
            cifrado.init(Cipher.ENCRYPT_MODE,clave);
            
            //el problema es como vamos a leer los bloques del mensaje
            byte[] buffer = new byte[1000];
            byte[] bufferCifrado;
            
            //generamos los archivos
            
            FileInputStream in = new FileInputStream(args[0]);
            FileOutputStream out = new FileOutputStream(args[0]+".cifrado");
            
            //lectura
            int bytesLeidos = in.read(buffer, 0, 1000);
            //mientras no esté al final del archivo
            while(bytesLeidos != -1){
                //pasar el texto claro leido al cifrador
                bufferCifrado=cifrado.update(buffer, 0, bytesLeidos);
                //vanos a generar la salida
                out.write(bufferCifrado);
                bytesLeidos = in.read(buffer, 0, 1000);
            }
            //vamos a reunir todos los bloques
            bufferCifrado=cifrado.doFinal();
            out.write(bufferCifrado);
            
            in.close();
            out.close();
        }
    }

    private static void mensajeAyuda() {
        
    }

    private static void mostrarBytes(byte[] encoded) {
    }
}
