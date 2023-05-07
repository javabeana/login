package com.neau.login.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
//跟表名对应
@TableName("sys_user")
public class SysUserEntity {
    private Long id;

    private String LoginName;

    private String name;

    private String password;
}
