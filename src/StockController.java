import javax.swing.*;
import java.io.*;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class StockController {

    public static final String $fileCarsPath = "resources\\cars.txt";
    public static List<Stock> carList;

    public static void loadCars() {

        carList = new ArrayList<Stock>();
        try {

            Scanner scanner = new Scanner(new File($fileCarsPath));
            //scanner.useDelimiter("[:\n]");

            while (scanner.hasNext()) {

                String[] $arr;
                $arr = scanner.nextLine().split("[:]"); //experiment using .split on : with regex instead of using delimiter

                Stock car = new Stock(Boolean.parseBoolean($arr[0]), $arr[1], $arr[2],
                            Float.parseFloat($arr[3]));

                if ($arr[0].equals("false")) {

                    LocalDate $date = LocalDate.parse($arr[4]);

                    car.setAvailableDate($date);
                }
                carList.add(car);

            }
            scanner.close();

            for (Stock c : carList) { // Check to see if every object is being read in/ displayed correctly
                if (c.getAvailable()) {
                    System.out.println("available: " + c.getAvailable() + " | model: " + c.getModel()
                            + " | registration: " + c.getRegistration() + " | price/ day: £" + c.getPrice());
                }
                else {
                    System.out.println("available: " + c.getAvailable() + " | model: " + c.getModel()
                            + " | registration: " + c.getRegistration() + " | price/ day: £" + c.getPrice()
                            + " | next available: " + c.getAvailableDate());
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

//    public static void loadCars() {
//        carList = new ArrayList<Stock>();
//
//        try {
//            Scanner scanner = new Scanner(new File($fileCarsPath));
//
//            while (scanner.hasNext()) {
//
//                String[] $arr;
//
//                $arr = scanner.nextLine().split("[:]");
//                if ($arr[0].equalsIgnoreCase("true")) {
//                    Stock car = new Stock($arr[1], $arr[2], Float.parseFloat($arr[3]));
//
//                    carList.add(car);
//                }
//            }
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//    }

    public static void populateStockGUI(JList<String> lstStock, boolean accountType) {

        DefaultListModel<String> listModel = new DefaultListModel<String>();
        String $temp = null;
        for (Stock c : carList) {

            if (accountType) {
                $temp = String.format("available: %b | model: %s | registration: %s | price/ day: £%.2f",
                        c.getAvailable(), c.getModel(), c.getRegistration(), c.getPrice());
                if (!c.getAvailable()) {
                    $temp += String.format(" | next available: %tF", c.getAvailableDate());
                }
            }
            else if (!accountType) { //check to see if user is customer
                if (c.getAvailable()) { //only display available cars to customer
                    $temp = String.format("model: %s | registration: %s | price per day: £%.2f",
                            c.getModel(), c.getRegistration(), c.getPrice());
                }
                else {
                    continue; //if car unavailable, go to next object in list
                }
            }
            else {
                JOptionPane.showMessageDialog(null, "There was an error trying to display vehicles in stock. Please contact system administrator");
            }

            listModel.addElement($temp);
            lstStock.setModel(listModel);
        }
    }

    public static Stock checkRegistration(String carReg) {

        boolean found = false;

        for (Stock c : carList) {
            if (c.getRegistration().equalsIgnoreCase(carReg)) {

                found = true;
                return c;
            }
        }
        return null;
    }

    public static void editStock(Stock car, String available, String model, String reg, String price, String availableDate) {

        for (Stock c : carList) {
            if (c == car) {

                c.setAvailable(Boolean.parseBoolean(available));
                c.setModel(model);
                c.setRegistration(reg);
                c.setPrice(Float.parseFloat(price));

                if (available.equals("true")) {
                    c.setAvailableDate(null);
                }
                else {
                    c.setAvailableDate(LocalDate.parse(availableDate));
                }
            }
        }
    }

    public static void addStock(String available, String model, String reg, String price, String availableDate) {

        Stock newCar = new Stock(Boolean.parseBoolean(available), model, reg, Float.parseFloat(price));
        if (!newCar.getAvailable()) {
            newCar.setAvailableDate(LocalDate.parse(availableDate));
        }
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
                }
                else {
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
            if (c.getRegistration().equalsIgnoreCase(reg)) {
                difference = ChronoUnit.DAYS.between(LocalDate.now(), date);
                price = (c.getPrice() * difference);
                if (price > 0) {
                    return Float.toString(Float.parseFloat(df.format(price)));
                }
            }
        }
        return "";
    }

    public static void setCarToUnavailable(String carReg, String returnDate) {
        for (Stock c : carList) {
            if (c.getRegistration().equalsIgnoreCase(carReg)) {
                c.setAvailable(false);
                c.setAvailableDate(LocalDate.parse(returnDate));
            }
        }
    }

    public static void clearCarList() {
        carList = new ArrayList<Stock>();
    }
}
