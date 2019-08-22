package com.tt.serial;

import com.tt.activeMQ.Sender;
import com.tt.entity.ParamConfig;
import gnu.io.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.TooManyListenersException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author tt
 * 功能：从串口中读取数据，并保存到ActiveMQ中去
 */
public class Serial1 implements SerialPortEventListener {
    private SerialPort serialPort;
    private InputStream inputStream;
    private OutputStream outputStream;
    private String data;
    private String dataHex;
    Enumeration<CommPortIdentifier> portEnum;
    //串口识别器，获取系统中的所有端口
    private CommPortIdentifier commPortIdentifier;
    private static byte[] buffer;
    //单例线程池，保证线程有序的执行，而不是系统随机选择执行
    private static ExecutorService executorService=Executors.newSingleThreadExecutor();
    private static ExecutorService poolService=Executors.newFixedThreadPool(1000);

    public static byte[] getBuffer() {
        return buffer;
    }

    public void init(ParamConfig paramConfig) {

        //获取系统中所有的通讯端口
        portEnum = CommPortIdentifier.getPortIdentifiers();
        boolean hasPort = false;
        while (portEnum.hasMoreElements()) {
            //获取系统中存在端口的识别号
            commPortIdentifier = portEnum.nextElement();
            //判断系统当前识别号和我们指定的端口号是否一致
            if (commPortIdentifier.getPortType() == CommPortIdentifier.PORT_SERIAL
                    && paramConfig.getSerialNumber().toLowerCase().equals(commPortIdentifier.getName().toLowerCase())
            ) {
                hasPort = true;
                try {
                    //打开串口
                    serialPort = (SerialPort) commPortIdentifier.open(Object.class.getSimpleName(), 2000);
                    serialPort.addEventListener(this);
                    //设置串口可监听
                    serialPort.notifyOnDataAvailable(true);
                    serialPort.setSerialPortParams(paramConfig.getBaudRate(), paramConfig.getDataBit(), paramConfig.getStopBit(), paramConfig.getCheckoutBit());

                } catch (PortInUseException e) {
                    e.printStackTrace();
                } catch (UnsupportedCommOperationException e) {
                    e.printStackTrace();
                } catch (TooManyListenersException e) {
                    e.printStackTrace();
                }
            }
        }
        if (!hasPort) {
            System.out.println("不存在该串口");
        }
    }

    @Override
    public void serialEvent(SerialPortEvent serialPortEvent) {
        switch (serialPortEvent.getEventType()) {
            case SerialPortEvent.BI:    //通讯中断
            case SerialPortEvent.CD:    //载波检测
            case SerialPortEvent.CTS:   //清除发送
            case SerialPortEvent.DSR:   //数据设备准备好
            case SerialPortEvent.FE:    //帧错误
            case SerialPortEvent.OE:    //溢位错误
            case SerialPortEvent.OUTPUT_BUFFER_EMPTY:   //缓冲区数据已空
            case SerialPortEvent.PE:    //奇偶校验错误
            case SerialPortEvent.RI:    //响铃侦测
                break;
            case SerialPortEvent.DATA_AVAILABLE:    //有数据到达，生产者
                //这边应该创建线程处理sendToActiveMQ这个操作
                //开启一个线程去处理，同步方法
                executorService.submit(new ActiveMq());
            default:
                break;
        }
    }

    private void sendToActiveMQ() {
        try {
            try {
                inputStream = serialPort.getInputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("available"+inputStream.available());
            buffer = new byte[inputStream.available()];
            int len = 0;
            while ((len = inputStream.read(buffer)) != -1) {
//                executorService.submit(activeMq);
//                activeMq.start();
                Sender.sendMessage(buffer);
                break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String readFromSerial() {
        try {
            //获取串口输入流
            inputStream = serialPort.getInputStream();
            byte[] buffer = new byte[inputStream.available()];
            int len = 0;
            while ((len = inputStream.read(buffer)) != -1) {
                data = new String(buffer, 0, len);
                System.out.println("data: " + data);
                System.out.println("size: " + buffer.length);
                inputStream.close();
                inputStream = null;
                break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    private void closeSerialPort() {
        if (serialPort != null) {
            serialPort.notifyOnDataAvailable(false);
        }
    }

    public static void main(String[] args) {
        Serial1 serial = new Serial1();
        //数据包格式：1位起始位、8位数据位、1位停止位，没有校验位
        ParamConfig paramConfig = new ParamConfig("com4", 115200, 0, 8, 1);
        serial.init(paramConfig);
    }

    private class ActiveMq extends Thread {
        @Override
        public void run() {
            super.run();
            sendToActiveMQ();
        }
    }
}
