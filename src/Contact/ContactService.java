package Contact;

import java.sql.SQLException;
import java.util.List;

public class ContactService {


    private final ConatctDAO dao = new ConatctDAO();


    public Contact add(String name,String phone, String email, String address) throws SQLException {
        validate(name,phone,email);
        return dao.create(new Contact(name,phone,email,address));
    }

    public boolean update(long id,String name,String phone,String email,String address) throws SQLException {
        validate(name,phone,email);
        return dao.update(new Contact(id,name,phone,email,address));
    }

    public boolean delete(long id) throws SQLException {
        return dao.delete(id);
    }

    public Contact findid(long id) throws SQLException {
        return dao.FindbyId(id);
    }

    public List<Contact> listall() throws SQLException {
        return dao.listall();
    }

    public List<Contact> searchbynameorphone(String nameorphone) throws SQLException {
        return dao.serchbynameorphone(nameorphone);
    }

    public void validate(String name,String phone,String email) {
        if (name == null || name.isBlank()) throw new IllegalArgumentException("Name is required");
        if (phone == null || phone.isBlank()) throw new IllegalArgumentException("phone is required");
        if (email != null && !email.isBlank() && !email.contains("@")) throw new IllegalArgumentException("invalid email");
    }
}
