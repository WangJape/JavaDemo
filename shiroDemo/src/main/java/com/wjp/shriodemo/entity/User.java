package com.wjp.shriodemo.entity;

import lombok.*;

//@EqualsAndHashCode
//@Data //生成setter/getter、equals、canEqual、hashCode、toString方法
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private String username;

    private String paaword;

    private String permission;

    private String role;

}
