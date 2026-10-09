package com.example.miprimerapp

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class CalculatorViewModel: ViewModel() {

    private val model = CalculatorModel()

    val result = mutableStateOf("0")
    var number1 = ""
    var number2 = ""
    var operator = ""

    fun manageClickButtons(valueButton: String){
        if (valueButton.isEmpty()) return

        if (valueButton == "+") {
            operator = "+"
        }

        if (valueButton == "-") {
            operator = "-"
        }

        if (valueButton == "="){
            if (operator == "+"){
                result.value = add(number1,number2)
                return
            }

            if (operator == "-"){
                result.value = subtraction(number1,number2)
                return
            }
        }

        if (number1.isEmpty()){
            number1 = valueButton
        } else {
            number2 = valueButton
        }

        result.value = valueButton

    }

    fun add(number1: String, number2: String): String{
        val num1 = number1.toDoubleOrNull()
        val num2 = number2.toDoubleOrNull()
        if (num1 != null && num2 != null) {
            val result = model.add(num1, num2).toString()
            return result
        } else {
            return "Error"
        }
    }

    fun subtraction(number1: String, number2: String): String{
        val num1 = number1.toDoubleOrNull()
        val num2 = number2.toDoubleOrNull()
        if (num1 != null && num2 != null) {
            val result = model.subtraction(num1, num2).toString()
            return result
        } else {
            return "Error"
        }
    }

}