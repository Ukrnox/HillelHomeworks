package org.hillel;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Meat
{
    private int calories;
    private boolean row;

    public Meat()
    {
        log.info(this.getClass().getName() + " constructor : 'Meat()'");
        calories = 100;
        row = true;
    }

    public int getCalories()
    {
        log.info(this.getClass().getName() + " method : 'getCalories()'");
        return calories;
    }

    public boolean isRow()
    {
        log.info(this.getClass().getName() + " method : 'isRow()'");
        return row;
    }

    public void setRow(boolean row)
    {
        log.info(this.getClass().getName() + " method : 'setRow(boolean row)'");
        this.row = row;
        if (!row)
        {
            calories = 200;
        }
    }

    @Override
    public String toString()
    {
        log.info(this.getClass().getName() + " method : 'toString()'");
        return  "row - " + row + '\n' +
                "calories - " + calories + '\n';
    }
}
