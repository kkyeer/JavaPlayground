package design.pattern.proxy.priviledged;

/**
 * @Author: kkyeer
 * @Description: 实现类
 * @Date:Created in 17:26 2019/7/3
 * @Modified By:
 */
class MenuServiceImpl implements MenuService{
    /**
     * 根据用户ID返回菜单
     * @param userId 用户ID
     * @return 菜单
     */
    @Override
    public String getMenu(int userId) {
        return "Fake menu:" + userId;
    }
}
