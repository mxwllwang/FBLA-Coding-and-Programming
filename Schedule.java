package fec;

public class Schedule implements java.io.Serializable {

    WorkInterval[] mon;
    WorkInterval[] tue;
    WorkInterval[] wed;
    WorkInterval[] thu;
    WorkInterval[] fri;
    WorkInterval[] sat;
    WorkInterval[] sun;

    // 24 hour clock object
    public class Time {
        // four digit integers determine start time and end time
        // 24-hour clock (0 - 23)
        public int hours;
        public int minutes;

        Time(int hours, int minutes) {
            this.hours = hours;
            this.minutes = minutes;
        }

        String print() {
            return (hours + ":" + minutes);
        }

    }

    // one work interval (ex. 8:00 - 14:00)
    public class WorkInterval {
        Time start;
        Time end;
        int elapsedTime;

        WorkInterval(Time start, Time end) {
            elapsedTime = (60 * end.hours + end.minutes)
                    - (60 * start.hours + start.minutes);
            this.start = start;
            this.end = end;
        }

        String print() {
            return (start.print() + " - " + end.print());
        }

    }

    // A schedule
    public Schedule(WorkInterval[] mon, WorkInterval[] tue, WorkInterval[] wed,
            WorkInterval[] thu, WorkInterval[] fri, WorkInterval[] sat,
            WorkInterval[] sun) {

    }

    public String print() {
        StringBuilder sb = new StringBuilder();
        sb.append("Monday: ");
        for (WorkInterval wi : mon) {
            sb.append(wi.toString() + " ");
        }
        sb.append("/nTuesday: ");
        for (WorkInterval wi : tue) {
            sb.append(wi.toString() + " ");
        }
        sb.append("/nWednesday: ");
        for (WorkInterval wi : wed) {
            sb.append(wi.toString() + " ");
        }
        sb.append("/nThursday: ");
        for (WorkInterval wi : thu) {
            sb.append(wi.toString() + " ");
        }
        sb.append("/nFriday: ");
        for (WorkInterval wi : fri) {
            sb.append(wi.toString() + " ");
        }
        sb.append("/nSaturday: ");
        for (WorkInterval wi : sat) {
            sb.append(wi.toString() + " ");
        }
        sb.append("/nSunday: ");
        for (WorkInterval wi : sun) {
            sb.append(wi.toString() + " ");
        }
        return sb.toString();
    }

}
