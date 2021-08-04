import java.time.LocalDate;

public class CarList {
    public Boolean $available;
    public String $model;
    public Float $price;
    public LocalDate $availableDate;

    public CarList(Boolean available, String model, Float price, LocalDate availableDate) {
        this.$available = available;
        this.$model = model;
        this.$price = price;
        this.$availableDate = availableDate;
    }

    public CarList(Boolean available, String model, Float price) {
        this.$available = available;
        this.$model = model;
        this.$price = price;
    }


    public Boolean getAvailable() {
        return $available;
    }

    public void setAvailable(Boolean $available) {
        this.$available = $available;
    }

    public String getModel() {
        return $model;
    }

    public void setModel(String $model) {
        this.$model = $model;
    }

    public Float getPrice() {
        return $price;
    }

    public void setPrice(Float $price) {
        this.$price = $price;
    }

    public LocalDate getAvailableDate() {
        return $availableDate;
    }

    public void setAvailableDate(LocalDate $availableDate) {
        this.$availableDate = $availableDate;
    }
}
