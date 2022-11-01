
fun main() {
    val ranks = listOf("A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K")
    val suits = listOf("♦", "♥", "♠", "♣")
    val deckCards = listOf("K♣", "Q♣", "J♣", "10♣", "9♣", "8♣", "7♣", "6♣", "5♣", "4♣", "3♣", "2♣", "A♣", "K♦", "Q♦", "J♦", "10♦", "9♦", "8♦", "7♦", "6♦", "5♦", "4♦", "3♦", "2♦", "A♦", "K♥", "Q♥", "J♥", "10♥", "9♥", "8♥", "7♥", "6♥", "5♥", "4♥", "3♥", "2♥", "A♥", "K♠", "Q♠", "J♠", "10♠", "9♠", "8♠", "7♠", "6♠", "5♠", "4♠", "3♠", "2♠", "A♠")

    // println(ranks.joinToString(" "))
    // println(suits.joinToString(" "))
    // println(deckCards.joinToString(" "))

    var userCards = deckCards
    var userChoice: String? = null

    do {
        println("Choose an action (reset, shuffle, get, exit):")
        userChoice = readln()

        when(userChoice) {
            "get" -> {
                println("Number of cards:")
                try {
                    val numOfCards = readln().toInt()
                    if (numOfCards in 1..52) {
                        if (numOfCards > userCards.size) {
                            println("The remaining cards are insufficient to meet the request.")
                        } else {
                            val removedCards = userCards.slice(0 until numOfCards)
                            for (card in removedCards) {
                                if (card == removedCards.last()) {
                                    println(card)
                                } else {
                                    print("$card ")
                                }
                            }
                            userCards = userCards.drop(numOfCards)
                        }
                    } else {
                        println("Invalid number of cards.")
                    }
                } catch(e: Exception) {
                    println("Invalid number of cards.")
                }
                userChoice = null
            }
            "reset" -> {
                userCards = deckCards
                println("Card deck is reset.")
                userChoice = null
            }
            "shuffle" -> {
                userCards = userCards.shuffled()
                println("Card deck is shuffled.")
                userChoice = null
            }
            "exit" -> {
                println("Bye")
            }
            else -> {
                println("Wrong action.")
                userChoice = null
            }
        }
    } while (userChoice == null)
}