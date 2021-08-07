import java.time.LocalDate;

public class Stock {
    private Boolean available;
    private String model;
    private String registration;
    private Float price;
    private LocalDate availableDate;

    public Stock(Boolean $available, String $model, String $registration, Float $price, LocalDate $availableDate) {
        this.available = $available;
        this.model = $model;
        this.registration = $registration;
        this.price = $price;
        this.availableDate = $availableDate;
    }

    public Stock(Boolean $available, String $model, String $registration, Float $price) {
        this.available = $available;
        this.model = $model;
        this.registration = $registration;
        this.price = $price;
//        this($available, $model, $registration, $price, null);
    }

    public Stock(String $model, String $registration, Float $price) {
        this.model = $model;
        this.registration = $registration;
        this.price = $price;
//        this(null, $model, $registration, $price, null);
    }


    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean $available) {
        this.available = $available;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String $model) {
        this.model = $model;
    }

    public String getRegistration() {
        return registration;
    }

    public void setRegistration(String $registration) {
        this.registration = $registration;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float $price) {
        this.price = $price;
    }

    public LocalDate getAvailableDate() {
        return availableDate;
    }

    public void setAvailableDate(LocalDate $availableDate) {
        this.availableDate = $availableDate;
    }
}
