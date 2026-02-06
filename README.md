# Bank Chatbot System 

## 📌 Project Overview

The Bank Chatbot System is a Java-based conversational assistant developed using Visual Studio Code. The chatbot interacts with users in natural language and provides banking-related information such as account types, branches, and general assistance.

The system is designed using a three-tier architecture consisting of a Natural Language Interface, Inference Engine, and Knowledge Base. The chatbot can also learn new responses from user interaction, demonstrating basic artificial intelligence concepts.

---

## 🎯 Objectives

* To develop a chatbot that communicates with users using natural language.
* To provide banking-related information automatically.
* To implement an inference engine to match user questions with answers.
* To use a knowledge base and text files to store dynamic information.
* To implement a self-learning feature to train the chatbot.

---

## 🧠 Key Features

### 1. Natural Language Interface

* Text-based user interaction through console.
* Accepts user questions and displays responses.

### 2. Static Knowledge Base (HashMap)

* Uses Java HashMap to store predefined questions and answers.
* Provides instant responses to known questions.

Example:

```
User: Good morning
Chat Bot: Good morning!
```

---

### 3. Dynamic Knowledge Base (Text Files)

* Uses text files to store updatable banking information.
* Files used:

  * courses.txt
  * branches.txt

Example:

```
User: What courses are available?
Chat Bot:
Savings Account
Current Account
Fixed Deposit Account
```

---

### 4. Inference Engine

* Uses keyword matching with String.contains()
* Maps different question formats to correct answers.

Example:

```
User: What accounts do you have?
User: Tell me available accounts
Chat Bot gives correct response
```

---

### 5. Random Response Feature

* Bot generates random responses for certain questions.
* Implemented using Java Random class.

Example:

```
User: How are you?
Chat Bot: I'm fine
Chat Bot: I am ok
Chat Bot: Good
```

---

### 6. Remember User Name Feature

* Bot asks and remembers user name.
* Uses stored name during exit.

Example:

```
User: What is your name?
Chat Bot: I'm Robby and your name?
User: Himashi
User: bye
Chat Bot: Good Bye Himashi
```

---

### 7. Self-Learning Feature (Training the Bot)

* If bot does not know the answer, it asks user to teach it.
* Stores new question and answer in knowledge base.

Example:

```
User: What is interest rate?
Chat Bot: You tell the answer please
User: Interest rate is 10%
```

---

## 🏗 System Architecture

The system follows Three-Tier Architecture:

### 1. Natural Language Interface Layer

* Handles user input and output.

### 2. Inference Engine Layer

* Processes user questions.
* Matches keywords.
* Retrieves correct answers.

### 3. Knowledge Base Layer

* Static knowledge → HashMap
* Dynamic knowledge → Text files
* Learned knowledge → User input

---

## 💻 Technologies Used

* Programming Language: Java
* JDK Version: JDK 8 or higher
* IDE: Visual Studio Code (VS Code)
* Data Structures: HashMap
* File Handling: Text Files
* Version Control: GitHub

---

## 📂 Project Structure

```
BankChatbot/
│
├── BotV4.java
├── courses.txt
├── branches.txt
├── README.md
```

---

## ▶ How to Run the Project

Step 1: Install Java JDK

Step 2: Open project folder in Visual Studio Code

Step 3: Make sure these files exist:

* BotV4.java
* courses.txt
* branches.txt

Step 4: Compile and run BotV4.java

Step 5: Start chatting with the bot

Example:

```
User: Hello
Chat Bot: Hello!

User: What courses are available?
Chat Bot:
Savings Account
Current Account
Fixed Deposit Account
```

---

## 🤖 Chatbot Capabilities Summary

| Feature                    | Status |
| -------------------------- | ------ |
| Natural Language Interface | ✅      |
| Static Knowledge Base      | ✅      |
| Dynamic Knowledge Base     | ✅      |
| Inference Engine           | ✅      |
| Random Responses           | ✅      |
| Name Memory                | ✅      |
| Self Learning              | ✅      |

---

## 🎓 Academic Purpose

This project was developed as an academic assignment to demonstrate chatbot development using Java, natural language processing concepts, inference engines, knowledge bases, and self-learning functionality.

---

## 👩‍💻 Author

Name: Himashi Imanshika
Project: Bank Chatbot System
Version: 4.0
Developed using: Visual Studio Code
Year: 2026

---

## 📜 License

This project is for educational purposes only.
