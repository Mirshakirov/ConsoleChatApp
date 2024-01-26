package chat.app;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class SignUp {
    private String email;
    private String password;
    private static final Map<String, List<String>> users = new HashMap<>(2);
    private final List<List<String>> messages = new ArrayList<>(4);
    Scanner scanner = new Scanner(System.in);
    public boolean doesAccountExist(String email, String password){
        if(users.containsKey(email))
            return users.get(email).get(0).equals(password);
        return false;
    }
    public boolean doesAccountExist(String email){
        return users.containsKey(email);
    }

    public Map.Entry<String, List<String>> findAccount (String accountEmail, String accountPassword){
        for(Map.Entry<String, List<String>> entry : users.entrySet()){
            if(entry.getKey().equals(accountEmail) && entry.getValue().get(0).equals(accountPassword)){
                return entry;
            }
        }
        return null;
    }

    public void loadToMap() throws IOException {
        users.clear();
        File file = new File("src/chat/app/users/user_objects.txt");
        String key;
        try(FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader)){
            while(bufferedReader.read() != -1){
                key = bufferedReader.readLine();
                ArrayList<String> arrayList = new ArrayList<>();
                arrayList.clear();
                arrayList.add(bufferedReader.readLine());
                arrayList.add(bufferedReader.readLine());
                users.put(key, arrayList);

            }
        }
        // System.out.println(users.entrySet());
    }

    public void saveMessage(String senderEmail, String receiveEmail, String text, LocalDateTime localDateTime){
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("d MMM uuuu, HH:mm");
        String date = localDateTime.format(dateTimeFormatter);
        messages.add(new ArrayList<>(List.of(senderEmail, receiveEmail, text, date)));
    }
    
    public void saveMessageToFile(){
        File file = new File("src/chat/app/users/messages.txt");
        messages.forEach((list)->{
            try(FileWriter fileWriter = new FileWriter(file, true)){
                fileWriter.write(" " + list.get(0) + "\n" + list.get(1) + "\n" + list.get(2) + "\n" + list.get(3) + "\n");
            } catch (IOException e) {
                System.out.println("There is a problem in save messageToFile method!");
            }
        });
    }

    public void addMessagesToMap(){
        File file = new File("src/chat/app/users/messages.txt");
        try(FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader)){
            while(bufferedReader.read() != -1) {
                List<String> list = new ArrayList<>(4);
                list.add(bufferedReader.readLine());
                list.add(bufferedReader.readLine());
                list.add(bufferedReader.readLine());
                list.add(bufferedReader.readLine());
                messages.add(list);
            }

        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public List<List<String>> findMessage(String receiverEmail){
        List<List<String>> receivedEmails = new ArrayList<>();
        messages.forEach((list)-> {
            if (receiverEmail.equals(list.get(1))) {
                receivedEmails.add(list);
            }
        });
        return receivedEmails;
    }


}
