package uk.co.devexe

import java.io.File

import org.junit.Assert.assertTrue
import org.junit.{After, Test}

/** This test class is written as an integration test rather than
  * a series of unit tests as the DirectoryMaker works mainly with
  * the file system which would otherwise require extensive mocking.
  */

@Test
class DirectoryMakerTest {

    val testDir = "src/test/resources"
    val regexp = "^[0-9]{4}_[0-9]{1,4}[s|a]"
    val expectedDir1 = new File(testDir,"1998_53a")
    val expectedDir2 = new File(testDir,"1991_859s")
    val expectedFile1 = new File(expectedDir1, "1998_53a_89956_19910328.txt")
    val expectedFile2 = new File(expectedDir2, "1991_859s_89958_19910328.txt")
    val expectedFile3 = new File(expectedDir2, "1991_859s_89959_19910328.txt")

    @Test
    def testRun() {
        val directoryMaker = new DirectoryMaker(testDir,regexp)
        directoryMaker.run()
        assertTrue(expectedFile1.exists())
        assertTrue(expectedFile2.exists())
        assertTrue(expectedFile3.exists())
    }

    @After
    def tearDown() {
        expectedFile1.renameTo(new File(testDir, expectedFile1.getName))
        expectedFile2.renameTo(new File(testDir, expectedFile2.getName))
        expectedFile3.renameTo(new File(testDir, expectedFile3.getName))
        expectedDir1.deleteOnExit()
        expectedDir2.deleteOnExit()
    }
}


