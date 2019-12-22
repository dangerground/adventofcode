package com.github.dangerground

import kotlin.math.ceil
import kotlin.math.floor

typealias Chemical = String

class Nanofactory(reactionsInput: String) {

    val reactions = HashMap<Chemical, Reaction>()

    init {
        reactionsInput.lines().forEach { reaction ->
            val reactionParts = reaction.split(" => ")
            val inputs = reactionParts.first().split(", ").map { stringToAmount(it) }
            val output = stringToAmount(reactionParts.last())
            reactions[output.chemical] = Reaction(output, inputs)
        }
    }

    private fun stringToAmount(it: String): Amount {
        val amout = it.split(" "); return Amount(amout.first().toLong(), amout.last())
    }

    //fun findOreForFuel() = resolve("FUEL" as Chemical)


    val unresolved = HashSet<Chemical>()
    val totalRequired = HashMap<Chemical, Long>()
    val totalGenerated = HashMap<Chemical, Long>()
    fun findOreForFuel() : Long {

        unresolved.add("FUEL")
        totalRequired["FUEL"] = 13108426
        do {
            val element = unresolved.first()
            unresolved.remove(element)

            if (element != "ORE") {
                val reaction = reactions[element]!!

                var totalValue = getGenerated(element)
                if (totalValue < totalRequired[element]!!) {
                    val required = totalRequired[element]!! - totalValue
                    totalValue += calcProduced(required, reaction.output.value)

                    reaction.inputs.forEach {
                        unresolved.add(it.chemical)
                        setRequired(Amount(it.value * stepCount(required, reaction.output.value), it.chemical))
                    }
                    totalGenerated[reaction.output.chemical] = totalValue
                }

            }
            println(totalGenerated)
        } while (unresolved.isNotEmpty())

        return totalRequired["ORE"]!!
    }

    private fun stepCount(required: Long, produceable: Long) = ceil(required.toDouble() / produceable).toLong()
    private fun calcProduced(required: Long, produceable: Long) = stepCount(required, produceable) * produceable

    private fun getGenerated(element: Chemical): Long {
        if (totalGenerated.containsKey(element)) {
            return totalGenerated[element]!!
        }

        return 0
    }

    private fun setRequired(amount: Amount) {
        if (totalRequired.containsKey(amount.chemical)) {
            totalRequired[amount.chemical] = totalRequired[amount.chemical]!! + amount.value
        } else {
            totalRequired[amount.chemical] = amount.value
        }
    }
}

class Reaction(val output: Amount, val inputs: List<Amount>)

class Amount(val value: Long, val chemical: Chemical)
