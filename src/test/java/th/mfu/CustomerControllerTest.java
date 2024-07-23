package th.mfu;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest
public class CustomerControllerTest {

    
    private CustomerController controller = new CustomerController();

    @Test
    public void createAndGet() {
        // create a new customer
        controller = new CustomerController();
        Customer customer = new Customer();
        customer.setName("Jay Doom");
        customer.setAddress("Sesame Street");
        customer.setEmail("Jay@Dmail.com");
        customer.setPhone("1234567890");

        ResponseEntity<String> response = controller.createCustomer(customer);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        // get that customer out
        ResponseEntity<Customer> getResponse = controller.getCustomer(0);
        assertEquals(HttpStatus.OK, getResponse.getStatusCode());
        assertEquals("Jay Doom", getResponse.getBody().getName());
    }
    @Test
    public void deleteAndNotFound(){
        //create new customer
        controller = new CustomerController();
        Customer customer = new Customer();
        customer.setName("Jay Doom");
        customer.setAddress("Sesame Street");
        customer.setEmail("Jay@Dmail.com");
        customer.setPhone("1234567890");

        controller.createCustomer(customer);

        //delete that customer
        ResponseEntity<String> response = controller.deleteCustomer(0);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        //get by id, shold reture 404
        ResponseEntity<Customer> response2 = controller.getCustomer(0);
        assertEquals(HttpStatus.NOT_FOUND, response2.getStatusCode());

    }
}