package design.pattern.proxy.priviledged;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @Author: kkyeer
 * @Description: 菜单服务的代理
 * @Date:Created in 17:22 2019/7/3
 * @Modified By:
 */
class MenuServiceProxy implements InvocationHandler {
    private final MenuService menuService;

    MenuServiceProxy(MenuService menuService) {
        this.menuService = menuService;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        int id = (int) args[0];
        if (id < 10) {
            return "Unauthorized";
        }else {
            return this.menuService.getMenu(id);
        }
    }
}
