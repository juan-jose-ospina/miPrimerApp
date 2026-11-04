package com.example.miprimerapp

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import java.math.BigDecimal
import java.math.MathContext

private const val INITIAL_RESULT = "0"
private const val NEGATIVE_SIGN = "-"
private const val DECIMAL_SEPARATOR = ','
private const val MAX_DIGITS = 9

class CalculatorViewModel : ViewModel() {

    private val model = CalculatorModel()

    // Estado que la pantalla observa (solo el ViewModel puede cambiarlo)
    var result by mutableStateOf(INITIAL_RESULT)
        private set

    var firstNumber by mutableStateOf("")
        private set

    var operator by mutableStateOf<Operator?>(null)
        private set

    var error by mutableStateOf<CalculatorError?>(null)
        private set

    private val _history = mutableStateListOf<HistoryItem>()
    val history: List<HistoryItem> = _history

    // Memoria interna
    private var isTypingNumber = false
    private var lastOperator: Operator? = null
    private var lastNumber = ""

    fun onDigitClick(digit: Int) {
        error = null
        result = when {
            !isTypingNumber || result == INITIAL_RESULT -> digit.toString()
            result.count { it.isDigit() } >= MAX_DIGITS -> result
            else -> result + digit
        }
        isTypingNumber = true
    }

    fun onDecimalClick() {
        error = null
        if (!isTypingNumber) {
            result = "0$DECIMAL_SEPARATOR"
            isTypingNumber = true
        } else if (DECIMAL_SEPARATOR !in result) {
            result += DECIMAL_SEPARATOR
        }
    }

    fun onOperatorClick(newOperator: Operator) {
        error = null
        val pendingOperator = operator
        if (pendingOperator != null && isTypingNumber) {
            // Operaciones consecutivas: en "2 + 3 x" primero se calcula 2 + 3
            result = completeOperation(firstNumber, pendingOperator, result) ?: return
        }
        firstNumber = result
        operator = newOperator
        isTypingNumber = false
    }

    fun onEqualsClick() {
        val pendingOperator = operator
        val repeatedOperator = lastOperator
        if (pendingOperator != null) {
            val secondNumber = result
            result = completeOperation(firstNumber, pendingOperator, secondNumber) ?: return
            lastOperator = pendingOperator
            lastNumber = secondNumber
        } else if (repeatedOperator != null) {
            // "=" repetido: se repite la última operación, como en el iPhone
            result = completeOperation(result, repeatedOperator, lastNumber) ?: return
        } else {
            return
        }
        firstNumber = ""
        operator = null
        isTypingNumber = false
    }

    // AC limpia la operación actual y conserva el historial
    fun onClearClick() {
        error = null
        resetCalculator()
    }

    fun onDeleteClick() {
        if (!isTypingNumber) return
        val shortened = result.dropLast(1)
        result = if (shortened.isEmpty() || shortened == NEGATIVE_SIGN) INITIAL_RESULT else shortened
    }

    fun onSignClick() {
        if (result == INITIAL_RESULT) return
        result = if (result.startsWith(NEGATIVE_SIGN)) {
            result.removePrefix(NEGATIVE_SIGN)
        } else {
            NEGATIVE_SIGN + result
        }
    }

    // % divide el número que está en pantalla entre 100
    fun onPercentClick() {
        result = format(model.percent(result.toNumber()))
    }

    // Calcula, guarda en el historial y devuelve el resultado; devuelve null si hay un error
    private fun completeOperation(first: String, selectedOperator: Operator, second: String): String? {
        val firstValue = first.toNumber()
        val secondValue = second.toNumber()

        if (selectedOperator == Operator.Divide && secondValue == 0.0) {
            showError(CalculatorError.DivisionByZero)
            return null
        }

        val value = model.calculate(firstValue, selectedOperator, secondValue)
        if (!value.isFinite()) {
            showError(CalculatorError.InvalidResult)
            return null
        }

        val total = format(value)
        _history.add(HistoryItem(format(firstValue), selectedOperator, format(secondValue), total))
        return total
    }

    private fun showError(newError: CalculatorError) {
        resetCalculator()
        error = newError
    }

    private fun resetCalculator() {
        result = INITIAL_RESULT
        firstNumber = ""
        operator = null
        lastOperator = null
        lastNumber = ""
        isTypingNumber = false
    }

    // Internamente se usa punto ("0.5"); en pantalla se muestra coma ("0,5")
    private fun String.toNumber(): Double {
        return replace(DECIMAL_SEPARATOR, '.').toDouble()
    }

    // 5.0 -> "5" y 0.30000000000000004 -> "0,3"
    private fun format(value: Double): String {
        return BigDecimal(value)
            .round(MathContext(MAX_DIGITS))
            .stripTrailingZeros()
            .toPlainString()
            .replace('.', DECIMAL_SEPARATOR)
    }
}