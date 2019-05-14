package main.scala.uit.ai.model

object CaroAIPlayer2 {
  def main(args: Array[String]): Unit = {

    val row1 = Array(-1.asInstanceOf[Byte], 1.asInstanceOf[Byte], -1.asInstanceOf[Byte], -1.asInstanceOf[Byte])
    val row2 = Array(1.asInstanceOf[Byte], 1.asInstanceOf[Byte], 1.asInstanceOf[Byte], 1.asInstanceOf[Byte])
    val row3 = Array(-1.asInstanceOf[Byte], -1.asInstanceOf[Byte], 0.asInstanceOf[Byte], 0.asInstanceOf[Byte])
    val row4 = Array(0.asInstanceOf[Byte], 0.asInstanceOf[Byte], 0.asInstanceOf[Byte], 0.asInstanceOf[Byte])
    val board = Array(row1, row2, row3, row4)

    val caroPlayer = new CaroPlayer
    val nextMove = caroPlayer.nextMove(board, 1, 3, true)
    println("Next move: x = " + nextMove._1 + " and y = " + nextMove._2)
  }
}