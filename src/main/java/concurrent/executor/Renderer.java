package concurrent.executor;

import java.util.List;

/**
 * @Author: kkyeer
 * @Description: 渲染器接口
 * @Date:Created in 16:52 2019/5/20
 * @Modified By:
 */
interface Renderer {
    /**
     * 渲染
     * @param textList 文本列表
     * @param imageList 图片列表
     */
    void render(List<String> textList, List<String> imageList);

}
