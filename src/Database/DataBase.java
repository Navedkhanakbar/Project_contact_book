package Database;

import java.sql.*;

public class DataBase {
    private static final String url = "jdbc:mysql://localhost:3306/contactdb";
    private static final String user = "root";
    private static final String password = "Naved";


    static {
        // create table if not exist (runs once on class load)
        try(Connection conn = getconnection()) {
            createschema(conn);
        }
        catch (SQLException e) {
            throw new RuntimeException("Failed to initialize database", e);
        }
    }

    public static Connection getconnection() throws SQLException {
        return DriverManager.getConnection(url,user,password);
    }

    private static void createschema(Connection conn) throws SQLException {
        String dbname = conn.getCatalog();
        String indexname = "idx_contacts_name";
        String tablename = "contacts";


        String sql = """
                create table if not exists contacts (
                id bigint primary key auto_increment,
                name varchar(255) not null,
                phone varchar(255) not null unique,
                email varchar(255) unique,
                address varchar(255)
                );
                """;

        try(Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        }

        String check_index_sql = "SELECT COUNT(*) FROM information_schema.statistics " +
                "WHERE table_schema = ? AND table_name = ? AND index_name = ?";

        try(PreparedStatement ps = conn.prepareStatement(check_index_sql)) {
            ps.setString(1,dbname);
            ps.setString(2,tablename);
            ps.setString(3,indexname);

            try(ResultSet rs = ps.executeQuery()) {
                rs.next();
                int count = rs.getInt(1);

                if (count == 0) {
                    try(Statement stmtindx = conn.createStatement()) {
                        stmtindx.executeUpdate("create index "+ indexname + " on " + tablename +" (name) ");
                        System.out.println("Index created");
                    }
                }
                else {
                    System.out.println("Index already exists");
                }
            }

        }

    }




}
