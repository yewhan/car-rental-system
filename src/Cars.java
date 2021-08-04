import javax.xml.crypto.dsig.SignatureMethod;
import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Cars {

    public static final String $fileCarsPath = "resources\\cars.txt";
    public static List<CarList> cars;

    public static void loadCarsAdmin() {

        cars = new ArrayList<CarList>();
        try {

            Scanner scanner = new Scanner(new File($fileCarsPath));
            //scanner.useDelimiter("[:\n]");

            while (scanner.hasNext()) {

                String[] $arr = new String[4];
                $arr = scanner.next().split("[:]");

                if ($arr[0].equals("1")) {

                    CarList carsList = new CarList(Boolean.parseBoolean($arr[0]), $arr[1],
                            Float.parseFloat($arr[2]));
                    cars.add(carsList);
                }
                else {

                    LocalDate $date = LocalDate.parse($arr[3]);

                    CarList carsList = new CarList(Boolean.parseBoolean($arr[0]), $arr[1],
                            Float.parseFloat($arr[2]), $date);
                    cars.add(carsList);
                }
            }
            scanner.close();

            for (CarList c : cars) { // Check to see if every object is being read in/ displayed correctly
                if (c.getAvailableDate() == null) {
                    System.out.println("available: " + c.getAvailable() + " | model: " + c.getModel()
                            + " | price/ day: £" + c.getPrice());
                }
                else {
                    System.out.println("available: " + c.getAvailable() + " | model: " + c.getModel()
                            + " | price/ day: £" + c.getPrice() + " | next available: " + c.getAvailableDate());
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
