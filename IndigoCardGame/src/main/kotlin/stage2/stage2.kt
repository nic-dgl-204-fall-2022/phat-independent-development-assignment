package stage2

val ranks = listOf("A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K")
val suits = listOf("♦", "♥", "♠", "♣")
var deckCards =  getFullCards()

fun get(cards: MutableList<String>): MutableList<String> {
    var newCards: MutableList<String> = mutableListOf()
    println("Number of cards:")
    try {
        val numOfCards = readln().toInt()
        if (numOfCards !in 1..52) {
            println("Invalid number of cards.")
            return newCards
        }
        // Print removed cards
        println(cards.slice(0 until numOfCards).joinToString(" "))
        newCards = cards.drop(numOfCards).toMutableList()
    }   catch(e: IndexOutOfBoundsException) {
        println("The remaining cards are insufficient to meet the request.")
    }   catch(e: NumberFormatException) {
        println("Invalid number of cards.")
    }

    return newCards
}

fun reset(): MutableList<String> {
    println("Card deck is reset.")
    return getFullCards()
}

fun shuffle(cards: MutableList<String>): MutableList<String> {
    println("Card deck is shuffled.")
    return cards.shuffled().toMutableList()
}

fun exit() = println("Bye")

fun showMenu() {
    println("Choose an action (reset, shuffle, get, exit):")

    when(readlnOrNull()) {
        "get" -> deckCards = get(deckCards)
        "reset" -> deckCards = reset()
        "shuffle" -> deckCards = shuffle(deckCards)
        "exit" -> return exit()
        else -> println("Wrong action.")
    }
    showMenu()
}

fun getFullCards(): MutableList<String> {
    val cards = mutableListOf<String>()

    for (suit in suits) {
        for (rank in ranks) {
            cards.add("$rank$suit")
        }
    }

    return cards
}

fun main() {
    showMenu()
}