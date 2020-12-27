package fun.airzihao.pandadb.kernel

import java.io.File

import fun.airzihao.pandadb.Utils.timingByMicroSec
import org.junit.{Assert, Before, Test}

/**
 * @Author: Airzihao
 * @Description:
 * @Date: Created at 13:41 2020/12/25
 * @Modified By:
 */
class PDBMetaDataTest {

  val dbPath: String = "./src/test/resource"

  @Before
  def initDirectory(): Unit = {
    val file = new File(dbPath)
    if(!file.exists()) file.mkdirs()
    else file.delete()
  }

  @Test
  def test1(): Unit = {
    val nodeId0 = PDBMetaData.availableNodeId
    Assert.assertEquals(nodeId0+1, PDBMetaData.availableNodeId)
    PDBMetaData.persist(dbPath)
    PDBMetaData.availableNodeId
    PDBMetaData.init(dbPath)
    Assert.assertEquals(nodeId0+2, PDBMetaData.availableNodeId)
  }

  @Test
  def test2(): Unit = {
    val propId0 = PDBMetaData.getPropId("name")
    Assert.assertEquals(propId0, PDBMetaData.getPropId("name"))
    PDBMetaData.persist(dbPath)
    //this prop name is not persisted
    val propId1 = PDBMetaData.getPropId("age")
    PDBMetaData.init(dbPath)
    val propId2 = PDBMetaData.getPropId("student")
    Assert.assertEquals(propId1, propId2)
    PDBMetaData.persist(dbPath)
    Assert.assertEquals(propId2, PDBMetaData.getPropId("student"))
  }

  //performance test
  @Test
  def test9(): Unit = {
    PDBMetaData.availabelIndexId
    timingByMicroSec(PDBMetaData.availableNodeId)
    timingByMicroSec(PDBMetaData.availableRelId)
    timingByMicroSec(PDBMetaData.availabelIndexId)
    timingByMicroSec(PDBMetaData.getPropId("alice"))
    timingByMicroSec(PDBMetaData.getPropId("alice"))
  }

}
