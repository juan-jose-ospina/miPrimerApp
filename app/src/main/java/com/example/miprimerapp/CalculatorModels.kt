package com.example.miprimerapp

import androidx.annotation.StringRes

enum class Operator(@StringRes val symbolId: Int) {
    Add(R.string.addSymbol),
    Subtraction(R.string.subtractionSymbol),
    Multiply(R.string.multiplySymbol),
    Divide(R.string.divideSymbol)
}

enum class CalculatorError(@StringRes val messageId: Int) {
    DivisionByZero(R.string.msgDivisionByZero),
    InvalidResult(R.string.msgError)
}

data class HistoryItem(
    val firstNumber: String,
    val operator: Operator,
    val secondNumber: String,
    val result: String
)