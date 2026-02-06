// Import required libraries
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import javax.speech.Central;
import javax.speech.synthesis.Synthesizer;
import javax.speech.synthesis.SynthesizerModeDesc;

public class ChatApp {

    private static JPanel chatPanel;
    private static HashMap<String, String> knowledge;
    private static ImageIcon userIcon;
    private static ImageIcon botIcon;
    private static ImageIcon botIconSad;
    private static ImageIcon botIconConfused;

    private static boolean trainingMode = false;
    private static String trainingQuestion = null;

    private static boolean awaitingUsername = false;
    private static String userName = "";

    private static HashMap<String, Integer> repeatTracker = new HashMap<>();

    public static void main(String[] args) {
        // Initialize the chatbot's basic knowledge base
        knowledge = new HashMap<>();
        knowledge.put("good morning", "Good morning");
        knowledge.put("i need help", "I'm always ready to help you. How can I help you? Please ask!");
        knowledge.put("where are your branches", "We have branches in Colombo, Kandy, Galle, and Jaffna. Which location are you interested in?");
        knowledge.put("what are the account types", "We have personal accounts, current accounts and foreign currency accounts. What do you prefer?");
        knowledge.put("do you have fixed deposits", "Yes, we have fixed deposit plans for three months and yearly renew basis plans. What do you prefer?");
        knowledge.put("what is your name", "I'm Econ from Econ Bank. I'm here to assist you.");
        knowledge.put("how can i open an account", "You can open an account by visiting the nearest branch or applying online through our website.");
        knowledge.put("how to apply for a loan", "You can apply for a loan online or visit the bank. Would you like to know the types of loans we offer?");
        knowledge.put("do you offer online banking", "Yes, we offer secure and easy-to-use online banking services. Would you like help registering?");
        knowledge.put("what are your working hours", "Our branches are open from 9:00 AM to 4:00 PM, Monday to Friday.");
        knowledge.put("where is your nearest branch", "Please share your location or city name, and I’ll help you find the nearest branch.");
        knowledge.put("how can i get my account balance", "You can check your balance via online banking, mobile app, or visit the nearest ATM.");
        knowledge.put("how to reset my password", "If you forgot your password, click on 'Forgot Password' on our login page and follow the steps.");
        knowledge.put("can i speak to a human", "Sure! Please call our 24/7 hotline at 011-200 3000 for further assistance.");
        knowledge.put("do you have mobile banking", "Yes, we do. You can download our mobile app from the Play Store or App Store.");
        knowledge.put("thank you", "You are welcome!");
       
        // Load and resize images used in chat
        userIcon = resizeIcon(new ImageIcon("user.png"), 150, 180);
        botIcon = resizeIcon(new ImageIcon("bot.png"), 250, 250);
        botIconSad = resizeIcon(new ImageIcon("angry.png"), 250, 250);
        botIconConfused = resizeIcon(new ImageIcon("sad.jpg"), 250, 250);

        // Initialize Text-to-Speech
        try {
            System.setProperty("freetts.voices", "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");
            Central.registerEngineCentral("com.sun.speech.freetts.jsapi.FreeTTSEngineCentral");
            synthesizer = Central.createSynthesizer(new SynthesizerModeDesc(Locale.US));
            synthesizer.allocate();
            synthesizer.resume();
        } catch (Exception e) {
            System.out.println("TTS initialization error: " + e.getMessage());
        }

        // Launch GUI in a separate thread
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Econ Bank ChatBot");
            frame.setSize(750, 600);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLocationRelativeTo(null);
            frame.setLayout(new BorderLayout());

            // Chat dispaly area
            chatPanel = new JPanel();
            chatPanel.setLayout(new BoxLayout(chatPanel, BoxLayout.Y_AXIS));
            chatPanel.setBackground(Color.lightGray);
            JScrollPane chatScroll = new JScrollPane(chatPanel);
            frame.add(chatScroll, BorderLayout.CENTER);
         
            // User input field and send button
            JPanel inputPanel = new JPanel(new BorderLayout());
            inputPanel.setBackground(Color.WHITE);
            JTextField inputField = new JTextField();
            JButton sendButton = new JButton("Send");
            inputPanel.add(inputField, BorderLayout.CENTER);
            inputPanel.add(sendButton, BorderLayout.EAST);
            frame.add(inputPanel, BorderLayout.SOUTH);

            // Handle send button click
            sendButton.addActionListener(e -> {
                String text = inputField.getText().trim();
                if (!text.isEmpty()) {
                    chatPanel.add(createMessageBubble(text, true, null));

                    String botReply = handleUserInput(text);
                    ImageIcon emotionIcon = getEmotionIcon(botReply);
                    chatPanel.add(createMessageBubble(botReply, false, emotionIcon));
                    speak(botReply);  // Text-to-speech

                    // Auto-scroll to latest message
                    chatPanel.revalidate();
                    chatPanel.repaint();

                    SwingUtilities.invokeLater(() -> {
                        JScrollBar vertical = chatScroll.getVerticalScrollBar();
                        vertical.setValue(vertical.getMaximum());
                    });

                    // Clear input field
                    inputField.setText("");
                }
            });

            // Pressing Enter sends message
            inputField.addActionListener(e -> sendButton.doClick());
            frame.setVisible(true);
        });

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
    try {
        if (synthesizer != null) {
            synthesizer.deallocate();
        }
    } catch (Exception e) {
        System.out.println("TTS shutdown error: " + e.getMessage());
    }
}));

    }

    // Handle user input and return bot response
    private static String handleUserInput(String input) {
    String lower = input.toLowerCase();

    // Detect repeat questions
    repeatTracker.put(lower, repeatTracker.getOrDefault(lower, 0) + 1);
    if (repeatTracker.get(lower) > 3) {
        return "Why do you keep asking the same thing? 😢";
    }

    // Store username after greeting
    if (awaitingUsername) {
        userName = input;
        awaitingUsername = false;
        return "Nice to meet you, " + userName + "! How can I assist you today?";
    }

    // Username Recognition
    if (lower.contains("hi") || lower.contains("hello")) {
        if (userName.isEmpty()) {
            awaitingUsername = true;
            return "Hello! What's your name? 😊";
        } else {
            return "Hi " + userName + "! How can I assist you today?";
        }
    }

    if (lower.contains("your name")) {
        awaitingUsername = true;
        return "I'm Econ and your name? :)";
    }

    // Exit handling
    if (lower.contains("exit") || lower.contains("bye")) {
        return "Ok. Good Bye My Friend " + (userName.isEmpty() ? "" : userName);
    }

    return getBotReply(input);
}

    //Dynamic Answers
    private static String getBotReply(String userMessage) {
        String lowerQuestion = userMessage.toLowerCase();

        // Account-based responses
        if (lowerQuestion.contains("personal")) return readFile("txt.DB/Personal.txt");
        if (lowerQuestion.contains("current")) return readFile("txt.DB/Current.txt");
        if (lowerQuestion.contains("foreign") || lowerQuestion.contains("foriegn")) return readFile("txt.DB/Foreign.txt");
        if (lowerQuestion.contains("fixed deposits") || lowerQuestion.contains("fixed deposit") || lowerQuestion.contains("fd")) 
        return readFile("txt.DB/FD.txt");
        if (lowerQuestion.contains("3 months") || lowerQuestion.contains("three months")) return readFile("txt.DB/Three.txt");
        if (lowerQuestion.contains("yearly")) return readFile("Yearly.txt");

        // Location-based responses
        if (lowerQuestion.contains("colombo")) return readFile("txt.DB/Colombo.txt");
        if (lowerQuestion.contains("galle")) return readFile("txt.DB/Galle.txt");
        if (lowerQuestion.contains("kandy")) return readFile("txt.DB/Kandy.txt");
        if (lowerQuestion.contains("jaffna")) return readFile("txt.DB/Jaffna.txt");
        

        // Training Bot
        if (trainingMode) {
            if (userMessage.equalsIgnoreCase("cancel")) {
                trainingMode = false;
                trainingQuestion = null;
                return "Training cancelled.";
            }
            knowledge.put(trainingQuestion.toLowerCase(), userMessage);
            trainingMode = false;
            trainingQuestion = null;
            return "Thanks for teaching me! I've learned something new.";
        }

        for (String key : knowledge.keySet()) {
            if (lowerQuestion.contains(key.toLowerCase())) return knowledge.get(key);
        }

        // Random smalltalk responses
        if (lowerQuestion.contains("hi") || lowerQuestion.contains("hello")) {
            String[] responses = {"Hi!", "Hello!", "Hello dear!", "Hey, nice to see you!"};
            return responses[new Random().nextInt(responses.length)];
        }
        if (lowerQuestion.contains("how are you")) {
            String[] responses = {"I'm fine.", "I am ok.", "Not bad dear.", "Good!", "Alright."};
            return responses[new Random().nextInt(responses.length)];
        }

        // Ask user to teach unknown question 
        trainingMode = true;
        trainingQuestion = userMessage;
        return "Hmm, I’m not sure about that 😕 Can you teach me the correct answer or type 'cancel' to skip?";
    }

    // Select bot image based on message/emotion
    private static ImageIcon getEmotionIcon(String message) {
        if (message.contains("😞") || message.contains("😢") || message.toLowerCase().contains("why do you keep asking"))
            return botIconSad;
        if (message.contains("😕")) return botIconConfused;
        return botIcon;
    }
    
    // Read content from file and return as string
    private static String readFile(String filename) {
        try {
            File file = new File(filename);
            Scanner scanner = new Scanner(file);
            StringBuilder builder = new StringBuilder();
            while (scanner.hasNextLine()) builder.append(scanner.nextLine()).append("\n");
            scanner.close();
            return builder.toString();
        } catch (Exception e) {
            return filename + " not found.";
        }
    }

    // Create styled message bubble (with timestamp and icon)
    private static JPanel createMessageBubble(String message, boolean isUser, ImageIcon customBotIcon) {
    JPanel rowPanel = new JPanel(new FlowLayout(isUser ? FlowLayout.RIGHT : FlowLayout.LEFT));
    rowPanel.setOpaque(false);

    JLabel iconLabel = new JLabel(isUser ? userIcon : (customBotIcon != null ? customBotIcon : botIcon));

    // Get current time
    String time = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));

    // Message and time
    String formattedMessage = "<html><p style='width: 200px;'>" + message + "<br><span style='font-size:10px;color:gray;'>" + time + "</span></p></html>";
    JLabel messageLabel = new JLabel(formattedMessage);
    messageLabel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
    messageLabel.setOpaque(false);

    // Create chat bubble with rounded corners
    JPanel bubble = new JPanel(new BorderLayout()) {
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(isUser ? new Color(220, 248, 198) : new Color(240, 240, 240));
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
            g2.dispose();
            super.paintComponent(g);
        }
    };
    bubble.setOpaque(false);
    bubble.setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 20));
    bubble.add(messageLabel, BorderLayout.CENTER);

    // Add icon and bubble to panel
    if (isUser) {
        rowPanel.add(bubble);
        rowPanel.add(iconLabel);
    } else {
        rowPanel.add(iconLabel);
        rowPanel.add(bubble);
    }

    return rowPanel;
}

    // Resize image icon
    private static ImageIcon resizeIcon(ImageIcon icon, int width, int height) {
        Image img = icon.getImage();
        Image resized = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(resized);
    }

    private static Synthesizer synthesizer;

    // Use Text-to-Speech to speak a message
private static void speak(String text) {
    new SwingWorker<Void, Void>() {
        @Override
        protected Void doInBackground() {
            try {
                if (synthesizer != null) {
                    synthesizer.speakPlainText(text, null);
                    synthesizer.waitEngineState(Synthesizer.QUEUE_EMPTY);
                }
            } catch (Exception e) {
                System.out.println("TTS Error during speech: " + e.getMessage());
            }
            return null;
        }
    }.execute();
}

}
