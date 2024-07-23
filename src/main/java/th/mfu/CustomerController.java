package th.mfu;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomerController {
    public static Map<Integer, Customer> customerDB = new HashMap<>();
    private int nextId = 0;

    @GetMapping("/customers/{id}")
    public ResponseEntity<Customer> getCustomer(@PathVariable Integer id) {
        if (customerDB.get(id) == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Customer customer = customerDB.get(id);
        return new ResponseEntity<>(customer, HttpStatus.OK);
    }

    @PostMapping("/customers")
    public ResponseEntity<String> createCustomer(@RequestBody Customer customer) {
        customerDB.put(nextId, customer);
        nextId++;
        return new ResponseEntity<>("Customer created successfully", HttpStatus.CREATED);
    }

    @DeleteMapping("/customers/{id}")
    public ResponseEntity<String> deleteCustomer(@PathVariable Integer id) {
        customerDB.remove(id);
        return new ResponseEntity<>("Customer Deleted", HttpStatus.NO_CONTENT);
    }
}