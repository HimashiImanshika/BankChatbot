Bank Chatbot System 

📌 Project Overview

The Bank Chatbot System is a Java-based conversational assistant developed using Visual Studio Code. The chatbot communicates with users through text-based natural language and provides banking-related information such as account types, services, and general assistance.

The system uses a three-tier architecture consisting of:

Natural Language Interface
Inference Engine
Knowledge Base (HashMap and Text Files)

The chatbot also includes a self-learning feature where it can learn new responses from user interaction.

🧠 Main Features

✅ Natural Language Interface

Text-based chatbot interface
User can ask questions in normal English
Bot responds with appropriate answers

✅ Static Knowledge Base (HashMap)

Stores predefined questions and answers
Fast and efficient response retrieval

Example:

User: Hello
Bot: Hello! nice to meet you!

✅ Dynamic Knowledge Base (Text Files)

Uses text files as database

Files used:
courses.txt
branches.txt

Bot reads information dynamically from these files.

Example:

User: What courses are available?
Bot:
Savings Account
Current Account
Fixed Deposit Account

✅ Inference Engine

Uses keyword matching with String.contains()
Identifies user intent
Maps question to correct answer

✅ Random Response Generator

Bot gives random responses for some questions.

Example:

User: How are you?
Bot: I'm fine
Bot: I am ok
Bot: Not bad dear

Implemented using Java Random class.

✅ Name Memory Feature

Bot remembers user's name.
Example:

User: What is your name?
Bot: I'm Robby and your name?
User: Himashi
User: bye
Bot: Good Bye Himashi

✅ Self-Learning Feature (Training the Bot)

If the bot doesn't know the answer, it asks the user and learns.

Example:

User: What is ATM?
Bot: You tell the answer please
User: Automated Teller Machine

Bot stores new knowledge using HashMap.

🏗 System Architecture

1. Natural Language Interface Layer

Handles user input and output

2. Inference Engine Layer

Processes questions
Matches keywords
Retrieves correct answers

3. Knowledge Base Layer

Static knowledge → HashMap
Dynamic knowledge → Text files
Learned knowledge → User input

💻 Technologies Used

Programming Language: Java
IDE: Visual Studio Code
JDK Version: JDK 8 or higher
Data Structure: HashMap
File Handling: Text Files
Version Control: GitHub

📂 Project Structure
BankChatbot/
│
├── BotV4.java
├── courses.txt
├── branches.txt
├── README.md

▶ How to Run the Project in Visual Studio Code

Step 1: Install Java JDK
Step 2: Install Visual Studio Code
Step 3: Install Java Extension Pack in VS Code
Step 4: Open Project Folder in VS Code
Step 5: Open BotV4.java
Step 6: Click Run button ▶
Step 7: Start chatting in Terminal

Example:
User: Hi
Bot: Hello!

🎯 Example Questions

Hi
Hello
How are you?
What courses are available?
What branches do you have?
What is your name?
Thank you
bye

🤖 Chatbot Capabilities
Feature	Available
Static Answers	✅
Dynamic Answers	✅
Keyword Matching	✅
Random Responses	✅
Name Memory	✅
Self Learning	✅

🎓 Academic Project Information

This chatbot was developed as part of a university assignment to demonstrate:
- Chatbot development
- Natural Language Processing concepts
- Inference Engine design
- Knowledge Base implementation
- Self-learning chatbot system

👩‍💻 Author

Name: Himashi Imanshika
Project: Bank Chatbot System
IDE: Visual Studio Code
Year: 2025

📜 License

This project is developed for educational purposes only.
