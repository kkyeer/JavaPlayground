package concurrent.executor;

import java.util.List;
import java.util.concurrent.*;

/**
 * @Author: kkyeer
 * @Description: 带有下载时间控制的并发下载/并发渲染，超过200ms的下载将被丢弃
 * @Date:Created in 16:30 2019/5/20
 * @Modified By:
 */
class TimeLimitConcurrentRenderer implements Renderer{
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
        int timeoutCount =0;
        int renderCount =0;
        for (int i=0;i<imageList.size();i++) {
            try {
                RenderUtils.renderImage(imageCompletionService.take().get(100,TimeUnit.MILLISECONDS));
                renderCount++;
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            } catch (TimeoutException e) {
                timeoutCount++;
                e.printStackTrace();
            }
        }
        System.out.println("rendered:" + renderCount);
        System.out.println("timeout:" + timeoutCount);
    }

}
