# directory-maker #

[![Build Status](https://travis-ci.org/rwalpole/directory-maker.svg?branch=master)](https://travis-ci.org/rwalpole/directory-maker)

The purpose of this utility tool is to split file system directories containing large numbers of files into new smaller sub-directories within the original directory which are named according to some pattern found within the file names. It will then move the files matching these patterns into the appropriate directory.

For example - given a directory that contains many files with a year in the file name and a regular expression to match the year it will create subdirectories for each distinct year found and then move the files into the appropriate subdirectory.

You will need [Apache Maven](https://maven.apache.org/) and the [Java SE 8 JDK](http://www.oracle.com/technetwork/java/javase/downloads/index-jsp-138363.html) to build this tool. 

To build run `mvn clean compile assembly:single` from the root of this project. This will create an executable jar file in the target directory which can then be used from the command line as follows:
 
    java -jar target/directory-maker-1.0-SNAPSHOT-jar-with-dependencies.jar

The required command line parameters are as follows:

- **base directory path** - the directory containing the files that you want to split into sub-directories.   
- **pattern** - the regular expression pattern to apply to the file names in the base directory. The pattern matches will be used to create the new sub-directories and the matching files will be moved into these new directories.

For example given a regular expression `^[0-9]{4}_[0-9]{1,4}[s|a]` and a file named `1998_53a_89956_19910328.txt` the file will moved into a new directory called `1998_53a`. 