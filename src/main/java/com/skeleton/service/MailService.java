package com.skeleton.service;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class MailService {

//    private final JavaMailSender mailSender;
//    private final MailContentBuilder mailContentBuilder;
//
//    @Async
//    void sendMail(NotificationEmail notificationEmail) throws Exception {
//        MimeMessagePreparator messagePreparation = mimeMessage -> {
//            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
//            messageHelper.setFrom("eng100shevoo@gmail.com");
//            messageHelper.setTo(notificationEmail.getRecipient());
//            messageHelper.setSubject(notificationEmail.getSubject());
//            messageHelper.setText(notificationEmail.getBody());
//        };
//        try {
//            mailSender.send(messagePreparation);
//            log.info("Activation email sent!!");
//        } catch (MailException e) {
//            log.error("Exception occurred when sending mail", e);
//            throw new Exception("Exception occurred when sending mail to " + notificationEmail.getRecipient(), e);
//        }
//    }

}
