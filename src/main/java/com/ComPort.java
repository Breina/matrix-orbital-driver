package com;

public interface ComPort {

    void useHardwareControlFlow(boolean enabled);

    void setBaudRate(int baudRate);
}
