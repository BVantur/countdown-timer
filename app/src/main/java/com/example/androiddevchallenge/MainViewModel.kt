package com.example.androiddevchallenge

import android.os.CountDownTimer
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.androiddevchallenge.model.TimeUnitType

class MainViewModel : ViewModel() {
    private val _secondValue = MutableLiveData(0)
    val second: LiveData<Int> = _secondValue
    private val _minuteValue = MutableLiveData(0)
    val minute: LiveData<Int> = _minuteValue
    private val _hourValue = MutableLiveData(0)
    val hour: LiveData<Int> = _hourValue
    private val _timeUnitTypeValue = MutableLiveData(TimeUnitType.SECOND)
    val timeUnitType: LiveData<TimeUnitType> = _timeUnitTypeValue

    private var timer: CountDownTimer? = null

    fun startTimer() {
        if (timer == null) {
            _timeUnitTypeValue.value = TimeUnitType.NONE
            val seconds = second.value?.toInt() ?: return
            val minutes = minute.value?.toInt() ?: return
            val hours = hour.value?.toInt() ?: return

            val timerValue = seconds.times(1000)
                .plus(minutes.times(60000))
                .plus(hours.times(3600000)).toLong()

            timer = object : CountDownTimer(timerValue, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    val secondsOnTick = millisUntilFinished / 1000
                    val minutesOnTick = secondsOnTick / 60
                    val hoursOnTick = minutesOnTick / 60
                    _secondValue.value = (secondsOnTick - minutesOnTick * 60).toInt()
                    _minuteValue.value = (minutesOnTick - hoursOnTick * 60).toInt()
                    _hourValue.value = hoursOnTick.toInt()

                    Log.e(
                        "COUNT_DOWN_TIMER",
                        "${_hourValue.value}:${_minuteValue.value}:${_secondValue.value}"
                    )
                }

                override fun onFinish() {
                    _secondValue.value = 0
                    _timeUnitTypeValue.value = TimeUnitType.SECOND
                    Log.e("COUNT_DOWN_TIMER", "done")
                    timer = null
                }
            }.start()
        } else {
            timer?.cancel()
            timer = null
            _secondValue.value = 0
            _minuteValue.value = 0
            _hourValue.value = 0
            _timeUnitTypeValue.value = TimeUnitType.SECOND
        }
    }

    fun sendNum(num: String) {
        if (_timeUnitTypeValue.value?.isNoneType() == true) return

        when (_timeUnitTypeValue.value) {
            TimeUnitType.HOUR -> handleHourInput(num)
            TimeUnitType.MINUTE -> handleMinuteInput(num)
            TimeUnitType.SECOND -> handleSecondInput(num)
        }
    }

    private fun handleHourInput(num: String) {
        val hour = _hourValue.value ?: return

        if (num == "<") {
            if (hour < 10) {
                _hourValue.value = 0
            } else {
                _hourValue.value = hour / 10
            }
            return
        }

        if (hour == 0) {
            _hourValue.value = num.toInt()
        } else if (hour < 10) {
            _hourValue.value = "$hour$num".toInt()
        }
    }

    private fun handleMinuteInput(num: String) {
        val minute = _minuteValue.value ?: return

        if (num == "<") {
            if (minute < 10) {
                _minuteValue.value = 0
            } else {
                _minuteValue.value = minute / 10
            }
            return
        }

        if (minute == 0) {
            _minuteValue.value = num.toInt()
        } else if (minute < 10) {
            var newValue = "$minute$num".toInt()
            if (newValue > 59) {
                newValue = 59
            }
            _minuteValue.value = newValue
        }
    }

    private fun handleSecondInput(num: String) {
        val second = _secondValue.value ?: return

        if (num == "<") {
            if (second < 10) {
                _secondValue.value = 0
            } else {
                _secondValue.value = second / 10
            }
            return
        }

        if (second == 0) {
            _secondValue.value = num.toInt()
        } else if (second < 10) {
            var newValue = "$second$num".toInt()
            if (newValue > 59) {
                newValue = 59
            }
            _secondValue.value = newValue
        }
    }

    fun selectHourTimeUnit() {
        if (_timeUnitTypeValue.value?.isNoneType() == false) {
            _timeUnitTypeValue.value = TimeUnitType.HOUR
        }
    }

    fun selectMinuteTimeUnit() {
        if (_timeUnitTypeValue.value?.isNoneType() == false) {
            _timeUnitTypeValue.value = TimeUnitType.MINUTE
        }
    }

    fun selectSecondTimeUnit() {
        if (_timeUnitTypeValue.value?.isNoneType() == false) {
            _timeUnitTypeValue.value = TimeUnitType.SECOND
        }
    }
}