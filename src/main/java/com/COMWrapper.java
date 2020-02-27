package com;

import api.communication.BaudRate;
import com.fazecast.jSerialComm.SerialPort;
import input.InputHandler;
import input.ReadHandler;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import static com.fazecast.jSerialComm.SerialPort.*;

@Log4j2
public class COMWrapper implements ComPort {

    private static final String DEVICE_NAME = "Matrix Orbital";
    private static final BaudRate DEFAULT_BAUD = BaudRate.RATE_19200;

    private final SerialPort port;

    private OutputStream outputStream;
    private InputStream inputStream;
    private InputHandler inputHandler;
    private Thread thread;

    public COMWrapper() throws CommunicationException {
        port = findPort();
        printPortInformation();
        setup();
    }

    private static SerialPort findPort() throws CommunicationException {
        for (SerialPort serialPort : SerialPort.getCommPorts())
            if (serialPort.getDescriptivePortName().startsWith(DEVICE_NAME))
                return serialPort;

        throw new CommunicationException("No COM port found.");
    }

    private void printPortInformation() {
        log.info("Port found:\n\tName: {}\n\tBaud rate: {}", port.getDescriptivePortName(), port.getBaudRate());
    }

    private void setup() throws CommunicationException {
        boolean success = port.openPort();
        if (!success)
            throw new CommunicationException("Could not open port");

        log.info("Port opened");

        port.setComPortParameters(DEFAULT_BAUD.getBaud(), 8, SerialPort.ONE_STOP_BIT, SerialPort.NO_PARITY);

        outputStream = port.getOutputStream();
        inputStream = port.getInputStream();

        createInputThread();
    }

    private void createInputThread() {
        inputHandler = new InputHandler(inputStream);
        thread = new Thread(inputHandler);
    }

    @Override
    public void useHardwareControlFlow(boolean enabled) {
        port.setFlowControl(enabled
                ? FLOW_CONTROL_RTS_ENABLED | FLOW_CONTROL_CTS_ENABLED
                : FLOW_CONTROL_DISABLED);
    }

    @Override
    public void setBaudRate(int baudRate) {
        port.setBaudRate(baudRate);
    }

    public void write(byte[] bytes) throws IOException {
        outputStream.write(bytes);
    }

    public void setReadHandler(ReadHandler handler) {
        inputHandler.setInputHandler(handler);
        thread.start();
    }

    public void resetBaudRate() {
        port.setBaudRate(DEFAULT_BAUD.getBaud());
    }
}
