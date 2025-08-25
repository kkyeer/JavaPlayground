import cn.hutool.core.io.FileUtil;
import cn.hutool.extra.pinyin.PinyinUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import net.sf.jsqlparser.JSQLParserException;
import okhttp3.*;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.tuple.Triple;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;

import java.io.File;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.nio.file.WatchService;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * @Author: kkyeer
 * @Description: 代码操场随便玩
 * @Date:Created in 21:00 2018/12/18
 * @Modified By:
 */

public class Playground {
    private static AtomicInteger count = new AtomicInteger(0);

    private enum MyEnum{

    }

    private static class AAA{
        String k;
        String v;

        public AAA(String k, String v) {
            this.k = k;
            this.v = v;
        }
    }

    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException, JSQLParserException {
        char firstLetter = PinyinUtil.getEngine().getFirstLetter('中');
        String pinyin = PinyinUtil.getEngine().getFirstLetter("认养一头牛", "").toUpperCase();
        System.out.println(pinyin);
    }

    private static void getTree(){
        File file = new File("/Users/chengyingzhang/Downloads/campain.csv");
        List<String> lines = new ArrayList<>();
        FileUtil.readLines(file, Charset.defaultCharset(), lines);
        System.out.println(lines.size());
        List<Campaign> datas = lines.stream().skip(1).map(
                lineString -> {
                    String[] split = lineString.split(",");
                    return new Campaign(Integer.parseInt(split[0]),Integer.parseInt(split[1]),Integer.parseInt(split[2]));
                }
        ).collect(Collectors.toList());
        System.out.println(datas.size());
        List<Campaign> roots = datas.stream().filter(
                data-> data.getParent()==0
        ).collect(Collectors.toList());
        Map<Integer, MyTreeNode> maps = new HashMap<>();
        for (Campaign data : datas) {
            Integer parentId = data.getParent();
            MyTreeNode current = new MyTreeNode(data.getId());
            if (maps.containsKey(parentId)) {
                MyTreeNode myTreeNode = maps.get(parentId);
                myTreeNode.getChildren().add(current);
            }else {
                MyTreeNode parent = new MyTreeNode(parentId);
                maps.put(parentId, parent);
                parent.getChildren().add(new MyTreeNode(data.getId()));
            }
            maps.put(data.getId(), current);
        }
        List<Integer> rootIds = Arrays.asList(1, 2, 3, 9, 26, 28);
        for (Integer rootId : rootIds) {
            MyTreeNode curNode = maps.get(rootId);
            System.out.println(countDepth(1,curNode));
        }

        System.out.println(roots.size());
    }

    private static int countDepth(Integer deps,MyTreeNode node){
        if (CollectionUtils.isEmpty(node.getChildren())) {
            return deps;
        }else {
            int max = 0;
            for (MyTreeNode child : node.getChildren()) {
                int countedDepth = countDepth(deps+1, child);
                max = Math.max(max, countedDepth);
            }
            return max;
        }
    }

    private static class Campaign{
        private int id;
        private int level;
        private int parent;

        public int getId() {
            return id;
        }

        public int getLevel() {
            return level;
        }

        public int getParent() {
            return parent;
        }

        public Campaign(int id, int level, int parent) {
            this.id = id;
            this.level = level;
            this.parent = parent;
        }
    }

    private static class MyTreeNode{
        private int id;
        private List<MyTreeNode> children;

        public MyTreeNode(int id) {
            this.id = id;
            this.children = new ArrayList<>();
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public List<MyTreeNode> getChildren() {
            return children;
        }

        public void setChildren(List<MyTreeNode> children) {
            this.children = children;
        }
    }

    public static void some(){
        ThreadPoolExecutor executor = new ThreadPoolExecutor(10, 100, 10L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>());
        for (int i = 0; i < 100; i++) {
            int finalI = i;
            executor.execute(
                    ()->{
                        response(finalI);
                    }
            );
        }
    }

    private static Response response(int i){
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/json;charset=UTF-8");
        RequestBody body = RequestBody.create(mediaType, "{\"title\":\"\",\"activityId\":\"\",\"status\":\"\",\"promotionalType\":\"\",\"goodsName\":\"\",\"applicantName\":\"\",\"repeatSignUp\":\"\",\"goodsApprovalIng\":\"\",\"goodsIdType\":1,\"goodsIds\":[],\"ssoPermission\":false,\"pageNum\":1,\"pageSize\":10}");
        Request request = new Request.Builder()
                .url("https://gams.danchuangglobal.com/api/activity/marketing/activity/platFormSearch?myid="+i)
                .method("POST", body)
                .addHeader("Accept", "application/json, text/plain, */*")
                .addHeader("Accept-Language", "zh-CN,zh;q=0.9,en;q=0.8,lb;q=0.7,zh-TW;q=0.6,ja;q=0.5")
                .addHeader("Cache-Control", "no-cache")
                .addHeader("Connection", "keep-alive")
                .addHeader("Content-Type", "application/json;charset=UTF-8")
                .addHeader("Cookie", "Admin-Token=eyJhbGciOiJIUzI1NiJ9.eyJlbXBsb3lOdW0iOjAsInN1YiI6InpoYW5nY2hlbmd5aW5nIiwiYXZhdGFyIjoiIiwic3VwZXJpb3JJZCI6bnVsbCwibG9naW5Db3VudCI6MTI1OCwiZmVpc2h1SWQiOiJBRzAyMzk0IiwibGFzdExvZ2luSXAiOiIxMTUuMjM2LjU0LjEwLCAxMjIuMTEyLjIwOC4yMzksMTE1LjIzNi41NC4xMCIsInJlYWxOYW1lIjoi5byg56iL6I2lIiwibGFzdExvZ2luVGltZSI6bnVsbCwiZW52aXJvbm1lbnQiOiJwcm9kIiwicG9zdCI6IiIsIm5pY2tuYW1lIjoi5byg56iL6I2lKOiwqOiogCkiLCJyYW5rIjoiIiwiaWQiOjQ3MDksInVzZXJEZXBhcnRtZW50TGlzdCI6W10sImV4cCI6MTY1NzYwNjUwNywiaWF0IjoxNjU3NTQxNzA3LCJhY2NvdW50IjoiemhhbmdjaGVuZ3lpbmciLCJlbWFpbCI6InpoYW5nY2hlbmd5aW5nQGFjY2Vzc2NvcnBvcmF0ZS5jb20uY24iLCJqb2JOdW1iZXIiOiJBRzAyMzk0IiwianRpIjoiMTY1NzU0MTcwNzY1NCIsInRpbWVzdGFtcCI6MTY1NzU0MTcwN30.yRXLounrY2u-ZYNzZAp6yYxAF-BA0XoW4SuF8V-uPGY")
                .addHeader("Origin", "https://gams.danchuangglobal.com")
                .addHeader("Pragma", "no-cache")
                .addHeader("Referer", "https://gams.danchuangglobal.com/")
                .addHeader("Sec-Fetch-Dest", "empty")
                .addHeader("Sec-Fetch-Mode", "cors")
                .addHeader("Sec-Fetch-Site", "same-origin")
                .addHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/103.0.0.0 Safari/537.36")
                .addHeader("appCode", "APP_FBOUIJ")
                .addHeader("sec-ch-ua", "\".Not/A)Brand\";v=\"99\", \"Google Chrome\";v=\"103\", \"Chromium\";v=\"103\"")
                .addHeader("sec-ch-ua-mobile", "?0")
                .addHeader("sec-ch-ua-platform", "\"macOS\"")
                .addHeader("sw8", "1-MDQxMDdiYjEtYTRlYi00NGNlLWI4YjctNjFlYTMxMmZkNzYx-NTA4NGMwMTEtYWZkYS00ZTE2LTg5MjMtODE4NmI0ODA3ZjE1-0-dnRuLWNvbXMtd2ViPGJyb3dzZXI+-djEuMC4w-aHR0cHM6Ly9nYW1zLmRhbmNodWFuZ2dsb2JhbC5jb20vIy9vcGVyYXRpb25zL0FjdGl2aXR5L0xvdHRlcnlNYW5hZ2UvaW5kZXgyMDIxP2NvZGU9JlRva2VuPWV5SmhiR2NpT2lKSVV6STFOaUo5LmV5SmxiWEJzYjNsT2RXMGlPakFzSW5OMVlpSTZJbnBvWVc1blkyaGxibWQ1YVc1bklpd2lZWFpoZEdGeUlqb2lJaXdpYzNWd1pYSnBiM0pKWkNJNmJuVnNiQ3dpYkc5bmFXNURiM1Z1ZENJNk1USTFPQ3dpWm1WcGMyaDFTV1FpT2lKQlJ6QXlNemswSWl3aWJHRnpkRXh2WjJsdVNYQWlPaUl4TVRVdU1qTTJMalUwTGpFd0xDQXhNakl1TVRFeUxqSXdPQzR5TXprc01URTFMakl6Tmk0MU5DNHhNQ0lzSW5KbFlXeE9ZVzFsSWpvaTVieWc1NmlMNkkybElpd2liR0Z6ZEV4dloybHVWR2x0WlNJNmJuVnNiQ3dpWlc1MmFYSnZibTFsYm5RaU9pSndjbTlrSWl3aWNHOXpkQ0k2SWlJc0ltNXBZMnR1WVcxbElqb2k1YnlnNTZpTDZJMmxLT2l3cU9pb2dDa2lMQ0p5WVc1cklqb2lJaXdpYVdRaU9qUTNNRGtzSW5WelpYSkVaWEJoY25SdFpXNTBUR2x6ZENJNlcxMHNJbVY0Y0NJNk1UWTFOell3TmpVd055d2lhV0YwSWpveE5qVTNOVFF4TnpBM0xDSmhZMk52ZFc1MElqb2llbWhoYm1kamFHVnVaM2xwYm1jaUxDSmxiV0ZwYkNJNklucG9ZVzVuWTJobGJtZDVhVzVuUUdGalkyVnpjMk52Y25CdmNtRjBaUzVqYjIwdVkyNGlMQ0pxYjJKT2RXMWlaWElpT2lKQlJ6QXlNemswSWl3aWFuUnBJam9pTVRZMU56VTBNVGN3TnpZMU5DSXNJblJwYldWemRHRnRjQ0k2TVRZMU56VTBNVGN3TjMwLnlSWExvdW5yWTJ1LVpZTnpaQXA2eVl4QUYtQkEwWG9XNFN1RjhWLXVQR1k=-Z2Ftcy5kYW5jaHVhbmdnbG9iYWwuY29t")
                .addHeader("token", "eyJhbGciOiJIUzI1NiJ9.eyJlbXBsb3lOdW0iOjAsInN1YiI6InpoYW5nY2hlbmd5aW5nIiwiYXZhdGFyIjoiIiwic3VwZXJpb3JJZCI6bnVsbCwibG9naW5Db3VudCI6MTI1OCwiZmVpc2h1SWQiOiJBRzAyMzk0IiwibGFzdExvZ2luSXAiOiIxMTUuMjM2LjU0LjEwLCAxMjIuMTEyLjIwOC4yMzksMTE1LjIzNi41NC4xMCIsInJlYWxOYW1lIjoi5byg56iL6I2lIiwibGFzdExvZ2luVGltZSI6bnVsbCwiZW52aXJvbm1lbnQiOiJwcm9kIiwicG9zdCI6IiIsIm5pY2tuYW1lIjoi5byg56iL6I2lKOiwqOiogCkiLCJyYW5rIjoiIiwiaWQiOjQ3MDksInVzZXJEZXBhcnRtZW50TGlzdCI6W10sImV4cCI6MTY1NzYwNjUwNywiaWF0IjoxNjU3NTQxNzA3LCJhY2NvdW50IjoiemhhbmdjaGVuZ3lpbmciLCJlbWFpbCI6InpoYW5nY2hlbmd5aW5nQGFjY2Vzc2NvcnBvcmF0ZS5jb20uY24iLCJqb2JOdW1iZXIiOiJBRzAyMzk0IiwianRpIjoiMTY1NzU0MTcwNzY1NCIsInRpbWVzdGFtcCI6MTY1NzU0MTcwN30.yRXLounrY2u-ZYNzZAp6yYxAF-BA0XoW4SuF8V-uPGY")
                .build();

        try {
            Response response = client.newCall(request).execute();
            System.out.println("成功" + count.incrementAndGet());
        } catch (IOException e) {
            System.err.println(i+"错了");
        }
        return null;
    }

    protected static   long getMaxWorkerId(long datacenterId,String pid, long maxWorkerId) {
        StringBuilder mpid = new StringBuilder();
        mpid.append(datacenterId);
        mpid.append(pid);
        long workerId = (long)(mpid.toString().hashCode() & '\uffff') % (maxWorkerId + 1L);
        System.out.println(workerId);
        System.out.println(System.currentTimeMillis());
        return workerId;
    }

    private static void localNetAddress() throws UnknownHostException {
        // TODO
        System.out.println(Inet4Address.getLocalHost().getCanonicalHostName());
        // 获取IP
        System.out.println(Inet4Address.getLocalHost().getHostAddress());
        //  TODO
        System.out.println(Inet4Address.getLocalHost().getHostName());
    }

    private static void sendMsgAsync(){
        Producer<String, String> producer = buildProducer();
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String value = scanner.next();
            ProducerRecord<String, String> record = new ProducerRecord<>("test", 0, "Key", value);
            Future<RecordMetadata> response =  producer.send(record, (metadata,exception)->{
                System.out.println("Async meta:" + metadata.offset());
            });
            RecordMetadata metadata = null;
            try {
                metadata = response.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
            System.out.println(metadata);
        }
    }

    private static void sendMsgSync(){
        Producer<String, String> producer = buildProducer();
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String value = scanner.next();
            ProducerRecord<String, String> record = new ProducerRecord<String, String>("test", 0,"Key",value);
            Future<RecordMetadata> response =  producer.send(record);
            RecordMetadata metadata = null;
            try {
                metadata = response.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
            System.out.println(metadata);
        }
    }

    private static Producer<String,String> buildProducer(){
        Properties kafkaProperties = new Properties();
        kafkaProperties.put("bootstrap.servers", "192.168.137.189:9092");
        kafkaProperties.put("key.serializer", StringSerializer.class.getCanonicalName());
        kafkaProperties.put("value.serializer", StringSerializer.class.getCanonicalName());
        return new KafkaProducer<>(kafkaProperties);

    }

    private static void getAddress() throws UnknownHostException {
        InetAddress inetAddress = Inet4Address.getLocalHost();
        System.out.println(inetAddress.getCanonicalHostName());
    }
}
