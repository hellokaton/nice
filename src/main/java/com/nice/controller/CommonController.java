package com.nice.controller;

import com.blade.kit.PatternKit;
import com.blade.kit.StringKit;
import com.blade.mvc.annotation.MultipartParam;
import com.blade.mvc.annotation.Path;
import com.blade.mvc.annotation.Route;
import com.blade.mvc.http.HttpMethod;
import com.blade.mvc.http.Request;
import com.blade.mvc.http.Response;
import com.blade.mvc.multipart.FileItem;
import com.nice.config.Constant;
import com.nice.model.entity.User;
import com.nice.utils.SessionUtils;
import com.nice.utils.UUID;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Path
public class CommonController {

    /**
     * 上传
     */
    @Route(value = "/uploadfile", method = HttpMethod.POST)
    public void upload(Request request, Response response, @MultipartParam FileItem fileItem) {
        User user = SessionUtils.getLoginUser();
        if (null == user) {
            return;
        }
        if (null == fileItem) {
            return;
        }

        Optional<String> type = request.query("type");

        String suffix = StringKit.fileExt(fileItem.getFileName());
        if (StringKit.isNotBlank(suffix)) {
            suffix = "." + suffix;
        }

        if (!PatternKit.isImage(suffix)) {
            return;
        }
        String savePath = "";
        if (type.equals("avatar")) {
            savePath = type + "/users/" + user.getUsername() + suffix;
        } else {
            String fileName = UUID.UU32() + suffix;
            // topic/biezhi/s6vunp10vihq9rb239ln9lotjh.jpg
            savePath = type + "/" + user.getUsername() + "/" + fileName;
        }

        String filePath = Constant.UPLOAD_FOLDER + File.separator + savePath;
        File   file     = new File(filePath);
        try {
            if (file.exists()) {
                file.delete();
            }
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }

            FileOutputStream fos = new FileOutputStream(file);
            fos.write(fileItem.getData());
            fos.close();

            Map<String, Object> map = new HashMap<>();
            map.put("status", 200);
            map.put("savekey", savePath);
            map.put("url", Constant.IMG_URL + "/" + savePath);
            response.json(map);

        } catch (Exception e) {
            log.error("上传失败", e);
        }
    }

}