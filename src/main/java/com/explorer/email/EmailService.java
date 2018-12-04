package com.explorer.email;

import com.explorer.exception.ServiceException;

public interface EmailService {

    void sendMail(String to, String subject, String htmlContent) throws ServiceException;

}
