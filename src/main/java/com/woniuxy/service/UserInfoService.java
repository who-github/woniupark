package com.woniuxy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.woniuxy.domain.UserInfo;
import com.woniuxy.vo.HandleNoPutawayVo;
import com.woniuxy.vo.HandlePutawayVo;
import com.woniuxy.vo.UserInfoVO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author clk
 * @since 2021-03-06
 */
public interface UserInfoService extends IService<UserInfo> {

    /**
     * 王鹏的代码：
     */
//    UserInfoVO findUserInfo(Integer userId);//查询用户详情信息，通过用户id

    /**
     * 唐山林的代码：
     */
    UserInfoVO findUserInfo(Integer userId);//查询用户详情信息，通过用户id

    Integer updateUserNickname(Integer userId,String nickname);//修改昵称

    Integer updateUserTel(Integer userId,String tel);//修改电话

    Integer updateUserEmail(Integer userId,String email);//修改邮箱

    Integer updateUserQQ(Integer userId,String QQ);//修改QQ号

    Integer updateUserPassword(Integer userId, String password, String newpassword);//修改密码

    Integer updateUserInfo(Integer userId, MultipartFile file);//图片识别信息

    String findUsername(Integer userId);//通过id拿昵称

    UserInfo findCard(Integer userId);//通过id拿头像，真实名称，身份证号码

    Integer updateUserHead(Integer userId, MultipartFile file);//修改头像

    List<HandlePutawayVo> findParkingInfoByUserId(Integer userId);////通过用户id查询车位已上架的信息

    List<HandleNoPutawayVo> findNoParkingInfoByUserId(Integer userId);////通过用户id查询车位已上架的信息

}
