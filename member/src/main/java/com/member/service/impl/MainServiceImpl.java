package com.member.service.impl;

import com.member.mapper.MainMapper;
import com.member.service.MainService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;

/**
 * @author yankaifeng
 * 创建日期 2021/6/8
 */
@Service
@RequiredArgsConstructor
public class MainServiceImpl implements MainService {

    private final MainMapper mainMapper;

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
}
