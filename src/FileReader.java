import java.io.*;
import java.util.Scanner;

public class FileReader {

    public static final String $fileLoginPath = "resources\\login.txt";
    public static final String $fileCarPath = "resources\\cars.txt";
    public static boolean found = false;

    public static void loadLoginInfo(String username, String password) {

        String $tempUsername;
        String $tempPassword;
        try {

            Scanner scanner = new Scanner(new File($fileLoginPath));
            scanner.useDelimiter("[:\n]"); //Regex to break up username/password into new lines upon finding colon

            while (scanner.hasNext() && !found) {

                $tempUsername = scanner.next();
                $tempPassword = scanner.next();

                if ($tempUsername.trim().equals(username.trim())) && $tempPassword.trim().equals(password.trim()) {
                    found = true;
                }
            }
            scanner.close();
        }
        catch (IOException f) {
            f.printStackTrace();
        }
    }

    public static void loadCarInfo() {
        
    }
}
