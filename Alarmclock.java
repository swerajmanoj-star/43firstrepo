import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class AlarmClock {
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");
    private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter alarm time (HH:mm): ");
        String alarmTimeStr = scanner.nextLine();
        LocalTime alarmTime = LocalTime.parse(alarmTimeStr, TIME_FORMATTER);

        System.out.println("Alarm set for " + alarmTime.format(TIME_FORMATTER));

        Runnable alarmTask = () -> {
            System.out.println("ALARM! Time to wake up!");
            // Add sound or other actions here if needed
        };

        long delay = calculateDelay(alarmTime);
        scheduler.schedule(alarmTask, delay, TimeUnit.SECONDS);
        scheduler.shutdown();
    }

    private static long calculateDelay(LocalTime alarmTime) {
        LocalTime now = LocalTime.now();
        long secondsUntilAlarm = alarmTime.toSecondOfDay() - now.toSecondOfDay();
        if (secondsUntilAlarm <= 0) {
            secondsUntilAlarm += 24 * 60 * 60; // Next day if already passed
        }
        return secondsUntilAlarm;
    }
}