package stage3

const val INITIAL_CARDS_AMOUNT = 4
const val DEAL_CARDS_AMOUNT = 6
const val PLAYER_NAME = "Player"
const val COMPUTER_NAME = "Computer"

val ranks = listOf("A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K")
val suits = listOf("♦", "♥", "♠", "♣")
var deckCards = getFullCards()
val tableCards = initialTable()
var playerCards = dealCards(DEAL_CARDS_AMOUNT)
var computerCards = dealCards(DEAL_CARDS_AMOUNT)
var whoPlayedFirst: String? = null
val cardsWonByPlayer = mutableListOf<String>()
val cardsWonByComputer = mutableListOf<String>()

fun getFullCards(): MutableList<String> {
    val cards = mutableListOf<String>()

    for (suit in suits) {
        for (rank in ranks) {
            cards.add("$rank$suit")
        }
    }

    return cards
}

fun chooseTurnToPlay(): Boolean? {
    println("Play first?")

    return when (readlnOrNull()?.lowercase() ?: "") {
        "yes" -> {
            whoPlayedFirst = PLAYER_NAME
            true
        }
        "no" -> {
            whoPlayedFirst = COMPUTER_NAME
            false
        }
        else -> null
    }
}

fun initialTable(): MutableList<String> {
    val initialCards = deckCards.take(INITIAL_CARDS_AMOUNT)
    deckCards = deckCards.drop(INITIAL_CARDS_AMOUNT).toMutableList()

    return initialCards.toMutableList()
}

fun showCardsOnTable(cards: MutableList<String>) {
    println()
    if (cards.size != 0) {
        println("${cards.size} cards on the table, and the top card is ${cards.last()}")
    } else if (cardsWonByPlayer.size + cardsWonByComputer.size != 52) {
//    } else {
        println("No cards on the table")
    }
}

fun dealCards(amountToDeal: Int): MutableList<String> {
    val amount = if (deckCards.size >= amountToDeal) amountToDeal else deckCards.size
    val dealCards = deckCards.take(amount).toMutableList()
    deckCards = deckCards.drop(amount).toMutableList()

    return dealCards.toMutableList()
}

fun showPlayerCards(playerCards: MutableList<String>) {
    println("Cards in hand: ${
        playerCards.mapIndexed { index, s ->  "${index + 1})$s" }.joinToString(" ")
    }")
}

fun playerPlayCard(playerCards: MutableList<String>, showCards: Boolean = true): String {
    if (showCards) showPlayerCards(playerCards)

    println("Choose a card to play (1-${playerCards.size}):")

    val choice = readln()
    val selectedIndex = choice.toIntOrNull() ?: 0

    return when {
        selectedIndex in 1..playerCards.size -> {
            return playerCards[selectedIndex - 1]
        }
        choice == "exit" -> return "exit"
        else -> playerPlayCard(playerCards, false)
    }
}

fun computerPlayCard(computerCards: MutableList<String>): String {
    println(computerCards.joinToString(" "))
    val selected = computerCards.shuffled().first()
    println("Computer plays $selected")

    return selected
}

fun gameOver() {
    println("Game Over")
}

fun main() {
    var isPlayerTurn: Boolean?
    var gameOver = false

    println("Indigo Card Game")
    // Shuffle
    deckCards = deckCards.shuffled().toMutableList()
    // Ask to play first
    do isPlayerTurn = chooseTurnToPlay() while (isPlayerTurn == null)

    println("Initial cards on the table: ${tableCards.joinToString(" ")}")

    showCardsOnTable(tableCards)
    while (!gameOver && tableCards.size <= 52) {
        if (isPlayerTurn!!) {
            // Deal new cards when no card in hands
            if (playerCards.size == 0) playerCards = dealCards(DEAL_CARDS_AMOUNT)

            // Select a card to play
            when (val selectedByPlayer = playerPlayCard(playerCards)) {
                "exit" -> break
                else -> {
                    playerCards.remove(selectedByPlayer)
                    tableCards.add(selectedByPlayer)
                }
            }
        } else {
            // Deal new cards when no card in hands
            if (computerCards.size == 0) computerCards = dealCards(DEAL_CARDS_AMOUNT)

            // Select a card to play
            val computerSelectedCard = computerPlayCard(computerCards)
            computerCards.remove(computerSelectedCard)
            tableCards.add(computerSelectedCard)

        }

        // Show cards on the table
        showCardsOnTable(tableCards)
        // Change turn
        isPlayerTurn = !isPlayerTurn
        // No more card
        if (tableCards.size == 52) gameOver = true
    }

    gameOver()
}