package com.nikola.spring.controler;


import com.nikola.spring.dto.CustomerDto;
import com.nikola.spring.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/customers")
public class CustomerControler {

    @Autowired private CustomerService customerService;

    @GetMapping(value = "/allCustomers")
    public ResponseEntity<List<CustomerDto>> getAllCustomers(){
        return new ResponseEntity<>(customerService.listAllCustomers(), HttpStatus.OK);
    }

    @GetMapping(value = "/customerDetails/{customerId}")
    public ResponseEntity<CustomerDto> getCustomerById(@PathVariable("customerId") Integer customerId){
        return new ResponseEntity<>(customerService.getCustomerById(customerId),HttpStatus.OK);
    }
    @DeleteMapping(value = "/deleteCustomer/{customerId}")
    public ResponseEntity<String> deleteCustomer(@PathVariable("customerId") Integer customerId){
        customerService.deleteCustomer(customerId);
        String message = "Customer with id: "+customerId+" has been deleted.";
        return new ResponseEntity<>(message,HttpStatus.OK);
    }

    @PutMapping(value = "/updateCustomer")
    public ResponseEntity<CustomerDto> updateCustomer(@Validated @RequestBody CustomerDto customerDto, Errors errors){
        return new ResponseEntity<>(customerService.updateCustomer(customerDto),HttpStatus.OK);
    }

    @GetMapping(value = "/currentCustomer")
    public ResponseEntity<CustomerDto> getCurrentCustomer(){
        return new ResponseEntity<>(customerService.getCurrentCustomer(),HttpStatus.OK);
    }



}
