package qa.scooter.model;

public class OrderData {

    private final String firstName;
    private final String lastName;
    private final String address;
    private final String metro;
    private final String phone;
    private final String date;       // формат: dd.MM.yyyy
    private final String duration;   // например: "сутки"
    private final String color;      // "BLACK" / "GREY" / "BLACK_GREY"
    private final String comment;

    public OrderData(String firstName,
                     String lastName,
                     String address,
                     String metro,
                     String phone,
                     String date,
                     String duration,
                     String color,
                     String comment) {

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

    // ГЕТТЕРЫ — именно то, чего не хватает
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

    public String getDuration() {
        return duration;
    }

    public String getColor() {
        return color;
    }

    public String getComment() {
        return comment;
    }
}