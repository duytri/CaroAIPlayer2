package main.scala.uit.ai.model

import scala.collection.mutable.Stack

class Vertex(
  val level: Int,
  val player: Byte,
  val move: (Int, Int),
  val board: Array[Array[Byte]]) {

  val rowCount = board.length
  val columnCount = if (board.isEmpty) 0 else board(0).length

  /*val numInARowNeeded: Int = {
    // so nuoc di thang hang can de chien thang
    // numbers chosen rather arbitrarily by me. I looked at this: http://en.wikipedia.org/wiki/M,n,k-game
    // and tried to pick numbers that more or less made sense
    if (rowCount <= 3 || columnCount <= 3) {
      // tic tac toe or bizarre tiny variants
      scala.math.min(rowCount, columnCount)
    } else if (rowCount <= 5) {
      // connect 4, sort of
      4
    } else {
      // gomoku
      5
    }
  }

  val children: scala.collection.immutable.Set[Vertex]

  def dfsMutableIterative(start: Vertex): scala.collection.immutable.Set[Vertex] = {
    var current: Vertex = start
    val found: scala.collection.mutable.Set[Vertex] = scala.collection.mutable.Set[Vertex]()
    val stack: Stack[Vertex] = Stack[Vertex]()
    stack.push(current)

    while (!stack.isEmpty) {
      current = stack.pop()
      if (!found.contains(current)) {
        found += current
        for (next <- current.children) {
          stack.push(next)
        }
      }
    }
    found.toSet
  }*/

}
  

