package com.study.web.component;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.LocaleResolver;
import org.thymeleaf.util.StringUtils;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

/**
 * 在链接上携带区域信息
 * @Author Harlan
 * @Date 2020/10/5
 */
public class MyLocaleResolver implements LocaleResolver {

    @Override
    public Locale resolveLocale(HttpServletRequest httpServletRequest) {
        Locale locale = null;
        String l = httpServletRequest.getParameter("l");
        if (!StringUtils.isEmpty(l)){
            //获取国际语言信息
            String[] local = l.split("_");
            locale = new Locale(local[0], local[1]);
        }else {
            //获取默认的信息
            locale = Locale.getDefault();
        }
        return locale;
    }

    @Override
    public void setLocale(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Locale locale) {

    }
}
