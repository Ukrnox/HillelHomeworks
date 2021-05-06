package org.hillel;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Bonfire
{
    private boolean burning;
    private int capacity;
    private long allMinutes;
    private final Time time;

    public int getCapacity()
    {
        log.info(this.getClass().getName() + " method : 'getCapacity()'");
        return capacity;
    }

    public void addCapacity(int capacity)
    {
        log.info(this.getClass().getName() + " method : 'addCapacity(int capacity)'");
        this.capacity += capacity;
    }

    public boolean isBurning()
    {
        log.info(this.getClass().getName() + " method : 'isBurning()'");
        return burning;
    }

    public void setBurning(boolean burning, Time time)
    {
        log.info(this.getClass().getName() + " method : 'setBurning(boolean burning, Time time)'");
        if (!this.burning)
        {
            allMinutes = time.getAllMinutes();
        }
        this.burning = burning;
    }

    public Bonfire(Time time)
    {
        log.info(this.getClass().getName() + " constructor : 'Bonfire(Time time)'");
        this.time = time;
        this.burning = false;
        this.capacity = 1000;
        allMinutes = time.getAllMinutes();
        log.info("Bonfire was made");
    }

    public boolean isBurning(Time time)
    {
        log.info(this.getClass().getName() + " method : 'isBurning(Time time)'");
        if (burning && time.getAllMinutes() - allMinutes > 10)
        {
            capacity -= (time.getAllMinutes() - allMinutes) / 10 * 10;
            allMinutes = time.getAllMinutes();
        }
        if (capacity < 0)
        {
            burning = false;
            capacity = 0;
        }
        return burning;
    }

    @Override
    public String toString()
    {
        log.info(this.getClass().getName() + " method : 'toString()'");
        isBurning(time);
        return "Bonfire:\n" +
                "burning - " + burning + '\n' +
                "capacity - " + capacity +
                '\n';
    }
}
