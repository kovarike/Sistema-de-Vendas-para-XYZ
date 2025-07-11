abstract public class Users{
    private String name;
    private String email;
    private boolean isAdmin = false;
    private String password;

    public Users(String cname, String cemail, String cpassword){
        this.name = cname;
        this.email = cemail;
        this.password = cpassword;
    }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public boolean isAdmin() { return isAdmin; }
    public String getPassword() { return password; }
    public void setAdmin(boolean isAdmin) { this.isAdmin = isAdmin; }
    // ...other common user methods...
}