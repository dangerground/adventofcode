package com.github.dangerground

import spock.lang.Specification
import spock.lang.Unroll

import static com.github.dangerground.util.HelperUtil.convertL

class Day9Test extends Specification {

    @Unroll
    def "test relative mode #program"(String program, long output) {
        given:
        def input = convertL(program)

        and:
        def day9 = new Day9(input, new Long[0])

        when:
        day9.runProgram()

        then:
        day9.lastOutput == output

        where:
        program                                                     | output
        "109,1,204,-1,1001,100,1,100,1008,100,16,101,1006,101,0,99" | 99
        "1102,34915192,34915192,7,4,7,99,0"                         | 1219070632396864
        "104,1125899906842624,99"                                   | 1125899906842624
    }


    def "calculate program code - part 2"() {
        given:
        def inputString = new File(getClass().getResource("/day9input.txt").file).text
        def program = convertL(inputString)

        def input = new Long[1]
        input[0] = 2

        and:
        def day9 = new Day9(program, input)

        when:
        day9.runProgram()

        then:
        println "out: ${day9.lastOutput}"
    }
}
