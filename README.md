# WordCounterCLI

Word Count Challenge

## Requirements

* Linux O/S
* Java 17
* git (if you want to download it)

## Assumptions

* Case-insensitive
* The printable characters to be processed are consistent with single byte UTF-8 encoding up to code point U+007F
* A basic definition of a word is used, text delimited by white space with commas and periods ignored
* No special handling is required for non-alphanumeric characters, i.e. extraction of words from structures 
such as <Word> is not required **(*)**

__**(*) The software allows for simple extension by way of custom word extractors for more complex cases.  This is left as an exercise for the reader**__

## Build

Get the code

`git clone https://github.com/codesqueak/WordCounterCLI.git`

Move to project directory

`cd WordCounterCLI`

and build

`./gradlew clean build test`

## Usage

The build step will generate a file `WordCounterCLI-1.0-SNAPSHOT.jar` in the `build/libs`  sub-directory

To run, use the command:

`java -jar WordCounterCLI-1.0-SNAPSHOT.jar  <file>`

## Example - no file specified

![No file specified](/docs/screen1.png)

## Example - test file from test resources

![With test file](/docs/screen2.png)



