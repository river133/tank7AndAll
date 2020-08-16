package com.mashibing.tankNet2.net;

import com.mashibing.tankNet2.tank.MsgType;

public abstract class Msg {
    public abstract void handle();
    public abstract byte[] toBytes();
    public abstract void parse(byte[] bytes);
    public abstract MsgType getMsgType();
}
