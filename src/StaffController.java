import javax.crypto.spec.PBEKeySpec;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.KeySpec;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class StaffController {
    private final String $fileLoginPath = "resources\\admin.txt";
    public List<StaffAccounts> staffList;

    private static final String salt = "1KP2NOZT[eUfJ4OzjzR0XHfhd>XyewhansCb}UUVsyM]1b6geIyeEL<2";

    public void loadStaff() {
        staffList = new ArrayList<StaffAccounts>(); //Store list of accounts for easy access to check against when logging in
        try {

            Scanner scanner = new Scanner(new File($fileLoginPath));
            scanner.useDelimiter("[:\n]"); //Regex to break up username/password into new lines upon finding colon

            while (scanner.hasNext()) { //While there are lines to read in admin.txt do below

                StaffAccounts staff = new StaffAccounts(scanner.next().trim(), scanner.next().trim()); //Create new object for each account
                staffList.add(staff); //Add account objects to array list
            }
            scanner.close();
        } catch (IOException f) {
            f.printStackTrace();
        }
    }

    public boolean checkLogin(String $username, String $password) {
        boolean found = false;

        for (StaffAccounts s : staffList) { //for every object in staff do below
            if ($username.equals(s.getUsername()) && $password.equals(s.getPass())) { //Check if input login matches database
                found = true;
                break;
            }
        }
        return found;
    }

    public String hash(String pass) {
        StringBuilder sb = new StringBuilder();

        try {
            MessageDigest sha512 = MessageDigest.getInstance("SHA-512");
            sha512.update(salt.getBytes()); //update sha512 algorithm with created salt
            byte[] bytes = sha512.digest(pass.getBytes());

            for (byte b : bytes) {
                sb.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
            }

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        System.out.println(sb);
        return sb.toString();
    }
}
