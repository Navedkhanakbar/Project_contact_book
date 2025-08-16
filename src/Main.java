import Contact.Contact;
import Contact.ContactService;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;


public class Main {

    private static final ContactService service  = new ContactService();
    private static final Scanner in = new Scanner(System.in);


    public static void main(String[] args) {


        System.out.println("=== contact book (jdbc + mysql) ===");

        while (true) {
            printmenu();
            String choose = in.nextLine().trim();
            try{

                switch (choose) {
                    case "1" -> ADD();
                    case "2" -> ListAll();
                    case "3" -> updatecon();
                    case "4" ->deletecon();
                    case "5" ->Search();
                    case "6" -> searchbyid();
                    case "0" -> {
                        System.out.println("Exiting....");
                        return;
                    }
                    default -> System.out.println("Invalid option");

                }

            }
            catch (SQLException | IllegalArgumentException e) {
                System.out.println("Error" + e.getMessage());
            }
        }

    }


    private static void printmenu() {
        System.out.print("""
                1. Add contact
                2. List all
                3. Update
                4. Delete
                5. Search by phone/name
                6. Search by ID
                0. EXIT
                Choose: """);
    }


    private static void ADD() throws SQLException {
        System.out.print("Name: "); String name = in.nextLine();
        System.out.print("Phone: "); String phone = in.nextLine();
        System.out.print("Email: "); String email = in.nextLine();
        System.out.print("Address: "); String address = in.nextLine();
        Contact c = service.add(name,phone,email,address);

        System.out.println("add: "+ c);
    }


    private static void ListAll() throws SQLException {
        List<Contact> conlist = service.listall();

        if (conlist.isEmpty()) {
            System.out.println("database is empty");
        }

        conlist.forEach(c -> System.out.printf("%-4s %-20s %-15s %-25s %s%n",
                c.getId(),c.getName(),c.getPhone(),c.getEmail(),c.getAddress())
        );
        System.out.println();

    }

    private static void updatecon() throws SQLException {
        long id = askId();
        Contact exist = service.findid(id);

        if (exist == null) {System.out.println("not found"); return;}

        System.out.println("Name [" + exist.getName() + "]: ");
        String Name = KeeporNew(exist.getName());

        System.out.println("Phone ["+ exist.getPhone() + "]");
        String phone = KeeporNew(exist.getPhone());

        System.out.println("email ["+ exist.getEmail()+ "]");
        String email =KeeporNew(exist.getEmail());

        System.out.println("Address ["+ exist.getAddress()+"]");
        String address = KeeporNew(exist.getAddress());


        boolean ok = service.update(id,Name,phone,email,address);

        System.out.println(ok ? "updated" : "not Found");


    }

    private static void deletecon() throws SQLException {
        long id = askId();
        boolean ok = service.delete(id);
        System.out.println(ok ? "DELETED" : "NOT found");
    }


    private static void Search() throws SQLException {
        System.out.println("Enter name/phone");
        String q = in.nextLine();
        List<Contact> searchlist = service.searchbynameorphone(q);


        searchlist.forEach(c -> System.out.printf("%-4s %-20s %-15s %-25s %s%n",
                c.getId(),c.getName(),c.getPhone(),c.getEmail(),c.getAddress())
        );
    }

    private static void searchbyid() throws SQLException {
        long id = askId();

        Contact findedcon = service.findid(id);
        if (findedcon == null) System.out.println("not found");

        System.out.println(findedcon);
    }



    private static long askId() {
        System.out.println("ID: ");
        return Long.parseLong(in.nextLine().trim());
    }

    private static String KeeporNew(String oldvalue) {
        String s = in.nextLine();
        return s.isBlank() ? oldvalue : s.trim();
    }
}
