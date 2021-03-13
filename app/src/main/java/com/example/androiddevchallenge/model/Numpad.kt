package com.example.androiddevchallenge.model

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Numpad(modifier: Modifier, onNumClick: (String) -> Unit) {
    val numpadValues = arrayOf(arrayOf("1", "2", "3"), arrayOf("4", "5", "6"), arrayOf("7", "8", "9"), arrayOf("  ", "0", "<"))
    Column(modifier = modifier.fillMaxWidth(), verticalArrangement = Arrangement.SpaceEvenly) {
        for (i in 0..3) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                for (j in 0..2) {
                    Text(text = numpadValues[i][j], fontSize = 30.sp, modifier = Modifier.clickable { onNumClick(numpadValues[i][j]) }.padding(16.dp))
                }
            }
        }
    }
}