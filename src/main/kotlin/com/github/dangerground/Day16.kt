package com.github.dangerground

import java.time.Duration
import java.time.Instant
import kotlin.math.absoluteValue

class FFT(signalString: String) {
    var signal: IntArray = signalString.toCharArray().map { it - '0' }.toIntArray()
    var offset = signalString.substring(0, 7).toInt()
    private val basePattern = arrayOf(0, 1, 0, -1)

    fun getOutput(): String {
        var output = ""
        for (i in offset until offset + 8) {
            output = "$output${signal[i]}"
        }

        return output
    }

    fun benchmark(action: () -> Unit): Duration {
        val start = Instant.now()
        action()
        return Duration.between(start, Instant.now())
    }

    fun phase() {
        //println(benchmark {
            val signalLength = signal.size
            val newSignal = IntArray(signalLength)
            var newNum = 0
            var patternIndex = 1
            for (i in 1 .. signalLength) {
                newNum = 0
                signal.forEachIndexed { i2: Int, el: Int ->

                    if ((i2 + 1) % i == 0) {
                        patternIndex++
                        if (patternIndex >= basePattern.size) {
                            patternIndex = 0
                        }
                    }
                    newNum += el * basePattern[patternIndex]
                    patternIndex = 0
                }
                newSignal[i-1] = lastDigit(newNum)
            }

            signal = newSignal
        //})
    }

    private fun lastDigit(newNum: Int) = newNum.absoluteValue % 10
}