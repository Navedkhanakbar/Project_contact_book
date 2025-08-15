package Contact;

import Database.DataBase;
import java.sql.*;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class ConatctDAO  {

    // adding contact to database
    public Contact create(Contact c) throws SQLException {
        String sql = "insert into contact (name,phone,email,address) values (?,?,?,?)";

        try(Connection conn = DataBase.getconnection()) {
            PreparedStatement prstmt = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
            prstmt.setString(1,c.getName());
            prstmt.setString(2, c.getPhone());
            prstmt.setString(3, c.getEmail());
            prstmt.setString(4, c.getAddress());
            prstmt.executeUpdate();
            try(ResultSet rs = prstmt.getGeneratedKeys()) {
                if (rs.next()) c.setId(rs.getLong(1));
            }
        }
        return c;
    }
    // updating the database contact
    public boolean update(Contact c) throws SQLException {
        if(c.getId() == null) throw new IllegalArgumentException("id required for update");
        String query = "update contacts name = ?, phone = ?, email = ?, address = ? where id = ?";
        try(Connection conn = DataBase.getconnection()) {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1,c.getName());
            ps.setString(2,c.getPhone());
            ps.setString(3,c.getEmail());
            ps.setString(4,c.getAddress());
            ps.setLong(5,c.getId());
            return ps.executeUpdate() == 1;
        }
    }

    //deleting contact
    public boolean delete(long id) throws SQLException {
        String query = "delete from contacts where id = ?";
        try(Connection conn = DataBase.getconnection()) {
            PreparedStatement prestmt = conn.prepareStatement(query);
            prestmt.setLong(1,id);
            return prestmt.executeUpdate() == 1;
        }
    }

    // finding by contact
    public Contact FindbyId(long id) throws SQLException {
        String query = "select name, phone, email, address from contacts where id = ?";
        try(Connection conn = DataBase.getconnection()) {
            PreparedStatement prestmt = conn.prepareStatement(query);
            prestmt.setLong(1,id);
            try(ResultSet rs = prestmt.executeQuery()){
                if (rs.next()) return map(rs);
            }
        }
        return null;
    }

    public List<Contact> listall() throws SQLException{
        String query = "select id, name, phone, email, address from contacts order by name asc, id asc";
        try(Connection conn = DataBase.getconnection()) {
            PreparedStatement prestmt = conn.prepareStatement(query);
            ResultSet rs = prestmt.executeQuery();
            List<Contact> out = new ArrayList<>();
            while (rs.next()) out.add(map(rs));
            return out;
        }
    }

    public List<Contact> serchbynameorphone(String query) throws SQLException {
        String like = "%" + query.trim() + "%";
        String sqlquery = """
                select name, phone, email, address from contacts where name like ? or phone like ? order by name asc, id asc
                """;

        try(Connection conn = DataBase.getconnection())
        {
            PreparedStatement prestmt = conn.prepareStatement(sqlquery);
            prestmt.setString(1,like);
            prestmt.setString(2,like);

            try(ResultSet rs = prestmt.executeQuery()) {
                List<Contact> out = new ArrayList<>();
                while (rs.next()) out.add(map(rs));
                return out;

            }
        }
    }

    public Contact map(ResultSet rs) throws SQLException{
        return new Contact(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getString("phone"),
                rs.getString("email"),
                rs.getString("address")
                );
    }
}
