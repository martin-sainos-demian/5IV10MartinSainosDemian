var mensaje="Habia una vez un patito que decia miau miau que queria mimir todo el dia, pero no lo dejaban y encontes chillaba y por eso tronaba gente wiiii";
var password="i forgor :skull:";

//ahora vamos a cifrar
var cifrado=CryptoJS.AES.encrypt(mensaje,password);
var descifrado=CryptoJS.AES.decrypt(cifrado,password);

//uff que largo
$("#demo00").text(mensaje);
$("#demo01").text(cifrado);
$("#demo02").text(descifrado);
$("#demo03").text(descifrado.toString(CryptoJS.enc.Utf8));