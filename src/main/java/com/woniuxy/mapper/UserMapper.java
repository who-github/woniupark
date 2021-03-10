package com.woniuxy.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.woniuxy.domain.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.woniuxy.vo.TanantVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author clk
 * @since 2021-03-06
 */
public interface UserMapper extends BaseMapper<User> {

    @Select("SELECT u.id,ui.`name`, balance,address,tel,credit,qq,email,head FROM " +
            "t_user as u JOIN t_user_info as ui on u.id =ui.user_id ${ew.customSqlSegment}")
    Page<TanantVO> findAllTanant(Page<TanantVO> tanantVOPage,@Param(Constants.WRAPPER) QueryWrapper<TanantVO> wrapper);

    @Select("SELECT u.id,ui.`name`, balance,address,tel,credit,qq,email,head FROM " +
            "t_user as u JOIN t_user_info as ui on u.id =ui.user_id where ui.`name` =#{username}")
    TanantVO findTanantByName(String username);

    @Select("SELECT u.id,ui.`name`, balance,address,tel,credit,qq,email,head FROM " +
            "t_user as u JOIN t_user_info as ui on u.id =ui.user_id ${ew.customSqlSegment}")
    Page<TanantVO> findAllLesser(Page<TanantVO> tanantVOPage,@Param(Constants.WRAPPER) QueryWrapper<TanantVO> wrapper);

}
