/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.androiddevchallenge.model

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
