package com.example.basicdatastore.widgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BasicDatastoreBody() {
    val inputSaveKeyValue = remember { mutableStateOf(TextFieldValue()) }
    val inputSaveContentValue = remember { mutableStateOf(TextFieldValue()) }
    val inputReadKeyValue = remember { mutableStateOf(TextFieldValue()) }

    val saveKeyError = rememberSaveable { mutableStateOf(false) }
    val saveContentError = rememberSaveable { mutableStateOf(false) }
    val readKeyError = rememberSaveable { mutableStateOf(false) }

    val readContentValue = remember {
        mutableStateOf("")
    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(12.dp),
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Text("Basic Datastore", fontSize = 20.sp)
        TextField(
            value = inputSaveKeyValue.value,
            onValueChange = {},
            placeholder = { Text("Type key to save content here...") },
            isError = saveKeyError.value,
        )
        TextField(
            value = inputSaveContentValue.value,
            onValueChange = {},
            placeholder = { Text("Type content here...") },
            isError = saveContentError.value
        )
        Button(onClick = {
            //todo validate and save
        }) {
            Text("SAVE")
        }

        TextField(
            value = inputReadKeyValue.value,
            onValueChange = {},
            placeholder = { Text("Type key to read content here...") },
            isError = readKeyError.value
        )
        Button(onClick = {
            //todo read content
        }) {
            Text("READ")
        }
        Text(text = readContentValue.value)
    }
}