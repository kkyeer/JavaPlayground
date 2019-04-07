package taste.annotaions.try2use;

/**
 * @Author: kkyeer
 * @Description: AuditorPojo类
 * @Date:Created in 22:48 2019/4/6
 * @Modified By:
 */
class AuditorPojo {
    /**
     * 姓名
     */
    private String name;

    /**
     * 等级
     */
    private int level;

    private AuditorPojo(){

    }

    public AuditorPojo(String name, int level) {
        this.name = name;
        this.level = level;
    }


    public String getName() {
        return name;
    }

    public int getLevel() {
        return level;
    }


}
