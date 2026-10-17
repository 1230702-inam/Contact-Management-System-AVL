package application;

public class Contact {

    private String fullName;
    private String phoneNumber;
    private String email;
    private String category;

    public Contact(String fullName, String phoneNumber, String email, String category) {
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.category = category;
    }

    public String getFullName() {
        return fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return fullName + " | " + phoneNumber + " | " + email + " | " + category;
    }
}