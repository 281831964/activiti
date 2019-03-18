package com.cms.test.springboot.service.impl;

import com.cms.test.springboot.dao.SettingDao;
import com.cms.test.springboot.domain.Setting;
import com.cms.test.springboot.service.SettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SettingServiceImpl implements SettingService {

    @Autowired
    private SettingDao settingDao;

    @Override
    public Setting findSettingById(int id) {
        Setting setting=settingDao.findById(id);
        return setting;
    }
}
