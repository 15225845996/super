package com.zs.admin.param;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * @Auther: zs
 * @Date: 2019/10/5 09:53
 * @Description:菜单
 */
@Data
public class Menu {
    private Long id;
    private String title;
    private String href;
    private String icon;
    private String target;
    private Long ordinal;
    private List<Menu> child;
}
