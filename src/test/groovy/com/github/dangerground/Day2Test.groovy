package com.github.dangerground


import spock.lang.Specification
import spock.lang.Unroll

import static de.rewe.digal.util.HelperUtil.convert

class Day2Test extends Specification {

    @Unroll
    def "run single step program #input"(String input, String output) {
        given:
        def day2 = new Day2(convert(input))

        when:
        Integer[] result = day2.runProgram()

        then:
        result == convert(output)

        where:
        input                 | output
        "1,0,0,0,99"          | "2,0,0,0,99"
        "2,3,0,3,99"          | "2,3,0,6,99"
        "2,4,4,5,99,0"        | "2,4,4,5,99,9801"
        "1,1,1,4,99,5,6,0,99" | "30,1,1,4,2,5,6,0,99"
    }


    def "calculate program code"() {
        given:
        def inputString = new File(getClass().getResource("/day2input.txt").file).text
        def input = convert(inputString)

        and:
        input[1] = 12
        input[2] = 2

        and:
        println(input)
        def day2 = new Day2(input)

        when:
        Integer[] result = day2.runProgram()

        then:
        println "at position 0: ${result[0]}"
    }

    def "searchResult"() {
        given:
        def inputString = new File(getClass().getResource("/day2input.txt").file).text
        Day2 day2
        int param1
        int param2

        when:
        for (int i = 0; i < 99; i++) {
            for (int j = 0; j < 99; j++) {
                day2 = initializeDayOne(inputString, i, j)
                try {

                    Integer[] result = day2.runProgram()
                    if (result[0] == 19690720) {
                        param1 = i
                        param2 = j
                        break
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                }
            }
        }

        then:
        println "at position 0: ${param1}#{$param2}"
    }

    private static Day2 initializeDayOne(def inputString, def param1, def param2) {
        def input = convert(inputString)

        input[1] = param1
        input[2] = param2
        return new Day2(input)
    }

}
