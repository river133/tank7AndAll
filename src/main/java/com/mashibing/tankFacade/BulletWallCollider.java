package com.mashibing.tankFacade;
/*
比较器策略模式
墙体与子弹碰撞策略
 */
public class BulletWallCollider implements Collider{
    @Override
    public boolean collide(GameObject o1, GameObject o2) {
        if(o1 instanceof Bullet && o2 instanceof Wall){
            Bullet b = (Bullet)o1;
            Wall w = (Wall)o2;

            if (b.rect.intersects(w.rect)) {
                b.die();
                return false;//如果相撞不再往下
            }
        }else if(o1 instanceof Wall && o2 instanceof Bullet){
            return collide(o2,o1);
        }
            return true;
    }
}
