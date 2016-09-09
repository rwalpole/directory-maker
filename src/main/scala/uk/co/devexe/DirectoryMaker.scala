package uk.co.devexe

import java.io.File

import org.slf4j.LoggerFactory

import scala.util.matching.Regex

/**
  * The purpose of this utility class is to split file system directories containing large
  * numbers of files into new smaller sub-directories within the original directory which are
  * named according to some pattern found within the filenames. It will then move the files matching
  * these patterns into the appropriate directory.
  *
  * For example - given a directory that contains many files with a year in the filename and a regular
  * expression to match the year it will create subdirectories for each distinct year found and then
  * move the files into the appropriate subdirectory.
  */
class DirectoryMaker(baseDirName: String, pattern: String) {

    val LOG = LoggerFactory.getLogger(classOf[DirectoryMaker])

    /** contains file operation */
    def getListOfFileNames(baseDir: File):List[String] = {
        if(baseDir.exists && baseDir.isDirectory) {
            baseDir.listFiles.filter(_.isFile).toList map { file =>
                file.getName
            }
        } else {
            List[String]()
        }
    }

    def getPatternMatches(fileName: String): Option[String] = {
        val regex = new Regex(pattern)
        regex.findFirstIn(fileName)
    }

    def getAllPatternMatches(baseDir: File):List[String] = {
        getListOfFileNames(baseDir) map { fileName =>
            getPatternMatches(fileName) match {
                case Some(matching) => matching
                case None => ""
            }
        }
    }

    def getDistinctPatternMatches(baseDir: File): List[String] = {
        getAllPatternMatches(baseDir).distinct.filter( v => v != "")
    }

    /** contains file operation **/
    def makeDirectories(baseDir: File, subDirs: List[String]) {
        subDirs foreach { subDir =>
            val citationDir = new File(baseDir, subDir)
            if(!citationDir.exists()) {
                if(citationDir.mkdir()){
                    LOG.info("Created directory " + citationDir.getPath)
                } else {
                    LOG.error("Failed to create directory " + citationDir.getPath)
                }
            } else {
                LOG.warn("Directory " + citationDir.getPath + " already exists")
            }
        }
    }

    /** contains file operation **/
    def moveFiles(baseDir: File) = {
       getListOfFileNames(baseDir) foreach { fileName =>
           getPatternMatches(fileName) match {
               case Some(subDir) =>
                   val sourceFile = new File(baseDir, fileName)
                   val destDir = new File(baseDir,subDir)
                   if(sourceFile.renameTo(new File(destDir, fileName))) {
                       LOG.info("Moved file " + fileName + " to " + destDir.getPath)
                   }
               case None => LOG.warn("File " + fileName + " not moved as no pattern match found")
           }
       }
    }

    def run() = {
        val baseDir = new File(baseDirName)
        val subDirs = getDistinctPatternMatches(baseDir)
        makeDirectories(baseDir, subDirs)
        moveFiles(baseDir)
    }
}

object DirectoryMaker {

    def main(args: Array[String]):Unit = {
        if(args.length != 2) {
            return usage()
        }
        val baseDirName = args(0)
        val pattern = args(1)
        val maker = new DirectoryMaker(baseDirName,pattern)
        maker.run()
    }

    def usage() {
        println("Usage:")
        println("\tjava -jar directory-maker-1.0-jar-with-dependencies.jar <base-directory-path> '<pattern>'")
    }
}
