import javax.swing.*;
import java.time.LocalDate;

public class Threading {

    private final JTextPane threadingTxtTemp;
    private final String $license;
    private final String $carReg;

    public Threading(JTextPane receipt, String license, String carReg) {

        threadingTxtTemp = receipt;
        $license = license;
        $carReg = carReg;


        PrintReceipt();
    }

    private void PrintReceipt() {

        new SwingWorker<Object, Object>() {

            @Override
            protected Object doInBackground() throws Exception { //perform GUI interaction below in a background thread

                String[] name = CustomerController.getCustomerName($license);
                String[] carDetails = CarHire.stock.getCarDetails($carReg);
                LocalDate returnDate = LocalDate.parse(carDetails[4]);
                String totalPrice = CarHire.stock.calculateTotalPrice($carReg, returnDate);

                String receipt = String.format("Led's Car Hire\n%tF\n\n\n%s %s's ITEMS:\nModel: %s\nRegistration: %s\n" +
                                "£%s per day\nDue to be returned on: %tF\n\nTotal price: £%s",
                        LocalDate.now(), name[0], name[1], carDetails[1], carDetails[2], carDetails[3], returnDate,
                        totalPrice); //create receipt text

                threadingTxtTemp.setText(receipt); //inject text into receipt
                return null;
            }
        }.execute(); //execute above
    }
}
