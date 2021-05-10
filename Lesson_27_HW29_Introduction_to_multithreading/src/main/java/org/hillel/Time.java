package org.hillel;

import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Slf4j
public class Time
{
    private long allMinutes;
    private long year;
    private long month;
    private long day;
    private long hour;
    private long minutes;

    public Time()
    {
        log.info(this.getClass().getName() + " constructor : 'Time()'");
    }

    public long getYear()
    {
        log.info(this.getClass().getName() + " method : 'getYear()'");
        return year;
    }

    public void setYear(long year)
    {
        log.info(this.getClass().getName() + " method : 'setYear(long year)'");
        this.year = year;
    }

    public long getMonth()
    {
        log.info(this.getClass().getName() + " method : 'getMonth()'");
        return month;
    }

    public void setMonth(long month)
    {
        log.info(this.getClass().getName() + " method : 'setMonth(long month)'");
        this.month = month;
    }

    public long getDay()
    {
        log.info(this.getClass().getName() + " method : 'getDay()'");
        return day;
    }

    public void setDay(long day)
    {
        log.info(this.getClass().getName() + " method : 'setDay(long day)'");
        this.day = day;
    }

    public long getHour()
    {
        log.info(this.getClass().getName() + " method : 'getHour()'");
        return hour;
    }

    public void setHour(long hour)
    {
        log.info(this.getClass().getName() + " method : 'setHour(long hour)'");
        this.hour = hour;
    }

    public long getMinutes()
    {
        log.info(this.getClass().getName() + " method : 'getMinutes()'");
        return minutes;
    }

    public void setMinutes(long minutes)
    {
        log.info(this.getClass().getName() + " method : 'setMinutes(long minutes)'");
        this.minutes = minutes;
    }

    public synchronized long getAllMinutes()
    {
        log.info(this.getClass().getName() + " method : 'getAllMinutes()'");
        return allMinutes;
    }

    public void setAllMinutes(long allMinutes)
    {
        log.info(this.getClass().getName() + " method : 'setAllMinutes(long allMinutes)'");
        this.allMinutes = allMinutes;
    }

    public synchronized void addMinutes(int minutes)
    {
        log.info(this.getClass().getName() + " method : 'addMinutes(int minutes)'");
        this.allMinutes += minutes;
        this.minutes += minutes;
        if (this.minutes > 60)
        {
            hour += this.minutes / 60;
            this.minutes %= 60;
            if (hour > 24)
            {
                day += this.hour / 24;
                hour %= 24;
                if (day > 30)
                {
                    month += day / 30;
                    day %= 30;
                    if (month > 12)
                    {
                        year += month / 12;
                        month %= 12;
                    }
                }
            }
        }
        log.info("Spend " + minutes + " minutes");
    }

    @Override
    public String toString()
    {
        log.info(this.getClass().getName() + " method : 'toString()'");
        return "Date:" + '\n' +
                "year - " + year + '\n' +
                "month - " + month + '\n' +
                "day - " + day + '\n' +
                "hour - " + hour + '\n' +
                "minutes - " + minutes + '\n';
    }

    @Override
    public boolean equals(Object o)
    {
        log.info(this.getClass().getName() + " method : 'equals(Object o)'");
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Time time = (Time) o;
        return year == time.year && month == time.month && day == time.day && hour == time.hour && minutes == time.minutes;
    }

    @Override
    public int hashCode()
    {
        log.info(this.getClass().getName() + " method : 'hashCode()'");
        return Objects.hash(year, month, day, hour, minutes);
    }
}
