public class User {
    private String login;
    private String password;
    private boolean isAdmin;

    public User(String login, String password, boolean isAdmin) {
        this.login = login;
        this.password = password;
        this.isAdmin = isAdmin;
    }

    public String getLogin() { return login; }
    public String getPassword() { return password; }
    public boolean isAdmin() { return isAdmin; }
}