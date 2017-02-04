package packets.datablocks;

import java.sql.Time;

/**
 *
 * @author Suhas S Pai
 */

public class TimeOfDay {
    public TimeOfDay(Integer hours, Integer minutes, Integer seconds) {
        time=new Time(hours, minutes, seconds);
    }
    @Override
    public String toString() {
        return time.toString();
    }
    
    private final Time time;
}
