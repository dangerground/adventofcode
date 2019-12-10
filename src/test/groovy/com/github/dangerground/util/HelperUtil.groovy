package com.github.dangerground.util

import java.util.stream.Stream

class HelperUtil {

    static Integer[] convert(String input) {

        return Stream.of(input.split(","))
                .mapToInt({ s -> Integer.parseInt(s) })
                .toArray()
    }
    static Long[] convertL(String input) {

        return Stream.of(input.split(","))
                .mapToLong({ s -> Long.parseLong(s) })
                .toArray()
    }
}
