package br.com.renancsdev.sicredi.viewmodel.validacao

import android.content.Context
import android.widget.Toast
import br.com.renancsdev.sicredi.model.Feira
import java.util.regex.Pattern

class Formulario(var context: Context) {

    fun vallidarFormulario(nome : String , email : String): Boolean{

        var check = false
        if(!nome.isNullOrEmpty() && !email.isNullOrEmpty()){
            if(nome.length > 3  && validarEmail(email)){

                check = true

            }else{
                if(nome.length <= 3){
                    Toast.makeText(context , "O nome deve ter mais de 3 caracteres !" , Toast.LENGTH_SHORT).show()
                }
                else if(!validarEmail(email)){
                    Toast.makeText(context , "E-mail digitado não é válido" , Toast.LENGTH_SHORT).show()
                }
            }
        }else{
            if(nome.isNullOrEmpty()){
                Toast.makeText(context , "Nome está em branco !" , Toast.LENGTH_SHORT).show()
            }
            else if(email.isNullOrEmpty()){
                Toast.makeText(context , "E-mail está em branco !" , Toast.LENGTH_SHORT).show()
            }
        }
        return check
    }

    fun validarEmail(email: String): Boolean{
        val emailPattern = Pattern.compile("([a-zA-Z0-9])\\w+@+([gmail]|[outlook])+.com|([a-zA-Z0-9])\\w+@+([yahoo])+.com.br")
        return emailPattern.matcher(email).matches()
    }

}