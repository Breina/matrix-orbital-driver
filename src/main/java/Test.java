import com.jezhumble.javasysmon.CpuTimes;
import com.jezhumble.javasysmon.JavaSysMon;
import com.jezhumble.javasysmon.MemoryStats;

public class Test {

    public static void main(String[] args) throws InterruptedException {
        JavaSysMon javaSysMon = new JavaSysMon();

//        for (;;) {
//            MemoryStats physical = javaSysMon.physical();
//            long freeBytes = physical.getFreeBytes();
//            long totalBytes = physical.getTotalBytes();
//
//            System.out.println((1d * totalBytes - freeBytes) * 100 / totalBytes);
//
//            Thread.sleep(100);
//        }



    }
}
