package lab.classloader;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

/**
 * @Author: kkyeer
 * @Description: 跨Classloader的实例的方法调用
 * @Date:Created in 5:39 PM 2021/4/26
 * @Modified By:
 */
public class TestMethodInvokeCrossClassLoader {
    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        ClassLoader userClassLoader = new MyClassLoader();
        Thread t1 = new Thread(
                ()->{
                    //                        Class<User> userClass =  (Class<User> )userClassLoader.loadClass("lab.classloader.User");
//                        System.out.println(userClass.getClassLoader().getClass().getName());
                    System.out.println(Thread.currentThread().getContextClassLoader());
                    User user = new User();
                    ClassLoader roleClassLoader = new MyClassLoader();
                    Thread t2 = new Thread(
                            ()->{
                                try {
                                    Class<Role> roleClass =  (Class<Role>) roleClassLoader.loadClass("lab.classloader.Role");
                                    System.out.println(roleClass.getClassLoader().getClass().getName());
                                    Role role =  roleClass.newInstance();
                                    user.setRole(role);
                                    user.hehe();
                                } catch (ClassNotFoundException e) {
                                    e.printStackTrace();
                                } catch (InstantiationException e) {
                                    e.printStackTrace();
                                } catch (IllegalAccessException e) {
                                    e.printStackTrace();
                                }
                            }
                    );
                    t2.setContextClassLoader(userClassLoader);
                    t2.start();
                }
        );
        t1.setContextClassLoader(userClassLoader);
        t1.start();
    }

    private static class MyClassLoader extends ClassLoader{

        /**
         * Finds the class with the specified <a href="#name">binary name</a>.
         * This method should be overridden by class loader implementations that
         * follow the delegation model for loading classes, and will be invoked by
         * the {@link #loadClass <tt>loadClass</tt>} method after checking the
         * parent class loader for the requested class.  The default implementation
         * throws a <tt>ClassNotFoundException</tt>.
         *
         * @param name The <a href="#name">binary name</a> of the class
         * @return The resulting <tt>Class</tt> object
         * @throws ClassNotFoundException If the class could not be found
         * @since 1.2
         */
        @Override
        protected Class<?> findClass(String name) throws ClassNotFoundException {
            FileInputStream fis = null;
            try {
                if ("lab.classloader.User".equals(name)) {
                    fis = new FileInputStream("/Users/chengyingzhang/workspace/study/JavaPlayground/target/classes/lab/cglib/User.class");
                }else if ("lab.classloader.Role".equals(name)){
                    fis = new FileInputStream("/Users/chengyingzhang/workspace/study/JavaPlayground/target/classes/lab/cglib/Role.class");
                }else {
                    return super.findLoadedClass(name);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            byte[] classData = new byte[4096];
            int length = 0;
            try {
                length = fis.read(classData);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return defineClass(null, classData, 0, length);
        }

        @Override
        public Class<?> loadClass(String name) throws ClassNotFoundException {
            return super.loadClass(name,true);
        }
    }
}
