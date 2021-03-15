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
package com.example.androiddevchallenge.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.androiddevchallenge.MainViewModel
import com.example.androiddevchallenge.model.TimeUnitType
import com.example.androiddevchallenge.model.TimerNumberType

@Composable
fun Timer(modifier: Modifier, viewModel: MainViewModel) {
    ConstraintLayout(modifier = modifier.fillMaxWidth()) {
        val seconds: Int by viewModel.second.observeAsState(0)
        val minutes: Int by viewModel.minute.observeAsState(0)
        val hours: Int by viewModel.hour.observeAsState(0)
        val timeUnitType: TimeUnitType by viewModel.timeUnitType.observeAsState(TimeUnitType.SECOND)
        val (hoursTimer, minutesTimer, secondsTimer) = createRefs()
        createHorizontalChain(hoursTimer, minutesTimer, secondsTimer, chainStyle = ChainStyle.Packed)
        TimerNumber(
            Modifier.constrainAs(hoursTimer) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(minutesTimer.start)
            },
            TimerNumberType.HOUR, hours, timeUnitType.isHourType()
        ) {
            viewModel.selectHourTimeUnit()
        }
        TimerNumber(
            Modifier.constrainAs(minutesTimer) {
                top.linkTo(hoursTimer.top)
                start.linkTo(hoursTimer.end, margin = 16.dp)
                end.linkTo(secondsTimer.start)
            },
            TimerNumberType.MINUTE, minutes, timeUnitType.isMinuteType()
        ) {
            viewModel.selectMinuteTimeUnit()
        }
        TimerNumber(
            Modifier.constrainAs(secondsTimer) {
                top.linkTo(hoursTimer.top)
                start.linkTo(minutesTimer.end, margin = 16.dp)
                end.linkTo(parent.end)
            },
            TimerNumberType.SECOND, seconds, timeUnitType.isSecondType()
        ) {
            viewModel.selectSecondTimeUnit()
        }
    }
}

@Composable
fun TimerNumber(modifier: Modifier, timerType: TimerNumberType, value: Int, selected: Boolean, onClick: () -> Unit) {
    var textColor = MaterialTheme.colors.secondary

    if (selected) {
        textColor = MaterialTheme.colors.primary
    }
    Row(modifier = modifier) {
        Column(
            modifier = Modifier.clickable {
                onClick()
            }
        ) {
            Text(value.presentationValue(), modifier = Modifier.align(Alignment.CenterHorizontally), fontSize = 56.sp, color = textColor)
            Text(timerType.text, modifier = Modifier.align(Alignment.CenterHorizontally), fontSize = 12.sp)
        }
        if (timerType != TimerNumberType.SECOND) {
            Text(text = ":", fontSize = 52.sp, modifier = Modifier.padding(start = 16.dp))
        }
    }
}

private fun Int.presentationValue(): String {
    if (this < 10) {
        return "0$this"
    }
    return "$this"
}
