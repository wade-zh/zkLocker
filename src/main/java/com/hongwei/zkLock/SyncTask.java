package com.hongwei.zkLock;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SyncTask implements Runnable {
    private final Integer id;
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private String currentSystemDate = null;

    public SyncTask(Integer id) {
        this.id = id;
    }

    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {
        /*
            模拟从数据库中取出最新库存数量，对其扣减，更改数据库结果
         */
        currentSystemDate = simpleDateFormat.format(new Date());
        synchronized (Products.getProducts()) {
            Integer stock = stock = Products.getProducts().get(101L);
            if (stock <= 0) {
                System.out.printf("用户ID[%s] -> 下单时间[%s] -> 下单失败", id, currentSystemDate);
                System.out.println();
            } else {
                Integer reduceStock = stock - 1;
                Products.getProducts().put(101L, reduceStock);
                System.out.printf("用户ID[%s] -> 下单时间[%s] -> 下单前库存[%s] -> 下单后库存[%s] -> 下单成功", id, currentSystemDate, stock, reduceStock);
                System.out.println();
            }
        }
    }
}
