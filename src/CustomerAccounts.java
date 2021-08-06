public class CustomerAccounts {

    private String fName;
    private String lName;
    private String address;
    private String licenseNum;
    private String refNum;

    public CustomerAccounts(String $fName, String $lName, String $address, String $licenseNum, String $refNum) {
        this.fName = $fName;
        this.lName = $lName;
        this.address = $address;
        this.licenseNum = $licenseNum;
        this.refNum = $refNum;
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

    public String getRefNum() {
        return refNum;
    }

    public void setRefNum(String $refNum) {
        this.refNum = $refNum;
    }
}
