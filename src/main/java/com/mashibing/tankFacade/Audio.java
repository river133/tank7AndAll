package com.mashibing.tankFacade;
/*
11集 时间61:30
 */
public class Audio extends Thread {
    String url=null;

    public Audio(String url) {
        this.url = url;
    }
    //    @Override
//    public void run() {
//        try{
//            byte[]b = new byte[1024];
//            int len=0;
//            sourceDataLine.open(audioFormat,1024);
//            sourceDataLine.start();
//            while (len=audioInputStream.read(b))>0){
//                sourceDataLine.write(b,0,len);
//            }
//            audioInputStream.close();
//            sourceDataLine.drain();
//            sourceDataLine.close;
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//    }

}
