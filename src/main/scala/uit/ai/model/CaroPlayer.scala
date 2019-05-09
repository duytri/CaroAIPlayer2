package main.scala.uit.ai.model

import java.util.Random
import java.lang.Math

class CaroPlayer extends Player {
  val numberOfLevel = 5

  def getName: String = "Computer AI"

  def nextMoveRandom(board: Array[Array[Byte]], playerSide: Byte, hasBlock: Boolean): (Int, Int) = {
    val size = board.length
    val random = new Random()
    while (true) {
      val i = random.nextInt(size)
      val j = random.nextInt(size)
      if (board(i)(j) == 0) //Blank
        return (i, j)
    }
    (-1, -1)
  }

  def nextMove(board: Array[Array[Byte]], playerSide: Byte, numInARowNeeded: Int, hasBlock: Boolean): (Int, Int) = {
    val rowCount = board.length
    val columnCount = board(0).length
    val countNonEmpty = board.foldLeft(0)((x, row) => {
      x + row.foldLeft(0)((i, j) => i + Math.abs(j))
    })

    if (countNonEmpty > 0) { // van co da dau roi
      val minimax = new MinimaxTree(numInARowNeeded)
      minimax.evaluateTreeWithAlphaBeta(board, numberOfLevel, hasBlock)
    } else { // van co chua dau
      (rowCount / 2, columnCount / 2)
    }
  }
  
  
}