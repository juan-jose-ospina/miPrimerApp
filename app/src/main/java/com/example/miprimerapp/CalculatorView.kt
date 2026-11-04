package com.example.miprimerapp

import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val KeySpacing = 12.dp

@Composable
fun ShowCalculator(
    viewModel: CalculatorViewModel,
    onHistoryClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(R.color.black))
            .safeDrawingPadding()
            .padding(horizontal = KeySpacing, vertical = 10.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        HistoryButton(onHistoryClick)
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            OperationText(viewModel)
            ResultText(viewModel.result)
            Keypad(viewModel)
        }
    }
}

@Composable
private fun HistoryButton(onClick: () -> Unit) {
    val description = stringResource(R.string.historyTitle)
    val iconColor = colorResource(R.color.white)

    Button(
        onClick = onClick,
        shape = CircleShape,
        colors = calculatorButtonColors(R.color.controlButtom),
        contentPadding = PaddingValues(0.dp),
        modifier = Modifier
            .size(48.dp)
            .semantics { contentDescription = description }
    ) {
        // Reloj dibujado: un círculo y dos manecillas
        Canvas(modifier = Modifier.size(22.dp)) {
            val strokeWidth = 2.dp.toPx()
            drawCircle(color = iconColor, style = Stroke(width = strokeWidth))
            drawLine(iconColor, center, Offset(center.x, size.height * 0.25f), strokeWidth)
            drawLine(iconColor, center, Offset(size.width * 0.7f, center.y), strokeWidth)
        }
    }
}

@Composable
private fun OperationText(viewModel: CalculatorViewModel) {
    val error = viewModel.error
    val operator = viewModel.operator
    val text = when {
        error != null -> stringResource(error.messageId)
        operator != null -> stringResource(
            R.string.pendingOperation,
            viewModel.firstNumber,
            stringResource(operator.symbolId)
        )
        else -> ""
    }
    val colorId = if (error != null) R.color.errorText else R.color.expressionText

    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        text = text,
        fontSize = 26.sp,
        color = colorResource(colorId),
        textAlign = TextAlign.End,
        softWrap = false,
        maxLines = 1
    )
}

@Composable
private fun ResultText(result: String) {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        text = result,
        fontSize = if (result.length > 7) 58.sp else 80.sp,
        color = colorResource(R.color.white),
        textAlign = TextAlign.End,
        softWrap = false,
        maxLines = 1
    )
}

@Composable
private fun Keypad(viewModel: CalculatorViewModel) {
    Column(verticalArrangement = Arrangement.spacedBy(KeySpacing)) {
        KeypadRow {
            ControlButton(R.string.deleteSymbol, viewModel::onDeleteClick)
            ControlButton(R.string.clearSymbol, viewModel::onClearClick)
            ControlButton(R.string.percentSymbol, viewModel::onPercentClick)
            OperatorButton(Operator.Divide, viewModel)
        }
        KeypadRow {
            NumberButton(7, viewModel)
            NumberButton(8, viewModel)
            NumberButton(9, viewModel)
            OperatorButton(Operator.Multiply, viewModel)
        }
        KeypadRow {
            NumberButton(4, viewModel)
            NumberButton(5, viewModel)
            NumberButton(6, viewModel)
            OperatorButton(Operator.Subtraction, viewModel)
        }
        KeypadRow {
            NumberButton(1, viewModel)
            NumberButton(2, viewModel)
            NumberButton(3, viewModel)
            OperatorButton(Operator.Add, viewModel)
        }
        KeypadRow {
            ShowButton(stringResource(R.string.signSymbol), R.color.numberButtom, viewModel::onSignClick)
            NumberButton(0, viewModel)
            ShowButton(stringResource(R.string.decimalSymbol), R.color.numberButtom, viewModel::onDecimalClick)
            ShowButton(stringResource(R.string.equalsSymbol), R.color.operatorButtom, viewModel::onEqualsClick)
        }
    }
}

@Composable
private fun KeypadRow(content: @Composable RowScope.() -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(KeySpacing),
        content = content
    )
}

@Composable
private fun RowScope.NumberButton(digit: Int, viewModel: CalculatorViewModel) {
    ShowButton(digit.toString(), R.color.numberButtom) { viewModel.onDigitClick(digit) }
}

@Composable
private fun RowScope.OperatorButton(operator: Operator, viewModel: CalculatorViewModel) {
    ShowButton(stringResource(operator.symbolId), R.color.operatorButtom) {
        viewModel.onOperatorClick(operator)
    }
}

@Composable
private fun RowScope.ControlButton(@StringRes textId: Int, onClick: () -> Unit) {
    ShowButton(stringResource(textId), R.color.controlButtom, onClick)
}

@Composable
private fun RowScope.ShowButton(text: String, @ColorRes colorId: Int, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        shape = CircleShape,
        colors = calculatorButtonColors(colorId),
        contentPadding = PaddingValues(0.dp),
        modifier = Modifier
            .weight(1f)
            .aspectRatio(1f)
    ) {
        Text(
            text = text,
            fontSize = 32.sp
        )
    }
}

@Composable
fun calculatorButtonColors(@ColorRes colorId: Int) = ButtonDefaults.buttonColors(
    containerColor = colorResource(colorId),
    contentColor = colorResource(R.color.white)
)

@Preview(showBackground = true)
@Composable
fun TestView() {
    ShowCalculator(viewModel = CalculatorViewModel(), onHistoryClick = {})
}