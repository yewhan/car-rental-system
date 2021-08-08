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

            if (accountType) {
                $temp = String.format("available: %b | model: %s | registration: %s | price/ day: £%.2f",
                        c.getAvailable(), c.getModel(), c.getRegistration(), c.getPrice());
                if (!c.getAvailable()) {
                    $temp += String.format(" | next available: %tF | rented by: %s", c.getAvailableDate(),
                            AccountsController.checkWhoHasCar(c.getRegistration()));
                }
            } else if (!accountType) { //check to see if user is customer
                if (c.getAvailable()) { //only display available cars to customer
                    $temp = String.format("model: %s | registration: %s | price per day: £%.2f",
                            c.getModel(), c.getRegistration(), c.getPrice());
                } else {
                    continue; //if car unavailable, go to next object in list
                }
            } else {
                JOptionPane.showMessageDialog(null, "There was an error trying to display vehicles in stock. Please contact system administrator");
            }

            listModel.addElement($temp.toUpperCase());
            lstStock.setModel(listModel);
        }
    }

    public static String[] getCarDetails(String carReg) {
        String[] $arr = new String[5];
        for (Stock c : carList) {
            if (c.getRegistration().equals(carReg)) {
                $arr[0] = c.getAvailable().toString();
                $arr[1] = c.getModel();
                $arr[2] = c.getRegistration();
                $arr[3] = c.getPrice().toString();
                if (c.getAvailableDate() != null) {
                    $arr[4] = c.getAvailableDate().toString();
                } else {
                    $arr[4] = "";
                }
                return $arr;
            }
        }
        return $arr;
    }

    public static boolean checkReg(String carReg) {
        for (Stock c : carList) {
            if (c.getRegistration().equalsIgnoreCase(carReg)) {

                return true;
            }
        }
        return false;
    }

    public static void editStock(String carReg, String price, String date) {
        for (Stock c : carList) {
            if (c.getRegistration().equalsIgnoreCase(carReg)) {
                c.setPrice(Float.parseFloat(price));
                if (!(date.isBlank()) && (!checkAvailability(carReg))) {
                    c.setAvailableDate(LocalDate.parse(date));
                }
                return;
            }
        }
    }

    public static void addStock(String model, String reg, String price) {

        Stock newCar = new Stock(true, model, reg, Float.parseFloat(price));
        carList.add(newCar);
    }

    public static void saveStock() {
        try {
            FileWriter fw = new FileWriter(new File($fileCarsPath));
            BufferedWriter bw = new BufferedWriter(fw);
            for (Stock c : carList) {
                String $temp = String.format("%b:%s:%s:%.2f:", c.getAvailable(), c.getModel(), c.getRegistration(), c.getPrice());
                if (!c.getAvailable()) {
                    $temp += String.format("%tF\n", c.getAvailableDate());
                } else {
                    $temp += "0\n";
                }
                bw.write($temp);
            }
            bw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void removeStock(String reg) {
        carList.removeIf(c -> c.getRegistration().equalsIgnoreCase(reg));
    }

    public static String calculateTotalPrice(String reg, LocalDate date) {
        DecimalFormat df = new DecimalFormat("0.00");
        long difference;
        float price;
        for (Stock c : carList) {
//            if (c.getAvailable()) {
                if (c.getRegistration().equalsIgnoreCase(reg)) {
                    difference = ChronoUnit.DAYS.between(LocalDate.now(), date);
                    price = (c.getPrice() * difference);
                    if (price > 0) {
                        return Float.toString(Float.parseFloat(df.format(price)));
                    }
                }
//            }
        }
        return "";
    }

    public static boolean checkAvailability(String carReg) {
        for (Stock c : carList) {
            if (c.getRegistration().equalsIgnoreCase(carReg)) {
                if (c.getAvailable()) {
                    return true;
                }
            }
        }
        return false;
    }

    public static void setCarToAvailable(String carReg) {
        for (Stock c : carList) {
            if (c.getRegistration().equalsIgnoreCase(carReg)) {
                c.setAvailable(true);
                c.setAvailableDate(null);
                return;
            }
        }
    }

    public static void setCarToUnavailable(String carReg, String returnDate) {
        for (Stock c : carList) {
            if (c.getRegistration().equalsIgnoreCase(carReg)) {
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
