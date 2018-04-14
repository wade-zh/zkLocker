package com.hongwei.zkLock;

import org.I0Itec.zkclient.ZkClient;

/***
 * 基于zk锁的实现类
 * @author HongWei
 */
public class ZkLockerImpl extends BaseZkLocker implements IZkLocker {
    /*用于保存Zookeeper中实现分布式锁的节点，如名称为locker：/locker，

     *该节点应该是持久节点，在该节点下面创建临时顺序节点来实现分布式锁 */

    private  String  basePath;

    /*锁名称前缀，locker下创建的顺序节点例如都以lock-开头，这样便于过滤无关节点

     *这样创建后的节点类似：lock-00000001，lock-000000002*/

    private  static final String nodePrefixName ="lock-";



    /*用于保存某个客户端在locker下面创建成功的顺序节点，用于后续相关操作使用（如判断）*/

    private String  ourLockPath;

    public ZkLockerImpl(ZkClient client, String path, String nodePrefixName) {
        super(client, path, nodePrefixName);
        this.basePath = path;
    }


    /**
     * 获取锁
     *
     * @param timeOut
     * @return
     */
    @Override
    public boolean get(long timeOut) throws Exception {
        ourLockPath = attemptLock(timeOut);
        return ourLockPath != null;
    }

    /**
     * 释放锁
     */
    @Override
    public void close() throws Exception {
        releaseLock(ourLockPath);
    }
}
