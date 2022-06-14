package com.amigos.spring.blog.utils;

import com.amigos.spring.blog.dtos.CustomerUserDTO;
import com.amigos.spring.blog.models.CustomerUser;

public class CustomerUserDTOHelper {

    public static CustomerUserDTO buildDTOFromCustomerUser(CustomerUser customerUser) {
        CustomerUserDTO customerUserDTO = new CustomerUserDTO();
        customerUserDTO.setName(customerUser.getName());
        customerUserDTO.setEmail(customerUser.getEmail());
        customerUserDTO.setIntro(customerUser.getIntro());
        customerUserDTO.setCreatedDate(customerUser.getCreatedDate());
        return customerUserDTO;
    }
}
