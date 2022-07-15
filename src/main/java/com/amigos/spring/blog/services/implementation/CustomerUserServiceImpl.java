package com.amigos.spring.blog.services.implementation;

import com.amigos.spring.blog.dtos.CustomerUserDTO;
import com.amigos.spring.blog.exceptions.ResourceAlreadyExistsException;
import com.amigos.spring.blog.exceptions.ResourceNotFoundException;
import com.amigos.spring.blog.models.CustomerUser;
import com.amigos.spring.blog.models.Role;
import com.amigos.spring.blog.repositories.CustomerUserRepository;
import com.amigos.spring.blog.repositories.RoleRepository;
import com.amigos.spring.blog.services.interfaces.CustomerUserService;
import com.amigos.spring.blog.utils.CustomerUserDTOHelper;
import com.amigos.spring.blog.utils.GlobalConstants;
import com.amigos.spring.blog.utils.MyLogger;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerUserServiceImpl implements CustomerUserService {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    Logger logger = MyLogger.getMyLogger();

    @Autowired
    private CustomerUserRepository customerUserRepository;

    private RoleRepository roleRepository;

    @Autowired
    public CustomerUserServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

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

        if(user.getProfileImageURL() == null || user.getProfileImageURL().length() <3) {
            user.setProfileImageURL(GlobalConstants.DEFAULT_PROFILE_IMAGE_URL);
        }
        Role role = roleRepository.findById(GlobalConstants.DEFAULT_USER_ROLE_ID).get();
        role.setRoleName(GlobalConstants.DEFAULT_USER_ROLE_NAME);
        user.getRoles().add(role);
        user.setEmail(user.getEmail().toLowerCase().trim());
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
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

    /*
     ** TODO : make much more reliable file name with no spaces and more, not to get unexpected error.
     ** Also, check more types to be accepted, as it can now even accept pdf files.
    */
    @Override
    public CustomerUserDTO uploadProfileImageForCustomerUser(Long customerUserId, MultipartFile file) {
        String filename;
        Optional<CustomerUser> customerUser = customerUserRepository.findById(customerUserId);
        if(!customerUser.isPresent()) {
            throw new ResourceNotFoundException("No User found in the server with ID " + customerUserId, 404);
        }
        try {
            File destinationFile = new File(GlobalConstants.CUSTOMER_USER_PROFILE_IMAGE_UPLOAD_DIR);
            filename = GlobalConstants.CUSTOMER_USER_PROFILE_IMAGE_PREFIX + customerUser.get().getId() + "_" +
                    customerUser.get().getName().replace(" ","") +
                    "_" + file.getSize() + file.getName() + "_" + file.getOriginalFilename();
            Path path = Paths.get(destinationFile.getAbsolutePath() + File.separator + filename);
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
        }
        catch (IOException exception) {
            logger.info("Something went wrong while uploading profile image : " + exception.getMessage());
            throw new ResourceAlreadyExistsException("Something went wrong while uploading profile image : " + exception.getMessage(), 500);
        }
        customerUser.get().setProfileImageURL(filename);
        CustomerUser savedCustomerUser = customerUserRepository.save(customerUser.get());
        logger.info("Image " + filename + " uploaded successfully.");
        return CustomerUserDTOHelper.buildDTOFromCustomerUser(savedCustomerUser);
    }

    // REF : https://www.devglan.com/spring-boot/spring-boot-file-upload-download
    @Override
    public String getImageURLByImageName(String imageName) {
        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("uploads/profile_images/")
                .path(imageName)
                .toUriString();
        return fileDownloadUri;
    }
}
