package bank;

import java.util.Scanner;

import javax.security.auth.login.LoginException;

import bank.amountexception.AmountException;

public class Menu {

  private Scanner scanner = null;

  public static void main(String[] args) {
    System.out.println("Welcome to my Bank");

    Menu menu = new Menu();

    menu.scanner = new Scanner(System.in);

    Customer customer = menu.AuthenticateUser();
    Account account = null;
    if (customer != null) {
      account = DataSource.getAccount(customer.getAccountId());
    }

    if (customer != null && account != null) {
      menu.showMenu(customer, account);
    }

    menu.scanner.close();
  }

  public Customer AuthenticateUser() {
    Customer customer = null;

    System.out.println("Please Enter username");
    String username = scanner.next();

    System.out.println("Please Enter password");
    String password = scanner.next();
    try {
      customer = Authenticated.login(username, password);
    } catch (LoginException e) {
      System.out.println("Some problem while authenticating user" + e.getMessage());
    }
    return customer;
  }

  public void showMenu(Customer customer, Account account) {
    int scannerVar = 0;
    double amount = 0;
    while (scannerVar != 4 && customer.isAuthenticated()) {
      System.out.println("Menu");
      System.out.println("=============================");
      System.out.println("Please enter 1 for Deposit");
      System.out.println("Please enter 2 for Withdraw");
      System.out.println("Please enter 3 for Balance");
      System.out.println("Please enter 4 for exit");
      System.out.println("=============================");
      scannerVar = scanner.nextInt();

      switch (scannerVar) {
        case 1:
          System.out.println("Please Enter Amount to Deposit");
          amount = scanner.nextDouble();
          try {
            account.deposit(amount);
          } catch (AmountException e) {
            System.out.println("Invalid Amount" + e.getMessage());
          }

          break;
        case 2:
          System.out.println("Please Enter Amount to Withdraw");
          amount = scanner.nextDouble();
          try {
            account.withdraw(amount);
          } catch (AmountException e) {
            System.out.println("Invalid Amount" + e.getMessage());
          }

          break;
        case 3:
          System.out.println("Balance is " + account.getBalance());
          break;
        case 4:
          System.out.println("Exiting");
          break;
        default:
          System.out.println("Invaid Input, please try again");
      }
    }

  }
}
