package com.rukawa.generate.sql.mapper;

import com.rukawa.generate.sql.domain.Collect;
import com.rukawa.generate.sql.dto.CollectDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CollectMapper {

    List<CollectDto> selectCollects(Collect collect);

    int insertCollect(Collect collect);

    int updateCollect(Collect collect);

    int deleteCollectByPrimaryKey(Integer collectId);

}
