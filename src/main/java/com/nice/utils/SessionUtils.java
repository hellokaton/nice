package com.nice.utils;

import com.blade.mvc.WebContext;
import com.blade.mvc.http.Session;
import com.nice.config.Constant;
import com.nice.model.entity.User;
import com.blade.kit.StringKit;
import com.blade.mvc.http.Request;
import com.blade.mvc.http.Response;

import java.util.Optional;

/**
 * Created by biezhi on 2017/2/14.
 */
public final class SessionUtils {


    public static void set(Session session, String name, Object value){
        if(null != session && StringKit.isNotBlank(name) && null != value){
            removeUser(session);
            session.attribute(name, value);
        }
    }

    public static <T> T get(Session session, String name){
        if(null != session && StringKit.isNotBlank(name)){
            return session.attribute(name);
        }
        return null;
    }

    public static void setLoginUser(Session session, User login_user){
        if(null != session && null != login_user){
            removeUser(session);
            session.attribute(Constant.LOGIN_SESSION_KEY, login_user);
        }
    }

    public static void removeUser(Session session){
        session.removeAttribute(Constant.LOGIN_SESSION_KEY);
    }

    public static User getLoginUser() {
        Session session = WebContext.request().session();
        if(null == session){
            return null;
        }
        User user = session.attribute(Constant.LOGIN_SESSION_KEY);
        return user;
    }


    private static final int one_month = 30*24*60*60;

    public static void setCookie(Response response, String cookieName, Integer uid) {
        if(null != response && StringKit.isNotBlank(cookieName) && null != uid){
            try {
                String val = Utils.encrypt(uid.toString(), Constant.AES_SALT);
                boolean isSSL = Constant.SITE_URL.startsWith("https");
                response.cookie("/", cookieName, val, one_month, isSSL);
            } catch (Exception e){}
        }
    }

    public static void setCookie(Response response, String cookieName, String value) {
        if(null != response && StringKit.isNotBlank(cookieName) && StringKit.isNotBlank(value)){
            try {
                String data = Utils.encrypt(value, Constant.AES_SALT);
                boolean isSSL = Constant.SITE_URL.startsWith("https");
                response.removeCookie(cookieName);

                String path = WebContext.contextPath();
                response.cookie(path, cookieName, data, 604800, isSSL);
            } catch (Exception e){
            }
        }
    }

    public static String getCookie(Request request, String cookieName) {
        if(null != request && StringKit.isNotBlank(cookieName)){
            Optional<String> val = request.cookie(cookieName);
            if (val.isPresent()) {
                try {
                    return Utils.decrypt(val.get(), Constant.AES_SALT);
                } catch (Exception e) {
                }
                return "";
            }
        }
        return null;
    }

    public static void removeCookie(Response response) {
        response.removeCookie(Constant.USER_IN_COOKIE);
        response.removeCookie(Constant.JC_REFERRER_COOKIE);
    }

}
