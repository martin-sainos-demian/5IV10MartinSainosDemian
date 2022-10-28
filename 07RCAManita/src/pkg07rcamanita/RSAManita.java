
package pkg07rcamanita;

import java.math.BigInteger;
import java.util.*;
import java.io.*;

public class RSAManita {

    //vars
    int tamprimo;
    BigInteger p, q, n;
    BigInteger fi;
    BigInteger e, d;
    
    public RSAManita(int tamprimo){
        this.tamprimo = tamprimo;
        
    }
    
    //generar primos p q
    public void generarPrimos(){
        //generar p
        p = new BigInteger(tamprimo, 10, new Random());
        //generar q diferente de p
        do q = new BigInteger(tamprimo, 10, new Random());
            while(q.compareTo(p)==0);
        
    }
    
    //generar llaves e d
    public void generarClave(){
        // n = (p*q)
        // fi = (p-1)
        n = p.multiply(q);
        
        fi = p.subtract(BigInteger.valueOf(1));
        
        fi = fi.multiply((q.subtract(BigInteger.valueOf(1))));
        
        //calcular e
        //coprimo i < e < fi(n)
        do e = new BigInteger(2*tamprimo, new Random());
            while ((e.compareTo(fi) != -1)
                ||(e.gcd(fi).compareTo(BigInteger.valueOf(1)) !=0 ));
        
        //calcular a d
        //inverso multiplicativo de e
        //d = e ^ 1 mod fi
        d = e.modInverse(fi);
    }
    
    //cifrar
    public BigInteger[] cifrar(String mensaje){
        int i;
        byte[] temp = new byte[1];
        byte[] digitos = mensaje.getBytes();
        
        BigInteger[] bigdigitos = new BigInteger[digitos.length];
        
        //recorrer arreglo
        for(i = 0; i<bigdigitos.length;i++){
            temp[0] = digitos[i];
            bigdigitos[i] = new BigInteger(temp);
        }
        
        //aplicar cifrado
        BigInteger[] cifrado = new BigInteger[bigdigitos.length];
        
        //aplico formula c = Msg ^ e * mod n
        for(i = 0; i<bigdigitos.length; i++){
            cifrado[i] = bigdigitos[i].modPow(e, n);
        }
        return cifrado;
    }
    //descifrar
    public String descifrar(BigInteger[] cifrado){
        int i;
        BigInteger[] descifrado = new BigInteger[cifrado.length];
        
        //descifrado Md= c ^ d * mod n
        
        //recorrer arreglo
        for(i = 0; i<descifrado.length;i++){
            descifrado[i] = cifrado[i].modPow(d, n);
        }
        
        //dar formato
        char[] charArray = new char[descifrado.length];
        
        //genero string
        for(i = 0; i<charArray.length; i++){
            charArray[i] = (char)(descifrado[i].intValue());
        }
        
        return new String(charArray);
    }
    
    public static void main(String[] args) {
        
    }
}