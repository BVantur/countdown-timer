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
package com.example.androiddevchallenge

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.androiddevchallenge.model.Numpad
import com.example.androiddevchallenge.model.TimeUnitType
import com.example.androiddevchallenge.ui.Timer
import com.example.androiddevchallenge.ui.theme.MyTheme

class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyTheme {
                MyApp(viewModel)
            }
        }
    }
}

// Start building your app here!
@Composable
fun MyApp(viewModel: MainViewModel) {
    Surface(color = MaterialTheme.colors.background) {
        ConstraintLayout(modifier = Modifier.fillMaxSize()) {

            val timeUnitType: TimeUnitType by viewModel.timeUnitType.observeAsState(TimeUnitType.SECOND)
            val (timer, numpad, button) = createRefs()
            createVerticalChain(timer, numpad, button, chainStyle = ChainStyle.Spread)
            Timer(Modifier.constrainAs(timer) {
                top.linkTo(parent.top, margin = 64.dp)
                bottom.linkTo(numpad.top)
            }, viewModel)
            Numpad(Modifier.constrainAs(numpad) {
                top.linkTo(timer.bottom, margin = 16.dp)
                bottom.linkTo(button.top)
            }) {
                if (it.isNotBlank()) {
                    viewModel.sendNum(it)
                }
            }
            Button(onClick = { viewModel.startTimer() }, modifier = Modifier.constrainAs(button) {
                top.linkTo(numpad.bottom, 16.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(parent.bottom)
            }) {
                if (timeUnitType.isNoneType()) {
                    Text("Stop")
                } else
                    Text("Start")
            }
        }
    }
}
