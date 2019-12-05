package com.github.dangerground


import spock.lang.Specification
import spock.lang.Unroll

import static com.github.dangerground.util.HelperUtil.convert

class Day5Test extends Specification {

    def "run tests"() {
        given:
        String program = "3,0,4,0,99"

        def day5 = new Day5(convert(program), 1)

        when:
        day5.runProgram()

        then:
        println "done"
    }

    @Unroll
    def "run new opcodes #program"(String program, int position, int expectedValue) {
        given:
        def day5 = new Day5(convert(program), 1)

        when:
        def result = day5.runProgram()

        then:
        result[position] == expectedValue

        where:
        program               | position | expectedValue
        "1002,4,3,4,33"       | 4        | 99
        "1101,100,-1,4,0"     | 4        | 99
        "1,0,0,0,99"          | 0        | 2
        "2,3,0,3,99"          | 3        | 6
        "2,4,4,5,99,0"        | 5        | 9801
        "1,1,1,4,99,5,6,0,99" | 0        | 30
    }

    def "calculate program code - part 1"() {
        given:
        def inputString = new File(getClass().getResource("/day5input.txt").file).text
        def input = convert(inputString)

        and:
        println(input)
        def day5 = new Day5(input)

        when:
        Integer[] result = day5.runProgram()

        then:
        println "at position 0: ${result[0]}"
    }


    @Unroll
    def "run jump opcodes #program"(String program, int input, int output) {
        given:
        def day5 = new Day5(convert(program), input)

        when:
        day5.runProgram()

        then:
        day5.lastOutput == output

        where:
        program                                                                                                                                                    | input | output
//        "3,9,8,9,10,9,4,9,99,-1,8"                                                                                                                                 | 8     | 1
//        "3,9,8,9,10,9,4,9,99,-1,8"                                                                                                                                 | 7     | 0
//        "3,3,1108,-1,8,3,4,3,99"                                                                                                                                   | 8     | 1
//        "3,3,1108,-1,8,3,4,3,99"                                                                                                                                   | 7     | 0
//        "3,3,6,12,15,1,13,14,13,4,13,99,-1,0,1,9"                                                                                                                 | 0     | 0
        "3,3,1105,-1,9,1101,0,0,12,4,12,99,1"                                                                                                                      | 0     | 0
        "3,3,1105,-1,9,1101,0,0,12,4,12,99,1"                                                                                                                      | 5     | 1
//        "3,21,1008,21,8,20,1005,20,22,107,8,21,20,1006,20,31,1106,0,36,98,0,0,1002,21,125,20,4,20,1105,1,46,104,999,1105,1,46,1101,1000,1,20,4,20,1105,1,46,98,99" | 7     | 999
//        "3,21,1008,21,8,20,1005,20,22,107,8,21,20,1006,20,31,1106,0,36,98,0,0,1002,21,125,20,4,20,1105,1,46,104,999,1105,1,46,1101,1000,1,20,4,20,1105,1,46,98,99" | 8     | 1000
//        "3,21,1008,21,8,20,1005,20,22,107,8,21,20,1006,20,31,1106,0,36,98,0,0,1002,21,125,20,4,20,1105,1,46,104,999,1105,1,46,1101,1000,1,20,4,20,1105,1,46,98,99" | 9     | 1001
    }
}
