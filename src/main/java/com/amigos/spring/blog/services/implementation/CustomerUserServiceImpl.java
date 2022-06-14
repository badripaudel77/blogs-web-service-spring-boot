package com.amigos.spring.blog.services.implementation;

import com.amigos.spring.blog.dtos.CustomerUserDTO;
import com.amigos.spring.blog.exceptions.ResourceAlreadyExistsException;
import com.amigos.spring.blog.exceptions.ResourceNotFoundException;
import com.amigos.spring.blog.models.CustomerUser;
import com.amigos.spring.blog.repositories.CustomerUserRepository;
import com.amigos.spring.blog.services.interfaces.CustomerUserService;
import com.amigos.spring.blog.utils.CustomerUserDTOHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerUserServiceImpl implements CustomerUserService {

    @Autowired
    private CustomerUserRepository customerUserRepository;

    @Override
    public List<CustomerUserDTO> getCustomerUsers() {
        List<CustomerUser> customerUsers = customerUserRepository.findAll();
        if(customerUsers.size() ==0)
            throw new ResourceNotFoundException("No Users Found", 404);

        List<CustomerUserDTO> customerUserDTOList = new ArrayList<>();
        customerUsers.forEach((customerUser -> {
            CustomerUserDTO customerUserDTO = CustomerUserDTOHelper.buildDTOFromCustomerUser(customerUser);
            customerUserDTOList.add(customerUserDTO);
        }
        ));
        return customerUserDTOList;
    }

    @Override
    public CustomerUserDTO registerCustomerUser(CustomerUser user) {
        Optional<CustomerUser> existingCustomerUser = customerUserRepository.findByEmail(user.getEmail());
        if(existingCustomerUser.isPresent())
            throw  new ResourceAlreadyExistsException("User with email "+ user.getEmail() + " already exists.", 500);

        CustomerUser customerUser = customerUserRepository.save(user);
        CustomerUserDTO customerUserDTO = CustomerUserDTOHelper.buildDTOFromCustomerUser(customerUser);
        return customerUserDTO;
    }

    @Override
    public CustomerUserDTO updateCustomerUser(CustomerUser customerUser, Long userId) {
        CustomerUser user = customerUserRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User with UserId "+ userId + " not found.", 404));

        user.setEmail(customerUser.getEmail());
        user.setName(customerUser.getName());
        user.setIntro(customerUser.getIntro());
        user.setPassword(customerUser.getPassword());
        user.setCreatedDate(customerUser.getCreatedDate());

        CustomerUser updateCustomerUser = customerUserRepository.save(user);
        return CustomerUserDTOHelper.buildDTOFromCustomerUser(updateCustomerUser);
    }

    @Override
    public CustomerUserDTO getCustomerUserDetails(Long userId) {
        CustomerUser user = customerUserRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User with UserId "+ userId + " not found.", 404));
        return CustomerUserDTOHelper.buildDTOFromCustomerUser(user);
    }

    @Override
    public boolean deleteCustomerUser(Long userId) {
        CustomerUser customerUser = customerUserRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User with UserId "+ userId + " not found.", 404));
        customerUserRepository.delete(customerUser);
        return true;
    }
}
