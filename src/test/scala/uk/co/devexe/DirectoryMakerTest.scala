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
    val regexp = "^[0-9]{4}"
    val expectedDir1 = new File(testDir,"1988")
    val expectedDir2 = new File(testDir,"1989")
    val expectedFile1 = new File(expectedDir1,"1988_1.txt")
    val expectedFile2 = new File(expectedDir1,"1988_2.txt")
    val expectedFile3 = new File(expectedDir2,"1989_1.txt")

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


