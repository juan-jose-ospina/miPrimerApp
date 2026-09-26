package com.example.miprimerapp

import androidx.lifecycle.ViewModel

class CalculatorViewModel: ViewModel() {

    private val model = CalculatorModel()

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


}