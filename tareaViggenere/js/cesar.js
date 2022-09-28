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
        
        while(desp>=abc.length){
            desp-=abc.length;
        }
        while(desp<0){
            desp+=abc.length;
        }

        return function(c){
            //necesitamos saber la posicion
            var i=abc.indexOf(c.toLowerCase());

            /* necesitamos saber donde estamos dentro de la matriz abc y como
             la vamos a recorrer para el momento del cifrado*/
            
             if(i!=-1){
                //primero obtenemos la posicion para el desplazamiento
                var pos=parseInt(i);
                //necesito saber la operacion a realizar: c o d
                if(action){
                    //cifrar hacia adelante
                    pos=parseInt(desp)+pos;
                }else{
                    //descifrar para atras
                    pos=parseInt(desp)-pos;
                }
                while(pos>=abc.length){
                    pos-=abc.length;
                }
                while(pos<0){
                    pos+=abc.length;
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
    document.getElementById("rs").innerHTML = cesar.encode(document.getElementById("mensaje").value,document.getElementById("cant").value);
});
$('#cd').click(function descifrar(){
    document.getElementById("rs").innerHTML = cesar.encode(document.getElementById("mensaje").value,document.getElementById("cant").value);
});
})