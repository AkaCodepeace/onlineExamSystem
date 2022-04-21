package cn.com.testol.service;


import cn.com.testol.DTO.UserPerInformationDTO;
import cn.com.testol.entity.User;
import cn.com.testol.utils.Msg;

public interface UserService {
    //登陆
    public User login(String name, String password);

    //查找
    public User getUserById(int u_id);
//    public User getUserByEmail(String email);
//    public User getUserByPhone(String phone);
    public Msg queryUserByC_id(int c_id, int userId);

    //注册
    public Msg addUser(User user,String password);

    public Msg getRole(Integer userId);

    public Msg changeRole(Integer userId);

    //修改用户信息
    public int updateUser(User user);

    //注销用户
    public int deleteUser(int id);

    //查询教师信息
    Msg getTchManageList(String role,String keyword);

    //通过userId查询用户信息
    Msg getUserMessageById(String userId);



    //修改用户信息和密码
    Msg updateUserAndPassword(User user, UserPerInformationDTO userPerInformationDTO);

    Msg deleteUserManageById(int userId);
}
