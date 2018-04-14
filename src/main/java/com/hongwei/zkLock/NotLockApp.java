package com.hongwei.zkLock;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NotLockApp {
    /**
     * 模拟真实业务场景：秒杀下单减库存
     * 假设当前程序设计为分布式应用部署，在下单环节中需协调多服务模块完成最终任务
     * 在定义Map中，键名为商品ID，用于定位分布式Lock资源，键值为商品库存
     */
    public static void main(String[] args) {
        Products.getProducts().put(101L, 1);

        /*
            模拟10名用户在相同时间段异步发起下单请求
            测试在不加锁的情况下，扣减结果是否正常
         */
        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i = 0; i < 10; i++) {
            executorService.submit(new NormalTask(i));
        }
        /*
            用户ID[1] -> 下单时间[2018-04-14 10:35:28] -> 库存[0] -> 下单成功
            用户ID[9] -> 下单时间[2018-04-14 10:35:28] -> 下单失败
            用户ID[8] -> 下单时间[2018-04-14 10:35:28] -> 下单失败
            用户ID[7] -> 下单时间[2018-04-14 10:35:28] -> 下单失败
            用户ID[6] -> 下单时间[2018-04-14 10:35:28] -> 下单失败
            用户ID[5] -> 下单时间[2018-04-14 10:35:28] -> 下单失败
            用户ID[4] -> 下单时间[2018-04-14 10:35:28] -> 下单失败
            用户ID[0] -> 下单时间[2018-04-14 10:35:28] -> 库存[0] -> 下单成功
            用户ID[2] -> 下单时间[2018-04-14 10:35:28] -> 库存[0] -> 下单成功
            用户ID[3] -> 下单时间[2018-04-14 10:35:28] -> 下单失败
            从上面的结果可以看出，由于HashMap是线程不安全的，本应该只有1个人能够下单成功，现在有3个人下单成功
         */
    }

}

