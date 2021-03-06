package fun.airzihao.ldbc

import java.io.{BufferedOutputStream, File, FileOutputStream}
import scala.collection.mutable.ArrayBuffer
import scala.io.Source

/**
 * @Author: Airzihao
 * @Description:
 * @Date: Created at 16:46 2021/1/11
 * @Modified By:
 */
class CSVReader(file: File, spliter: String = LDBCTransformer.spliter) {
  val source = Source.fromFile(file)
  val iter = source.getLines()

  def getAsCSVLines: Iterator[CSVLine] = source.getLines().map(line => new CSVLine(line.split(spliter)))
  def close: Unit = source.close()
}

class CSVWriter(target: File) {
  val bos = new BufferedOutputStream(new FileOutputStream(target))

  def close: Unit = {
    bos.flush()
    bos.close()
  }

  def write(bytes: Array[Byte]): Unit = {
    bos.write(bytes)
    bos.flush()
  }
  def write(lineArr: Array[String]): Unit = write(s"${lineArr.mkString("|")}\n".getBytes())
  def write(line: String): Unit = write(s"$line\n".getBytes)
}

class CSVLine(arr: Array[String]) {
  private val _lineArrayBuffer: ArrayBuffer[String] = new ArrayBuffer[String]() ++ arr

  def insertElemAtIndex(index: Int, elem: String): Unit = {
    _lineArrayBuffer.insert(index, elem)
  }

  def dropElemAtIndex(index: Int): Unit = {
    _lineArrayBuffer.remove(index)
  }

  def replaceElemAtIndex(index: Int, elem: String): Unit = {
    _lineArrayBuffer.remove(index)
    _lineArrayBuffer.insert(index, elem)
  }

  def getAsArray: Array[String] = _lineArrayBuffer.toArray
  def getAsString: String = _lineArrayBuffer.mkString("|")
}