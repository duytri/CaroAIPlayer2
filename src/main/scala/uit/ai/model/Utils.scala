package main.scala.uit.ai.model

import scala.collection.mutable.ArrayBuffer

object Utils {

  val WIN_VALUE = 100000
  val DIVIDE_RATIO = 7

  def getCandidates(board: Array[Array[Byte]]): Array[(Int, Int)] = {
    val rowCount = board.length
    val columnCount = if (board.isEmpty) 0 else board(0).length

    val candidates = new ArrayBuffer[(Int, Int)] //set of candidates
    val nonAvailableElems = new ArrayBuffer[(Int, Int)] //set of non available movements
    // get all non-available moves
    for (r <- 0 until rowCount)
      for (c <- 0 until columnCount)
        if (board(r)(c) != 0)
          nonAvailableElems.append((r, c))
    // check around to get candidates
    for (e <- nonAvailableElems) {
      if (e._1 + 1 < rowCount && !nonAvailableElems.contains((e._1 + 1, e._2)) && !candidates.contains((e._1 + 1, e._2))) //East
        candidates.append((e._1 + 1, e._2))
      if (e._1 - 1 >= 0 && !nonAvailableElems.contains((e._1 - 1, e._2)) && !candidates.contains((e._1 - 1, e._2))) //West
        candidates.append((e._1 - 1, e._2))
      if (e._2 - 1 >= 0 && !nonAvailableElems.contains((e._1, e._2 - 1)) && !candidates.contains((e._1, e._2 - 1))) //North
        candidates.append((e._1, e._2 - 1))
      if (e._2 + 1 < columnCount && !nonAvailableElems.contains((e._1, e._2 + 1)) && !candidates.contains((e._1, e._2 + 1))) //South
        candidates.append((e._1, e._2 + 1))
      if (e._1 + 1 < rowCount && e._2 - 1 >= 0 && !nonAvailableElems.contains((e._1 + 1, e._2 - 1)) && !candidates.contains((e._1 + 1, e._2 - 1))) //East-North
        candidates.append((e._1 + 1, e._2 - 1))
      if (e._1 + 1 < rowCount && e._2 + 1 < columnCount && !nonAvailableElems.contains((e._1 + 1, e._2 + 1)) && !candidates.contains((e._1 + 1, e._2 + 1))) //East-South
        candidates.append((e._1 + 1, e._2 + 1))
      if (e._1 - 1 >= 0 && e._2 + 1 < columnCount && !nonAvailableElems.contains((e._1 - 1, e._2 + 1)) && !candidates.contains((e._1 - 1, e._2 + 1))) //West-South
        candidates.append((e._1 - 1, e._2 + 1))
      if (e._1 - 1 >= 0 && e._2 - 1 >= 0 && !nonAvailableElems.contains((e._1 - 1, e._2 - 1)) && !candidates.contains((e._1 - 1, e._2 - 1))) //West-North
        candidates.append((e._1 - 1, e._2 - 1))
    }
    candidates.toArray
  }

  def cloneArray(x: Array[Array[Byte]]): Array[Array[Byte]] = {
    return x.map(a => {
      a.map(b => b)
    })
  }

  def getStateAfterMove(board: Array[Array[Byte]], move: (Int, Int), player: Byte) = {
    val cloneBoard = cloneArray(board)
    cloneBoard(move._1)(move._2) = player
    cloneBoard
  }

  def getRow(board: Array[Array[Byte]], move: (Int, Int)): Array[Byte] = {
    board(move._1)
  }

  def getColumn(board: Array[Array[Byte]], move: (Int, Int), rowCount: Int): Array[Byte] = {
    (for (r <- 0 until rowCount) yield board(r)(move._2)).toArray
  }

  def getLTR(board: Array[Array[Byte]], move: (Int, Int), rowCount: Int, columnCount: Int): Array[Byte] = {
    val resulfBuffer = new ArrayBuffer[Byte]
    // start point
    var start = (0, 0)
    if (move._1 > move._2) start = (move._1 - move._2, 0) // neu x > y
    else start = (0, move._2 - move._1)
    var i = 0
    if (rowCount - 1 - move._1 > columnCount - 1 - move._2) { // diem cuoi cham canh doc lon nhat (column max)
      for (col <- start._2 until columnCount) {
        resulfBuffer.append(board(start._1 + i)(col))
        i += 1
      }
    } else { // diem cuoi cham canh ngang lon nhat (row max)
      for (row <- start._1 until rowCount) {
        resulfBuffer.append(board(row)(start._2 + i))
        i += 1
      }
    }

    resulfBuffer.toArray
  }

  def getRTL(board: Array[Array[Byte]], move: (Int, Int), rowCount: Int, columnCount: Int): Array[Byte] = {
    val rowCount = board.length
    val columnCount = if (board.isEmpty) 0 else board(0).length
    val resulfBuffer = new ArrayBuffer[Byte]
    // start point
    var start = (0, 0)
    if (move._2 > rowCount - 1 - move._1)
      start = (rowCount - 1, move._1 + move._2 - rowCount + 1)
    else
      start = (move._1 + move._2, 0)
    var i = 0
    if (move._1 < columnCount - 1 - move._2) { // diem cuoi cham hang be nhat (row 0)
      for (row <- start._1 to 0 by -1) {
        resulfBuffer.append(board(row)(start._2 + i))
        i += 1
      }
    } else { // diem cuoi cham canh doc lon nhat (column max)
      for (col <- start._2 until columnCount) {
        resulfBuffer.append(board(start._1 - i)(col))
        i += 1
      }
    }

    resulfBuffer.toArray
  }

  def nInARow(n: Int, array: Array[Byte], hasBlock: Boolean): Byte = {
    for (i <- 0 until array.length - (n - 1)) {
      var allTrue = true;
      for (j <- i + 1 until i + n) {
        allTrue &= (array(j - 1) == array(j))
      }
      if (allTrue && array(i) != 0) {
        if (hasBlock) {
          if (i > 0 && i + n < array.length && array(i - 1) != array(i) && array(i + n) != array(i) && array(i - 1) != 0 && array(i + n) != 0) // dieu kien chan hai dau thi khong thang
            return 0
          else
            return array(i)
        } else
          return array(i)
      }
    }

    return 0
  }

  def calculateValue(node: Vertex, numInARowNeeded: Int, player: Byte, hasBlock: Boolean): Int = {
    //val rowCount = node.board.length
    //val columnCount = if (node.board.isEmpty) 0 else node.board(0).length

    var point = 0
    val bufferMove = new ArrayBuffer[Array[Byte]]()
    bufferMove.append(getRow(node.board, node.move))
    bufferMove.append(getColumn(node.board, node.move, node.rowCount))
    bufferMove.append(getLTR(node.board, node.move, node.rowCount, node.columnCount))
    bufferMove.append(getRTL(node.board, node.move, node.rowCount, node.columnCount))

    val arrayMove = bufferMove.toArray

    for (num <- numInARowNeeded to 1 by -1) {
      arrayMove.foreach(row => {
        val side = nInARow(num, row, hasBlock)
        if (side == player) // Me
          point = point + WIN_VALUE / ((numInARowNeeded + 1 - num) * DIVIDE_RATIO)
        else if (side == -1 * player) // My Opponent
          point = point - WIN_VALUE / ((numInARowNeeded + 1 - num) * DIVIDE_RATIO)
      })
    }

    return point
  }

  def checkWinAtState(node: Vertex, numInARowNeeded: Int, hasBlock: Boolean): Byte = {
    //val rowCount = board.length
    //val columnCount = if (board.isEmpty) 0 else board(0).length

    if (node.move != null) {
      val row = nInARow(numInARowNeeded, getRow(node.board, node.move), hasBlock)
      val column = nInARow(numInARowNeeded, getColumn(node.board, node.move, node.rowCount), hasBlock)
      val ltr = nInARow(numInARowNeeded, getLTR(node.board, node.move, node.rowCount, node.columnCount), hasBlock)
      val rtl = nInARow(numInARowNeeded, getRTL(node.board, node.move, node.rowCount, node.columnCount), hasBlock)

      if (row != 0) return row
      else if (column != 0) return column
      else if (ltr != 0) return ltr
      else if (rtl != 0) return rtl
      else return 0
    } else return 0
  }
}