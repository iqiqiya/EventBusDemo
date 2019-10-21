package iqiqiya.lanlana.eventbusdemo;

/**
 * Author: iqiqiya
 * Date: 2019/10/21
 * Time: 20:54
 * Blog: blog.77sec.cn
 * Github: github.com/iqiqiya
 */
public class MainOrderedEvent {
    public final String threadInfo;

    public MainOrderedEvent(String threadInfo){
        this.threadInfo = threadInfo;
    }
}
