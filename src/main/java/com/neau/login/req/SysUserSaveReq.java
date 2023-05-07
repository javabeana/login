package com.neau.login.req;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class SysUserSaveReq {
    private Long id;

    private String LoginName;

    private String name;

    private String password;
}
