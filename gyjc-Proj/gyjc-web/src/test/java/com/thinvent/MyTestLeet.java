package com.thinvent;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * @author xufeng
 * @version 1.0
 * @date 2021/1/18 10:05
 **/
public class MyTestLeet {
    public static final String SERVER_HOSTNAME = "localhost";
    // 服务器端口
    public static final int SERVER_PORT = 514;
    // 本地发送端口
    public static final int LOCAL_PORT = 8888;

    public static void main(String[] args) {
        try {
            int count = 100;
            for (int i=0;i<2;i++) {
            /*try {
                System.out.println("===================11");
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/
            // 1，创建udp服务。通过DatagramSocket对象。
            DatagramSocket socket = new DatagramSocket(LOCAL_PORT);
            // 2，确定数据，并封装成数据包。DatagramPacket(byte[] buf, int length, InetAddress
            // address, int port)
            String zz = "<8>Jan 26 14:00:41 csbfc {\"date\":\"2021-01-26 13:57:43\",\"alarm_name\":\"SMB445端口扫描\",\"src_country_name\":\"中国\",\"level\":\"2\",\"alarm_category\":\"信息收集\",\"event_type_code\":\"TSA_64\",\"x_real_ip\":\"\",\"dst_ip\":\"129.211.76.63\",\"wl_proxy_client_ip\":\"\",\"link_id\":1,\"x_forwarded_for\":\"\",\"src_ip\":\"218.64.4.113\",\"src_port\":\"2529\",\"alarm_condition\":\"端口(目的):445 135 139 TCP标识:------1- \",\"event_type\":\"alarmLog\",\"src_city_name\":\"南昌\",\"dst_port\":\"445\",\"host\":\"\",\"id\":\"f2214971-4c38-4d40-9c64-2e4e40a4387d\",\"src_province_name\":\"江西\"}";
                byte[] buf = zz.getBytes();

                DatagramPacket dp = new DatagramPacket(buf, buf.length, InetAddress.getByName(SERVER_HOSTNAME),
                        SERVER_PORT);
                // 3，通过socket服务，将已有的数据包发送出去。通过send方法。
                socket.send(dp);

                // 4，关闭资源。
                socket.close();
            }
            while (true) {
                try {
                    System.out.println("===================55555");
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
