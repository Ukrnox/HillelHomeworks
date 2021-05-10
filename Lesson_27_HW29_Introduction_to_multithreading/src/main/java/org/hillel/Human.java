package org.hillel;

import lombok.extern.slf4j.Slf4j;

import java.util.Comparator;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.PriorityBlockingQueue;

@Slf4j
public class Human
{
    private final String name;
    private final Bonfire bonfire;
    private final Dog dog;
    private Queue<Meat> meats = new PriorityBlockingQueue<>(10, Comparator.comparingInt(Meat::getCalories).reversed());
    private final Time time;

    public Human(String name, Time time, Dog dog)
    {
        log.info(this.getClass().getName() + " constructor : 'Human(String name, Time time, Dog dog)'");
        this.dog = dog;
        this.time = time;
        this.name = name;
        bonfire = new Bonfire(time);
    }

    public String hunting()
    {
        log.info(this.getClass().getName() + " method : 'hunting()'");
        Random random = new Random();
        int numberOfMeat = random.nextInt(6);
        for (int i = numberOfMeat; i > 0; i--)
        {
            meats.add(new Meat());
        }
        String resultText = "You could not find any animals!";
        switch (numberOfMeat)
        {
            case 1:
                resultText = "You found a rabbit.";
                break;
            case 2:
                resultText = "You found two rabbits.";
                break;
            case 3:
                resultText = "You found a wild boar.";
                break;
            case 4:
                resultText = "You found a wild boar and one rabbit.";
                break;
            case 5:
                resultText = "You found a deer!";
                break;
        }
        return resultText;
    }

    public void feedingADog(Meat meat)
    {
        if (meat != null)
        {
            if (dog.isAlive(time))
            {
                dog.eat(meat, time);
            }
        }
    }

    public synchronized void fryOneMeat(Meat meat)
    {
        log.info(this.getClass().getName() + " method : 'fryOneMeat()'");
        if (bonfire.isBurning(time))
        {
            meat.setRow(false);
            meats.add(meat);
        }
    }

    public synchronized Meat getRowMeat()
    {
        for (Meat meat : meats)
        {
            if (meat.isRow())
            {
                meats.remove(meat);
                return meat;
            }
        }
        return null;
    }

    public String printMeats()
    {
        log.info(this.getClass().getName() + " method : 'printMeats()'");
        StringBuilder result = new StringBuilder();
        result.append("Meats:\n");
        for (Meat meat :
                meats)
        {
            result.append(meat);
            result.append("*************\n");
        }
        return result.toString();
    }

    public String getName()
    {
        log.info(this.getClass().getName() + " method : 'getName()'");
        return name;
    }

    public Time getTime()
    {
        log.info(this.getClass().getName() + " method : 'getTime()'");
        return time;
    }

    public Bonfire getBonfire()
    {
        log.info(this.getClass().getName() + " method : 'getBonfire()'");
        return bonfire;
    }

    public void addWoodInFire()
    {
        bonfire.addCapacity(100);
    }

    public void makeFire()
    {
        log.info(this.getClass().getName() + " method : 'makeFire()'");
        if (bonfire.getCapacity() > 0)
        {
            bonfire.setBurning(true, time);
        }
    }

    public Dog getDog()
    {
        log.info(this.getClass().getName() + " method : 'getDog()'");
        return dog;
    }

    public boolean checkRowMeats()
    {
        if (!meats.isEmpty())
        {
            for (Meat meat : meats)
            {
                if (meat.isRow())
                {
                    return true;
                }
            }
        }
        return false;
    }

    public synchronized Queue<Meat> getMeats()
    {
        log.info(this.getClass().getName() + " method : 'getMeats()'");
        return meats;
    }

    public void setMeats(Queue<Meat> meats)
    {
        log.info(this.getClass().getName() + " method : 'setMeats(Queue<Meat> meats)'");
        this.meats = meats;
    }

    @Override
    public String toString()
    {
        log.info(this.getClass().getName() + " method : 'toString()'");
        return "Human:\n" +
                "name: '" + name + '\'' + '\n' +
                "**********\n" +
                bonfire +
                "**********\n" +
                dog +
                "**********\n" +
                time;
    }
}
