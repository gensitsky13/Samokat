package qa.scooter.model;

public class OrderData {

    private final String firstName;
    private final String lastName;
    private final String address;
    private final String metro;
    private final String phone;
    private final String date;
    private final String rentalPeriod;
    private final String color;
    private final String comment;

    public OrderData(String firstName,
                     String lastName,
                     String address,
                     String metro,
                     String phone,
                     String date,
                     String rentalPeriod,
                     String color,
                     String comment) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.metro = metro;
        this.phone = phone;
        this.date = date;
        this.rentalPeriod = rentalPeriod;
        this.color = color;
        this.comment = comment;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getAddress() {
        return address;
    }

    public String getMetro() {
        return metro;
    }

    public String getPhone() {
        return phone;
    }

    public String getDate() {
        return date;
    }

    public String getRentalPeriod() {
        return rentalPeriod;
    }

    public String getColor() {
        return color;
    }

    public String getComment() {
        return comment;
    }
}