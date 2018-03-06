# GRAWIT Test Framework

## Goals:
- Build Test Cases without having programming skills
- Easy  to modify the code
- Reusable codes

## The tool:
- GUI/Java
- With Selenium Webdriver
- Structures are represented in threes
## Elements
- Base Element
- Element step
- Test Case
- Parameters
- Driver 
  ### Base Element <img src="https://raw.githubusercontent.com/dallaszkorben/java-hu.akoel.grawit/master/wiki/baseelement.png" width="32" height="32">
  - Types: Text field, Link, Checkbox ...
  - Identifier
  - Identification types: id/css ~~xpath~~
  - Timing
  ### Element step  <img src="https://raw.githubusercontent.com/dallaszkorben/java-hu.akoel.grawit/master/wiki/baseelement.png" width="32" height="32">
  - Base Element-You make operations always on Base Element
  - Operations-Depends on the type of the Base Element
  - Parameters-Depends on the type of the Base Element - Constant, Saved value ...
  ### Test Case <img src="https://raw.githubusercontent.com/dallaszkorben/java-hu.akoel.grawit/master/wiki/testcase.png" width="32" height="32">
  - Collections of the Element steps (in sequence)
  ### Parameters  <img src="https://raw.githubusercontent.com/dallaszkorben/java-hu.akoel.grawit/master/wiki/constant.png" width="32" height="32">
  - String
  - Constant
  - Saved value by the Base Element
  ### Driver
	- Now only the Windows Explorer 
	<img src="https://raw.githubusercontent.com/dallaszkorben/java-hu.akoel.grawit/master/wiki/explorer.png" width="16" height="16"> and Firefox <img src="https://raw.githubusercontent.com/dallaszkorben/java-hu.akoel.grawit/master/wiki/firefox.png" width="16" height="16"> is supported
	

## Data connections

![data_connections](https://raw.githubusercontent.com/dallaszkorben/java-hu.akoel.grawit/master/wiki/dataconnections.png)

## Features:
- Drag & Drop function works on the element trees
- Tabs for handle elements
  - Web driver Tab for define Firefox and Explorer
  - Constants Tab for define constants
  - Base elements Tab for define Web elements identified by ID or CSS
  - Atomic Steps Tab for assemble collections of operations on Base Elements
  - Test Cases Tab for assemble Test cases using Atomic Steps
  - Run Tab for run Test Cases
    -- Prints the equivalent java source code out the console.(The source code can be run from console as a normal application)
    - Play/Pause/Resume functions
    - On the Output section links appear to DataModel trees in case of Exceptions
 

