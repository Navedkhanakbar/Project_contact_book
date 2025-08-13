package Contact;

public class Contact {

    private Long id;
    private String name;
    private String phone;
    private String email;
    private String address;

    // public Contact () {} // a class always have a default constructor.


    public Contact(Long id,String name,String phone,String email,String address) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
    }

    public Contact(String name,String phone,String email,String address) {
        this(null,name,phone,email,address);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "contact{id='%s', name='%s', phone='%s', email='%s', address='%s'}" .formatted(id,name,phone,email,address);
    }
}
