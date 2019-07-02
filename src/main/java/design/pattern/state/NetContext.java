package design.pattern.state;

/**
 * @Author: kkyeer
 * @Description: 网络上下文，根据不同的状态来执行不同的动作
 * @Date:Created in 22:50 2019/7/2
 * @Modified By:
 */
class NetContext {
    private NetState currentNetState;
    final NetState LINKED = new NetLinkedState(this);
    final NetState UNLINK = new NetUnlinkState(this);

    NetContext() {
        this.currentNetState = UNLINK;
    }

    NetState getCurrentNetState() {
        return currentNetState;
    }

    void setCurrentNetState(NetState currentNetState) {
        this.currentNetState = currentNetState;
    }

    void link(){
        this.currentNetState.link();
    }

    void unlink(){
        this.currentNetState.unlink();
    }

    public static void main(String[] args) {
        NetContext netContext = new NetContext();
        netContext.link();
        netContext.link();
        netContext.unlink();
        netContext.link();
    }
}
