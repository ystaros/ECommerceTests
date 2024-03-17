package utils;

import java.lang.reflect.Method;
import java.text.DateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ReportUtils {

    private static final String LINE = "\n" + "*".repeat(100);
    private static final String LONG_TABS = "\t".repeat(7);
    private static final String SHORT_TABS = "\t".repeat(5);

    public static void logLine() {
        LoggerUtils.logInfo(LINE);
    }

    public static void logReportHeader() {
        final String header = """
                
                
                %sTest Run
                %sDate: %s
                """.formatted(LONG_TABS, SHORT_TABS, getLocalDateTime());
        LoggerUtils.logInfo(LINE + header + LINE);

    }

    private static String getLocalDateTime() {
        LocalDateTime dateTimeNow = LocalDateTime.now();
        DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd, HH:mm"); // Date: 2024-03-17, 13:30
//        DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd, hh:mma");  // Date: 2024-03-17, 01:30PM

        return dateTimeNow.format(dateTimeFormat);
    }

    public static void logTestName(Method method) {
        String testInfo = """
                Run: class - %s""".formatted((method.getDeclaringClass().getSimpleName() + ", test/method - " + method.getName()) + ".");
        LoggerUtils.logInfo(testInfo);


    }




}
