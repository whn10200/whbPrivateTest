package com.whb.thread.semaphore;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.Semaphore;

/**
 * 这就是一个选手。
 * 为了简单起见，我们只记录这个选手名字、选手编号、最低速度（创建时随机生成）。
 * 当然，最为一名选手，最重要的工作就是“跑步”
 */
public class Player implements Callable<Result> , Comparable<Player>{

    /**
     * 选手编号
     */
    private int number;

    /**
     * 选手的名字
     */
    private String name;

    /**
     * 最低速度
     */
    private float minSpeed;

    /**
     * 本次比赛结果
     */
    private Result result;

    /**
     * 跑道
     */
    private Semaphore runway;

    public Player(String name , int number , Semaphore runway) {
        this.name = name;
        this.number = number;
        this.runway = runway;

        // 这个最低速度设置是 8米/秒（否则就真是‘龟速’了）
        this.minSpeed = 8f;
    }

    /* (non-Javadoc)
     * @see java.util.concurrent.Callable#call()
     */
    @Override
    public Result call() throws Exception {
        try {
            // 申请上跑道
            this.runway.acquire();
            return this.doRun();
        } catch(Exception e) {
            e.printStackTrace(System.out);
        } finally {
            // 都要进入初赛结果排序（中途退赛的成绩就为0）
            this.runway.release();
        }

        // 如果执行到这里，说明异常发生了
        this.result = new Result(Float.MAX_VALUE);
        return result;
    }

    /**
     * 开始跑步
     * @return
     * @throws Exception
     */
    private Result doRun()  throws Exception {
        /*
         * 为了表现一个选手每一次跑步都有不同的状态（但是都不会低于其最低状态），
         * 所以每一次跑步，系统都会为这个选手分配一个即时速度。
         * 
         * 这个即时速度不会低于其最小速度，但是也不会高于 14米/秒(否则就是‘超人’咯)
         * */
        // 生成即时速度
        float presentSpeed = 0f;
        presentSpeed = this.minSpeed * (1.0f + new Random().nextFloat());
        if(presentSpeed > 14f) {
            presentSpeed = 14f;
        }

        // 计算跑步结果(BigDecimal的使用可自行查阅资料)
        BigDecimal calculation =  new BigDecimal(100).divide(new BigDecimal(presentSpeed) , 3, RoundingMode.HALF_UP);
        float presentTime = calculation.floatValue();

        // 让线程等待presentSpeed的时间，模拟该选手跑步的过程
        synchronized (this) {
            this.wait((long)(presentTime * 1000f));
        }

        // 返回跑步结果
        this.result = new Result(presentTime);
        return result;
    }

    /**
     * @return the result
     */
    public Result getResult() {
        return result;
    }

    /**
     * @return the number
     */
    public int getNumber() {
        return number;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /* (non-Javadoc)
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    @Override
    public int compareTo(Player o) {
        /*
         * 两个选手间，还可以通过他们的result进行比较
         * 耗时越小，当然越靠前
         * */
        Result myReslut = this.getResult();
        Result targetReslut = o.getResult();

        // 如果出现了reslut为null或者targetReslut为null，说明比赛结果出现了问题
        // 当然如果真的出现这样的问题，最可能的选手中途退赛了
        if(myReslut == null) {
            return 1;
        }
        if(targetReslut == null) {
            return -1;
        }

        // 耗时越少的选手，当然应该排在“成绩”队列的越前面
        if(myReslut.getTime() < targetReslut.getTime()) {
            return -1;
        } else {
            return 1;
        }
    }
}