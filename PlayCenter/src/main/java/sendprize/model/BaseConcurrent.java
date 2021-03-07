package sendprize.model;

/**
 * @author: guangxush
 * @create: 2021/03/07
 */
public abstract class BaseConcurrent {

    protected void ready(){
        // 线程处理前的准备
    }

    protected void release(){
        // 线程完成后的清理工作
    }
}
