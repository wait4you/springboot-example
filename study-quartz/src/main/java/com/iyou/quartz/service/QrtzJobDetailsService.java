package com.iyou.quartz.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.iyou.quartz.entity.QrtzJobDetails;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author weiguohua
 * @since 2019-11-23
 */
public interface QrtzJobDetailsService extends IService<QrtzJobDetails> {
    List<QrtzJobDetails> queryAll();

    List<QrtzJobDetails> queryByGroup(String group);

    QrtzJobDetails queryByGroupAndName(String group, String name);
}
