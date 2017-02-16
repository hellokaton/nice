package com.nice.config;

import com.blade.annotation.Order;
import com.blade.config.BaseConfig;
import com.blade.config.Configuration;
import com.blade.ioc.annotation.Component;
import com.blade.kit.base.Config;

@Component
@Order(sort = 3)
public class LoadConfig implements BaseConfig {

    @Override
    public void config(Configuration configuration) {
        Config config = configuration.config();

        Constant.SITE_URL = config.get("app.site_url");
        Constant.AES_SALT = config.get("app.aes_salt");
        Constant.UPLOAD_FOLDER = config.get("app.upload_dir");
        Constant.IMG_URL = config.get("app.img_url");

        Constant.MAIL_HOST = config.get("mail.smtp.host");
        Constant.MAIL_USER = config.get("mail.user");
        Constant.MAIL_USERNAME = config.get("mail.from");
        Constant.MAIL_PASS = config.get("mail.pass");

    }

}
