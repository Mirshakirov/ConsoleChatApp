package chat.app;

import java.io.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.*;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        Map.Entry<String, List<String>> currentAccount;

        Logger logger = Logger.getLogger("Registration");
        logger.setUseParentHandlers(false);
        Handler handler = new ConsoleHandler();
        SimpleFormatter simpleFormatter = new SimpleFormatter();
        handler.setFormatter(simpleFormatter);
        logger.addHandler(handler);

        Scanner scanner = new Scanner(System.in);
        System.out.print("1. Sign Up: \n2. Sign In: " + "\nChoose: ");
        int opt = Integer.parseInt(scanner.nextLine());

        boolean saved;
        switch (opt) {
            case 1:
                SignIn signIn = new SignIn();
                saved = signIn.addToMap(signIn);
                signIn.writeToFile();
                if (saved)
                    logger.log(Level.INFO, "Signed Up Successfully!");
                break;
            case 2:
                SignUp signUp = new SignUp();
                signUp.loadToMap();

                System.out.print("Enter your email: ");
                String email = scanner.nextLine();
                System.out.print("Enter your password: ");
                String password = scanner.nextLine();
                if (signUp.doesAccountExist(email, password)) {
                    currentAccount = signUp.findAccount(email, password);
                    System.out.println();
                    logger.log(Level.INFO, "Signed In Successfully\n");
                    Thread.sleep(50);

                    System.out.print("1.Sent a message \n2.Read messages\nChoose: ");
                    int messageOpt = Integer.parseInt(scanner.nextLine());

                    switch (messageOpt) {
                        case 1:
                            System.out.print("Enter email of who you want to send message: ");
                            String receiverEmail = scanner.nextLine();
                            if (signUp.doesAccountExist(receiverEmail)) {
                                System.out.print("Write your message: ");
                                String messageText = scanner.nextLine();

                                signUp.saveMessage(currentAccount.getKey(), receiverEmail, messageText, LocalDateTime.now());
                                signUp.saveMessageToFile();
                                logger.log(Level.INFO, "Message Sent");
                            } else {
                                System.err.println("Such account does not exist!");
                            }
                            break;

                        case 2:
                            signUp.addMessagesToMap();
                            List<List<String>> messages = signUp.findMessage(currentAccount.getKey());
                            for (List<String> message : messages) {
                                System.out.printf("Sender: %s%nMessage: %s%nTime: %s%n-------------------------------------------%n", message.get(0), message.get(2), message.get(3));
                            }
                            break;

                        default:
                            System.out.println("Choose options from above!");
                    }

                } else {
                    System.out.println("There is no such account!");
                }
                break;
            default:
                System.out.println("Enter one of the options above!");
                break;
        }
    }
}
