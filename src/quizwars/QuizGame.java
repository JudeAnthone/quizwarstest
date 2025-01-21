package quizwars;
import java.util.*;

public class QuizGame {
// ANSI color codes
    private static final String RESET = "\u001B[0m";
    private static final String RED = "\u001B[31m";
    private static final String GREEN = "\u001B[32m";
    private static final String YELLOW = "\u001B[33m";
    private static final String BLUE = "\u001B[34m";
    private static final String PURPLE = "\u001B[35m";
    public static final String CYAN = "\u001B[36m"; 

    public static void main(String[] args) {
        Scanner userInput = new Scanner(System.in);
        boolean playAgain = true;

        // Display animated welcome message
        displayAsciiWelcome();

        while (playAgain) {
            int score = 0;
            int totalQuestions = 0;

// Display difficulty options with proper centering
            System.out.println("\n" + CYAN + "╔════════════════════════════════╗");
            System.out.println("║     Select Difficulty Level    ║");
            System.out.println("╠════════════════════════════════╣");
            System.out.println("║  1. Easy                       ║");
            System.out.println("║  2. Medium                     ║");
            System.out.println("║  3. Hard                       ║");
            System.out.println("╚════════════════════════════════╝" + RESET);

// Error handling for user input
            String difficulty;
            while (true) {
                System.out.print(CYAN + "Enter your choice (1-3): " + RESET);
                String choice = userInput.nextLine().trim();
                switch (choice) {
                    case "1": difficulty = "easy"; break;
                    case "2": difficulty = "medium"; break;
                    case "3": difficulty = "hard"; break;
                    default:
                        System.out.println(RED + "Invalid choice! Please select 1, 2, or 3." + RESET);
                        continue;
                }
                break;
            }

// Load and validate questions 
            List<Question> questions = QuestionLoader.loadQuestion(difficulty);
            if (questions == null || questions.isEmpty()) {
                System.out.println(RED + "⚠ No questions available for the selected difficulty. Please try again." + RESET);
                continue;
            }

// Game start animation
            displayGameStart();
            
            totalQuestions = questions.size();

// Question display and handling
            for (int i = 0; i < questions.size(); i++) {
                Question q = questions.get(i);
                
                Collections.shuffle(questions);
                
                
                // Progress bar
                displayProgressBar(i + 1, totalQuestions);

                // Question display
                System.out.println("\n" + PURPLE + "╔══════════════════════════════════════════╗");
                System.out.printf("║ Question %d of %d                         ║\n", (i + 1), totalQuestions);
                System.out.println("╚══════════════════════════════════════════╝" + RESET);
                
                System.out.println(CYAN + "┌────────────────────────────────────────────────────────┐");
                System.out.printf("│ %-54s │\n", q.getQuestionText());
                System.out.println("├────────────────────────────────────────────────────────┤");
                System.out.printf("│ %-25s %-25s    │\n", "A: " + q.getOptionA(), "B: " + q.getOptionB());
                System.out.printf("│ %-25s %-25s    │\n", "C: " + q.getOptionC(), "D: " + q.getOptionD());
                System.out.println("└────────────────────────────────────────────────────────┘" + RESET);


 // Input and validate user answer
                String answer;
                while (true) {
                    System.out.print(BLUE + "Your Answer (A/B/C/D): " + RESET);
                    answer = userInput.nextLine().trim().toUpperCase();
                    if (answer.matches("[ABCD]")) break;
                    System.out.println(RED + "Invalid input! Please enter A, B, C, or D." + RESET);
                }

// Show result
                if (answer.equals(q.getCorrectAnswer())) {
                    System.out.println(GREEN + "✓ CORRECT! Well done!" + RESET);
                    score++;
                } else {
                    System.out.println(RED + "✗ Incorrect. The correct answer was: " + q.getCorrectAnswer() + RESET);
                }

// Display current score
                displayScore(score, i + 1);
                
// Delay for 1 second
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }

            
// Final score with percentage
            double percentage = (score * 100.0) / totalQuestions;
            System.out.println("\n" + PURPLE + "╔══════════════════════════════════════════╗");
            System.out.println("║             FINAL RESULTS                ║");
            System.out.println("╠══════════════════════════════════════════╣");
            System.out.printf("║ Score: %d/%d                              ║\n", score, totalQuestions);
            System.out.printf("║ Percentage: %.1f%%                        ║\n", percentage);
            System.out.println("╚══════════════════════════════════════════╝" + RESET);
            
            
// play again prompt
            while (true) {
            	
            	System.out.println("\n" + YELLOW + "Would you like to play again? (Y/N): " + RESET);
                String userResponse = userInput.nextLine().trim().toLowerCase();
                
                if(userResponse.equals("y")) {
                	playAgain = true;
                	break;
                	
                } else if (userResponse.equals("n")) {
                	displayGoodbye();
                	displayAsciiWelcome();
                	playAgain = false;
                	break;
                	
                } else {
                	System.out.println( RED + "ERROR: Invalid Input. Y for yes N for no. " + RESET);
                	 
                }
            	
            }
   
        }

        userInput.close();
    }
    
    
    

// Display animated welcome message
    private static void displayAsciiWelcome() {
        String[] welcomeFrames = {
            "\r\n"
            + "                ██╗    ██╗███████╗██╗      ██████╗ ██████╗ ███╗   ███╗███████╗    ████████╗ ██████╗      ██████╗ ██╗   ██╗██╗███████╗██╗    ██╗ █████╗ ██████╗ ███████╗██╗\r\n"
            + "                ██║    ██║██╔════╝██║     ██╔════╝██╔═══██╗████╗ ████║██╔════╝    ╚══██╔══╝██╔═══██╗    ██╔═══██╗██║   ██║██║╚══███╔╝██║    ██║██╔══██╗██╔══██╗██╔════╝██║\r\n"
            + "                ██║ █╗ ██║█████╗  ██║     ██║     ██║   ██║██╔████╔██║█████╗         ██║   ██║   ██║    ██║   ██║██║   ██║██║  ███╔╝ ██║ █╗ ██║███████║██████╔╝███████╗██║\r\n"
            + "                ██║███╗██║██╔══╝  ██║     ██║     ██║   ██║██║╚██╔╝██║██╔══╝         ██║   ██║   ██║    ██║▄▄ ██║██║   ██║██║ ███╔╝  ██║███╗██║██╔══██║██╔══██╗╚════██║╚═╝\r\n"
            + "                ╚███╔███╔╝███████╗███████╗╚██████╗╚██████╔╝██║ ╚═╝ ██║███████╗       ██║   ╚██████╔╝    ╚██████╔╝╚██████╔╝██║███████╗╚███╔███╔╝██║  ██║██║  ██║███████║██╗\r\n"
            + "                 ╚══╝╚══╝ ╚══════╝╚══════╝ ╚═════╝ ╚═════╝ ╚═╝     ╚═╝╚══════╝       ╚═╝    ╚═════╝      ╚══▀▀═╝  ╚═════╝ ╚═╝╚══════╝ ╚══╝╚══╝ ╚═╝  ╚═╝╚═╝  ╚═╝╚══════╝╚═╝\r\n"
        };

        for (int i = 0; i < 1; i++) {
            for (String frame : welcomeFrames) {
                System.out.print("\r" + PURPLE + frame + RESET);
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
        System.out.println("\n");
    }
    
    
    
    
// count down
    private static void displayGameStart() {
        System.out.println("\n" + GREEN + "3...");
        sleep(500);
        System.out.println("2...");
        sleep(500);
        System.out.println("1...");
        sleep(500);
        System.out.println("GO!" + RESET + "\n");
        sleep(500);
    }
    
    
    
// progress bar
    private static void displayProgressBar(int current, int total) {
        int barWidth = 30;
        int progress = (int) ((double) current / total * barWidth);
        System.out.print("\r" + BLUE + "Progress: [");
        for (int i = 0; i < barWidth; i++) {
            if (i < progress) System.out.print("=");
            else System.out.print(" ");
        }
        System.out.print("] " + current + "/" + total + " " + RESET);
    }
    
    
    
// score
    private static void displayScore(int score, int questionNumber) {
        System.out.println("Score after Question " + questionNumber + ": " + GREEN + score + RESET);
    }

    
//good bye message
    private static void displayGoodbye() {
        System.out.println(CYAN + "\nThank you for playing! Have a great day ahead!" + RESET);
    }
    

    
//delays
     private static void sleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
