package com.github.dangerground

import com.github.dangerground.util.HelperUtil
import spock.lang.Specification
import spock.lang.Unroll

class Day8Test extends Specification {

    @Unroll
    def 'test image input #image'(String image, int width, int height, int multResult) {

        given:
        def day8 = new Day8()
        day8.readSpaceImage(image, width, height)

        when:
        def result = day8.findLayerWithFewestZeros()

        then:
        result == multResult

        where:
        image          | width | height | multResult
        "001122000122" | 3     | 2      | 4
    }

    def 'test image task input'() {
        given:
        def inputString = new File(getClass().getResource("/day8input.txt").file).text
        def day8 = new Day8()
        day8.readSpaceImage(inputString, 25, 6)

        when:
        def result = day8.findLayerWithFewestZeros()

        then:
        println("result $result")
    }

    @Unroll
    def 'decode image input #image'(String image, int width, int height, String line1, String line2) {

        given:
        def day8 = new Day8()
        day8.readSpaceImage(image, width, height)

        when:
        def result = day8.decodeImage()

        then:
        result.lines[0].pixel == HelperUtil.convert(line1)
        result.lines[1].pixel == HelperUtil.convert(line2)

        where:
        image              | width | height | line1 | line2
        "0222112222120000" | 2     | 2      | "0,1" | "1,0"
    }

    def 'decode puzzle input'() {
        given:
        def inputString = new File(getClass().getResource("/day8input.txt").file).text
        def day8 = new Day8()
        day8.readSpaceImage(inputString, 25, 6)

        when:
        def result = day8.decodeImage()

        then:
        result.lines.forEach {
            it.pixel.forEach { p ->
                print(p)
            }
            println()
        }

    }
}
