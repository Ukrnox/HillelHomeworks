package org.hillel;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.TimeUnit;

public class Roads
{
    private JFrame frame;
    private JPanel draws;
    public static final int SCALE = 30;
    public static final int WIDTH = 20;
    public static final int HEIGHT = 20;

    private int indicator1 = 1;
    private int indicator2 = 2;

    private final Car car1 = new Car(9, 0, 0, Color.YELLOW);
    private final Car car2 = new Car(0, 8, 1, Color.DARK_GRAY);

    public Roads()
    {
        frameGenerator();
        movement();
    }

    public synchronized void changeTrafficLight()
    {
        if (indicator1 == 1)
        {
            indicator1 = 2;
            indicator2 = 1;
        }
        else
        {
            indicator1 = 1;
            indicator2 = 2;
        }
        notifyAll();
    }

    public synchronized void checkTrafficLight(Car car)
    {
        while (car.getDirection() == 0 && car.carY == 12 && (indicator1 == 1 ||
                car2.carX > 5 && car2.carX < 12 && !car2.isStop()))
        {
            car.setStop(true);
            try
            {
                wait();
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
        while (car.getDirection() == 1 && car.carX == 5 && (indicator2 == 1 ||
                car1.carY > 5 && car1.carY < 12 && !car1.isStop()))
        {
            car.setStop(true);
            try
            {
                wait();
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
        car.setStop(false);
        notifyAll();
    }

    private JPanel mainDraw()
    {
        return new DrawLines()
        {
            private final int SCALE = 30;
            private final int WIDTH = 20;
            private final int HEIGHT = 20;

            private void drawRoads(Graphics graphics)
            {
                graphics.setColor(Color.BLACK);
                graphics.drawLine(8 * SCALE, HEIGHT * SCALE, 8 * SCALE,
                        HEIGHT * SCALE / 2 + SCALE);
                graphics.drawLine(12 * SCALE, HEIGHT * SCALE, 12 * SCALE,
                        HEIGHT * SCALE / 2 + SCALE);
                graphics.drawLine(12 * SCALE, 0, 12 * SCALE,
                        7 * SCALE);
                graphics.drawLine(8 * SCALE, 0, 8 * SCALE,
                        7 * SCALE);
                graphics.drawLine(0, 7 * SCALE, 8 * SCALE,
                        7 * SCALE);
                graphics.drawLine(0, 11 * SCALE, 8 * SCALE,
                        11 * SCALE);
                graphics.drawLine(12 * SCALE, 7 * SCALE, WIDTH * SCALE,
                        7 * SCALE);
                graphics.drawLine(12 * SCALE, 11 * SCALE, WIDTH * SCALE,
                        11 * SCALE);
                for (int y = 0; y < HEIGHT; y += 2)
                {
                    graphics.drawLine(10 * SCALE, y * SCALE, 10 * SCALE, y * SCALE + SCALE);
                }
                for (int x = 0; x < HEIGHT; x += 2)
                {
                    graphics.drawLine(x * SCALE, 9 * SCALE, x * SCALE + SCALE, 9 * SCALE);
                }
            }

            private void drawCar(Graphics graphics, Car car, Color color)
            {
                graphics.setColor(color);
                graphics.fillRect(
                        car.carX * SCALE,
                        car.carY * SCALE,
                        SCALE * 2,
                        SCALE * 2);
            }

            private void drawTrafficLight(Graphics graphics, int color1)
            {
                if (color1 == 1)
                {
                    graphics.setColor(Color.GREEN);
                    graphics.fillOval(6 * SCALE, 11 * SCALE, SCALE, SCALE);

                    graphics.setColor(Color.RED);
                    graphics.fillOval(12 * SCALE, 12 * SCALE, SCALE, SCALE);
                }
                else
                {
                    graphics.setColor(Color.RED);
                    graphics.fillOval(6 * SCALE, 11 * SCALE, SCALE, SCALE);

                    graphics.setColor(Color.GREEN);
                    graphics.fillOval(12 * SCALE, 12 * SCALE, SCALE, SCALE);
                }
            }

            public void paint(Graphics graphics)
            {
                graphics.setColor(Color.WHITE);
                graphics.fillRect(0, 0, WIDTH * SCALE, HEIGHT * SCALE);

                drawRoads(graphics);
                drawCar(graphics, car1, car1.getColor());
                drawCar(graphics, car2, car2.getColor());
                drawTrafficLight(graphics, indicator1);
            }
        };
    }

    private void frameGenerator()
    {
        frame = new JFrame("Roads");
        frame.setSize(WIDTH * SCALE + 12, HEIGHT * SCALE + 36);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        draws = mainDraw();
        frame.add(draws);
        frame.setVisible(true);
    }

    public void movement()
    {
        Runnable runnableCar1 = () ->
        {
            while (true)
            {
                checkTrafficLight(car1);
                car1.move();
                draws.repaint();
                try
                {
                    TimeUnit.MILLISECONDS.sleep(500 / car1.getCarSpeed());
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
        };

        Runnable runnableCar2 = () ->
        {
            while (true)
            {
                checkTrafficLight(car2);
                car2.move();
                draws.repaint();
                try
                {
                    TimeUnit.MILLISECONDS.sleep(500 / car2.getCarSpeed());
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
        };

        Runnable runnableTrafficLight = () ->
        {
            while (true)
            {
                changeTrafficLight();
                try
                {
                    TimeUnit.SECONDS.sleep(2);
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
        };

        Thread thread1 = new Thread(runnableCar1);
        Thread thread2 = new Thread(runnableCar2);
        Thread thread3 = new Thread(runnableTrafficLight);
        thread1.setName("Car1");
        thread2.setName("Car2");
        thread3.setName("TrafficLight");
        thread1.setDaemon(true);
        thread2.setDaemon(true);
        thread3.setDaemon(true);
        thread1.start();
        thread2.start();
        thread3.start();
    }
}