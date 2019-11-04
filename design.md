# dblp-explorer

## AUTHOR
### Keith Dinh
### COSC 4353
### PROFESSOR AMIN ALIPOUR
### ASSIGNMENT 5

## GOAL
### Citation Network Analysis
### Scientific papers cite other relevant articles. Junior scientists usually struggle to find the most
### relevant papers. In this assignment, you will help (for now, only the computer scientists).
### Your program takes a (large) JSON file that contains the information about the papers, a
### keyword and an integer n. It first searches the articles with titles containing the keyword, then it
### finds the papers that cited the papers in this step. We call them tier-1 papers. For tire-2 papers,
### we find the papers that were cited by tire-1 papers. More generally, tier-K papers are the
### papers that are cited in tier-(K-1) papers.
### The output should be information of the papers all tiers up to level n ranked in order of
### importance. You decide how to define importance.

## PROPOSED DESIGN
### Ask users for the search keyword and the number of desired tier.
### Let users select a test case/text file
### Read the text file under the form of JSON format and process the data
### Output the data containing the keyword
### Output the data cited the previous data
### Calculate the Memory and time consumed
### Show a complete message

## RESTRICTION
### Only process 50,000 papers each time
### Throw Out of Memory if the file is too large

## TESTING
### There are 6 different testcases to target the bugs in the program
### Users will be asked to choose one of the test cases to process

## DEPENDENCIES
### JSON-Simple by fangyidong
#### Github URL: https://github.com/fangyidong/json-simple