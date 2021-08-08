import javax.swing.*;
import java.time.LocalDate;

public class Threading {

    private JTextPane threadingTxtTemp;
    private String $license;
    private String $carReg;

    public Threading (JTextPane receipt, String license, String carReg) {

        threadingTxtTemp = receipt;
        $license = license;
        $carReg = carReg;


        DefaultLoader();
    }

    private void DefaultLoader() {

        new SwingWorker<Object, Object>() {

            @Override
            protected Object doInBackground() throws Exception {

                String[] name = AccountsController.getCustomerName($license);
                String[] carDetails = StockController.getCarDetails($carReg);
                LocalDate returnDate = LocalDate.parse(carDetails[4]);
                String totalPrice = StockController.calculateTotalPrice($carReg, returnDate);

                String receipt = String.format("Led's Car Hire\n%tF\n\n\n%s %s's ITEMS:\nModel: %s\nRegistration: %s\n" +
                                "£%s per day\nDue to be returned on: %tF\n\nTotal price: £%s",
                        LocalDate.now(), name[0], name[1], carDetails[1], carDetails[2], carDetails[3], returnDate,
                        totalPrice);

                threadingTxtTemp.setText(receipt);
                return null;
            }
        }.execute();
    }
}
