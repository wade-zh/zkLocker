package com.hongwei.zkLock;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SynchronizedLockApp {
    /**
     * 模拟真实业务场景：秒杀下单减库存
     * 假设当前程序设计为分布式应用部署，在下单环节中需协调多服务模块完成最终任务
     * 在定义Map中，键名为商品ID，用于定位分布式Lock资源，键值为商品库存
     */
    public static void main(String[] args) {
        // 使用Hashtable是没有意义的，内部需要对库存扣减逻辑判断时加锁，所以使用HashMap就可以
        Products.setProducts(new HashMap<>());
        Products.getProducts().put(101L, 1);
        /*
            模拟10名用户在相同时间段异步发起下单请求
            测试在不加锁的情况下，扣减结果是否正常
         */
        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i = 0; i < 10; i++) {
            executorService.submit(new SyncTask(i));
        }
        /*
            用户ID[2] -> 下单时间[2018-04-14 11:59:02] -> 下单前库存[1] -> 下单后库存[0] -> 下单成功
            用户ID[9] -> 下单时间[2018-04-14 11:59:02] -> 下单失败
            用户ID[8] -> 下单时间[2018-04-14 11:59:02] -> 下单失败
            用户ID[7] -> 下单时间[2018-04-14 11:59:02] -> 下单失败
            用户ID[6] -> 下单时间[2018-04-14 11:59:02] -> 下单失败
            用户ID[5] -> 下单时间[2018-04-14 11:59:02] -> 下单失败
            用户ID[4] -> 下单时间[2018-04-14 11:59:02] -> 下单失败
            用户ID[3] -> 下单时间[2018-04-14 11:59:02] -> 下单失败
            用户ID[1] -> 下单时间[2018-04-14 11:59:02] -> 下单失败
            用户ID[0] -> 下单时间[2018-04-14 11:59:02] -> 下单失败
            基于本地锁机制实现只有一个用户抢先下单的效果，这里需注意使用的是线程池，线程池核心概念就是复用，所以当调试时发现线程都是WAIT状态是正常的
         */
    }

}
