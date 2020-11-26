package lab.io.netty.oio;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.nio.charset.StandardCharsets;

/**
 * @Author: kkyeer
 * @Description: 使用OIO event loop
 * @Date:Created in 下午4:13 2020/6/4
 * @Modified By:
 */
public class NIOServer {
    public static void main(String[] args) throws InterruptedException {
        final ByteBuf outputContent = Unpooled.unreleasableBuffer(Unpooled.copiedBuffer("Hello from netty!", StandardCharsets.UTF_8));
        EventLoopGroup group = new NioEventLoopGroup();
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.localAddress(12345)
                .group(group)
                .channel(NioServerSocketChannel.class)
                .childHandler(
                        new ChannelInitializer<SocketChannel>() {
                            @Override
                            protected void initChannel(SocketChannel ch) throws Exception {
                                ch.pipeline().addLast(
                                        new ChannelInboundHandlerAdapter(){
                                            @Override
                                            public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                                ctx.writeAndFlush(outputContent.duplicate()).addListener(ChannelFutureListener.CLOSE);
                                            }
                                        }
                                );
                            }
                        }
                );
        ChannelFuture channelFuture = bootstrap.bind().sync();
        channelFuture.channel().closeFuture().sync();
        group.shutdownGracefully().sync();
    }
}
