package org.example.spring_fullstack.user;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class EmailService {
    private final JavaMailSender mailSender;

    // 이메일 인증 메일 전송 메서드
    public void sendWelcomeMail(String email) {

        // 전송될 메일 객체 생성
        MimeMessage message = mailSender.createMimeMessage();
        try {
            // 이메일 인증에 사용할 고유 토큰 (UUID) 생성
            String uuid = UUID.randomUUID().toString();

            // MimeMessage를 더 쉽게 다루기 위한 Helper 객체 생성
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            // 수신자 이메일 설정
            helper.setTo(email);

            // 메일 제목 및 본문 설정
            String subject = "[회원가입 인증 메일] 환영합니다";
            // 사용자가 클릭하면 /user/verify?uuid=xxxx로 요청이 감 (인증 링크)
            String htmlContents = "<a href='http://localhost:8080/user/verify?uuid="+uuid+"'>이메일 인증</a>";

            helper.setSubject(subject);
            helper.setText(htmlContents, true);

            // 실제 메일 전송
            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
