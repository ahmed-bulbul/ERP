package com.bulbul.erp.service;

import com.bulbul.erp.entity.User;
import java.io.UnsupportedEncodingException;
import javax.mail.MessagingException;

public interface UserVerificationService {

    public void sendVerificationEmail(User user, String siteURL)
            throws MessagingException, UnsupportedEncodingException;

    public boolean verify(String verificationCode);
}
