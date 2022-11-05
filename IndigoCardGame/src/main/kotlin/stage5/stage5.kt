package stage5

val ranks = listOf("A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K")
val suits = listOf("♦", "♥", "♠", "♣")
var deckCards =  getFullCards().shuffled()
const val INITIAL_CARDS_AMOUNT = 4
const val DEAL_CARDS_AMOUNT = 6

val tableCards = initialTable()
var playerCards = dealCards(DEAL_CARDS_AMOUNT)
var computerCards = dealCards(DEAL_CARDS_AMOUNT)

const val PLAYER_NAME = "Player"
const val COMPUTER_NAME = "Computer"
val RANKS_HAS_1POINT = mutableListOf("A", "10", "J", "Q", "K")

val cardsWonByPlayer = mutableListOf<String>()
val cardsWonByComputer = mutableListOf<String>()

// Total points is 23 (20 from cards and extra 3 for having more cards)
var playerPoints = 0
var computerPoints = 0
var whoPlayedFirst: String? = null
var lastRoundWinner: String? = null

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
    val selected = pickCandidateCard(computerCards)
    println("Computer plays $selected")

    return selected
}

fun gameOver() {
    println("Game Over")
}

fun findRoundWinner(cardsOnTable: MutableList<String>, isPlayerTurn: Boolean? = null) {
    if (isPlayerTurn == null) {
        throw Exception("Can not determine a winner in this turn.")
    }

    var winner: String? = null

    // Check the tossed card has the same rank or suit with the top card
    val tossedCard = cardsOnTable.last()
    val topCardOnTable = if (cardsOnTable.size >= 2) cardsOnTable[cardsOnTable.size - 2] else ""
    val hasWinnableCard = if (topCardOnTable.isNotEmpty()) hasSameRankOrSuit(tossedCard, topCardOnTable) else false

    if (hasWinnableCard) {
        winner = if (isPlayerTurn) PLAYER_NAME else COMPUTER_NAME
        println("$winner wins cards")
        storeResult(cardsOnTable, winner)
        tableCards.clear()
        showResult()
        lastRoundWinner = winner
    }
    // When no one wins and no more cards
    else if (deckCards.isEmpty() && playerCards.size == 0 && computerCards.size == 0) {
        // Show cards on the table before being cleared
        showCardsOnTable(tableCards)
        // Who won the last round gets the points
        winner = lastRoundWinner ?: whoPlayedFirst
        storeResult(cardsOnTable, winner!!)
        tableCards.clear()
        return
    }
}

fun getRank(card: String): String = card.dropLast(1)
fun getSuit(card: String): String = card.last().toString()
fun hasSameRank(card1: String, card2: String): Boolean = getRank(card1) == getRank(card2)
fun hasSameSuit(card1: String, card2: String): Boolean = getSuit(card1) == getSuit(card2)
fun hasSameRankOrSuit (card1: String, card2:String): Boolean = hasSameRank(card1, card2) || hasSameSuit(card1, card2)

fun storeResult(wonCards: MutableList<String>, winner: String) {
    val wonPoints = calculatePoints(wonCards)
    when (winner) {
        PLAYER_NAME -> {
            cardsWonByPlayer.addAll(wonCards)
            playerPoints += wonPoints
        }

        COMPUTER_NAME -> {
            cardsWonByComputer.addAll(wonCards)
            computerPoints += wonPoints
        }
    }
}

fun calculatePoints(cards: MutableList<String>): Int {
    var points = 0
    val cardsHavePoint = cards.filter { it.dropLast(1) in RANKS_HAS_1POINT }

    if (cardsHavePoint.isNotEmpty()) {
        points += cardsHavePoint.size
    }

    return points
}

fun showResult() {
    println("Score: Player $playerPoints - Computer $computerPoints")
    println("Cards: Player ${cardsWonByPlayer.size} - Computer ${cardsWonByComputer.size}")
}

fun findCardsWithSameSuit(cards: MutableList<String>, suit: String) = cards.filter { getSuit(it) == suit }
fun findCardsWithSameRank(cards: MutableList<String>, rank: String) = cards.filter { getRank(it) == rank }
fun findCardsWithDuplicateSuitOrRank(cardsInHands: MutableList<String>): MutableList<String> {
    val suitsInHands = cardsInHands.map { getSuit(it) }
    val duplicateSuits = suits.filter {
        suitsInHands.filter{s -> s == it}.size >= 2
    }
    val ranksInHands = cardsInHands.map{ getRank(it) }
    val duplicateRanks = ranks.filter {
        ranksInHands.filter{s -> s == it}.size >= 2
    }

    // Priority: Suit > Rank
    // If there are two more duplicate SUITS -> Choose one of them
    if (duplicateSuits.isNotEmpty())
        return cardsInHands.filter { getSuit(it) in duplicateSuits }.toMutableList()

    // If there are two more duplicate RANKS -> Choose one of them
    if (duplicateRanks.isNotEmpty())
        return cardsInHands.filter { getRank(it) in duplicateRanks }.toMutableList()

    // Otherwise, return the origin
    return cardsInHands
}

fun findCandidateCards(cardsInHands: MutableList<String>, topCardOnTable: String): MutableList<String> {
    val candidateCards = mutableListOf<String>()
    val cardsWithSameSuit = findCardsWithSameSuit(cardsInHands, getSuit(topCardOnTable))
    val cardsWithSameRank = findCardsWithSameRank(cardsInHands, getRank(topCardOnTable))

    // Priority: Suit > Rank
    if (cardsWithSameSuit.isNotEmpty()) candidateCards.addAll(cardsWithSameSuit.toMutableList())
    if (cardsWithSameRank.isNotEmpty()) candidateCards.addAll(cardsWithSameRank.toMutableList())

    // No card has the same rank or suit
    return candidateCards
}


fun pickCandidateCard(cardsInHands: MutableList<String>): String {
    val topCardOnTable = if (tableCards.isNotEmpty()) tableCards.last() else ""
    // Only one card in hands
    if (cardsInHands.size == 1) return cardsInHands.first()

    var candidateCard = ""

    // No card on table
    if (tableCards.isEmpty()) {
        // Toss a random card that have duplicate suits or ranks
        // Otherwise, toss a random card in hands
        candidateCard = findCardsWithDuplicateSuitOrRank(cardsInHands).shuffled().first()
    } else {
        val candidateCards = findCandidateCards(cardsInHands, topCardOnTable)
//        println("candidateCards: $candidateCards")
        candidateCard = when(candidateCards.size) {
            // No candidate card
            0 -> {
                // Toss a random card from the cards with same rank or suit
                // Otherwise, toss a random card in hands
                findCardsWithDuplicateSuitOrRank(cardsInHands).first()
            }
            // Only one candidate card
            1 -> candidateCards.first()
            else -> {
                val cardsWithSameSuit = findCardsWithSameSuit(candidateCards, getSuit(topCardOnTable))
                val cardsWithSameRank = findCardsWithSameRank(candidateCards, getRank(topCardOnTable))
//                println("cardsWithSameSuit: $cardsWithSameSuit")
//                println("cardsWithSameRank: $cardsWithSameRank")
                if (cardsWithSameSuit.size > cardsWithSameRank.size) {
                    cardsWithSameSuit.first()
                } else if (cardsWithSameSuit.size < cardsWithSameRank.size) {
                    cardsWithSameRank.first()
                } else {
                    candidateCards.first()
                }
            }
        }
    }

    return candidateCard
}

fun main() {
    var isPlayerTurn: Boolean?
    val gameOver = false

    println("Indigo Card Game")
    // Shuffle
    deckCards = deckCards.shuffled().toMutableList()
    // Ask to play first
    do isPlayerTurn = chooseTurnToPlay() while (isPlayerTurn == null)

    println("Initial cards on the table: ${tableCards.joinToString(" ")}")

    while (!gameOver) {
        showCardsOnTable(tableCards)
        // No more cards
        if (deckCards.isEmpty() && playerCards.size == 0 && computerCards.size == 0) break

        if (isPlayerTurn!!) {
            if (tableCards.size == 0) whoPlayedFirst = PLAYER_NAME
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
            if (tableCards.size == 0) whoPlayedFirst = COMPUTER_NAME
            // Deal new cards when no card in hands
            if (computerCards.size == 0) computerCards = dealCards(DEAL_CARDS_AMOUNT)

            // Select a card to play
            val computerSelectedCard = computerPlayCard(computerCards)
            computerCards.remove(computerSelectedCard)
            tableCards.add(computerSelectedCard)
        }

        // Find the winner after a card is tossed
        findRoundWinner(tableCards, isPlayerTurn)
        // Change turn
        isPlayerTurn = !isPlayerTurn
    }

    // Who has the most cards and played first get 3 more points
    when {
        cardsWonByPlayer.size > cardsWonByComputer.size -> playerPoints += 3
        cardsWonByComputer.size > cardsWonByPlayer.size -> computerPoints += 3
        else -> {
            if (whoPlayedFirst == PLAYER_NAME) playerPoints += 3
            else computerPoints += 3
        }
    }

    if (deckCards.isEmpty()) showResult()
    gameOver()
}