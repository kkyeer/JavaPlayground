package design.pattern.proxy.priviledged;

/**
 * @Author: kkyeer
 * @Description: 菜单服务
 * @Date:Created in 17:20 2019/7/3
 * @Modified By:
 */
interface MenuService {
    /**
     * 根据用户ID返回菜单
     * @param userId 用户ID
     * @return 菜单
     * @throws Exception 无此用户
     */
    String getMenu(int userId) throws Exception;
}
