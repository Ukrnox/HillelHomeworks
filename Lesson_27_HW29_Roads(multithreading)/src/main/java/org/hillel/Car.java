package org.hillel;

import java.awt.*;
import java.util.Random;

public class Car
{
    public int direction;

    public int carX;
    public int carY;
    private int carSpeed;
    private boolean stop;

    Color carColor;

    public Car(int x, int y, int direction, Color carColor)
    {
        stop = false;
        changeSpeed();
        this.carColor = carColor;
        carX = x;
        carY = y;
        this.direction = direction;
    }

    public boolean isStop()
    {
        return stop;
    }

    public void setStop(boolean stop)
    {
        this.stop = stop;
    }

    public int getDirection()
    {
        return direction;
    }

    private void changeSpeed()
    {
        Random random = new Random();
        carSpeed = (random.nextInt(10) + 1);
    }

    private void changeColor()
    {
        Random random = new Random();
        int colorNum = random.nextInt(7);
        switch (colorNum)
        {
            case 1:
                carColor = Color.BLACK;
                break;
            case 2:
                carColor = Color.BLUE;
                break;
            case 3:
                carColor = Color.GRAY;
                break;
            case 4:
                carColor = Color.cyan;
                break;
            case 5:
                carColor = Color.pink;
                break;
            case 6:
                carColor = Color.ORANGE;
                break;
        }
    }

    public void move()
    {
        if (direction == 0)
        {
            carY--;
        }
        if (direction == 2)
        {
            carY++;
        }
        if (direction == 1)
        {
            carX++;
        }
        if (direction == 3)
        {
            carX--;
        }

        if (carY > Roads.HEIGHT - 1)
        {
            carY = 0;
            changeColor();
            changeSpeed();
        }
        if (carX > Roads.WIDTH - 1)
        {
            carX = 0;
            changeColor();
            changeSpeed();
        }
        if (carY < 0)
        {
            carY = Roads.HEIGHT - 1;
            changeColor();
            changeSpeed();
        }
        if (carX < 0)
        {
            carX = Roads.WIDTH - 1;
            changeColor();
            changeSpeed();
        }
    }

    public Color getColor()
    {
        return carColor;
    }

    public int getCarSpeed()
    {
        return carSpeed;
    }
}
