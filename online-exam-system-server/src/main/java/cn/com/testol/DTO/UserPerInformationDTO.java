package cn.com.testol.DTO;

import lombok.Data;

@Data
public class UserPerInformationDTO {
    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 用户名称
     */
    private String userName;

    /**
     * 性别
     */
    private String sex;

    /**
     * 手机号码
     */
    private String phone;

    /**
     * 电子邮箱
     */
    private String email;

    /**
     * 工作职位
     */
    private String work;

    /**
     * 用户登录密码
     */
    private String password;

}
