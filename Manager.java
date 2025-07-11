public class Manager extends Users {
    private int minStockAlert = 1;
    public Manager(String name, String email, String password) {
        super(name, email, password);
        setAdmin(true);
    }
    public void setMinStockAlert(int min) {
        this.minStockAlert = min;
    }
    public int getMinStockAlert() {
        return minStockAlert;
    }
    // ...additional Manager-specific methods...
}
