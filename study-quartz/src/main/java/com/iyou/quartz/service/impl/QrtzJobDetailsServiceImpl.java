package com.iyou.quartz.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iyou.quartz.entity.QrtzJobDetails;
import com.iyou.quartz.mapper.QrtzJobDetailsMapper;
import com.iyou.quartz.service.QrtzJobDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author weiguohua
 * @since 2019-11-23
 */
@Service
public class QrtzJobDetailsServiceImpl extends ServiceImpl<QrtzJobDetailsMapper, QrtzJobDetails> implements QrtzJobDetailsService {
    @Override
    public List<QrtzJobDetails> queryAll() {
        return this.lambdaQuery().list();
    }

    @Override
    public List<QrtzJobDetails> queryByGroup(String group) {
        return this.lambdaQuery()
                .eq(QrtzJobDetails::getJobGroup, group)
                .list();
    }

    @Override
    public QrtzJobDetails queryByGroupAndName(String group, String name) {
        return this.lambdaQuery()
                .eq(QrtzJobDetails::getJobGroup, group)
                .eq(QrtzJobDetails::getJobName, name)
                .one();
    }
}
