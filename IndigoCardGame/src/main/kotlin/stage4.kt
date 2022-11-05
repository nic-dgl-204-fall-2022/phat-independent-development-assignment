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

fun findRoundWinner(cardsOnTable: MutableList<String>, isPlayerTurn: Boolean? = null) {
    if (isPlayerTurn == null) {
        throw Exception("Can not determine a winner in this turn.")
    }

    if (cardsOnTable.size < 2) return

    var winner: String? = null

    // Check the tossed card has the same rank or suit
    val playedCardSuit = getSuit(cardsOnTable.last())
    val playedCardRank = getRank(cardsOnTable.last())
    val topCardSuit = getSuit(cardsOnTable[cardsOnTable.size - 2])
    val topCardRank = getRank(cardsOnTable[cardsOnTable.size - 2])
    val hasSameSuitOrRank = playedCardSuit == topCardSuit || playedCardRank == topCardRank

    if (hasSameSuitOrRank) {
        winner = if (isPlayerTurn) PLAYER_NAME else COMPUTER_NAME
        println("$winner wins cards")
        storeResult(cardsOnTable, winner)
        tableCards.clear()
        showResult()
        lastRoundWinner = winner
    } // When no one wins and no more cards
    else if (deckCards.size == 0 && playerCards.size == 0 && computerCards.size == 0) {
        // Show cards on the table before being cleared
        showCardsOnTable(tableCards)
        // Who won the last round gets the points
        winner = lastRoundWinner ?: whoPlayedFirst
        storeResult(cardsOnTable, winner!!)
        tableCards.clear()
    }
}

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

fun getRank(card: String): String = if (card.length == 3) card.drop(2) else card.drop(1)

fun getSuit(card: String): String = card.dropLast(1)

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
        if (deckCards.size == 0 && playerCards.size == 0 && computerCards.size == 0) break

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

    if (deckCards.size == 0) showResult()
    gameOver()
}