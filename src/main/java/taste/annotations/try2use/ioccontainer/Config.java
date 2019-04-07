package taste.annotations.try2use.ioccontainer;

/**
 * @Author: kkyeer
 * @Description: 模拟实际使用时定义的配置类
 * @Date:Created in 23:44 2019/4/6
 * @Modified By:
 */
@Configuration
public class Config {
    @Bean
    public AuditorPojo auditorPojo(){
        return new AuditorPojo("ChiYou",2000);
    }
}
