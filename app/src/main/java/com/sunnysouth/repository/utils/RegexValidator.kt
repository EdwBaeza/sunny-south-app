package com.sunnysouth.repository.utils

object  RegexValidator {
    fun CharactersAndNumbersOnly(textValue:String):Boolean{
        return textValue.matches("[a-zA-Z0-9.?]*".toRegex());
    }

/*    fun CharactersNumbersAnSpacesOnly(textValue:String):Boolean{
        return textValue.matches("[a-zA-Z0-9.?]*".toRegex());
    }*/

    fun NumbersOnly(textValue:String):Boolean{
        return textValue.matches("[0-9.?]*".toRegex());
    }

    fun CharactersOnly(textValue:String):Boolean{
        return textValue.matches("[a-zA-Z.?]*".toRegex());
    }
}