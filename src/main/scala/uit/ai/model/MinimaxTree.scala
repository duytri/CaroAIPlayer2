package main.scala.uit.ai.model

class MinimaxTree(
  val currentPlayer: Byte,
  val board: Array[Array[Byte]],
  val move: (Int, Int),
  val rowCount: Int,
  val columnCount: Int,
  val numInARowNeeded: Int) {

  val MAX_DEPTH = 3

  var root: Vertex = null

  def setRoot(board: Array[Array[Byte]], player: Byte, move: (Int, Int)) {
    root = new Vertex(0, player, move, board.clone())
  }

  /*private def minimize(node: Vertex, player:Byte, depth: Int, alpha: Int, beta: Int, hasBlock: Boolean): Int = {
    if (Utils.checkWinAtState(node, numInARowNeeded, hasBlock) != 0 || depth == 0) return Utils.calculateValue(node, numInARowNeeded, player, hasBlock) // calculate node value
    var newBeta = beta
    val opponent: Byte = if (node.player == 1) -1 else 1
    Utils.getCandidates(node.board).foreach(move => {
      var child = new Vertex(node.level + 1, opponent, move, Utils.getStateAfterMove(node.board, move, opponent))
      newBeta = math.min(newBeta, maximize(child, depth - 1, alpha, newBeta, hasBlock))
      if (alpha >= newBeta) {
        return alpha
      }
    })
    newBeta
  }

  private def maximize(node: Vertex, player:Byte, depth: Int, alpha: Int, beta: Int, hasBlock: Boolean): Int = {
    if (Utils.checkWinAtState(node, numInARowNeeded, hasBlock) != 0 || depth == 0) return Utils.calculateValue(node, numInARowNeeded, player, hasBlock) // calculate node value
    var newAlpha = alpha
    val opponent: Byte = if (node.player == 1) -1 else 1
    Utils.getCandidates(node.board).foreach(move => {
      var child = new Vertex(node.level + 1, opponent, move, Utils.getStateAfterMove(node.board, move, opponent))
      newAlpha = math.max(newAlpha, minimize(child, depth - 1, newAlpha, beta, hasBlock))
      if (newAlpha >= beta) {
        return beta
      }
    })
    newAlpha
  }

  def evaluateTreeWithAlphaBeta(board: Array[Array[Byte]], player:Byte, numberOfLevel: Int, hasBlock: Boolean): (Int, Int) = {
    var maxValue = Int.MinValue
    var maxMove: (Int, Int) = (0, 0)
    //minimize(root, numberOfLevel, Int.MinValue, Int.MaxValue, hasBlock)
    Utils.getCandidates(board).foreach(move => {
      val tree = new MinimaxTree(numInARowNeeded)
      tree.setRoot(Utils.getStateAfterMove(board, move, 1), 1, move)
      val newValue = tree.maximize(tree.root, numberOfLevel, Int.MinValue, Int.MaxValue, hasBlock)
      if (maxValue < newValue) {
        maxValue = newValue
        maxMove = move
      }
    })
    return maxMove
  }*/

  def minimaxWithAlphaBeta(depth: Int, player: Byte, stepBoard: Array[Array[Byte]], move: (Int, Int), rowCount: Int, columnCount: Int, alpha: Int, beta: Int, hasBlock: Boolean): Int = {
    if (Utils.checkWinAtState(stepBoard, move, rowCount, columnCount, numInARowNeeded, hasBlock) != 0 || depth == 0)
      return Utils.calculateValue(stepBoard, move, rowCount, columnCount, numInARowNeeded, player, hasBlock) // calculate node value

    if (player == currentPlayer) {
      var best: Int = Int.MinValue
      val oponentPlayer: Byte = (-1 * player).asInstanceOf[Byte]

      Utils.getCandidates(stepBoard).foreach(move => {
        var value = minimaxWithAlphaBeta(depth + 1, oponentPlayer, Utils.getStateAfterMove(stepBoard, move, oponentPlayer), move, rowCount, columnCount, alpha, beta, hasBlock)
        best = math.max(best, value)
        val alphaNew = math.max(alpha, best)

        if (alphaNew <= beta)
          return alphaNew;
      })
      return best;
    }
    return 0
  }

}