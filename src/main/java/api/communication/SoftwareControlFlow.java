package api.communication;

import api.Commander;

public class SoftwareControlFlow extends Commander {

    private static final byte
            DEFAULT_XON = (byte) 0xFF, DEFAULT_XOFF = (byte) 0xFE,
            STANDARD_XON = 0x11, STANDARD_XOFF = 0x13;

    private byte
            xOn = DEFAULT_XON,
            xOff = DEFAULT_XOFF;

    public SoftwareControlFlow(Commander parent) {
        super(parent);
    }

    /**
     * Enables simple flow control. The display will return a single, Xoff, byte to the host when the display buffer is
     * almost full and a different, Xon, byte when the buffer is almost empty. Full value should provide enough room for
     * the largest data packet to be received without buffer overflow. No data should be sent to the display between full
     * and empty responses to permit processing. Buffer size is 256* bytes. Not available in I 2 C. Default off.
     *
     * @param almostFull Byte Number of bytes remaining before buffer is completely full, 0 < almostFull < almostEmpty < 256.
     * @param almostEmpty Byte Number of bytes before buffer can be considered empty enough to accept data.
     */
    public void turnOn(int almostFull, int almostEmpty) {
        send(CommunicationCommands.turnSoftwareFlowControlOn(almostFull, almostEmpty));
    }

    /**
     * Disables flow control. Bytes sent to the display may be permitted to overflow the buffer resulting in data loss.
     */
    public void turnOff() {
        send(CommunicationCommands.turnSoftwareFlowControlOff());
    }

    /**
     * This command permits the display to utilize standard flow control values of {@value STANDARD_XON} and {@value STANDARD_XOFF}.
     */
    public void setStandardControlResponse() {
        setControlResponse(STANDARD_XON, STANDARD_XOFF);
    }

    /**
     * Sets the values returned for almost full and almost empty messages when in flow control mode. Note that defaults are {@value DEFAULT_XON} and {@value DEFAULT_XOFF}.
     *
     * @param almostEmptyResponse Byte Value returned when display buffer is almost empty, permitting transmission to resume.
     * @param almostFullResponse Byte Value returned when display buffer is almost full, signaling transmission to halt.
     */
    public void setControlResponse(byte almostEmptyResponse, byte almostFullResponse) {
        send(CommunicationCommands.setSoftwareFlowControlResponse(almostEmptyResponse, almostFullResponse));

        this.xOn = almostEmptyResponse;
        this.xOff = almostFullResponse;
    }

    /**
     * Gets the current expected response for when the buffer is almost empty.
     *
     * @return xOn.
     */
    public byte getAlmostEmptyResponse() {
        return xOn;
    }

    /**
     * Gets the current expected response for when the buffer is almost full.
     *
     * @return xOff.
     */
    public byte getAlmostFullResponse() {
        return xOff;
    }
}
