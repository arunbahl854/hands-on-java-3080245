package bank;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DataSource {
  public static Connection connect() {
    String db_file = "jdbc:sqlite:resources/bank.db";
    Connection connection = null;

    try {
      connection = DriverManager.getConnection(db_file);
      System.out.println("We are now connected");
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return connection;
  }

  public static Customer getCustomer(String username) {
    String sql = "Select * from customers where username = ?";
    Customer customer = null;
    try {
      Connection connection = connect();
      PreparedStatement stmt = connection.prepareStatement(sql);
      stmt.setString(1, username);
      ResultSet resultSet = stmt.executeQuery();
      customer = new Customer(resultSet.getInt("id"),
          resultSet.getString("name"), resultSet.getString("username"),
          resultSet.getString("password"),
          resultSet.getInt("account_id"));

    } catch (SQLException e) {
      e.printStackTrace();
    }
    return customer;
  }

  public static void main(String[] args) {
    System.out.println(getCustomer("twest8o@friendfeed.com").getName());
  }
}
