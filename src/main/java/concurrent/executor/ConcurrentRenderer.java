package concurrent.executor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * @Author: kkyeer
 * @Description: 并发下载/并发渲染
 * @Date:Created in 16:30 2019/5/20
 * @Modified By:
 */
class ConcurrentRenderer implements Renderer{
    private ExecutorService downloadExecutorService = Executors.newFixedThreadPool(8);

    @Override
    public void render(List<String> textList, List<String> imageList) {
        for (String s : textList) {
            RenderUtils.renderText(s);
        }
        List<Future<String>> imageDataList = new ArrayList<>(imageList.size());
        for (String imageUrl : imageList) {
            Future<String> imageInfo = downloadExecutorService.submit(
                    () -> RenderUtils.downloadImage(imageUrl)
            );
            imageDataList.add(imageInfo);
        }

        //　
        for (Future<String> s : imageDataList) {
            try {
                RenderUtils.renderImage(s.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
    }

}
