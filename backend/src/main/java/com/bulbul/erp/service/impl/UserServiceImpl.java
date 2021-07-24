package com.bulbul.erp.service.impl;

import com.bulbul.erp.repository.RoleRepository;
import com.bulbul.erp.repository.UserRepository;
import com.bulbul.erp.entity.User;
import com.bulbul.erp.error.SimilarPasswordException;
import com.bulbul.erp.error.UserAlreadyExistsException;
import com.bulbul.erp.error.UserNotFoundException;
import com.bulbul.erp.error.UsernameOrPasswordNotMatchedException;
import com.bulbul.erp.request.ResetPasswordRequest;
import com.bulbul.erp.service.UserService;
import com.bulbul.erp.service.UserVerificationService;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Objects;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserVerificationService verificationService;


    //creating user
    @Override
    public User createUser(User user, HttpServletRequest request)
            throws UserAlreadyExistsException, MessagingException, UnsupportedEncodingException {

        User local = this.userRepository.findByUsername(user.getUsername());
        if(local!=null){
            System.out.println("User is already exists");
            throw new UserAlreadyExistsException("User already exists");
        }else{

            if (user.getProfile()==null){
                user.setProfile("default.png");
            }
            String siteUrl =getSiteURL(request)+"/user";
            String randomCode = RandomString.make(64);
            user.setVerificationCode(randomCode);
            local = this.userRepository.save(user);
            verificationService.sendVerificationEmail(user,siteUrl);
        }

        return local;
    }

    //getting user by username
    @Override
    public User getUser(String username) throws UserNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user==null){
            throw new UserNotFoundException("User Not Found ");

        }
        return this.userRepository.findByUsername(username);
    }

    @Override
    public List<User> fetchUserList() {
        return userRepository.findAll();
    }

    @Override
    public void deleteUser(Long userId) {
        this.userRepository.deleteById(userId);
    }

    @Override
    public User updateUser(Long userId, User user) {
        User userDB = userRepository.findById(userId).get();

        if(Objects.nonNull(user.getFirstName()) &&
                !"".equalsIgnoreCase(user.getFirstName())){
            userDB.setFirstName(user.getFirstName());
        }

        if(Objects.nonNull(user.getLastName()) &&
                !"".equalsIgnoreCase(user.getLastName())){
            userDB.setLastName(user.getLastName());
        }
        //......filled all the field

        return userRepository.save(userDB);
    }

    /** User created By System Only
     * This function use only for System Auto creation User
     * ***[Don't be use for another purpose]
     * */
    @Override
    public User createUser(User user) throws Exception {

        User local = this.userRepository.findByUsername(user.getUsername());
        if(local!=null){
            System.out.println("User is already exists");
            throw new UserAlreadyExistsException("User already exists");
        }else{

            if (user.getProfile()==null){
                user.setProfile("default.png");
            }
            local = this.userRepository.save(user);
        }
        return local;
    }

    /**
     * This function use for resetting user password
     * User can change or resetting password
     * */

    @Override
    public User resetPassword(ResetPasswordRequest resetPasswordRequest, Long id)
            throws UsernameOrPasswordNotMatchedException, UserNotFoundException, SimilarPasswordException {

        User local = userRepository.findById(id).get();
        if(local ==null){
            throw new UserNotFoundException("User not found with this id "+id);
        }else {
            if (bCryptPasswordEncoder.matches(resetPasswordRequest.getOldPassword(),local.getPassword())){
                if (bCryptPasswordEncoder.matches(resetPasswordRequest.getNewPassword(),local.getPassword())){
                    throw new SimilarPasswordException("New password can not be same as current password");
                }
                local.setPassword(bCryptPasswordEncoder.encode(resetPasswordRequest.getNewPassword()));
                userRepository.save(local);
                return local;
            }else {
                throw new UsernameOrPasswordNotMatchedException("Username or password not matched");
            }
        }

    }


    /**
     * This function Returns the current url of the project
     * This function use only verification link that user is received
     * */
    private String getSiteURL(HttpServletRequest request) {
        String siteURL = request.getRequestURL().toString();
        return siteURL.replace(request.getServletPath(), "");
    }


}
