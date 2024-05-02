package com.nikola.spring.controler;


import com.nikola.spring.dto.ShippingAddressDto;
import com.nikola.spring.service.ShippingAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/shippingAddresses")
public class ShippingAddressControler {

    @Autowired private ShippingAddressService shippingAddressService;

    @GetMapping(value = "/addressDetails/{addressId}")
    public ResponseEntity<ShippingAddressDto> addressDetailsById(@PathVariable("addressId") Integer addressId){
        return new ResponseEntity<>(shippingAddressService.getAddressById(addressId), HttpStatus.OK);
    }

    @PutMapping(value = "/updateAddressDetails")
    @PreAuthorize(value = "hasAuthority('ADMIN')")
    public ResponseEntity<ShippingAddressDto> updateAddress(@Validated @RequestBody ShippingAddressDto shippingAddressDto, Errors errors){
        return new ResponseEntity<>(shippingAddressService.updateAddress(shippingAddressDto),HttpStatus.OK);
    }

}
