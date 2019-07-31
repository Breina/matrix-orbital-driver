package api.communication;

import api.Commander;
import commands.Util;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Log4j2
public class Communication extends Commander {

    private Protocol transmissionProtocol;

    @Getter(lazy = true, value = AccessLevel.PRIVATE)
    private final SoftwareControlFlow softwareControlFlow = new SoftwareControlFlow(this);

    public Communication(Commander parent) {
        super(parent);
    }

    /**
     * Immediately changes the baud rate. Not available in I2C. Baud rate can be temporarily forced to 19200 by a
     * manual override.
     *
     * @param baudRate The valid baud rates.
     */
    public void changeBaudRate(BaudRate baudRate) {
        checkI2CProtocol();
        send(CommunicationCommands.changeBaudRate(baudRate));
    }

    /**
     * Immediately changes the I2C write address. Only even values are permitted as the next odd address will become
     * the read address. Default is 80.
     *
     * @param address Even value.
     */
    public void changeI2CSlaveAddress(int address) {
        send(CommunicationCommands.changeI2CSlaveAddress(address));
    }

    /**
     * Selects the protocol used for data transmission from the display. Data transmission to the display is not affected.
     * Must be set to the protocol in use to receive data correctly.
     *
     * @param protocol Serial (RS232/RS422/TTL/USB) or I2C.
     */
    public void transmissionProtocolSelect(Protocol protocol) {
        send(CommunicationCommands.transmissionProtocolSelect(protocol));
        this.transmissionProtocol = protocol;
    }

    /**
     * Immediately changes the baud rate to the value specified. Baud must be a whole number between 0 and
     * 1,000,000. Not available in I2C. Can be temporarily forced to 19200 by a manual override.
     *
     * @param baudRate Baud rate speed.
     */
    public void setNonStandardBaudRate(int baudRate) {
        checkI2CProtocol();
        send(CommunicationCommands.setNonStandardBaudRate(baudRate));
    }

    /**
     * Disables all forms of flow control.
     */
    public void disableControlFlow() {
        send(CommunicationCommands.setFlowControlMode(ControlFlowMode.NONE));
    }

    /**
     * Enables and sets the hardware flow control trigger level. The Clear To Send signal will be deactivated once the number of
     * characters in the display buffer reaches the level set; it will be reactivated once all data in the buffer is handled.
     *
     * @param triggerLevel Trigger level.
     */
    public void enableHardwareControlFlow(HardwareControlFlowTriggerLevel triggerLevel) {
        send(CommunicationCommands.setFlowControlMode(ControlFlowMode.HARDWARE));
        send(CommunicationCommands.setHardwareFlowControlTriggerLevel(triggerLevel));
    }

    /**
     * Sets the flow control mode to Software, returns a handler for software control flow. Not available in I2C.
     *
     * @return A handler which is to be used to further define the software control flow.
     */
    public SoftwareControlFlow enableSoftwareControlFlow() {
        checkI2CProtocol();
        send(CommunicationCommands.setFlowControlMode(ControlFlowMode.SOFTWARE));

        return getSoftwareControlFlow();
    }

    /**
     * Send data to the display that it will echo. Useful to confirm communication or return information from scripts.
     *
     * @param data Byte(s) An arbitrary array of data that the module will return.
     *
     * @return Byte(s) The same arbitrary array of data originally sent.
     */
    public synchronized Byte[] echo(byte[] data) {
        Future<Byte[]> expect = getInput().expectBytes(data.length);
        send(CommunicationCommands.echo(data));
        try {
            return expect.get();
        } catch (InterruptedException | ExecutionException e) {
            log.error(e);
            return null;
        }
    }

    /**
     * Pause command execution to and responses from the display for the specified length of time.
     *
     * @param timeMs Short Length of delay in ms, maximum 2000.
     */
    public void delay(int timeMs) {
        send(CommunicationCommands.delay(timeMs));
    }

    /**
     * Reset the display as if power had been cycled via a software command. No commands should be sent while the
     * unit is in the process of resetting; a response will be returned to indicate the unit has successfully been reset.
     */
    public void reset() {
        Future<Byte> r0 = getInput().expect(Util.hex("FE"));
        Future<Byte> r1 = getInput().expect(Util.hex("D4"));
        send(CommunicationCommands.reset());

        try {
            r0.get();
            r1.get();
        } catch (InterruptedException | ExecutionException e) {
            log.error(e);
        }
    }

    private void checkI2CProtocol() {
        if (transmissionProtocol == Protocol.I2C) {
            throw new IllegalStateException("Not available while using the I2C protocol.");
        }
    }
}
