package share;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * @Author: kkyeer
 * @Description:
 * @Date:Created in 14:02 2025/8/29
 * @Modified By:
 */
@Data
@Builder
@ToString
@Accessors(fluent = true)
public class TestLombokFluent {

    private int id;

    private String name;

    private TestLombokFluent sub;

    public static TestLombokFluent testData(){
        return TestLombokFluent.builder()
                .id(1)
                .name("name")
                .sub(TestLombokFluent.builder()
                        .id(2)
                        .name("subName")
                        .build())
                .build();
    }
}
