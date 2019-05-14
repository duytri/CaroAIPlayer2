package main.scala.uit.ai.model

class MinimaxTree(
  val currentPlayer: Byte,
  val board: Array[Array[Byte]],
  val move: (Int, Int),
  val rowCount: Int,
  val columnCount: Int,
  val numInARowNeeded: Int) {

  val MAX_DEPTH = 5

  def minimaxWithAlphaBeta(depth: Int, player: Byte, stepBoard: Array[Array[Byte]], move: (Int, Int), rowCount: Int, columnCount: Int, alpha: Int, beta: Int, hasBlock: Boolean): Int = {
    if (Utils.checkWinAtState(stepBoard, move, rowCount, columnCount, numInARowNeeded, hasBlock) != 0 || depth == MAX_DEPTH)
      return Utils.calculateValue(stepBoard, move, rowCount, columnCount, numInARowNeeded, player, hasBlock) // calculate node value

    if (player == currentPlayer) { //is Maximizing
      var alphaNew = alpha
      val oponentPlayer: Byte = (-1 * player).asInstanceOf[Byte]

      Utils.getCandidates(stepBoard, rowCount, columnCount).foreach(move => {
        alphaNew = math.max(alphaNew, minimaxWithAlphaBeta(depth + 1, oponentPlayer, Utils.getStateAfterMove(stepBoard, move, oponentPlayer), move, rowCount, columnCount, alpha, beta, hasBlock))

        if (beta <= alphaNew)
          return alphaNew;
      })
      return alphaNew;
    } else { // is Minimizing
      var betaNew = beta
      Utils.getCandidates(stepBoard, rowCount, columnCount).foreach(move => {
        betaNew = math.min(betaNew, minimaxWithAlphaBeta(depth + 1, currentPlayer, Utils.getStateAfterMove(stepBoard, move, currentPlayer), move, rowCount, columnCount, alpha, beta, hasBlock))

        if (betaNew <= alpha)
          return betaNew;
      })
      return betaNew;
    }
    return 0
  }
  
  def startEvaluate(board: Array[Array[Byte]], playerSide: Byte, numInARowNeeded: Int, hasBlock: Boolean):(Int, Int)={
    var maxValue = Int.MinValue
      var maxMove: (Int, Int) = (0, 0)
      //println("=====================================")
      Utils.getCandidates(board, rowCount, columnCount).foreach(move => {
        //println("Candidate: x = " + move._1 + " and y = " + move._2)
        val newValue = minimaxWithAlphaBeta(1, playerSide, board, move, rowCount, columnCount, Int.MinValue, Int.MaxValue, hasBlock)
        if (maxValue < newValue) {
          maxValue = newValue
          maxMove = move
        }
      })
      return maxMove
  }

}