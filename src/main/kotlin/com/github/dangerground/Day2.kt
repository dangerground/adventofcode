package com.github.dangerground

class Day2(var memory: Array<Int>) {

    var instructionPointer = 0
    var done = false

    fun opcode1(src1: Int, src2: Int, dest: Int) {
//        println("${memory[dest]} = ${memory[src1]} + ${memory[src2]}")
        memory[dest] = memory[src1] + memory[src2]
    }

    fun opcode2(src1: Int, src2: Int, dest: Int) {
//        println("${memory[dest]} = ${memory[src1]} * ${memory[src2]}")
        memory[dest] = memory[src1] * memory[src2]
    }

    fun opcode99() {
        println("done")
        done = true
    }

    fun nextOpcode() {
//        println("next Opcode")
        instructionPointer += 4
    }

    fun runProgram(): Array<Int> {
        do {
//            println("post $instructionPointer / ${memory.size}")
            val code = memory[instructionPointer]
            instruction(code)

            nextOpcode()
        } while (!done)

        return memory
    }

    private fun instruction(code: Int) {
        when (code) {
            1 -> opcode1(
                memory[instructionPointer + 1],
                memory[instructionPointer + 2],
                memory[instructionPointer + 3]
            )
            2 -> opcode2(
                memory[instructionPointer + 1],
                memory[instructionPointer + 2],
                memory[instructionPointer + 3]
            )
            99 -> opcode99()
        }
    }
}