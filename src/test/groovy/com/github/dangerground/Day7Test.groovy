package com.github.dangerground

import spock.lang.Specification
import spock.lang.Unroll

import static com.github.dangerground.util.HelperUtil.convert

class Day7Test extends Specification {

    def "generate permutations"() {
        when:
        def day7 = new Day7(convert("99"), convert("0,1,2,3"))

        then:
        day7.phaseConfigs.size() == 24
    }

    @Unroll
    def "test example inputs #program"(String program, String phaseConfig, int output) {
        given:
        def input = convert(program)

        and:
        def day7 = new Day7(input, convert("0,1,2,3,4"))

        when:
        def result = day7.findMaxAmplitude()

        then:
        day7.bestConfig == convert(phaseConfig)
        result == output

        where:
        program                                                                                               | phaseConfig | output
        "3,15,3,16,1002,16,10,16,1,16,15,15,4,15,99,0,0"                                                      | "4,3,2,1,0" | 43210
        "3,23,3,24,1002,24,10,24,1002,23,-1,23,101,5,23,23,1,24,23,23,4,23,99,0,0"                            | "0,1,2,3,4" | 54321
        "3,31,3,32,1002,32,10,32,1001,31,-2,31,1007,31,0,33,1002,33,7,33,1,33,31,31,1,32,31,31,4,31,99,0,0,0" | "1,0,4,3,2" | 65210
    }

    def "calculate max amplification"() {
        given:
        def inputString = new File(getClass().getResource("/day7input.txt").file).text

        and:
        def day7 = new Day7(convert(inputString), convert("0,1,2,3,4"))

        when:
        def result = day7.findMaxAmplitude()

        then:
        println "max amplification: ${result}"
    }


    @Unroll
    def "test extended inputs #program"(String program, String phaseConfig, int output) {
        given:
        def input = convert(program)

        and:
        def day7 = new Day7(input, convert("5,6,7,8,9"))

        when:
        def result = day7.findMaxAmplitude()

        then:
        day7.bestConfig == convert(phaseConfig)
        result == output

        where:
        program                                                                                                                                                                         | phaseConfig | output
        "3,26,1001,26,-4,26,3,27,1002,27,2,27,1,27,26,27,4,27,1001,28,-1,28,1005,28,6,99,0,0,5"                                                                                         | "9,8,7,6,5" | 139629729
        "3,52,1001,52,-5,52,3,53,1,52,56,54,1007,54,5,55,1005,55,26,1001,54,-5,54,1105,1,12,1,53,54,53,1008,54,0,55,1001,55,1,55,2,53,55,53,4,53,1001,56,-1,56,1005,56,6,99,0,0,0,0,10" | "9,7,8,5,6" | 18216
    }
}
