package com.bootx.config;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Locale;

/**
 * 国际化配置
 */
public class LocaleResolverConfig  implements LocaleResolver  {

    private static final String I18N_LANGUAGE = "i18n_language";
    private static final String I18N_LANGUAGE_SESSION = "i18n_language_session";

    @Override
    public Locale resolveLocale(HttpServletRequest request) {
        String i18n_language = request.getParameter(I18N_LANGUAGE);
        Locale locale = Locale.getDefault();
        if(!StringUtils.isEmpty(i18n_language)) {
            String[] language = i18n_language.split("_");
            locale = new Locale(language[0], language[1]);

            //将国际化语言保存到session
            HttpSession session = request.getSession();
            session.setAttribute(I18N_LANGUAGE_SESSION, locale);
        }else {
            //如果没有带国际化参数，则判断session有没有保存，有保存，则使用保存的，也就是之前设置的，避免之后的请求不带国际化参数造成语言显示不对
            HttpSession session = request.getSession();
            Locale localeInSession = (Locale) session.getAttribute(I18N_LANGUAGE_SESSION);
            if(localeInSession != null) {
                locale = localeInSession;
            }
        }
        return locale;
    }

    @Override
    public void setLocale(HttpServletRequest request, HttpServletResponse response, Locale locale) {

    }
}
