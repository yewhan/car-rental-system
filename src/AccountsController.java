import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AccountsController {

    private static final String $fileLoginPath = "resources\\admin.txt";
    private static final String $fileCustomerPath = "resources\\customer.txt";
    private static List<StaffAccounts> staffList;
    private static List<CustomerAccounts> customersList;

    public static void loadStaff() {
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

    public static boolean checkLogin(String $username, String $password) {
        boolean found = false;

        for (StaffAccounts s : staffList) { //for every object in staff do below
            if ($username.equals(s.getUsername()) && $password.equals(s.getPass())) { //Check if input login matches database
                found = true;
                break;
            }
        }
        return found;
    }

    public static void loadCustomers() {
        customersList = new ArrayList<CustomerAccounts>();
        try {
            Scanner scanner = new Scanner(new File($fileCustomerPath)); //read from database of customers

            while (scanner.hasNext()) { //while there are more entries in database keep adding customer entries to list

                String[] $arr;
                $arr = scanner.nextLine().split("[:]"); //customer details are separated by : in txt file, .nextLine() used to ensure entry doesn't stop on whitespace

                CustomerAccounts customer = new CustomerAccounts($arr[0], $arr[1], $arr[2], $arr[3]);

                if (!($arr[4].equals("0"))) { //if a customer has an outstanding rented car, add that car to their entry
                    customer.setCarReg($arr[4]);
                }
                customersList.add(customer); //add current customer to list
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static boolean checkIfCustomerExists(String license) {
        for (CustomerAccounts c : customersList) {
            if (c.getLicenseNum().equalsIgnoreCase(license)) { //return true if input license matches current customer
                return true;
            }
        }
        return false;
    }

    public static String checkIfCustomerHasCar(String license) {
        for (CustomerAccounts c : customersList) {
            if (c.getLicenseNum().equalsIgnoreCase(license)) { //ensure correct customer is being checked
                if (c.getCarReg() != null) { //if current customer has an outstanding rented car, return car's reg
                    return c.getCarReg();
                }
            }
        }
        return null;
    }

    public static String checkWhoHasCar(String carReg) {
        for (CustomerAccounts c : customersList) {
            if (c.getCarReg() != null) {
                if (c.getCarReg().equalsIgnoreCase(carReg)) {
                    return String.format("%s %s", c.getfName(), c.getlName());
                }
            }
        }
        return null;
    }

    public static void addCustomer(String name, String address, String license) {
        String fName;
        String lName;
        String[] fullName;

        fullName = name.split("\\s+");
        fName = fullName[0];
        lName = fullName[fullName.length - 1];

        CustomerAccounts customer = new CustomerAccounts(fName, lName, address, license);
        customersList.add(customer);
    }

    public static boolean editCustomer(String name, String address, String license) {
        String fName;
        String lName;
        String[] fullName;

        fullName = name.trim().split("\\s+"); //split up name on whitespaces
        fName = fullName[0]; //set first name
        lName = fullName[fullName.length - 1]; //set last name

        for (CustomerAccounts c : customersList) {
            if (c.getLicenseNum().equalsIgnoreCase(license)) { //when correct customer is found, update name and address
                c.setfName(fName);
                c.setlName(lName);
                c.setAddress(address);
                return true;
            }
        }
        return false;
    }

    public static void saveCustomer() {
        try {
            FileWriter fw = new FileWriter(new File($fileCustomerPath));
            BufferedWriter bw = new BufferedWriter(fw);
            for (CustomerAccounts c : customersList) {
                String $temp = String.format("%s:%s:%s:%s:", c.getfName(), c.getlName(), c.getAddress(),
                        c.getLicenseNum());
                if (c.getCarReg() == null) {
                    $temp += "0\n";
                } else {
                    $temp += String.format("%s\n", c.getCarReg());
                }
                bw.write($temp);
            }
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void addCarToCustomer(String license, String carReg) {
        for (CustomerAccounts c : customersList) {
            if (c.getLicenseNum().equalsIgnoreCase(license)) {
                c.setCarReg(carReg);
            }
        }
    }

    public static void removeCarFromCustomer(String license, String carReg) {
        for (CustomerAccounts c : customersList) {
            if (c.getLicenseNum().equalsIgnoreCase(license) && c.getCarReg().equalsIgnoreCase(carReg)) { //if current customer matches input license and car reg, remove car reg from profile
                c.setCarReg(null);
            }
        }
    }

    public static void clearCustomerList() {
        customersList = new ArrayList<CustomerAccounts>(); //instantiate new customerList to empty it
    }

    public static String[] getCustomerName(String license) {
        String[] $details = new String[2];
        for (CustomerAccounts c : customersList) {
            if (c.getLicenseNum().equalsIgnoreCase(license)) {
                $details[0] = c.getfName();
                $details[1] = c.getlName();
                return $details;
            }
        }
        return null;
    }
}