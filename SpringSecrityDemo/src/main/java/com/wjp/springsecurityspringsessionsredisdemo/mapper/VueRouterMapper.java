package com.wjp.springsecurityspringsessionsredisdemo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wjp.springsecurityspringsessionsredisdemo.entity.VueRouter;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.ArrayList;

public interface VueRouterMapper extends BaseMapper<VueRouter> {

    @Select("<script>"+
            "select v.vuerouter_path,v.vuerouter_path_name,v.vuerouter_component,v.elementui_menuicon,v.vuerouter_orderscore " +
            "from account a " +
            "left join account_role ar on a.user_account = ar.user_account " +
            "left join role_vuerouter rv on ar.role_code = rv.role_code " +
            "left join vuerouter v on rv.vuerouter_path = v.vuerouter_path " +
            "<if test='account!=null' > " +
                "where a.user_account = #{account} " +
            "</if>" +
            "ORDER BY v.vuerouter_orderscore " +
            "</script>")
    ArrayList<VueRouter> queryUserRoleAllVueRouter(@Param("account")String account);

}
