package api.communication;

import com.google.common.primitives.Bytes;
import commands.AbstractCommands;
import commands.Util;
import lombok.experimental.UtilityClass;

@UtilityClass
// TODO make not public
public class CommunicationCommands extends AbstractCommands {

    byte[] changeBaudRate(BaudRate baudRate) {
        return chain("39", baudRate.getCommand(), 1);
    }

    byte[] changeI2CSlaveAddress(int address) {
        checkValidUByte(address);
        if (address %2 != 0) {
            throw new IllegalArgumentException("The address should be even.");
        }
        return chain("33", address, 1);
    }

    byte[] transmissionProtocolSelect(Protocol protocol) {
        return chain("A0", protocol.getId(), 1);
    }

    byte[] setNonStandardBaudRate(int baudRate) {
        checkRange(baudRate, 0, 1_000_000);
        return chain("A4", baudRate, 4);
    }

    byte[] setFlowControlMode(ControlFlowMode controlFlowMode) {
        return chain("3F", controlFlowMode.getId(), 1);
    }

    byte[] setHardwareFlowControlTriggerLevel(HardwareControlFlowTriggerLevel level) {
        return chain("3E", level.getLevel(), 1);
    }

    byte[] turnSoftwareFlowControlOn(int almostFull, int almostEmpty) {
        return chain("3A", almostFull, 1, almostEmpty, 1);
    }

    byte[] turnSoftwareFlowControlOff() {
        return chain("3B");
    }

    byte[] setSoftwareFlowControlResponse(int xOn, int xOff) {
        return chain("3C", xOn, 1, xOff, 1);
    }

    byte[] echo(byte[] data) {
        checkValidUShort(data.length);
        return Bytes.concat(chain("FF"), Util.shortToBytes((short) data.length), data);
    }

    byte[] delay(int time) {
        checkRange(time, 0, 2000);
        return chain("FB", time, 2);
    }

    public byte[] reset() {
        return chain("FD", Util.hex("4D"), 1, Util.hex("4F"), 1, Util.hex("75"), 1, Util.hex("6E"), 1);
    }

}
