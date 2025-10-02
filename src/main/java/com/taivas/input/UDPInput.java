package com.taivas.input;

import com.taivas.settings.Settings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;

public class UDPInput implements Input {

    private static final Logger LOG = LoggerFactory.getLogger(UDPInput.class);
    private static final int BUFFER_SIZE = 1024;

    private InetSocketAddress externalAddress;

    private DatagramSocket socket;

    @Override
    public void open(Settings settings) throws IOException {
        LOG.info("Reading config");
        int receivePort = settings.getInt("plugin-system.input.port");
        externalAddress = settings.getAddress("plugin-system.input.host");

        LOG.info("Binding to port {}", receivePort);
        socket = new DatagramSocket(receivePort);
        LOG.info("Ready to receive messages from {}:{}", externalAddress.getHostName(), externalAddress.getPort());

    }

    @Override
    public String readLine() throws IOException {
        while (true) {
            byte[] receiveData = new byte[BUFFER_SIZE];
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            socket.receive(receivePacket);
            if (!receivePacket.getAddress().equals(externalAddress.getAddress()) || receivePacket.getPort() != externalAddress.getPort()) {
                continue;
            }

            return new String(receivePacket.getData(), "cp1252").replaceAll("\\x00", "").replaceAll("\n", "").strip();
        }
    }

}
