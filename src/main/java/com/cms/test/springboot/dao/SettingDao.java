package com.cms.test.springboot.dao;

import com.cms.test.springboot.domain.Setting;
import org.apache.ibatis.annotations.Param;

public interface SettingDao {
    Setting findById(@Param("id") int cityName);
}
