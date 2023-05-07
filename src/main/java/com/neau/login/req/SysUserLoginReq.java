package com.neau.login.req;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class SysUserLoginReq {

    private String LoginName;

    private String password;
}
