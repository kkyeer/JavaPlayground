package design.pattern.proxy.priviledged;

import java.lang.reflect.Proxy;

/**
 * @Author: kkyeer
 * @Description: 试用代理
 * @Date:Created in 17:25 2019/7/3
 * @Modified By:
 */
class TestCase {
    public static void main(String[] args) throws Exception {
        MenuServiceProxy menuServiceProxy = new MenuServiceProxy(new MenuServiceImpl());
        MenuService menuService = (MenuService) Proxy.newProxyInstance(
                TestCase.class.getClassLoader(),
                new Class[]{MenuService.class},
                menuServiceProxy
        );
        System.out.println(menuService.getMenu(1));
        System.out.println(menuService.getMenu(20));
    }
}
