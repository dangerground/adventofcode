package com.github.dangerground

import com.github.dangerground.Day5.ParameterMode.ImmediateMode
import com.github.dangerground.Day5.ParameterMode.PositionMode

class Day5(var memory: Array<Int>, val inputCode: Int) {

    companion object {
        const val INSTRUCTION_CODE_LENGTH = 2
        const val THREE_PARAMS = 3
        const val TWO_PARAM = 2
        const val ONE_PARAM = 1
        const val ZERO_PARAMS = 0
    }

    var lastOutput: Int = -1
    private var instructionPointer = 0
    private var done = false
    private var skipInstructionPointerUpdate = false

    private fun opcode(paramCount: Int, command: (IntArray) -> Unit) {
        val params = getParams(paramCount)

        command(params)

        if (!skipInstructionPointerUpdate) {
            instructionPointer += paramCount + 1
        }
        skipInstructionPointerUpdate = false
    }

    private fun setInstruction(pointer: Int)  {
        skipInstructionPointerUpdate = true
        instructionPointer = pointer
    }

    fun runProgram(): Array<Int> {
        do {
//            println("$instructionPointer -> ${memory.toIntArray().contentToString()}")
            val codeString = getInstruction()
            val instructionCode =
                if (codeString.length == 1) codeString.toInt() else codeString.substring(codeString.length - INSTRUCTION_CODE_LENGTH).toInt()

            instruction(instructionCode)
        } while (!done && instructionPointer <= memory.size)

        return memory
    }

    private fun getInstruction(): String {
        val code = memory[instructionPointer]
        val codeString = code.toString()
        return codeString
    }

    private fun instruction(code: Int) {
        when (code) {
            1 ->  { debug("ADD"); opcode(THREE_PARAMS) { params -> memory[params[2]] = memory[params[0]] + memory[params[1]] }}
            2 ->  { debug("MUL"); opcode(THREE_PARAMS) { params -> memory[params[2]] = memory[params[0]] * memory[params[1]] }}
            3 ->  { debug("INP"); opcode(ONE_PARAM) { params -> memory[params[0]] = inputCode }}
            4 ->  { debug("OUT"); opcode(ONE_PARAM) { params -> println(memory[params[0]]); lastOutput = memory[params[0]] }}
            5 ->  { debug("JIT"); opcode(TWO_PARAM) { params -> debug("cond ${params[0]}"); if (memory[params[0]] != 0) setInstruction(memory[params[1]]) }}
            6 ->  { debug("JIF"); opcode(TWO_PARAM) { params -> debug("cond ${params[0]}"); if (memory[params[0]] == 0) setInstruction(memory[params[1]]) }}
            7 ->  { debug("LT");  opcode(THREE_PARAMS) { params -> debug("${params[0]} < ${params[1]}");  memory[params[2]] = if (memory[params[0]] < memory[params[1]]) 1 else 0 }}
            8 ->  { debug("EQU"); opcode(THREE_PARAMS) { params -> debug("${params[0]} == ${params[1]}"); memory[params[2]] = if (memory[params[0]] == memory[params[1]]) 1 else 0 }}
            99 -> { debug("END"); opcode(ZERO_PARAMS) { println("HALT"); done = true }}
        }
        println()
    }

    private fun debug(s: String) {
        print("$s ")
    }

    private fun getParams(num: Int): IntArray {
        if (num == 0) {
            return IntArray(0)
        }
        val result = IntArray(num)
        val codeString = getInstruction()
                .padStart(INSTRUCTION_CODE_LENGTH + num, '0')
                .substring(0, num)
                .reversed()
//        print("code $codeString ")

        for (i in 0 until num) {
            val mode = if (codeString.elementAt(i) == '0') PositionMode else ImmediateMode
            val paramPos = instructionPointer + 1 + i

            result[i] = if (mode == PositionMode) memory[paramPos] else paramPos
//            println ("mode: $i ($paramPos) - $mode = ${memory[result[i]]}")
        }

//        print("cont ${result.contentToString()}")
        return result
    }

    enum class ParameterMode {
        PositionMode, // 0
        ImmediateMode // 1
    }
}