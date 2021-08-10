public class StaffAccounts {

    private final String username;
    private final String passHash;

    public StaffAccounts(String $uname, String $hash) {

        this.username = $uname;
        this.passHash = $hash;
    }

    public String getUsername() {
        return username;
    }

    public String getPass() {
        return passHash;
    }
}
