/**vamos a crear una funcion recursiva que se encargue de mandar a llamar a la
 *  funcion como un objeto en caso de que no se pueda ejecutar */

var cesar=cesar || (function(){
    var proceso = function(txt, desp, action){
        var replace = (function(){
        //primero necesito una matriz del abc
        var fullabc="abcdefghijklmnopqrstuvwxyz";
        var abc=fullabc.split('');
        var l=abc.length;

        /*necesitamos una funcion que pueda obtener la posicion que va a venir
         por parte de la clave privada o desplazamiento*/
        
        return function(c){
            //necesitamos saber la posicion
            var i=abc.indexOf(c.toLowerCase());

            /* necesitamos saber donde estamos dentro de la matriz abc y como
             la vamos a recorrer para el momento del cifrado*/
            
             if(i!=-1){
                //primero obtenemos la posicion para el desplazamiento
                var pos=i;
                //necesito saber la operacion a realizar: c o d
                if(action){
                    //cifrar hacia adelante
                    pos+=desp;
                    //definir como se va a mover
                    pos -=(pos>=l)?1:0;
                }else{
                    //descifrar para atras
                    pos-=desp;
                    //movimiento
                    pos+=(pos<0)?1:0;
                }
                return abc[pos];
             }
             return c;
        };
    })();
    //tenemos que saber que el texto este acorde abc

    var re=(/([a-z])/ig);

    /*una funcion que se encargue del intercambio de las posiciones acorde a
     si coincide el texto a cifrar con la regex */

    return String(txt).replace(re,function(match){
        return replace(match);
    });

    }

    return{
        encode : function(txt, desp){
             return proceso(txt, desp, true);
        },
        decode : function(txt, desp){
            return proceso(txt, desp, false);
       }
    };

})();
$(document).ready(function(){
    $('#ce').click(function cifrar(){
    if(!isNaN(document.getElementById("cant").value)){
        document.getElementById("cadena").innerHTML = cesar.encode(document.getElementById("mensaje").value,document.getElementById("cant").value);
    }else{
        document.getElementById("error").innerHTML = "no es un numero";
    }
});
$('#cd').click(function descifrar(){
    if(!isNaN(document.getElementById("cant").value)){
        document.getElementById("cadena").innerHTML = cesar.encode(document.getElementById("mensaje").value,document.getElementById("cant").value);
    }else{
        document.getElementById("error").innerHTML = "no es un numero";
    }
});
})