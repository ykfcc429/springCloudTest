package com.commonTools.util;

import org.springframework.stereotype.Component;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

/**
 * @author yankaifeng
 * 创建日期 2021/6/8
 */
@Component
public class ServletUtil {

    private static final String[] ADDRESS_HEADER = { "X-Forwarded-For", "Proxy-Client-IP", "WL-Proxy-Client-IP",
            "X-Real-IP" };
    private static final String UNKNOWN = "unknown";

    public String getRemoteAddress(ServletRequest request) {
        String address = null;
        if (request instanceof HttpServletRequest) {
            HttpServletRequest hsr = (HttpServletRequest) request;
            for (String header : ADDRESS_HEADER)
                if (StringUtils.isBlank(address) || UNKNOWN.equalsIgnoreCase(address)) {
                    address = hsr.getHeader(header);
                } else {
                    break;
                }
        }
        if (StringUtils.isBlank(address) || UNKNOWN.equalsIgnoreCase(address))
            address = request.getRemoteAddr();
        else {
            int i = address.indexOf(",");
            if (i > 0)
                address = address.substring(0, i);
        }
        return address;
    }
}
