package com.nice.service.impl;

import com.blade.ioc.annotation.Bean;
import com.blade.jdbc.page.Page;
import com.blade.kit.DateKit;
import com.blade.kit.EncrypKit;
import com.blade.kit.StringKit;
import com.blade.kit.UUID;
import com.nice.exception.TipException;
import com.nice.ext.ActionType;
import com.nice.model.Acode;
import com.nice.model.User;
import com.nice.service.UserService;
import com.nice.utils.EmailUtils;
import com.nice.utils.ThreadUtils;

import java.util.ArrayList;
import java.util.List;

@Bean
public class UserServiceImpl implements UserService {

    @Override
    public User getUserById(String username) {
        if (null == username) {
            return null;
        }
        User user = new User();
        return user.find(user);
    }

    @Override
    public User update(User user) throws Exception {
        if (null == user) {
            throw new TipException("对象为空");
        }
        try {
            user.update();
            return getUserById(user.getUsername());
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public void delete(String username) throws Exception {
        if (null == username) {
            throw new TipException("主键为空");
        }
        try {
            User user = new User();
            user.delete(username);
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public void signup(String username, String password, String email, String avatar) {
        if (StringKit.isBlank(username)) {
            throw new TipException("用户名不能为空");
        }
        if (StringKit.isBlank(password)) {
            throw new TipException("密码不能为空");
        }
        if (StringKit.isBlank(email)) {
            throw new TipException("邮箱不能为空");
        }

        User user = new User();
        user.setUsername(username);

        long count = user.count();
        if (count > 0) {
            throw new TipException("该用户名已经被注册, 请更换");
        }

        User temp = new User();
        temp.setEmail(email);
        count = temp.count();
        if (count > 0) {
            throw new TipException("该邮箱已经被绑定, 请更换");
        }

        String pwd = EncrypKit.md5(username + password);
        user.setPassword(pwd);
        user.setEmail(email);
        user.setState(2);
        if (StringKit.isBlank(avatar)) {
            avatar = "";
        }
        user.setAvatar(avatar);
        user.setCreated(DateKit.nowUnix());
        user.setNickname(username);

        user.save();

        ThreadUtils.submit(() -> {
            // 发送激活邮件
            Acode acode = Acode.builder()
                    .username(username)
                    .type("signup")
                    .created(DateKit.nowUnix())
                    .expired(0)
                    .code(UUID.UU32())
                    .build();
            acode.save();
            EmailUtils.sendSignup(email, username, acode.getCode());
        });

    }

    @Override
    public void active(String code) {
        Acode acode = new Acode();
        acode.setCode(code);
        acode = acode.find();
        if (null == acode) {
            throw new TipException("无效的激活码");
        }
        if (acode.getExpired() > 0 && acode.getExpired() < DateKit.nowUnix()) {
            throw new TipException("激活码已经过期");
        }
        if (acode.getUsed()) {
            throw new TipException("该激活码已经被使用");
        }

        String username = acode.getUsername();
        User   user     = new User();
        user.setState(1);
        user.where("username", username).update();

        Acode temp = new Acode();
        temp.setUsed(true);
        temp.update(acode.getId());

    }

    @Override
    public User signin(String loginname, String password) {

        if (StringKit.isBlank(loginname) || StringKit.isBlank(password)) {
            throw new TipException("用户名和密码不能为空");
        }

        User user = new User();
        user.where("username", loginname).or("email", loginname);
        long count = user.count();
        if (count < 1) {
            throw new TipException("不存在的用户");
        }

        String pwd = EncrypKit.md5(loginname + password);
        String sql = "select * from t_user where (username = ? or email = ?) and password = ?";

        User u = user.query(sql, loginname, loginname, pwd);
        if (null == u) {
            throw new TipException("用户名或密码错误");
        }
        if (u.getState() == 0) {
            throw new TipException("该用户已经被停用");
        }
        if (u.getState() == 0) {
            throw new TipException("该用户未激活");
        }
        return u;
    }

    @Override
    public void upCount(ActionType actionType, String username, int hits) {
        String m    = hits > 0 ? "+" : "";
        String type = actionType.toString();
        String sql  = "update t_user set " + type + " = (" + type + m + hits + ") where username = '" + username + "'";
        new User().execute(sql);
    }

    @Override
    public List<User> getUsers(String username, int page, int limit) {
        if (page <= 0) {
            page = 1;
        }
        if (limit < 0 || limit > 20) {
            limit = 10;
        }
        if (StringKit.isBlank(username) || username.length() < 5) {
            return new ArrayList<>(0);
        }

        User user = new User();
        user.like("username", "%" + username + "%");
        Page<User> pageUser = user.page(page, limit);
        return pageUser.getRows();
    }
}
