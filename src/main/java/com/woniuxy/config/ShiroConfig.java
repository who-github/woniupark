package com.woniuxy.config;

import com.woniuxy.realm.ConstomerRealm;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;


//shiro的配置类：
@Configuration
public class ShiroConfig {

//    realm:连接底层的realm
    @Bean
    public Realm realm(){
        ConstomerRealm constomerRealm = new ConstomerRealm();
//        设置MD5的解码：使用jwt解决跨域问题时，不能使用解密，或出现密码对应不上情况
//        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
//        hashedCredentialsMatcher.setHashAlgorithmName("MD5");
//        hashedCredentialsMatcher.setHashIterations(1024);
//        constomerRealm.setCredentialsMatcher(hashedCredentialsMatcher);

//        开启缓存：减少数据的压力
        constomerRealm.setCachingEnabled(true);
        constomerRealm.setAuthorizationCachingEnabled(true);
        constomerRealm.setAuthenticationCachingEnabled(true);
        constomerRealm.setCacheManager(new EhCacheManager());

//        返回值
        return constomerRealm;
    }

    //    注册一个rememberMe管理器：
    @Bean
   public CookieRememberMeManager cookieRememberMeManager(){
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();

//        创建cookie:需要添加参数
        SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
//        设置cookie
        simpleCookie.setMaxAge(7*24*60*60);
//        将cookie添加到管理器中：
        cookieRememberMeManager.setCookie(simpleCookie);
//        设置管理器的解码编码格式：24位，最后两位必须为==
        cookieRememberMeManager.setCipherKey(Base64.decode("a1b2c3d4e5f6g7h8i9j10k=="));

        return cookieRememberMeManager;
    }


//    注册安全管理器：
    @Bean
    public DefaultWebSecurityManager defaultWebSecurityManager(Realm realm,CookieRememberMeManager cookieRememberMeManager){
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();

//        将realm和cookieRememberMeManager交于安全管理器
        defaultWebSecurityManager.setRealm(realm);
//        将rememberMe管理器交于安全管理器
        defaultWebSecurityManager.setRememberMeManager(cookieRememberMeManager);

        return defaultWebSecurityManager;
    }

//    注册shiroFilter过滤器：
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(DefaultWebSecurityManager defaultWebSecurityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();

//        设过滤器的过滤信息：
        LinkedHashMap<String, String> filterChainDefinitionMap = new LinkedHashMap<>();

//        linked为有序,首先添加白名单,
        filterChainDefinitionMap.put("/css/**","anon");
        filterChainDefinitionMap.put("/js/**","anon");

        filterChainDefinitionMap.put("/user/login","anon");
        filterChainDefinitionMap.put("/user/register","anon");
        filterChainDefinitionMap.put("/page/register.html","anon");
//        注销路径
        filterChainDefinitionMap.put("/user/logout","logout");
//        目前还不清楚前后端分离之后，怎么拦截，所以直接全部放行
        filterChainDefinitionMap.put("/**","user");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
//        设置阻拦之后跳转的界面
        shiroFilterFactoryBean.setLoginUrl("/page/login.html");

        shiroFilterFactoryBean.setSecurityManager(defaultWebSecurityManager);

        return shiroFilterFactoryBean;
    }

}
