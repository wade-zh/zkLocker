package com.hongwei.zkLock;

/***
 * 基于zk锁的接口
 * @author HongWei
 */
public interface IZkLocker {
    /**
     * 获取锁
     * @param timeOut
     * @return
     */
    public boolean get(long timeOut) throws Exception;

    /**
     * 释放锁
     */
    public void close() throws Exception;
}
