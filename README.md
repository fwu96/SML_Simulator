# Simpletron

- This is the Version Control project for CISC 594 - Software Testing Prin & Techniques, Harrisburg University of Science and Technology

---

Assignment description: [ProjectDescription](/ProjectDescription.pdf)

This Simpletron Machine is implemented in Java 21 with maven to simplify jar build and application launch

To generate jar file only, execute command

`mvn build`

To launch the application, execute command

`mvn compile exec:java`

---

## Releases

### v1.0
Version 1.0 implementation supports all the operations mentioned in Fig 8.29 from the project description

- 1.0 release supports only user inputs
- Simple unit tests are added



### v2.0
Version 2.0 added feature to allow user select the way of the program loading either from user manual input or file input

- In the `resource` folder there is a simple file for loading addition operation to the program
- The prompt will show selection as

```text
This Simpletron program loads from two sources:
1) User Input
2) File Input
```
Once user choose `File Input`, the program will ask for file location. User can provide the path as

```text
src/main/resources/instructions/AddTwoNumbers.txt
```
- Once the program terminated execution, memory dump will be printed as

```text
REGISTERS:
accumulator                   +0009
instructionCounter               06
instructionRegister           +4300
operationCode                    43
operand                          00

MEMORY:
          0       1       2       3       4       5       6       7       8       9
  0   +1007   +1008   +2007   +3008   +2109   +1109   +4300   +0004   +0005   +0009
 10   +0000   +0000   +0000   +0000   +0000   +0000   +0000   +0000   +0000   +0000
 20   +0000   +0000   +0000   +0000   +0000   +0000   +0000   +0000   +0000   +0000
 30   +0000   +0000   +0000   +0000   +0000   +0000   +0000   +0000   +0000   +0000
 40   +0000   +0000   +0000   +0000   +0000   +0000   +0000   +0000   +0000   +0000
 50   +0000   +0000   +0000   +0000   +0000   +0000   +0000   +0000   +0000   +0000
 60   +0000   +0000   +0000   +0000   +0000   +0000   +0000   +0000   +0000   +0000
 70   +0000   +0000   +0000   +0000   +0000   +0000   +0000   +0000   +0000   +0000
 80   +0000   +0000   +0000   +0000   +0000   +0000   +0000   +0000   +0000   +0000
 90   +0000   +0000   +0000   +0000   +0000   +0000   +0000   +0000   +0000   +0000
```
