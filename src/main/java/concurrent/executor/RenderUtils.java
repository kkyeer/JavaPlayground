package concurrent.executor;

import java.util.Random;

/**
 * @Author: kkyeer
 * @Description:  工具类
 * @Date:Created in 15:53 2019/5/20
 * @Modified By:
 */
class RenderUtils {
    private static final Random RANDOM = new Random();
    /**
     * 渲染文字，假设每个渲染需要10毫秒且不会失败
     * @param text 要渲染的文字
     * @return 是否渲染成功
     */
    static boolean renderText(String text) {
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
            return false;
        }
//        System.out.println("rendered :" + text);
        return true;
    }

    /**
     * 模拟下载图片，下载时间100-300毫秒随机
     * @param url url
     * @return 图片信息，假设是string
     */
    static String downloadImage(String url){
        try {
            int consumedTime = RANDOM.nextInt(200) + 100;
            Thread.sleep(consumedTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        }
        return "image:"+url;
    }

    /**
     * 模拟渲染图片，假设渲染时长为100毫秒
     * @param imageInfo
     * @return 是否渲染成功
     */
    public static boolean renderImage(String imageInfo){
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
            return false;
        }
//        System.out.println("rendered :" + imageInfo);
        return true;
    }
}
