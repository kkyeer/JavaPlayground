package design.pattern.composite;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @Author: kkyeer
 * @Description: 菜单对象，非线程安全
 * @Date:Created in 14:51 2019/7/24
 * @Modified By:
 */
class MenuItem {
    private final List<MenuItem> itemList = new ArrayList<>();
    private final int id;
    private final String label;

    public MenuItem(int id, String label) {
        this.id = id;
        this.label = label;
    }

    /**
     * 获取所有子元素
     *
     * @return 子元素
     */
    public Iterator<MenuItem> getSubItems() {
        return itemList.iterator();
    }

    /**
     * 新增子元素
     *
     * @param menuItem 菜单
     */
    public void addSubItem(MenuItem menuItem) {
        itemList.add(menuItem);
    }

    /**
     * 清除子元素
     *
     * @param menuItem 要删除的菜单
     */
    public void removeSubItem(MenuItem menuItem) {
        itemList.remove(menuItem);
    }

    /**
     * 是否叶子节点
     */
    public boolean isLeaf() {
        return itemList.size() == 0;
    }

    public int getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }
}
