/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paquete;

/**
 *
 * @author cerna
 * 
 * Vamos a programar bajo un modo de cifrado por bloques de 64 bits
 * vamos a manejar una llave privada de 64 bits
 * Se debe de tomar para la inicialización de la llave 56 bit exactamente
 * tal y como nos dice la teoría porque es para poder formar 
 * 
 * Las tablas de permutación y cajas de sustitución 
 * se van a cargar por parte del proveedor de servicios
 * Para poder hacer todo esto vamos a usar Librerias
 * crypto y security
 */
import java.security.*;
//es para poder generar las claves y subllaves
import javax.crypto.spec.*;
import javax.crypto.*;

import java.io.*;

public class DES {
    
    public static void main(String[] args)throws Exception{
        //vamos a comprobar la entrada de un archivo o fichero para cifrar
        if(args.length!=1){
            //no hay archivos cargados
            mensajeAyuda();
            System.exit(1);
            /*Lo primero que tenemos que hacer es cargar una instancia
            del proveedor del tipo de cifrado, para eso es la parte de las librerias
            */
        }
            System.out.println("1.-Generar la clave DES:");
            
            //vamos a ocupar la clase KeyGenerator
            KeyGenerator generadorDES = KeyGenerator.getInstance("DES");
            //inicializamos la llave
            
            //vamos a generar las claves
            SecretKey clave=generadorDES.generateKey();
            //estamos creando las 16 subllaves
            
            System.out.println("La clave es: "+clave);
            
            /*
            Recordamos que en la criptografia moderna, solo se ocupan bits o bytes
            */
            
            //la llave que se a generado no tiene formato
            mostrarBytes(clave.getEncoded());
            
            System.out.println("");
            
            /*
            Vamos a cifrar, para ello debemos de aplicar los
            estandares vistos en clase PKCS para ello tenemos
            que crear una instancia del algoritmo DES en el
            modo de cifrado
            
            ALGORITMO DES
            MODO ECB (Electronic Code Book)
            RELLENO PKCS5
            */
            
            System.out.println("2.- Cifrar con DES y el archivo: "+args[0]+"dejar el resultado en: "+args[0]+".cifrado");
            
            //instancia
            Cipher cifrado=Cipher.getInstance("DES/ECB/PKCS5Padding");
            
            cifrado.init(Cipher.ENCRYPT_MODE, clave);
            
            //El problema es como vamos a leer los bloques del mensaje
            
            byte[] buffer=new byte[1000];
            byte[] bufferCifrado;
            
            //generamos los archivos
            
            FileInputStream in= new FileInputStream(args[0]+".txt");
            FileOutputStream out= new FileOutputStream(args[0]);
            
            
            //lectura
            int bytesleidos=in.read(buffer,0,1000);
            //mientras no esté al final del archivo
            while(bytesleidos!=-1){
                //pasar el texto claro leído al cifrador
                bufferCifrado=cifrado.update(buffer,0,bytesleidos);
                //generar la salida
                out.write(bufferCifrado);
                bytesleidos=in.read(buffer,0,1000);
                
            }
            //Vamos a reunir todos los bloques
            bufferCifrado=cifrado.doFinal();
            out.write(bufferCifrado);
            
            in.close();
            out.close();
            
            //vamos a descifrar
            System.out.println("3.- >Descifrar con DES el archivo"
            + args[0] +".cifrado" + " , vamos a ver el resultado en el archivo"
                    + args[0]+".descifrado");
            
            //empezamos con el modo en decifrar
            cifrado.init(Cipher.DECRYPT_MODE, clave);
            
            //buffer para la entrada y salida de los bits
            byte[] bufferPlano;
            in = new FileInputStream(args[0]+".cifrado");
            out = new FileOutputStream(args[0]+".descifrado");
            
            //damos lectura de cada elemento
            
            bytesleidos = in.read(buffer, 0, 1000);
            //mientras no esté al final del archivo que continúe
            while(bytesleidos!=-1){
                //pasamos el texto plano al cifrado
                bufferPlano=cifrado.update(buffer,0,bytesleidos);
                //generamos la salida
                out.write(bufferPlano);
                bytesleidos = in.read(buffer, 0, 1000);
            }
            //vamos a reunir los bloques
            bufferPlano=cifrado.doFinal();
            
            in.close();
            out.close();
        
    }

    private static void mensajeAyuda() {
        System.out.println("Ejemplo de un cifrado DES para archivos de txt");
        System.out.println("Cuidado, la llave solo puede ser de 8 caracteres");
        System.out.println("Compruebe que está cargado el archivo en el programa");
        System.out.println("ñam ñam n-n/");
    }

    private static void mostrarBytes(byte[] buffer) {
        System.out.write(buffer,0,buffer.length);
    }
    
}
