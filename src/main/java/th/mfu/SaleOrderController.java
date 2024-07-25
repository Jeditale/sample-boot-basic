package th.mfu;

import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SaleOrderController {

    @Autowired
    private CustomerRepository customerRepo;
    @Autowired
    private SaleOrderRepository saleOrderRepo;

    // create order by customer
    @PostMapping("/customers/{customerId}/orders")
    public ResponseEntity<String> createOrder(@PathVariable Long customerId, @RequestBody SaleOrder saleOrder) {
        // check if customer exists
        if (!customerRepo.existsById(customerId)) {
            return new ResponseEntity<String>("Customer not found", HttpStatus.NOT_FOUND);
        }
        Optional<Customer> optCustomer = customerRepo.findById(customerId);
        // set customer to order
        saleOrder.setCustomer(optCustomer.get());
        saleOrderRepo.save(saleOrder);
        return new ResponseEntity<String>("Order created", HttpStatus.CREATED);
    }
    @GetMapping("/customers/{customerId}/orders")
    public ResponseEntity<Collection<SaleOrder>> getOrderByCustomer(@PathVariable Long customerId) {
        // check if customer exists
        if (!customerRepo.existsById(customerId)) {
            return new ResponseEntity<Collection<SaleOrder>>( HttpStatus.NOT_FOUND);
        }
        Collection<SaleOrder> orders = saleOrderRepo.findByCustomerId(customerId);
        return new ResponseEntity<Collection<SaleOrder>>(orders, HttpStatus.OK);
    }


    // get all order
    @GetMapping("/orders")
    public ResponseEntity<Collection<SaleOrder>> getAllOrders() {
        return new ResponseEntity<Collection<SaleOrder>>(saleOrderRepo.findAll(), HttpStatus.OK);
    }

}