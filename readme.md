# JSON Difference Checker

To check the difference between two JSONs. The order of the records within the JSON file doesn't matter. Useful for REST API Output Validation and Web Service Validation 


# New Features!

-	Compares 2 JSON files. The JSON files maybe Sorted and Unsorted ones.
-	Refactored and modularized the code with ID fields as parameters so that it is easier to integrate into any project
-	Updated the Result structure for better understanding of the Output



### Tech

This project uses a number of open source projects to work properly:

* [Zjsonpatch](https://github.com/flipkart-incubator/zjsonpatch) - Awesome wrapper for Jackson Java Library
* [JSON](https://mvnrepository.com/artifact/org.json/json) - To Organize and sort JSON files

And of course this itself is open source on GitHub.

### Installation

Download the project and import in your favorite JAVA IDE [Tested in Eclipse] as a Maven project. Make sure you have actual.json and expected.json in the root directory for comparison, to run project as is.

### Example Output 

### Future Enhancements