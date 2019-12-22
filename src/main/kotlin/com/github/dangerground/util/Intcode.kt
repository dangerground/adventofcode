package com.github.dangerground.util

class Intcode {

    private var waitForInput = false
    private var memory = HashMap<Long, Long>()
    var inputCode: Array<Long> = arrayOf()
        set(value) {
            outputs.clear()
            field = value; inputIndex = 0
        }
    var outputs = ArrayList<Long>()

    constructor(memory: Array<Long>) {
        var i = 0L
        memory.forEach {
            this.memory[i++] = it
        }
        this.inputCode = inputCode
        this.lastOutput = -1
    }

    companion object {
        const val INSTRUCTION_CODE_LENGTH = 2
        const val THREE_PARAMS = 3
        const val TWO_PARAM = 2
        const val ONE_PARAM = 1
        const val ZERO_PARAMS = 0

        fun of(input: String) : Intcode {
            return Intcode(HelperUtil.convertL(input))
        }
        fun ofFile(fileName: String) : Intcode {
            val input  =Intcode::class.java.getResource(fileName).readText()
            return Intcode(HelperUtil.convertL(input))
        }
    }

    var inputIndex = 0
    var lastOutput: Long
    private var instructionPointer: Long = 0
    private var done = false
    private var skipInstructionPointerUpdate = false
    private var relativeBase: Long = 0


    private fun opcode(paramCount: Int, command: (LongArray) -> Unit) {
        val params = getParams(paramCount)

        command(params)

        if (!skipInstructionPointerUpdate) {
            instructionPointer += paramCount + 1
        }
        skipInstructionPointerUpdate = false
    }

    private fun setInstruction(pointer: Long) {
        skipInstructionPointerUpdate = true
        instructionPointer = pointer
    }

    fun runProgram(): Collection<Long> {
        do {
            waitForInput = false
            val codeString = getInstruction()
            val instructionCode =
                    if (codeString.length == 1) codeString.toInt() else codeString.substring(codeString.length - INSTRUCTION_CODE_LENGTH).toInt()

            instruction(instructionCode)
        } while (!(waitForInput || done))

        return memory.values
    }

    private fun getInstruction(): String {
        val code = getMem(instructionPointer)
        return code.toString()
    }

    private fun instruction(code: Int) {
        when (code) {
            1 -> {
                debug("ADD")
                opcode(THREE_PARAMS) { params ->
                    debug("${getMem(params[0])} + ${getMem(params[1])}")
                    memory[params[2]] = getMem(params[0]) + getMem(params[1])
                }
            }
            2 -> {
                debug("MUL")
                opcode(THREE_PARAMS) { params ->
                    debug("${getMem(params[0])} * ${getMem(params[1])}")
                    memory[params[2]] = getMem(params[0]) * getMem(params[1])
                }
            }
            3 -> {
                debug("INP")
                if (inputCode.size <= inputIndex) {
                    waitForInput = true
                    return
                }
                opcode(ONE_PARAM) { params -> memory[params[0]] = inputCode[inputIndex++] }
            }
            4 -> {
                debug("OUT")
                opcode(ONE_PARAM) { params ->
                    debug("(${params[0]}) ${getMem(params[0])}")
                    lastOutput = getMem(params[0])
                    outputs.add(lastOutput)
                    //println(lastOutput)
                }
            }
            5 -> {
                debug("JIT")
                opcode(TWO_PARAM) { params ->
                    debug("cond ${params[0]}")
                    if (getMem(params[0]) != 0L) setInstruction(getMem(params[1]))
                }
            }
            6 -> {
                debug("JIF")
                opcode(TWO_PARAM) { params ->
                    debug("cond ${params[0]}")
                    if (getMem(params[0]) == 0L) setInstruction(getMem(params[1]))
                }
            }
            7 -> {
                debug("LT")
                opcode(THREE_PARAMS) { params ->
                    debug("${params[0]} < ${params[1]}")
                    memory[params[2]] = if (getMem(params[0]) < getMem(params[1])) 1L else 0L
                }
            }
            8 -> {
                debug("EQU")
                opcode(THREE_PARAMS) { params ->
                    debug("${params[0]} == ${params[1]}")
                    memory[params[2]] = if (getMem(params[0]) == getMem(params[1])) 1L else 0L
                }
            }
            9 -> {
                debug("BAS")
                opcode(ONE_PARAM) { params ->
                    debug(getMem(params[0]))
                    relativeBase += getMem(params[0])
                    debug("-> $relativeBase")
                }
            }
            99 -> {
                debug("END")
                opcode(ZERO_PARAMS) {
                    debug("HALT")
                    done = true
                }
            }
        }
        //println()
    }

    private fun debug(s: Any) {
        //print("$s ")
    }

    private fun getParams(num: Int): LongArray {
        if (num == 0) {
            return LongArray(0)
        }
        val result = LongArray(num)
        val codeString = getInstruction()
                .padStart(INSTRUCTION_CODE_LENGTH + num, '0')
                .substring(0, num)
                .reversed()

        for (i in 0 until num) {
            val paramPos = instructionPointer + 1 + i

            result[i] = when (codeString.elementAt(i)) {
                '0' -> getMem(paramPos) // Position Mode
                '1' -> paramPos // Immediate Mode
                '2' -> relativeBase + getMem(paramPos) // Relative Mode
                else -> throw RuntimeException("Should not happen")
            }
        }

        return result
    }

    private fun getMem(pos: Long): Long {
        if (memory.containsKey(pos)) {
            return memory[pos]!!
        }

        return 0
    }

    fun setMem(position: Long, value: Long) {
        memory[position] = value
    }

    fun isHalted(): Boolean = done

}