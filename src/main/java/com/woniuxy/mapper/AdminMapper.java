package com.woniuxy.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.woniuxy.domain.Admin;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.woniuxy.vo.ProportyVO;

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
public interface AdminMapper extends BaseMapper<Admin> {

    @Select("SELECT role_name FROM  " +
            "t_admin as a join t_admin_role as ar on a.admin_id=ar.aid " +
            "JOIN t_role as r on  ar.rid = r.role_id " +
            "WHERE a.`name` = #{username}")
    String findRoleByName(String username);


    @Select("SELECT * FROM `t_admin` JOIN t_admin_role on t_admin_role.aid = t_admin.admin_id WHERE t_admin_role.rid=2 and name =#{username}")
    ProportyVO findPeopertyByName(String username);

    @Select("SELECT * FROM `t_admin` JOIN t_admin_role on t_admin_role.aid = t_admin.admin_id ${ew.customSqlSegment}")
    Page<ProportyVO> findAllPeoperty(Page<ProportyVO> page,@Param(Constants.WRAPPER) QueryWrapper<ProportyVO> wrapper);


    @Select("SELECT role_name FROM  " +
            "t_admin as a join t_admin_role as ar on a.admin_id=ar.aid " +
            "JOIN t_role as r on  ar.rid = r.role_id " +
            "WHERE ar.rid = 2 ")
    List<String> findAdminTotal();
}
