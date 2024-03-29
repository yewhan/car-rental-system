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
    public List<Stock> carList;

    public void loadCars() {

        carList = new ArrayList<Stock>();
        try {

            Scanner scanner = new Scanner(new File($fileCarsPath));
            //scanner.useDelimiter("[:\n]");

            while (scanner.hasNext()) {

                String[] $arr;
                $arr = scanner.nextLine().split("[:]"); //experiment using .split on : with regex instead of using delimiter

                Stock car = new Stock(Boolean.parseBoolean($arr[0].trim()), $arr[1], $arr[2].trim(),
                        Float.parseFloat($arr[3].trim()));

                if ($arr[0].equalsIgnoreCase("false")) {

                    LocalDate $date = LocalDate.parse($arr[4].trim());

                    car.setAvailableDate($date);
                }
                carList.add(car);

            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void populateStockGUI(JList<String> lstStock, boolean accountType) {

        DefaultListModel<String> listModel = new DefaultListModel<String>();
        for (Stock c : carList) {
            StringBuilder $temp = new StringBuilder(); //using a stringbuilder inside loop for memory usage optimization

            if (accountType) { //if the user calling the method is an admin, display information only admins should have access to

                $temp.append("available: ").append(c.getAvailable()).append(" | model: ").append(c.getModel())
                        .append(" | registration: ").append(c.getRegistration()).append(" | price/ day: £")
                        .append(c.getPrice());
                if (!c.getAvailable()) { //if the car is not available, also display when it will be and what customer has rented it

                    $temp.append(" | next available: ").append(c.getAvailableDate()).append(" | rented by: ")
                            .append(CustomerController.checkWhoHasCar(c.getRegistration()));
                }
            } else if (!accountType) { //check to see if user is customer
                if (c.getAvailable()) { //only display available cars and their prices to customer

                    $temp.append("model: ").append(c.getModel()).append(" | registration: ").append(c.getRegistration())
                            .append(" | price per day: £").append(c.getPrice());
                } else {
                    continue; //if car unavailable, go to next object in list as customers should only see available cars
                }
            } else {
                JOptionPane.showMessageDialog(null, "There was an error trying to display vehicles in stock. Please contact system administrator");
            }

            listModel.addElement($temp.toString().toUpperCase());
            lstStock.setModel(listModel); //add the current car's details to the list passed into the method
        }
    }

    public String[] getCarDetails(String carReg) {
        String[] $arr = new String[5];
        for (Stock c : carList) {
            if (c.getRegistration().equalsIgnoreCase(carReg)) { //if registration matches one in car database, populate string array with car details
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

    public boolean checkReg(String carReg) {
        for (Stock c : carList) {
            if (c.getRegistration().equalsIgnoreCase(carReg)) {

                return true; //if the registration of the car matches the input registration, return true
            }
        }
        return false;
    }

    public boolean editStock(String carReg, String price, String date) {
        for (Stock c : carList) {
            if (c.getRegistration().equalsIgnoreCase(carReg)) { //if current car matches input car reg update car's price
                c.setPrice(Float.parseFloat(price));
                if (!(date.isBlank()) && (!checkAvailability(carReg))) { //if car is unavailable and the input date is not empty update the return date
                    c.setAvailableDate(LocalDate.parse(date));
                }
                return true;
            }
        }
        return false;
    }

    public void addStock(String model, String reg, String price) {

        Stock newCar = new Stock(true, model, reg, Float.parseFloat(price));
        carList.add(newCar); //create new car object and add it into list of cars
    }

    public void saveStock() {
        try {
            FileWriter fw = new FileWriter($fileCarsPath);
            BufferedWriter bw = new BufferedWriter(fw);
            for (Stock c : carList) {
                StringBuilder $temp = new StringBuilder(); //using a stringbuilder inside loop for memory usage optimization

                $temp.append(c.getAvailable()).append(":").append(c.getModel()).append(":").append(c.getRegistration())
                        .append(":").append(c.getPrice()).append(":");
                if (!c.getAvailable()) {
                    $temp.append(c.getAvailableDate()).append("\n");
                } else {
                    $temp.append("0\n");
                }
                bw.write($temp.toString().toUpperCase()); //write the details of the current car in the loop, to cars.txt
            }
            bw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void removeStock(String reg) {
        carList.removeIf(c -> c.getRegistration().equalsIgnoreCase(reg)); //if car registration plate matches one in our system, remove it from the system
    }

    public String calculateTotalPrice(String reg, LocalDate date) {
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

    public boolean checkAvailability(String carReg) {
        for (Stock c : carList) {
            if (c.getRegistration().equalsIgnoreCase(carReg)) { //if current car matches input car reg, check to see if it's available
                if (c.getAvailable()) {
                    return true;
                }
            }
        }
        return false;
    }

    public void setCarToAvailable(String carReg) {
        for (Stock c : carList) {
            if (c.getRegistration().equalsIgnoreCase(carReg)) { //if input car reg matches current car, remove it's return data and set it to available
                c.setAvailable(true);
                c.setAvailableDate(null);
                return;
            }
        }
    }

    public void setCarToUnavailable(String carReg, String returnDate) {
        for (Stock c : carList) {
            if (c.getRegistration().equalsIgnoreCase(carReg)) { //if input car reg matches current car, set it to unavailable and set its return date to input return date
                c.setAvailable(false);
                c.setAvailableDate(LocalDate.parse(returnDate));
                return;
            }
        }
    }

    public void clearCarList() {
        carList = new ArrayList<Stock>();
    }
}
