package com.whb.thread.semaphore;

/**
 * 选手某一次跑步的成绩
 * @author yinwenjie
 *
 */
public class Result {
    /**
     * 记录了本次赛跑的用时情况
     */
    private float time;

    public Result(float time) {
        this.time = time;
    }

    /**
     * @return the time
     */
    public float getTime() {
        return time;
    }

    /**
     * @param time the time to set
     */
    public void setTime(float time) {
        this.time = time;
    }
}
