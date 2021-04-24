package com.thinvent.listener;

import com.jayway.jsonpath.JsonPath;
import com.thinvent.entity.secure.NetworkKeyNodesLog;
import com.thinvent.service.secure.NetworkKeyNodesLogService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.concurrent.Executor;

/**
 * @author xufeng
 * @version 1.0
 * @date 2021/1/19 17:09
 **/
@Component
@WebListener
@Slf4j
public class UdpPortDataReceiveListener implements ServletContextListener {
    private static final int UDP_PORT = 514;
    private static DatagramSocket socket = null;
    private static final int MAX_UDP_DATA_SIZE = 409600;

    @Resource(name = "taskExecutor")
    private Executor executor;

    @Autowired
    private NetworkKeyNodesLogService networkKeyNodesLogService;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            log.info("==========启动线程，监听UDP数据报。PORT:" + UDP_PORT +"======");
            UDPProcess udpProcess = new UDPProcess(UDP_PORT);
            executor.execute(udpProcess);
            //不可用多线程
            //executor.execute(udpProcess);
            //executor.execute(udpProcess);
            //new Thread(new UDPProcess(UDP_PORT)).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    class UDPProcess implements Runnable {

        public UDPProcess(final int port) throws SocketException {
            //创建服务器端DatagramSocket，指定端口
            socket = new DatagramSocket(port);
        }

        @Override
        public void run() {
            // TODO Auto-generated method stub
            log.info("=======创建数据报，用于接收客户端发送的数据,正在监听" + UDP_PORT + "端口接收消息========");
            while (true) {
                byte[] buffer = new byte[MAX_UDP_DATA_SIZE];
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                try {
                    //log.info("=======此方法在接收到数据报之前会一直阻塞======");
                    socket.receive(packet);
                    //byte[] buffData = packet.getData();
                    //String srt2 = new String(buffData, "UTF-8").trim();
                    //JsonPath.read(srt2, "$.event_type")
                    //log.info("=======Process srt2 UTF-8======" + srt2);
                    //Thread.sleep(1000);
                    //使用线程池启动,交由线程处理
                    executor.execute(new Process(packet));
                    //new Thread(new Process(packet)).start();
                } catch (Exception e) {
                    e.printStackTrace();
                    log.error("=============超出线程池最大线程数，拒绝加入！！！");
                }
            }

        }
    }

    class Process implements Runnable {

        private String str;
        public Process(DatagramPacket packet) throws UnsupportedEncodingException {
            // TODO Auto-generated constructor stub
            byte[] buffer = packet.getData();// 接收到的UDP信息，然后解码
//            String srt1 = new String(buffer, "GBK").trim();
            //            logger.info("=======Process srt1 GBK======" + srt1);
            String srt2 = new String(buffer, "UTF-8").trim();
            this.str = srt2;
            //log.info("=======Process srt2 UTF-8======" + srt2);
//            String srt3 = new String(buffer, "ISO-8859-1").trim();
//            logger.info("=======Process srt3 ISO-8859-1======" + srt3);
        }

        @Override
        public void run() {
            // TODO Auto-generated method stub
            log.info("====过程运行=====");
            try {
                //log.info("====向客户端响应数据=====" + JsonPath.read(str, "$.event_type"));
                if (!StringUtils.isEmpty(str)) {
                    String jsonData = str.substring(str.indexOf("{"));
                    HashMap dataMap = JsonPath.read(jsonData, "$");
                    NetworkKeyNodesLog networkKeyNodesLog = new NetworkKeyNodesLog();
                    networkKeyNodesLog.setId(MapUtils.getString(dataMap, "id"));
                    if (!StringUtils.isEmpty(MapUtils.getString(dataMap, "date"))) {
                        networkKeyNodesLog.setTriggerDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                                .parse(MapUtils.getString(dataMap, "date")));
                    }
                    networkKeyNodesLog.setEventType(MapUtils.getString(dataMap, "event_type"));
                    networkKeyNodesLog.setAlarmCategory(MapUtils.getString(dataMap, "alarm_category"));
                    networkKeyNodesLog.setEventTypeCode(MapUtils.getString(dataMap, "event_type_code"));
                    networkKeyNodesLog.setAlarmName(MapUtils.getString(dataMap, "alarm_name"));
                    networkKeyNodesLog.setBehaviorName(MapUtils.getString(dataMap, "alarm_name"));
                    networkKeyNodesLog.setThreatLevel(MapUtils.getString(dataMap, "level"));
                    networkKeyNodesLog.setSrcIp(MapUtils.getString(dataMap, "src_ip"));
                    networkKeyNodesLog.setDstIp(MapUtils.getString(dataMap, "dst_ip"));
                    networkKeyNodesLog.setSrcPort(MapUtils.getString(dataMap, "src_port"));
                    networkKeyNodesLog.setDstPort(MapUtils.getString(dataMap, "dst_port"));
                    networkKeyNodesLog.setSrcCountryName(MapUtils.getString(dataMap, "src_country_name"));
                    networkKeyNodesLog.setSrcProvinceName(MapUtils.getString(dataMap, "src_province_name"));
                    networkKeyNodesLog.setSrcCityName(MapUtils.getString(dataMap, "src_city_name"));
                    networkKeyNodesLog.setxRealIp(MapUtils.getString(dataMap, "x_real_ip"));
                    networkKeyNodesLog.setxForwardedFor(MapUtils.getString(dataMap, "x_forwarded_for"));
                    networkKeyNodesLog.setPayload(MapUtils.getString(dataMap, "payload"));
                    networkKeyNodesLog.setWlProxyClientIp(MapUtils.getString(dataMap, "wl_proxy_client_ip"));
                    networkKeyNodesLog.setHost(MapUtils.getString(dataMap, "host"));
                    networkKeyNodesLog.setLinkId(MapUtils.getLong(dataMap, "link_id"));
                    networkKeyNodesLog.setAlarmContition(MapUtils.getString(dataMap, "alarm_condition"));
                    networkKeyNodesLog.setDeviceidLinkid(MapUtils.getString(dataMap, "deviceid_linkid"));
                    networkKeyNodesLog.setDeviceId(MapUtils.getString(dataMap, "device_id"));
                    networkKeyNodesLogService.save(networkKeyNodesLog);
                    log.info("=========网络关键结点数据插入成功！！！");
                    //1.定义客户端的地址、端口号、数据
                    //InetAddress address = packet.getAddress();
                    //int port = packet.getPort();
                    //byte[] data2 = "{'request':'alive','errcode':'0'}".getBytes();
                    //2.创建数据报，包含响应的数据信息
                    //DatagramPacket packet2 = new DatagramPacket(data2, data2.length, address, port);
                    //3.响应客户端
                    //socket.send(packet2);
                }

            } catch (Exception e) {
                log.info("解析失败");
                e.printStackTrace();
            }
        }

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        log.info("========UDPListener摧毁=========");
    }


}
