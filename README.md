# directory-maker #

[![Build Status](https://travis-ci.org/rwalpole/directory-maker.svg?branch=master)](https://travis-ci.org/rwalpole/directory-maker)

The purpose of this utility tool is to split file system directories containing large numbers of files into new smaller sub-directories within the original directory which are named according to some pattern found within the file names. It will then move the files matching these patterns into the appropriate directory.

For example - given a directory that contains many files with a year in the file name and a regular expression to match the year it will create subdirectories for each distinct year found and then move the files into the appropriate subdirectory.