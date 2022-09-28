//vamos a ocupar parte de cesar
var abc="abcdefghijklmnñopqrstuvwxyz".split('');

//llave
let key="";

//bienvenidos a interpretar código
$(document).ready(function(){
    //vamos a hacer una funcion para poder cifrar con viggener
    $('#ve').click(function(){

        //parar cifrar vamos a utilizar una funcion de modulo la cual es  Y=(X+Z)mod27

        //vamos a traer los datos de la llave

        key=document.getElementById('llave').value;
        //tenemos que verificar la llave
        key=key.replace(/ /g, '');

        //vamos a traer los datos del mensaje

        let mess=document.getElementById('mensaje').value;
        //tenemos que verificar el mensaje
        mess=mess.replace(/ /g, '');

        let newMess="";
        let keyCompleta="";

        //para aplicar el algoritmo debemos crear una funcion que se encargue de revisar las condiciones del mismo
        if(revision(mess, key)){
            //vamos primero por aplicar y obtener la posicion de la longitud del mensaje y emparejarlo contra la llave

            for(var i=0;i<mess.length;i++){
                //emparejo conforme a la posicion de caracter obteniendo el numero de dicha posicion
                keyCompleta+=key.charAt((i%Number(key.length)));
            }
            alert(keyCompleta);

            //tengo que volver a recorrer el mensaje para obtener caracteres y posiciones
            for(var i=0;i<mess.length;i++){
                //obtener la posicion de la letra
                let charr=mess.charAt(i);
                //debemos crear una funcion para obtener la posicion de ese caracter
                let posm=getPosicion(charr);

                //tambien aplicarlo a la llave
                charr=keyCompleta.charAt(i);
                //obtenemos la posicion
                let posk=getPosicion(charr);

                //tenemos que ejecutar el cifrado
                let newValores=cifrado(posm, posk);

                newMess+=abc[newValores];
            }

            //imprimir el resultado
            document.getElementById('rs').value=newMess;
      }else{
        alert("no se puede no hay tortillas");
      }
    });
    //decifrar
    $('#vd').click(function(){

        //parar cifrar vamos a utilizar una funcion de modulo la cual es  Y=(X+Z)mod27

        //vamos a traer los datos de la llave

        key=document.getElementById('llave').value;
        //tenemos que verificar la llave
        key=key.replace(/ /g, '');

        //vamos a traer los datos del mensaje

        let mess=document.getElementById('mensaje').value;
        //tenemos que verificar el mensaje
        mess=mess.replace(/ /g, '');

        let newMess="";
        let keyCompleta="";

        //para aplicar el algoritmo debemos crear una funcion que se encargue de revisar las condiciones del mismo
        if(revision(mess, key)){
            //vamos primero por aplicar y obtener la posicion de la longitud del mensaje y emparejarlo contra la llave

            for(var i=0;i<mess.length;i++){
                //emparejo conforme a la posicion de caracter obteniendo el numero de dicha posicion
                keyCompleta+=key.charAt((i%Number(key.length)));
            }
            alert(keyCompleta);

            //tengo que volver a recorrer el mensaje para obtener caracteres y posiciones
            for(var i=0;i<mess.length;i++){
                //obtener la posicion de la letra
                let charr=mess.charAt(i);
                //debemos crear una funcion para obtener la posicion de ese caracter
                let posm=getPosicion(charr);

                //tambien aplicarlo a la llave
                charr=keyCompleta.charAt(i);
                //obtenemos la posicion
                let posk=getPosicion(charr);

                //tenemos que ejecutar el cifrado
                let newValores=descifrado(posm, posk);

                newMess+=abc[newValores];
            }

            //imprimir el resultado
            document.getElementById('rs').value=newMess;
      }else{
        alert("no se puede no hay tortillas");
      }
    });
})

//funcion de cambio o de cifrado
function cifrado(posm, posk){
    //tengo que aplicar la formula
    let y=(posm+posk)%abc.length;
    return y;
}
//funcion de descifrado
function descifrado(posm, posk){
    //tengo que aplicar la formula
    let val=0;
    if((posm-posk)>=0){
        //todo ta nice
        val=(posm-posk)%abc.length;
    }else{
        val=(posm-posk+abc.length)%abc.length;
    }
    return val;
}

//funcion de la posicion
function getPosicion(letra){
    let posicion=abc.indexOf(letra);
    return posicion
}

//funcion de la revision
function revision(mess, key){
    //primero hay que validar la entrada de los dato a partir de una revision regular

    var expresion = /^([a-zñ?]+([]*[a-zñ?]?['-]?[a-zñ?]+)*)$/;

    var aceptado=true;

    //evaluar la expresion
    if(!expresion.test(mess)){
        document.getElementById("error").innerHTML = "el texto que ingresó no ha sido aceptado, ingrese solo minusculas y evite números y simbolos";
        aceptado=false;
    }
    //evaluar la expresion
    if(!expresion.test(key)){
        document.getElementById("error").innerHTML = "la clave ingresada es incorrecta, no cumple con las normas de solo minusculas y no de usar numero y/o simbolos";
        aceptado=false;
    }
    if(key.length>mess.length){
        document.getElementById("error").innerHTML = "la llave no puede ser mayor que el mensaje";
        aceptado=false;
    }

    return aceptado;
}