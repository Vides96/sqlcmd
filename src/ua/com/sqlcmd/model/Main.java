package ua.com.sqlcmd.model;

import java.sql.*;
import java.util.Random;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");
        Connection connection = DriverManager.getConnection(
        "jdbc:postgresql://127.0.0.1:5432/sqlcmd", "postgres",
        "tiopampa2017");

        String sql = "INSERT INTO public.user(name, password) " +
                "VALUES ('Stiven','Pupkin')";
        update(connection, sql);

        //select
        String sql1 = "SELECT * FROM public.user WHERE id > 5";
        insert(connection, sql1);

        //delete
        String delete = "DELETE  FROM public.user WHERE id > 10 AND id <100";
        update(connection, delete);

        //update
        PreparedStatement ps = connection.prepareStatement("UPDATE public.user SET password = ? WHERE id >3");
        ps.setString(1, "password_" + new Random().nextInt());

        ps.executeUpdate();

        connection.close();



    }



    private static void insert(Connection connection, String sql1) throws SQLException {
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(sql1);
        while(rs.next()){
            System.out.println("id: " + rs.getString(1));
            System.out.println("name: " + rs.getString(2));
            System.out.println("pasword: " + rs.getString(3));
            System.out.println("----");
        }
        rs.close();
        stmt.close();
    }

    private static void update(Connection connection, String sql) throws SQLException {
        Statement stmt = connection.createStatement();
        stmt.executeUpdate(sql);
        stmt.close();
    }
}
