public class StaffAccounts {

    private final String username;
    private final String password;
    private String passHash;

    public StaffAccounts(String $uname, String $hash) {

        this.username = $uname;
        this.password = $hash;
    }

    public String getUsername() {
        return username;
    }

    public String getPass() {
        return password;
    }
}
