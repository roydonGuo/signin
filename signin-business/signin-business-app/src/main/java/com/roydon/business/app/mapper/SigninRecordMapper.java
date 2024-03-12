package com.roydon.business.app.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.roydon.business.app.domain.entity.SigninRecord;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * 签到记录表(SigninRecord)表数据库访问层
 *
 * @author roydon
 * @since 2024-02-19 16:11:09
 */
public interface SigninRecordMapper extends BaseMapper<SigninRecord>{

}

