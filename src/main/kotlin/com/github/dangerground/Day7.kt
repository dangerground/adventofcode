package com.github.dangerground

import com.github.dangerground.util.Intcode

class Day7(val program: Array<Int>, configRange: Array<Int>) {

    var bestConfig = arrayOf(-1, -1, -1, -1, -1)

    val phaseConfigs = HashSet<Array<Int>>()
    private var amplifiers = ArrayList<Intcode>()

    init {
        generatePermutations(configRange.size, configRange)
        initializeAmplifiers()
    }

    fun runAmplifiers(phase: Array<Int>): Int {
        var lastOutput = 0
        for (i in 0..4) {
            val computer = amplifiers[i]
            computer.inputCode = arrayOf(phase[i], lastOutput)
            computer.runProgram()
            lastOutput = computer.lastOutput
        }
        var halted = false
        do {
            for (i in 0..4) {
//                println("-------------- Amplifier ${'A' + i} start")
                val computer = amplifiers[i]
                computer.inputCode = arrayOf(lastOutput)
                computer.runProgram()
                lastOutput = computer.lastOutput
                halted = computer.isHalted()
//                println("-------------- Amplifier ${'A' + i} end")
            }
        } while (!halted)
//        println("-------------- Amplifiers done")

        return lastOutput
    }

    private fun initializeAmplifiers() {
        amplifiers = ArrayList<Intcode>(5)
        for (i in 0..4) {
            amplifiers.add(Intcode(program.copyOf()))
        }
    }

    fun findMaxAmplitude(): Int {
        var maxOutput = 0
        for (config in phaseConfigs) {
            initializeAmplifiers()
            val output = runAmplifiers(config)

            if (output > maxOutput) {
                maxOutput = output
                bestConfig = config
            }
        }

        return maxOutput
    }

    fun generatePermutations(n: Int, elements: Array<Int>) {
        if (n == 1) {
            phaseConfigs.add(elements.toIntArray().toTypedArray())
        } else {
            for (i in 0 until n) {
                generatePermutations(n - 1, elements.copyOf())
                swap(elements, i, n - 1)
            }
        }
    }

    fun swap(elements: Array<Int>, i: Int, j: Int) {
        val x = elements[i]
        elements[i] = elements[j]
        elements[j] = x
    }
}