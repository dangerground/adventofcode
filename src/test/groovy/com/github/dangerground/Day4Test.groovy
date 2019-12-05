package com.github.dangerground

import spock.lang.Specification
import spock.lang.Unroll

class Day4Test extends Specification {

    @Unroll
    def "check for criteria #input"(Integer input, Boolean valid) {
        given:
        def day4 = new Day4()

        when:
        def result = day4.isValidPassword(input)

        then:
        result == valid

        where:
        input  | valid
        333333 | false
        334570 | false
        345689 | false

        334455 | true
        345666 | false
        333344 | true
    }

    def "count criteria for puzzle input "() {
        given:
        def day4 = new Day4()
        def count = 0;

        when:
        for (i in 307237..769058) {
            if (day4.isValidPassword(i)) {
                count++
            }
        }

        then:
        println "counted $count"
    }
}
