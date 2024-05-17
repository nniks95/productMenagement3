package com.nikola.spring.service.impl;

import com.nikola.spring.converter.TempConverter;
import com.nikola.spring.dto.CustomerDto;
import com.nikola.spring.dto.OrderDto;
import com.nikola.spring.dto.ShippingAddressDto;
import com.nikola.spring.dto.UserDto;
import com.nikola.spring.entities.*;
import com.nikola.spring.exceptions.DuplicateNotAllowed;
import com.nikola.spring.exceptions.InstanceUndefinedException;
import com.nikola.spring.repositories.*;
import com.nikola.spring.service.CustomerService;
import com.nikola.spring.service.OrderService;
import com.nikola.spring.utils.RegistrationForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private TempConverter tempConverter;

    @Autowired private CustomerRepository customerRepository;

    @Autowired private UserRepository userRepository;
    @Autowired private RoleRepository roleRepository;
    @Autowired private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired private ShippingAddressRepository shippingAddressRepository;
    @Autowired private CartRepository cartRepository;
    @Autowired private OrderService orderService;

    @Override
    public CustomerDto addCustomer(RegistrationForm form) {
        UserDto user = form.getUser();
        Optional<UserEntity> userEntityOptional = userRepository.findByEmail(user.getEmail());
        if(userEntityOptional.isPresent()){
            throw new DuplicateNotAllowed(new Error("User already exists!"));
        }
        RoleEntity roleEntity = roleRepository.findByRole("USER").orElseThrow(()->new InstanceUndefinedException(new Error("Role not found.")));
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        UserEntity userEntity = tempConverter.dtoToEntity(user);
        userEntity.setRoles(List.of(roleEntity));
        userEntity.setEnabled((byte) 1);
        UserEntity storedUser = userRepository.save(userEntity);
        roleEntity.setUsers(List.of(storedUser));
        roleRepository.saveAndFlush(roleEntity);
        ShippingAddressDto shippingAddressDto = form.getShippingAddress();
        ShippingAddressEntity shippingAddressEntity = tempConverter.dtoToEntity(shippingAddressDto);
        ShippingAddressEntity storedAddress = shippingAddressRepository.save(shippingAddressEntity);
        CartEntity cartEntity = new CartEntity();
        cartEntity.setCartPrice(0.00);
        CartEntity storedCart = cartRepository.save(cartEntity);
        CustomerDto customerDto = form.getCustomer();
        customerDto.setUserId(storedUser.getId());
        customerDto.setCartId(storedCart.getId());
        customerDto.setShippingAddressId(storedAddress.getId());
        CustomerEntity customerEntity = tempConverter.dtoToEntity(customerDto);
        CustomerEntity storedCustomer = customerRepository.save(customerEntity);
        storedCart.setCustomer(storedCustomer);
        cartRepository.saveAndFlush(storedCart);
        storedAddress.setCustomer(storedCustomer);
        shippingAddressRepository.saveAndFlush(storedAddress);
        return tempConverter.entityToDto(storedCustomer);
    }

    @Override
    public List<CustomerDto> listAllCustomers() {
        List<CustomerDto> returnValue = new ArrayList<>();
        List<CustomerEntity> allCustomers = customerRepository.findAll();
        for(CustomerEntity customer:allCustomers){
            returnValue.add(tempConverter.entityToDto(customer));
        }
        return returnValue;
    }

    @Override
    public CustomerDto getCustomerById(Integer customerId) {
        CustomerDto returnValue = null;
        Optional<CustomerEntity> customerEntityOptional = customerRepository.findById(customerId);
        if(customerEntityOptional.isPresent()){
            returnValue = tempConverter.entityToDto(customerEntityOptional.get());
        }else{
            throw new InstanceUndefinedException(new Error("Customer undefined"));
        }
        return returnValue;
    }

    @Override
    public void deleteCustomer(Integer customerId) {
        CustomerDto customer = getCustomerById(customerId);
        List<OrderDto> allOrders = orderService.listAllByCartId(customer.getCartId());
        for(OrderDto order:allOrders){
            orderService.deleteOrder(order.getId());
        }
        customerRepository.deleteById(customer.getId());
        customerRepository.flush();

    }



    @Override
    public CustomerDto getCurrentCustomer() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentCustomer = authentication.getName();
        CustomerEntity customer = customerRepository.findByEmail(currentCustomer)
                .orElseThrow(() -> new InstanceUndefinedException(new Error("Error while trying to find customer by email: " + currentCustomer))); //todo: napravi ObjectNotFoundException(String msg) extendenduje RuntimeException
        return tempConverter.entityToDto(customer);
    }
    @Override
    public CustomerDto updateCustomer(CustomerDto customer) {
        CustomerDto returnValue = getCurrentCustomer();
        returnValue.setCustomerPhone(customer.getCustomerPhone());
        return returnValue;
    }

}
