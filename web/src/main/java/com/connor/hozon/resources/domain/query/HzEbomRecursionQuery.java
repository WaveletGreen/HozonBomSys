package com.connor.hozon.resources.domain.query;

/**
 * @Author: Fancyears Milos Malvis
 * @Date: Created in  2019/8/23 12:03
 * @Modified By:
 */
public class HzEbomRecursionQuery extends HzEbomTreeQuery{
    private static final long serialVersionUID = -5879442668572556324L;
    /**
     * 向上递归，默认不会向上递归查
     */
    private boolean up=false;
    /**
     * 向下递归，默认向下查
     */
    private boolean down=true;

    public boolean isUp() {
        return up;
    }

    public boolean isDown() {
        return down;
    }
    /**
     * 锁限制
     * @param up
     */
    public void setUp(boolean up) {
        this.up = up;
        this.down = !up;
    }

    /**
     * 锁限制
     * @param down
     */
    public void setDown(boolean down) {
        this.down = down;
        this.up = !down;
    }
}
