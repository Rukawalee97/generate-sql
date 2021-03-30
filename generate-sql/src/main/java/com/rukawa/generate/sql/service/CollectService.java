package com.rukawa.generate.sql.service;

import com.rukawa.common.util.BeanUtil;
import com.rukawa.generate.sql.domain.Collect;
import com.rukawa.generate.sql.dto.CollectDto;
import com.rukawa.generate.sql.mapper.CollectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CollectService {

    @Autowired
    private CollectMapper collectMapper;

    public List<CollectDto> queryCollects(Collect collect) {
        return collectMapper.selectCollects(collect);
    }

    public int deleteCollect(Integer collectId) {
        return collectMapper.deleteCollectByPrimaryKey(collectId);
    }

    public int updateCollect(Collect collect) {
        if (BeanUtil.isEmpty(collect)) {
            return 0;
        }
        if (collectMapper.updateCollect(collect) == 0) {
            return collectMapper.insertCollect(collect);
        }
        return 1;
    }

}
