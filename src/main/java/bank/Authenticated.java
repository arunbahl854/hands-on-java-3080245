package bank;

import javax.security.auth.login.LoginException;

public class Authenticated {
  public static Customer login(String username, String password) throws LoginException {
     Customer customer = DataSource.getCustomer(username);
     if(customer == null){
        throw new LoginException("username not found");
     }

     if (customer.getPassword().equals(password))
     {
        customer.setAuthenticated(true);
        return customer;
     }
     else
     {
        customer.setAuthenticated(false);
        throw new LoginException("Password did not match")
     }
     return true; 
  }

  public static void logoff(Customer customer) {
    customer.setAuthenticated(false);
  }

}
