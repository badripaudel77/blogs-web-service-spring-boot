package com.amigos.spring.blog.services.interfaces;

import com.amigos.spring.blog.dtos.CustomerUserDTO;
import com.amigos.spring.blog.models.CustomerUser;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CustomerUserService {

    List<CustomerUserDTO> getCustomerUsers();

    CustomerUserDTO registerCustomerUser(CustomerUser customerUser);

    CustomerUserDTO updateCustomerUser(CustomerUser customerUser, Long userId);

    CustomerUserDTO getCustomerUserDetails(Long userId);

    boolean deleteCustomerUser(Long userId);

    CustomerUserDTO uploadProfileImageForCustomerUser(Long customerUserId, MultipartFile file);

    String getImageURLByImageName(String imageName);

}
