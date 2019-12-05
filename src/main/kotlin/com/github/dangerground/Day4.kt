package com.github.dangerground

class Day4 {

    val doublettes = Regex.fromLiteral("/(\\d)\\1/")

    fun isValidPassword(password: Int) : Boolean {
        val pwString = password.toString()

        if (pwString.length != 6) {
            return false
        }

        if (password < 307237 || password > 769058) {
            return false
        }

        if (pwString[0] > pwString[1]
            || pwString[1] > pwString[2]
            || pwString[2] > pwString[3]
            || pwString[3] > pwString[4]
            || pwString[4] > pwString[5]) {
            return false
        }

        var matchCount = 0
        var lastChar = '0'
        for (i in 0..4) {
            if (pwString[i] == pwString[i+1]) {
                if (lastChar == pwString[i]) {
                    matchCount++
                } else {
                    matchCount = 1
                }
            } else if (matchCount == 1) {
                break
            }
            lastChar = pwString[i]
        }

        println("pw: $pwString $matchCount")
        return matchCount == 1
    }
}