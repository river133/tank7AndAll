package com.mashibing.tankDecorator;

import java.util.LinkedList;
import java.util.List;

public class ColliderChain implements Collider {
    private List<Collider> colliders =new LinkedList<>();

    public ColliderChain( ) {
        //添加碰撞器
      add(new BulletTankCollider());
      add(new TankTankCollider());
      add(new BulletWallCollider());
      add(new WallTankCollider());
    }

    public void add(Collider c){
        colliders.add(c);
    }

    @Override
    public boolean collide(GameObject o1, GameObject o2) {
        for (int i = 0; i < colliders.size(); i++) {
            //相撞后不在往下执行
            if (!colliders.get(i).collide(o1,o2)) {
                return false;
            }
        }
        return true;
    }
}
