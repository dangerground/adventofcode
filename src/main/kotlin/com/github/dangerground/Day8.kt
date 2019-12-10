package com.github.dangerground

class Day8 {

    val img = ArrayList<Layer>()

    fun readSpaceImage(image: String, width: Int, height: Int) {
        var ptr = 0

        do {
            val layer = ArrayList<Line>()
            var count0 = 0
            var count1 = 0
            var count2 = 0
            for (h in 0 until height) {
                val line = image.substring(ptr, ptr + width)
                val lineInt = ArrayList<Int>()
                line.toCharArray().forEach {
                    val num = "$it".toInt()
                    lineInt.add(num)
                    when (num) {
                        0 -> count0++
                        1 -> count1++
                        2 -> count2++
                    }
                }
                layer.add(Line(lineInt))
                ptr += width
            }
            img.add(Layer(layer, count0, count1, count2))
        } while (ptr < image.length)
    }

    fun findLayerWithFewestZeros(): Int {
        var fewestZeros = Int.MAX_VALUE
        var result = 0
        img.forEach {
            if (it.count0 < fewestZeros) {
                fewestZeros = it.count0
                result = it.count1 * it.count2
                println("$result = ${it.count1} * ${it.count2}")
            }
        }

        return result
    }

    fun decodeImage(): Layer {
        val decoded = img[0]
        img.forEach {
            for (l in 0 until it.lines.size) {
                val line = it.lines[l]
                for (p in 0 until line.pixel.size) {
                    if (decoded.lines[l].pixel[p] != 2) {
                        continue
                    }
                    when(line.pixel[p]) {
                        0 -> decoded.lines[l].pixel[p] = 0
                        1 -> decoded.lines[l].pixel[p] = 1
                        2 -> decoded.lines[l].pixel[p] = 2
                    }
                }
            }
        }

        return decoded
    }

    class Layer(val lines: ArrayList<Line>, val count0: Int, val count1: Int, val count2: Int)
    class Line(val pixel: ArrayList<Int>)
}