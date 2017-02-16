package com.nice.service;

import com.nice.model.Acode;

public interface AcodeService {

    String newAcode(String username, String signup);

    Acode getByCode(String code);

    void useCode(Integer id);
}
