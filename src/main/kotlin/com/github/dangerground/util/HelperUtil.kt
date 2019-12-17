package com.github.dangerground.util

import java.util.stream.Stream

class HelperUtil {

    companion object {
        @JvmStatic
        fun convert(input: String): Array<Int> {
            return input.split(",").map { s -> s.toInt() }.toTypedArray()
        }

        @JvmStatic
        fun convertL(input: String): Array<Long> {
            return input.split(",").map { s -> s.toLong() }.toTypedArray()
        }
    }
}
