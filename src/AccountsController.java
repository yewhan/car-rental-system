import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AccountsController {

    public static final String $fileLoginPath = "resources\\admin.txt";
    public static List<StaffAccounts> staff;
    //public static final String $fileCarPath = "resources\\cars.txt";
    //public static boolean found = false;

    public static void loadAccounts() {

        staff = new ArrayList<StaffAccounts>(); //Store list of accounts for easy access to check against when logging in
        try {

            Scanner scanner = new Scanner(new File($fileLoginPath));
            scanner.useDelimiter("[:\n]"); //Regex to break up username/password into new lines upon finding colon

            while (scanner.hasNext()) { //While there are lines to read in admin.txt do below

                StaffAccounts staffAccounts = new StaffAccounts(scanner.next().trim(), scanner.next().trim()); //Create new object for each account
                staff.add(staffAccounts); //Add account objects to array list
            }
            scanner.close();
        } catch (IOException f) {
            f.printStackTrace();
        }
    }

    public static boolean checkLogin(String $username, String $password) {
        boolean found = false;

        for (StaffAccounts s : staff) { //for every object in staff do below
            if ($username.equals(s.getUsername()) && $password.equals(s.getPass())) { //Check if input login matches database
                found = true;
                break;
            }
        }
        return found;
    }
}