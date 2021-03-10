package com.woniuxy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.woniuxy.domain.User;
import com.woniuxy.domain.UserInfo;
import com.woniuxy.mapper.UserInfoMapper;
import com.woniuxy.mapper.UserMapper;
import com.woniuxy.service.UserInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.woniuxy.utils.BASE64;
import com.woniuxy.utils.baiduOcr;
import com.woniuxy.vo.HandleNoPutawayVo;
import com.woniuxy.vo.HandlePutawayVo;
import com.woniuxy.vo.UserInfoVO;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.*;

import static com.woniuxy.utils.PictureUtil.getHashMapByJson;
import static com.woniuxy.utils.PictureUtil.request;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author clk
 * @since 2021-03-06
 */
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements UserInfoService {

    @Resource
    private UserMapper userMapper;
    /**
     * 王鹏的代码：
     */
    @Resource
    private UserInfoMapper userInfoMapper;
//    @Override
//    //查询用户详情信息
//    public UserInfoVO findUserInfo(Integer userId) {
//        UserInfoVO userInfoByUserId = userInfoMapper.findUserInfoByUserId(userId);
//        return userInfoByUserId;
//    }


    /**
     * 唐山林的代码：
     * @param userId
     * @return
     */
    @Override
    //查询用户详情信息
    public UserInfoVO findUserInfo(Integer userId) {
        UserInfoVO userInfoByUserId = userInfoMapper.findUserInfoByUserId(userId);
        return userInfoByUserId;
    }

    @Override
    //修改昵称
    public Integer updateUserNickname(Integer userId, String nickname) {//修改昵称
        User user = new User();
        user.setNickName(nickname);
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id",userId);
        int update = userMapper.update(user, updateWrapper);
        return update;
    }

    @Override
    //修改电话
    public Integer updateUserTel(Integer userId, String tel) {
        User user = new User();
        user.setTel(tel);
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id",userId);
        int update = userMapper.update(user, updateWrapper);
        return update;
    }

    @Override
    //修改邮箱
    public Integer updateUserEmail(Integer userId, String email) {
        UserInfo userInfo = new UserInfo();
        userInfo.setEmail(email);
        UpdateWrapper<UserInfo> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("user_id",userId);
        int update = userInfoMapper.update(userInfo, updateWrapper);
        return update;
    }

    @Override
    //修改QQ
    public Integer updateUserQQ(Integer userId, String QQ) {
        UserInfo userInfo = new UserInfo();
        userInfo.setQq(QQ);
        UpdateWrapper<UserInfo> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("user_id",userId);
        int update = userInfoMapper.update(userInfo, updateWrapper);
        return update;
    }

    @Override
    //修改密码
    public Integer updateUserPassword(Integer userId, String password, String newpassword) {
        User userDB = userMapper.selectById(userId);
        Md5Hash md5Hash = new Md5Hash(password, userDB.getSalt(), 2048);
        String md5Password = md5Hash.toHex();
        if (userDB.getPassword().equals(md5Password)) {
            Md5Hash md5Hash1 = new Md5Hash(newpassword, userDB.getSalt(), 2048);
            String md5Password1 = md5Hash1.toHex();
            System.out.println(md5Password1+"-----------------");
            User user = new User();
            user.setPassword(md5Password1);

            UpdateWrapper<User> wrapper = new UpdateWrapper<>();
            wrapper.eq("id",userId);

            int update = userMapper.update(user, wrapper);
            return update;
        }else{
            return 0;
        }
    }

    @Override
    //图片识别身份证信息
    public Integer updateUserInfo(Integer userId, MultipartFile file) {

        System.out.println("进来了");
        //上传文件路径到硬盘
        String path="D:/HBuilder/49期/三阶段/woniupark/static/images/identity".replace("/", File.separator);

        File file1=new File(path);
        if (!file1.exists()) {
            file1.mkdirs();
            System.out.println("新建了文件夹");
        }
        //旧文件名
        String oldName = file.getOriginalFilename();
        System.out.println("旧名字"+oldName);

        //避免重复（加uuid和系统当前时间）
        //生产uuid，并将uuid中间的“-”替换成”“
        String uuid = UUID.randomUUID().toString().replace("-", "");
        //获取系统时间
        long currentTimeMillis = System.currentTimeMillis();

        //得到最终上传文件名
        String uploadFileName=uuid+currentTimeMillis+oldName;
        System.out.println("新名字"+uploadFileName);
        try {
            //执行上传操作file
            File file2 = new File(path, uploadFileName);
            file.transferTo(file2);
            System.out.println("上传成功");
            System.out.println(path+"\\"+uploadFileName);



            //填写图片路径如："E:\\photo\\1.png"  开始身份信息识别
            File identity = new File(path+"/"+uploadFileName);
            System.out.println("11111111111111111111111111"+path+uploadFileName);
            // 进行BASE64位编码
            String imageBase = BASE64.encodeImgageToBase64(identity);
            imageBase = imageBase.replaceAll("\r\n", "");
            imageBase = imageBase.replaceAll("\\+", "%2B");
            // 百度云的文字识别接口,后面参数为获取到的token
            String httpUrl = "https://aip.baidubce.com/rest/2.0/ocr/v1/idcard?access_token=" + baiduOcr.getAuth();
            String httpArg = "detect_direction=true&id_card_side=front&image=" + imageBase;
            String jsonResult = request(httpUrl, httpArg);
            System.out.println("返回的结果--------->" + jsonResult);
            HashMap<String, String> map = getHashMapByJson(jsonResult);
            Collection<String> values = map.values();//将识别的文字json格式转换成集合
            if (!values.isEmpty()) {    //进行判断，如果是身份证照片进行存储到数据库
                System.out.println("=======================是身份证图片");
                Iterator<String> iterator2 = values.iterator();
                //用迭代器进行循环遍历，共五个参数
                /*第一个是地址信息      北京市海淀区上地十号七栋2单元110室
                 * 第二个是身份证号码     532101198906010015
                 * 第三个是性别            男
                 * 第四个是姓名            百度熊
                 * 第五个是出生日期        19890601  */
                ArrayList<String> arrayList = new ArrayList<>();
                while (iterator2.hasNext()) {
                    arrayList.add(iterator2.next());
                }
                UserInfo userInfo = new UserInfo();
                userInfo.setAddress(arrayList.get(0));
                userInfo.setIdCard(arrayList.get(1));
                userInfo.setSex(arrayList.get(2));
                userInfo.setName(arrayList.get(3));
                userInfo.setAuthentication("已认证");
                //userInfo.setBirthday(arrayList.get(5));
                UpdateWrapper<UserInfo> wrapper = new UpdateWrapper<>();
                wrapper.eq("user_id", userId);
                int update = userInfoMapper.update(userInfo, wrapper);
                return 1;
            }else{
                file2.delete();
                return 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("上传失败");
            return 0;

        }


    }

    @Override
    public String findUsername(Integer userId) {
        UserInfoVO userInfoVO = userInfoMapper.findUserInfoByUserId(userId);
        return userInfoVO.getNickname();
    }

    @Override
    //通过id拿头像，真实名称，身份证号码
    public UserInfo findCard(Integer userId) {
        System.out.println(userId);
        QueryWrapper<UserInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",userId);
        UserInfo userInfo = userInfoMapper.selectOne(wrapper);
        System.err.println(userInfo);

        if (ObjectUtils.isEmpty(userInfo)) {
            return null;
        }else{

            return userInfo;
        }

    }

    @Override
    //通过用户id保存头像并修改头像名称
    public Integer updateUserHead(Integer userId,MultipartFile file) {
        System.out.println("进来了");





        //上传文件路径到硬盘
        String path="D:/HBuilder/49期/三阶段/woniupark/static/images/head".replace("/", File.separator);

        File file1=new File(path);
        if (!file1.exists()) {
            file1.mkdirs();
        }

        UserInfo userInfoDB = userInfoMapper.selectById(userId);
        String head = userInfoDB.getHead();
        if(!head.equals("1.jpg")){
            new File(path,head).delete();
        }



        //旧文件名
        String oldName = file.getOriginalFilename();
        System.out.println("旧名字"+oldName);

        //避免重复（加uuid和系统当前时间）
        //生产uuid，并将uuid中间的“-”替换成”“
        String uuid = UUID.randomUUID().toString().replace("-", "");
        //获取系统时间
        long currentTimeMillis = System.currentTimeMillis();

        //得到最终上传文件名
        String uploadFileName=uuid+currentTimeMillis+oldName;
        System.out.println("新名字"+uploadFileName);
        try {
            //执行上传操作file
            file.transferTo(new File(path,uploadFileName));
            System.out.println("上传成功");

            UserInfo userInfo = new UserInfo();
            userInfo.setHead(uploadFileName);
            UpdateWrapper<UserInfo> wrapper = new UpdateWrapper<>();
            wrapper.eq("user_id",userId);
            userInfoMapper.update(userInfo, wrapper);


            return 1;
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("上传失败");
            return 0;

        }
    }

    @Override
    public List<HandlePutawayVo> findParkingInfoByUserId(Integer userId) {//查询已上架车位信息
        List<HandlePutawayVo> handlePutawayVo = userInfoMapper.findParkingInfoByUserId(userId);
        return handlePutawayVo;
    }

    @Override
    public List<HandleNoPutawayVo> findNoParkingInfoByUserId(Integer userId) {//查询未上架车位信息
        List<HandleNoPutawayVo> noParkingInfoByUserId = userInfoMapper.findNoParkingInfoByUserId(userId);
        return noParkingInfoByUserId;
    }


}
