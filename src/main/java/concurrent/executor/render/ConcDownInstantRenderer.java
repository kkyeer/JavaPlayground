package concurrent.executor.render;

import java.util.List;
import java.util.concurrent.*;

/**
 * @Author: kkyeer
 * @Description: 并发下载/并发渲染，使用CompletionService
 * @Date:Created in 16:30 2019/5/20
 * @Modified By:
 */
class ConcDownInstantRenderer implements Renderer{
    private ExecutorService downloadExecutorService = Executors.newFixedThreadPool(8);

    @Override
    public void render(List<String> textList, List<String> imageList) {
        for (String s : textList) {
            RenderUtils.renderText(s);
        }
        CompletionService<String> imageCompletionService = new ExecutorCompletionService<>(downloadExecutorService);
        for (String imageUrl : imageList) {
            imageCompletionService.submit(
                    () -> RenderUtils.downloadImage(imageUrl)
            );
        }

        for (int i=0;i<imageList.size();i++) {
            try {
                RenderUtils.renderImage(imageCompletionService.take().get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
    }

}
