package MyLib;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Log {
    private static final List<Log> logEntries = new ArrayList<>();

    private LocalDate timestamp;
    private String details;
    private String transactionId;

    public Log(String transactionId, String details)
    {
        this.transactionId = transactionId;
        this.details = details;
        this.timestamp = LocalDate.now();
    }

    public static void record(String transactionId, String details)
    {
        logEntries.add(new Log(transactionId, details));
    }

    public static List<Log> getAll()
    {
        return logEntries;
    }

    public LocalDate getTimestamp()
    {
        return timestamp;
    }

    public String getDetails()
    {
        return details;
    }

    public String getTransactionId()
    {
        return transactionId;
    }

    @Override
    public String toString()
    {
        return String.format("[%s] %s - %s", timestamp, transactionId, details);
    }
}
