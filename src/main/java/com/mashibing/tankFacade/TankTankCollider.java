package com.mashibing.tankFacade;
/*
比较器策略模式
坦克与坦克相撞,改变方向，不重叠
 */
public class TankTankCollider implements Collider{
    @Override
    public boolean collide(GameObject o1, GameObject o2) {
        if(o1 instanceof Tank && o2 instanceof Tank){
            Tank t1 = (Tank)o1;
            Tank t2 = (Tank)o2;
            if(t1.getRect().intersects(t2.getRect())){
                t1.back();
                t2.back();
                return false;
            }
        }
        return true;
    }
}
