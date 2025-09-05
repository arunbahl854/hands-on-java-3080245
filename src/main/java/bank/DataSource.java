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

  public static Account getAccount(int account_id) {
    String sql = "Select * from Accounts where id = ?";
    Account account = null;

    try {
      Connection connection = connect();
      PreparedStatement stmt = connection.prepareStatement(sql);
      stmt.setInt(1, account_id);
      ResultSet resultSet = stmt.executeQuery();
      account = new Account(resultSet.getInt("id"),
          resultSet.getString("type"),
          resultSet.getDouble("balance"));

    } catch (SQLException e) {
      e.printStackTrace();
    }
    return account;
  }

  public static void updateAccountBalance(int account_id, double balance) {
    String sql = "update accounts set balance = ? where id = ?";
    try (Connection connection = connect();
        PreparedStatement stmt = connection.prepareStatement(sql);) {
      stmt.setDouble(1, balance);
      stmt.setInt(2, account_id);
      stmt.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public static void main(String[] args) {
    System.out.println(getCustomer("twest8o@friendfeed.com").getName());
    System.out.println(getAccount(10385).getBalance());
  }
}
