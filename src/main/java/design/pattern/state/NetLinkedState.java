package design.pattern.state;

/**
 * @Author: kkyeer
 * @Description: 网络还未连接的状态
 * @Date:Created in 22:51 2019/7/2
 * @Modified By:
 */
class NetLinkedState implements NetState{
    private final NetContext netContext;
    public NetLinkedState(NetContext netContext) {
        this.netContext = netContext;
    }


    @Override
    public void link() {
        System.out.println("Unsupported operation");
    }

    /**
     * 尝试断开
     */
    @Override
    public void unlink() {
        System.out.println("unlink net");
        this.netContext.setCurrentNetState(netContext.UNLINK);
    }
}
