public class CustomerAccounts {

    private String fName;
    private String lName;
    private String address;
    private String licenseNum;
    private String carReg;

    public CustomerAccounts(String $fName, String $lName, String $address, String $licenseNum, String $carReg) {
        this.fName = $fName;
        this.lName = $lName;
        this.address = $address;
        this.licenseNum = $licenseNum;
        this.carReg = $carReg;
    }

    public CustomerAccounts(String $fName, String $lName, String $address, String $licenseNum) {
        this($fName, $lName, $address, $licenseNum, null);
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String $fName) {
        this.fName = $fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String $lName) {
        this.lName = $lName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String $address) {
        this.address = $address;
    }

    public String getLicenseNum() {
        return licenseNum;
    }

    public void setLicenseNum(String $licenseNum) {
        this.licenseNum = $licenseNum;
    }

    public String getCarReg() {
        return carReg;
    }

    public void setCarReg(String $carReg) {
        this.carReg = $carReg;
    }
}
