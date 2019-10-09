package com.zs.admin.param;

import com.zs.admin.api.constant.Constant;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @Auther: zs
 * @Date: 2019/10/5 07:37
 * @Description:
 */
@Data
@Accessors(chain = true)
public class InitMenu implements Serializable {

    private ClearInfo clearInfo = new ClearInfo(Constant.CLEAR_URL);
    private LogoInfo logoInfo = new LogoInfo(Constant.LOGO_TITLE,Constant.LOGO_IMG,Constant.LOGO_HREF);
    private HomeInfo homeInfo = new HomeInfo(Constant.HOME_TITLE,Constant.HOME_ICON,Constant.HOME_HREF);
    /**
     * 名字适配json，不能乱改
     */
    private Map<String,Menu> menuInfo;

    /**
     * 请缓存配置
     */
    @Data
    @AllArgsConstructor
    class ClearInfo{
        private String clearUrl;

    }

    /**
     * logo
     */
    @Data
    @AllArgsConstructor
    class LogoInfo{
        private String title;
        private String image;
        private String href;
    }

    @Data
    @AllArgsConstructor
    public class HomeInfo {
        private String title;
        private String icon;
        private String href;
    }

}
