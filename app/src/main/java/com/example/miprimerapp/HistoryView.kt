package com.example.miprimerapp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ShowHistory(
    viewModel: CalculatorViewModel,
    onBackClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(R.color.black))
            .safeDrawingPadding()
            .padding(10.dp)
    ) {
        Button(
            onClick = onBackClick,
            colors = calculatorButtonColors(R.color.controlButtom)
        ) {
            Text(text = stringResource(R.string.backButton))
        }

        Text(
            modifier = Modifier.padding(vertical = 16.dp),
            text = stringResource(R.string.historyTitle),
            fontSize = 32.sp,
            color = colorResource(R.color.white)
        )

        if (viewModel.history.isEmpty()) {
            Text(
                text = stringResource(R.string.historyEmpty),
                fontSize = 20.sp,
                color = colorResource(R.color.expressionText)
            )
        } else {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                items(viewModel.history) { item ->
                    HistoryRow(item)
                }
            }
        }
    }
}

@Composable
private fun HistoryRow(item: HistoryItem) {
    Text(
        text = stringResource(
            R.string.historyEntry,
            item.firstNumber,
            stringResource(item.operator.symbolId),
            item.secondNumber,
            item.result
        ),
        fontSize = 28.sp,
        color = colorResource(R.color.white)
    )
}