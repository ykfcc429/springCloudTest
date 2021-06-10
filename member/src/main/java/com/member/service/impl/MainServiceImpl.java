package com.member.service.impl;

import com.commonTools.util.StringUtils;
import com.member.mapper.MainMapper;
import com.member.service.MainService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import java.nio.charset.StandardCharsets;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @author yankaifeng
 * 创建日期 2021/6/8
 */
@Service
@RequiredArgsConstructor
public class MainServiceImpl implements MainService {

    private final MainMapper mainMapper;

    private final JavaMailSender mailSender;

    @SuppressWarnings("All")
    @Resource(name = "redisTemplate")
    private ValueOperations<String, String> valueOperations;

    @Value("${spring.mail.properties.from}")
    private String from;

    @SneakyThrows
    @Override
    public boolean checkLoginAccount(String account, String password) {
        return mainMapper.checkLoginAccount(account, password) > 0;
    }

    @SneakyThrows
    @Override
    public String getToken(String account, String password, String ip) {
        String content = account+password+ip;
        return DigestUtils.md5DigestAsHex(content.getBytes(StandardCharsets.UTF_8));
    }

    @SneakyThrows
    @Override
    public void sendVerificationCode(String receiver)throws MessagingException {
        String key = "vfCode:" + receiver;
        String s = valueOperations.get(key);
        if(StringUtils.isBlank(s)) {
            String randomCode = getRandomCode();
            send(receiver, "验证码", randomCode);
            valueOperations.set(key, randomCode, 1, TimeUnit.MINUTES);
        }else throw new MessagingException("一分钟内可获取一次验证码");
    }

    @SneakyThrows
    public void send(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);
    }

    private String getRandomCode(){
        Random random = new Random();
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            result.append(random.nextInt(10));
        }
        return result.toString();
    }
}
