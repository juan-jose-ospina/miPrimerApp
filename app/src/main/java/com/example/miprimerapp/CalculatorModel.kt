package com.example.miprimerapp

class CalculatorModel {

    fun add(num1: Double, num2: Double): Double {
        return num1 + num2
    }

    fun subtraction(num1: Double, num2: Double): Double {
        return num1 - num2
    }

    fun multiply(num1: Double, num2: Double): Double {
        return num1 * num2
    }

    fun divide(num1: Double, num2: Double): Double {
        return num1 / num2
    }

    fun percent(value: Double): Double {
        return value / 100
    }

    fun calculate(num1: Double, operator: Operator, num2: Double): Double {
        return when (operator) {
            Operator.Add -> add(num1, num2)
            Operator.Subtraction -> subtraction(num1, num2)
            Operator.Multiply -> multiply(num1, num2)
            Operator.Divide -> divide(num1, num2)
        }
    }
}