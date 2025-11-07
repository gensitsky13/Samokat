package qa.scooter.model;

public class OrderData {
    public final String firstName;
    public final String lastName;
    public final String address;
    public final String metro;
    public final String phone;
    public final String date;       // dd.MM.yyyy
    public final String duration;   // "сутки", "двое суток", ...
    public final String color;      // "black" | "grey"
    public final String comment;

    public OrderData(String firstName, String lastName, String address, String metro,
                     String phone, String date, String duration, String color, String comment) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.metro = metro;
        this.phone = phone;
        this.date = date;
        this.duration = duration;
        this.color = color;
        this.comment = comment;
    }
}