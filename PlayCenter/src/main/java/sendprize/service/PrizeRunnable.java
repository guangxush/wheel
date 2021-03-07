package sendprize.service;

import sendprize.model.BaseConcurrent;

/**
 * @author: guangxush
 * @create: 2021/03/07
 */
public abstract class PrizeRunnable extends BaseConcurrent implements Runnable{

    @Override
    public final void run() {
        try{
            ready();
            doRun();
        }finally {
            release();
        }
    }

    /**
     * 核心线程处理逻辑
     */
    protected abstract void doRun();
}
