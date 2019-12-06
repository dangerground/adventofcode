package com.github.dangerground

class Day6(directOrbits: MutableList<String>) {

    private val orbits = HashMap<String, String>()

    init {
        directOrbits.forEach {
            val objects = it.split(")")
            orbits[objects[1]] = objects[0]
        }
    }


    private fun countOrbit(obj: String?): Int {
        if (obj == "COM" || obj == null) {
            return 0
        }

        return countOrbit(orbits[obj]) + 1
    }

    fun countOrbits(): Int {
        var total = 0
        orbits.forEach { (key, value) ->
//            println("$key -> $value")
            total += countOrbit(key)
        }

        return total
    }


    private fun findWay(obj: String): ArrayList<String> {

        val path = ArrayList<String>()
        var current : String? = obj
        do {
            current = orbits[current]
            if (current != null) {
                path.add(current)
            } else break

        } while (current != "COM")

        return path
    }


    fun countMinimalDistance(): Int {
        val you = findWay("YOU").reversed()
        val san = findWay("SAN").reversed()

        for (i in 0..you.size) {
            if (you[i] != san[i]) {
                return you.size - i + san.size - i
            }
        }
        return 0
    }

}