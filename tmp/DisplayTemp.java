/***********************************************************
 *  MATRIX ORBITAL - DISPLAY TEMPERATURE SAMPLE CODE
 *  December 16, 2002 
 *  This program demonstrates how to communicate with a DS1620 chip
 *  to check for the room temperature in degree Celsius.
 *  Once the program has the temperature, it proceeds to display it on a
 *  CD using large digits.
 *  The program iterates endlessly checking and displaying the
 *  temperature every minute.
 *  LIMITATIONS:
 *  1. This program assumes that the temperature will never be below 0
 *      or above 99 degrees Celsius.
 *  2. This program also assumes a minimum LCD size of 4 rows by 20
 *      columns.
 ***********************************************************/

// Stamp core basic classes
import stamp.core.*;
import stamp.peripheral.sensor.temperature.DS1620;

// This class encapsulates the basic capabilities of the Dallas DS1620 3-wire
// temperature sensor

public class DisplayTemp {

    // A virtual peripheral UART.
    // Each instance of this class provides asynchronous serial communication
    // ina single direction. For full-duplex communications create two Uart
    // objects, one for each direction.
    // Uart.dirTransmit -> Uart will be used to transmit data only.
    // CPU.pin0 -> Pin used to transmit data
    // Uart.dontInvert -> data is not inverted
    // Uart.speed19200 -> serial data transfer speed (baud rate)
    // Uart.stop1 -> Number of stop bits used is 1
    static Uart txUart = new Uart(Uart.dirTransmit, CPU.pin0, Uart.dontInvert,
            Uart.speed19200, Uart.stop1 );

    // main function drives the program
    public static void main() {

        // Creates DS1620 temperature sensor object
        // CPU.pin4: Data pin
        // CPU.pin5: Clock pin
        // CPU.pin6: RST
        DS1620 indoor = new DS1620(CPU.pin4,CPU.pin5,CPU.pin6);
        int iTemp = 0;   // Holds temperature in Celsious
        int iTenths = 0; // Most significant digit of temperature (Tenths)
        int iOnes = 0;   // Least significant digit of temperature (Ones)

        // General display settings
        // Clear screen
        txUart.sendByte(0xFE); // Command Prefix
        txUart.sendByte(0x58); // Clear command
        // Underline Off
        txUart.sendByte(0xFE); // Command Prefix
        txUart.sendByte(0x4B); // Underline off command
        // Blinking Off
        txUart.sendByte(0xFE); // Command Prefix
        txUart.sendByte(0x54); // Blinking off command

        // Display 'Degree Celsius' beside number of degrees
        // Position cursor
        txUart.sendByte(0xFE); // Command Prefix
        txUart.sendByte(0x47); // Move cursor command
        txUart.sendByte(0x09); // column position
        txUart.sendByte(0x02); // row position
        // Send text to the above cursor position
        txUart.sendString("Degrees");  //Sends the word "Degrees" to the LCD
        // Next LineBuilder - Move cursor to the next line
        txUart.sendByte(0xFE); // Command Prefix
        txUart.sendByte(0x47); // Move cursor command
        txUart.sendByte(0x09); // column position
        txUart.sendByte(0x03); // row position
        // Send text to the above position
        txUart.sendString("Celsius"); // Sends the word "Celsius" to the LCD
        // Large Digits On
        txUart.sendByte(0xFE); // Command Prefix
        txUart.sendByte(0x6E); // Large digits on command
        // Infinite Loop that checks current temperature and displays it
        while(true)
        {
            // Get Temperature in Celsius
            iTemp = indoor.getTempC();  // Requests temperature from DS1620
            // Get tenths - the most significant digit to display first
            iTenths = (int)iTemp / 10;
            // Get ones - the least significant digit to display second
            iOnes = iTemp - iTenths * 10;
            // Send temperature to lcd - first tenths then ones
            // Send tenths
            txUart.sendByte(0xFE); // Command Prefix
            txUart.sendByte(0x23); // Large digit command
            txUart.sendByte(0x01); // Column location - Large digits take-up 3 cols
            txUart.sendByte(iTenths); // Digit - Takes up columns 1, 2, and 3
            // Send ones - leave column 4 blank
            txUart.sendByte(0xFE); // Command Prefix
            txUart.sendByte(0x23); // Large digit command
            txUart.sendByte(0x05); // Column location - 1,2,3 taken-up by tenths
            txUart.sendByte(iOnes); // Digit - Takes up columns 5, 6, an 7
            // Pause for a minute - maximum delay is 32 seconds
            CPU.delay(32000); // Pauses for 32 seconds
            CPU.delay(32000); // Pauses for 32 seconds
        }
    }
} 