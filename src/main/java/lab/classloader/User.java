package lab.classloader;

/**
 * @Author: kkyeer
 * @Description:
 * @Date:Created in 5:40 PM 2021/4/26
 * @Modified By:
 */
public class User {
    private Role role;

    public User(){

    }

    public void setRole(Role role) {
        this.role = role;
    }

    public void hehe(){
        this.role.haha();
    }
}
