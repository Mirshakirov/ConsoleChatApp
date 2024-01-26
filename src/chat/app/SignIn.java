package chat.app;

import java.io.*;
import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignIn {
    private String email;
    private String password;
    private LocalDateTime localDateTime;
    private final List<String> passwordDate = new ArrayList<>(2);
    private static Map<String, List<String>> users = new HashMap<>(2);
    Scanner scanner = new Scanner(System.in);


    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public LocalDateTime getLocalDateTime() { return localDateTime; }

    public SignIn() {
        checkEmailValidity();
        checkPasswordValidity();
    }

    public void checkEmailValidity(){
        System.out.print("Enter email: ");
        String checkEmail = scanner.nextLine();
        Pattern pattern = Pattern.compile("^[a-z_&.0-9]+@[a-z]{4,10}\\.[a-z]{2,7}$");
        Matcher matcher = pattern.matcher(checkEmail);
        if(!matcher.find()){
            System.out.println("Not valid email, Enter again: ");
            checkEmailValidity();
        }else {
            this.email = checkEmail;
        }
    }
    public void checkPasswordValidity(){
        System.out.print("Enter strong password: ");
        String checkPassword = scanner.nextLine();
        Pattern pattern = Pattern.compile("(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9]+)(?=.*[!@#$%^&*_])+");
        Matcher matcher = pattern.matcher(checkPassword);
        if (!matcher.find()){
            System.out.println("Weak password, please enter strong password: ");
            checkPasswordValidity();
        }else{
            this.password = checkPassword;
            localDateTime = LocalDateTime.now();
        }
    }


    public void writeToFile(){
        File file = new File("src/chat/app/users/user_objects.txt");
        users.forEach((k, v) ->{
            try (FileWriter fileWriter = new FileWriter(file, true)) {
                fileWriter.write(" " + k + "\n" + v.get(0) + "\n" + v.get(1) + "\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public boolean addToMap(SignIn object){
        if(users.containsKey(object.email)){
            System.err.println("Such account already exists");
            return false;
        }else {
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd MMM uuuu, HH:mm");
            String date = object.getLocalDateTime().format(dateTimeFormatter);
            passwordDate.add(object.getPassword());
            passwordDate.add(date);
            users.put(object.getEmail(), passwordDate);
            return true;
        }
    }










}
