import javax.swing.*;
import java.io.*;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class StockController {

    private static final String $fileCarsPath = "resources\\cars.txt";
    private static List<Stock> carList;

    public static void loadCars() {

        carList = new ArrayList<Stock>();
        try {

            Scanner scanner = new Scanner(new File($fileCarsPath));
            //scanner.useDelimiter("[:\n]");

            while (scanner.hasNext()) {

                String[] $arr;
                $arr = scanner.nextLine().split("[:]"); //experiment using .split on : with regex instead of using delimiter

                Stock car = new Stock(Boolean.parseBoolean($arr[0].trim()), $arr[1], $arr[2].trim(),
                        Float.parseFloat($arr[3].trim()));

                if ($arr[0].equals("false")) {

                    LocalDate $date = LocalDate.parse($arr[4].trim());

                    car.setAvailableDate($date);
                }
                carList.add(car);

            }
            scanner.close();

            //TODO: REMOVE BELOW WHEN DONE
            for (Stock c : carList) { // Check to see if every object is being read in/ displayed correctly
                if (c.getAvailable()) {
                    System.out.println("available: " + c.getAvailable() + " | model: " + c.getModel()
                            + " | registration: " + c.getRegistration() + " | price/ day: £" + c.getPrice());
                } else {
                    System.out.println("available: " + c.getAvailable() + " | model: " + c.getModel()
                            + " | registration: " + c.getRegistration() + " | price/ day: £" + c.getPrice()
                            + " | next available: " + c.getAvailableDate());
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void populateStockGUI(JList<String> lstStock, boolean accountType) {

        DefaultListModel<String> listModel = new DefaultListModel<String>();
        String $temp = null;
        for (Stock c : carList) {

            if (accountType) { //if the user calling the method is an admin, display information only admins should have access to
                $temp = String.format("available: %b | model: %s | registration: %s | price/ day: £%.2f",
                        c.getAvailable(), c.getModel(), c.getRegistration(), c.getPrice());
                if (!c.getAvailable()) { //if the car is not available, also display when it will be and what customer has rented it
                    $temp += String.format(" | next available: %tF | rented by: %s", c.getAvailableDate(),
                            AccountsController.checkWhoHasCar(c.getRegistration()));
                }
            } else if (!accountType) { //check to see if user is customer
                if (c.getAvailable()) { //only display available cars and their prices to customer
                    $temp = String.format("model: %s | registration: %s | price per day: £%.2f",
                            c.getModel(), c.getRegistration(), c.getPrice());
                } else {
                    continue; //if car unavailable, go to next object in list as customers should only see available cars
                }
            } else {
                JOptionPane.showMessageDialog(null, "There was an error trying to display vehicles in stock. Please contact system administrator");
            }

            listModel.addElement($temp.toUpperCase());
            lstStock.setModel(listModel); //add the current car's details to the list passed into the method
        }
    }

    public static String[] getCarDetails(String carReg) {
        String[] $arr = new String[5];
        for (Stock c : carList) {
            if (c.getRegistration().equals(carReg)) { //if registration matches one in car database, populate string array with car details
                $arr[0] = c.getAvailable().toString();
                $arr[1] = c.getModel();
                $arr[2] = c.getRegistration();
                $arr[3] = c.getPrice().toString();
                if (c.getAvailableDate() != null) {
                    $arr[4] = c.getAvailableDate().toString(); //if the car is not available, add next available date to array
                } else {
                    $arr[4] = ""; //otherwise, add empty string
                }
                return $arr; //return array of car details
            }
        }
        return $arr; //return empty array on failure
    }

    public static boolean checkReg(String carReg) {
        for (Stock c : carList) {
            if (c.getRegistration().equalsIgnoreCase(carReg)) {

                return true; //if the registration of the car matches the input registration, return true
            }
        }
        return false;
    }

    public static void editStock(String carReg, String price, String date) {
        for (Stock c : carList) {
            if (c.getRegistration().equalsIgnoreCase(carReg)) { //if current car matches input car reg update car's price
                c.setPrice(Float.parseFloat(price));
                if (!(date.isBlank()) && (!checkAvailability(carReg))) { //if car is unavailable and the input date is not empty update the return date
                    c.setAvailableDate(LocalDate.parse(date));
                }
                return;
            }
        }
    }

    public static void addStock(String model, String reg, String price) {

        Stock newCar = new Stock(true, model, reg, Float.parseFloat(price));
        carList.add(newCar); //create new car object and add it into list of cars
    }

    public static void saveStock() {
        try {
            FileWriter fw = new FileWriter($fileCarsPath);
            BufferedWriter bw = new BufferedWriter(fw);
            for (Stock c : carList) {
                String $temp = String.format("%b:%s:%s:%.2f:", c.getAvailable(), c.getModel(), c.getRegistration(), c.getPrice());
                if (!c.getAvailable()) {
                    $temp += String.format("%tF\n", c.getAvailableDate()); //if car is rented out, append the return date to the string
                } else {
                    $temp += "0\n"; //if car isn't rented out, append 0
                }
                bw.write($temp); //write the details of the current car in the loop, to cars.txt
            }
            bw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void removeStock(String reg) {
        carList.removeIf(c -> c.getRegistration().equalsIgnoreCase(reg)); //if car registration plate matches one in our system, remove it from the system
    }

    public static String calculateTotalPrice(String reg, LocalDate date) {
        DecimalFormat df = new DecimalFormat("0.00"); //ensure total price is in the correct format
        long difference;
        float price;
        for (Stock c : carList) {
//            if (c.getAvailable()) {
            if (c.getRegistration().equalsIgnoreCase(reg)) { //check to make sure correct car's price/ day is used
                difference = ChronoUnit.DAYS.between(LocalDate.now(), date);
                price = (c.getPrice() * difference); //calculate total price
                if (price > 0) {
                    return Float.toString(Float.parseFloat(df.format(price))); //if the total price is positive, return it in the correct format
                }
            }
//            }
        }
        return "";
    }

    public static boolean checkAvailability(String carReg) {
        for (Stock c : carList) {
            if (c.getRegistration().equalsIgnoreCase(carReg)) { //if current car matches input car reg, check to see if it's available
                if (c.getAvailable()) {
                    return true;
                }
            }
        }
        return false;
    }

    public static void setCarToAvailable(String carReg) {
        for (Stock c : carList) {
            if (c.getRegistration().equalsIgnoreCase(carReg)) { //if input car reg matches current car, remove it's return data and set it to available
                c.setAvailable(true);
                c.setAvailableDate(null);
                return;
            }
        }
    }

    public static void setCarToUnavailable(String carReg, String returnDate) {
        for (Stock c : carList) {
            if (c.getRegistration().equalsIgnoreCase(carReg)) { //if input car reg matches current car, set it to unavailable and set its return date to input return date
                c.setAvailable(false);
                c.setAvailableDate(LocalDate.parse(returnDate));
                return;
            }
        }
    }

    public static void clearCarList() {
        carList = new ArrayList<Stock>();
    }
}
