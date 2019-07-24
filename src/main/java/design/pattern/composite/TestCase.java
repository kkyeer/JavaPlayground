package design.pattern.composite;

import java.util.Iterator;

/**
 * @Author: kkyeer
 * @Description: 试一试组合模式
 * @Date:Created in 15:03 2019/7/24
 * @Modified By:
 */
class TestCase {
    public static void main(String[] args) {
        MenuItem root = createMenu();
        printMenu(root,"");

    }

    private static MenuItem createMenu() {
        MenuItem root = new MenuItem(1, "bar");
        MenuItem file = new MenuItem(11, "file");
        MenuItem createFile = new MenuItem(111, "createFile");
        MenuItem openFile = new MenuItem(112, "openFile");
        file.addSubItem(createFile);
        file.addSubItem(openFile);
        root.addSubItem(file);
        MenuItem about = new MenuItem(12, "about");
        root.addSubItem(about);
        return root;
    }

    private static void printMenu(MenuItem root,String prefixSpaces) {
        System.out.println(prefixSpaces + root.getLabel());
        Iterator<MenuItem> menuItemIterator = root.getSubItems();
        while (menuItemIterator.hasNext()) {
            printMenu(menuItemIterator.next(), prefixSpaces + "    ");
        }
    }
}
