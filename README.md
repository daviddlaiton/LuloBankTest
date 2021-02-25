# Lulo Bank: Code Challenge: Transaction Authorizer

The objective of this project was to create an application that authorizes a transaction for a specific account following a set of predefined rules. In order to complete the challenges some assumptions were made:

1. If inn the **stdin**  a empty line or a invalid JSON is entered it will be omitted.
2. When a "account-not-initialized" violations is thrown it will include the account information provided like this:  *{"account" : {"id":2,"active-card":false,"available-limit":0,"violations":["account-not-initialized]}*
3. Always the output will include the account id be like this:  *{"account" : {"id":2,"active-card":false,"available-limit":0,"violations":["account-not-initialized]}*

## Getting Started
To get a copy of these project install git bash, open it from the command line and use
```bash
$ git clone:  https://github.com/daviddlaiton/LuloBankTest.git
```
In *LuloBankCodeChallenge* folder will be the project available. 


The code will be in srs/main package. There are 4 classes:
1. **Console Input**: Allows Gson parse the *stdin* JSON into the proper Object.
2. **Main Class**: Manages the main logic of the project, including the business rules.
3. **Account**: Manages the Account Object logic
4. **Transaction**: Manages the Transaction Object Logic.
## Run it
To run the project you can do two things:

1. Run a JAR file:

To do this navigate, in the command line, to the main folder of the project and run the following command:
```bash
$ java -jar LuloBankCodeChallenge.jar
```
2. Run it from eclipse

Open the project in Eclipse. Then go to src/main and right-click over MainClass.java and Run As -> Java Application.

Once is running in the command line the project will wait for every line and process it.

### Prerequisites

You just need the Java 1.8 SDK .

If you wanna run it from Eclipse you have to installed.

## Test it

There are 5 available. This files are available in src/test. In order to test it in Eclipse do a right-click over the project and Run As -> JUnit Test.

In the src/test package are 2 files. One for *Account* testing and one for *Transaction* testing. The 5 available test are build in order to follow the business rules.

## Built With

* [Java](https://www.java.com/es/)
* [Gson](https://github.com/google/gson) - Gson is a Java library that can be used to convert Java Objects into their JSON representation.

## Author

* **Andrés David Laiton** - [daviddlaiton](https://github.com/daviddlaiton)
