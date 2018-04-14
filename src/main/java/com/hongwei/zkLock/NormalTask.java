package com.hongwei.zkLock;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class NormalTask implements Runnable {
    private final Integer id;
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private String currentSystemDate = null;

    public NormalTask(Integer id) {
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

        currentSystemDate = simpleDateFormat.format(new Date());
        Integer stock = Products.getProducts().get(101L);
        if (stock <= 0) {
            System.out.printf("用户ID[%s] -> 下单时间[%s] -> 下单失败", id, currentSystemDate);
            System.out.println();
        } else {
            Products.getProducts().put(101L, --stock);
            System.out.printf("用户ID[%s] -> 下单时间[%s] -> 库存[%s] -> 下单成功", id, currentSystemDate, stock);
            System.out.println();
        }
    }
}
