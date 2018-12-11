import jdk.nashorn.internal.ir.annotations.Ignore;

import java.io.*;
import java.util.*;
import javax.comm.*;

/**
 * Collection of methods to control a Matrix Orbital LCD.
 *
 *	<p> Bugs: None known.
 *
 *	@author Owen Langman (langman@cs.wisc.edu)
 *
 **/
public class LCDControl {

    private CommPortIdentifier portId;
    private SerialPort serialPort;
    private OutputStream outputStream;

    public static final char COMMAND_START = (char)254;
    public static final char COMMAND_NULL = (char)0;
    public static final char COMMAND_ETX = (char)3;
    public static final char COMMAND_DEL = (char)253;

    /**
     * Constructor. Uses COM1 as the default port.
     *
     **/
    public LCDControl() {
        this(1);	//uses com1 with default const.
    }

    /**
     * Constructor. Initializes COM port and resets the LCD.
     *
     * @param port Interger specifying the COM port to output to.
     **/
    public LCDControl(int port) {

        String portname = new String("COM");
        portname += port;
        try {
            portId = CommPortIdentifier.getPortIdentifier(portname);
        } catch (NoSuchPortException ex) {
            System.out.println(portname + " does not exist.");
            return;
        }

        try {
            serialPort = (SerialPort)portId.open("LCDControl",2000);
        } catch (PortInUseException ex) {
            System.out.println("Could not open " + portname);
            return;
        }

        try {
            serialPort.setSerialPortParams(19200,
                    SerialPort.DATABITS_8,
                    SerialPort.STOPBITS_1,
                    SerialPort.PARITY_NONE);
        } catch (UnsupportedCommOperationException ex) {
            System.out.println("Could not set serial stuff");
        }

        try {
            outputStream = serialPort.getOutputStream();
        } catch (IOException e) {}

        resetLCD();
    }

    /**
     * Resets the lcd in every way possible.
     */
    public void resetLCD() {

        String resetString;
        resetString = new String(COMMAND_START + "B" + COMMAND_NULL +
                COMMAND_START + "~" + COMMAND_NULL + COMMAND_START +
                "T" + COMMAND_START + "Y" + COMMAND_ETX +
                COMMAND_START + "X");
        try {
            outputStream.write(resetString.getBytes());
        } catch (Exception ex) {
            System.out.println("Could not reset lcd");
        }
    }

    /**
     * Clears the LCD screen.
     */
    public void clear() {

        String clearString;
        clearString = new String(COMMAND_START + "X");
        try {
            outputStream.write(clearString.getBytes());
        } catch (Exception ex) {
            System.out.println("Could not clear lcd");
        }
    }

    /**
     * Writes a string to the screen at the cursor's current
     * position.
     *
     * @param str String to write to the screen.
     */
    public void display_write(String str) {

        try {
            outputStream.write(str.getBytes());
        } catch (Exception ex) {
            System.out.println("Could not write to lcd");
        }

    }

    /**
     * Changes position of the lcd's cursor.
     *
     * @param x Horizontal index to move the cursor to
     * @param y LineBuilder number to move the cursor to
     */
    public void display_position(int x, int y) {

        String displayString;
        displayString = new String(COMMAND_START + "G");

        try {
            outputStream.write(displayString.getBytes());
            outputStream.write(y);
            outputStream.write(x);
        } catch (Exception ex) {
            System.out.println("Could not change cursor position on lcd");
        }
    }

    /**
     * Full clear then shutdown of the LCD
     */
    public void display_close() {

        String clearString, closeString;
        clearString = new String(COMMAND_START + "X");
        closeString = new String(COMMAND_START + "F");

        try {
            outputStream.write(clearString.getBytes());
            outputStream.write(closeString.getBytes());
        } catch (Exception ex) {
            System.out.println("Could not close lcd");
        }
    }
}