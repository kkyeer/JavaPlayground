package concurrent.executor.render;

import java.util.List;

/**
 * @Author: kkyeer
 * @Description: 顺序渲染，无优化
 * @Date:Created in 15:48 2019/5/20
 * @Modified By:
 */
class SequentialRenderer implements Renderer{
    @Override
    public void render(List<String> textList,List<String> imageInfoList) {
        for (String s : textList) {
            RenderUtils.renderText(s);
        }
        for (String url : imageInfoList) {
            String imageInfo = RenderUtils.downloadImage(url);
            RenderUtils.renderImage(imageInfo);
        }
    }
}
