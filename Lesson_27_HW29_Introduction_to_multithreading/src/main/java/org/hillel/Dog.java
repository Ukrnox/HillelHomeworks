package org.hillel;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Dog
{
    private final String name;
    private int energy;
    private boolean alive;
    private boolean eating;
    private final int MIN_ENERGY = 0;
    private long allMinutes;
    private final Time time;

    public Dog(String name, Time time)
    {
        log.info(this.getClass().getName() + " constructor : 'Dog(String name, Time time)'");
        allMinutes = time.getAllMinutes();
        this.time = time;
        this.name = name;
        energy = 1000;
        alive = true;
        eating = false;
    }

    public boolean isEating()
    {
        log.info(this.getClass().getName() + " method : 'isEating()'");
        return eating;
    }

    public void setEating(boolean eating)
    {
        log.info(this.getClass().getName() + " method : 'setEating(boolean eating)'");
        this.eating = eating;
    }

    public String getName()
    {
        log.info(this.getClass().getName() + " method : 'getName()'");
        return name;
    }

    private void checkTime(Time time)
    {
        log.info(this.getClass().getName() + " method : 'checkTime(Time time)'");
        if (time.getAllMinutes() - allMinutes > 10)
        {
            energy -= ((time.getAllMinutes() - allMinutes) / 10 * 10);
            allMinutes = time.getAllMinutes();
        }
    }

    public int getEnergy(Time time)
    {
        log.info(this.getClass().getName() + " method : 'getEnergy(Time time)'");
        checkTime(time);
        return energy;
    }

    public void addEnergy(int energy)
    {
        log.info(this.getClass().getName() + " method : 'addEnergy(int energy)'");
        this.energy += energy;
        checkEnergy();
    }

    public boolean eat(Meat meat, Time time)
    {
        log.info(this.getClass().getName() + " method : 'eat(Meat meat, Time time)'");
        addEnergy(meat.getCalories());
        checkTime(time);
        return isAlive(time);
    }


    public void checkEnergy()
    {
        log.info(this.getClass().getName() + " method : 'checkEnergy()'");
        if (energy < MIN_ENERGY)
        {
            alive = false;
        }
    }

    public boolean isAlive(Time time)
    {
        log.info(this.getClass().getName() + " method : 'isAlive(Time time)'");
        checkTime(time);
        checkEnergy();
        return alive;
    }

    @Override
    public String toString()
    {
        log.info(this.getClass().getName() + " method : 'toString()'");
        isAlive(time);
        return "Dog:\n" +
                "name: '" + name + '\'' + '\n' +
                "energy - " + energy + '\n' +
                "alive - " + alive + '\n';
    }
}
