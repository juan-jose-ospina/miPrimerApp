package com.example.miprimerapp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel


@Composable
fun ShowCalculator(
    viewModel: CalculatorViewModel = viewModel()
) {

    var number1 by remember { mutableStateOf("") }
    var number2 by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(35.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Sumador",
            fontSize = 32.sp
        )

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = number1,
            label = { Text("Numero 1")},
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true,
            onValueChange = { number1 = it}
        )

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = number2,
            label = { Text("Numero 2") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true,
            onValueChange = { number2 = it}
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(modifier = Modifier.fillMaxWidth(),
            onClick = {
                result = viewModel.add(number1, number2)
            }
        ) {
            Text("Sumar")
        }

        Spacer(modifier = Modifier.height(40.dp))

        Text(
            text = "Resultado: $result",
            fontSize = 25.sp
        )

    }

}


@Preview(showBackground = true)
@Composable
fun TestView(){
    ShowCalculator()
}