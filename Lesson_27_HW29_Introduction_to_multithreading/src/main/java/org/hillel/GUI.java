package org.hillel;

import lombok.extern.slf4j.Slf4j;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Slf4j
public class GUI
{
    private final JFrame frame = new JFrame("Dog live");

    private final Time time = new Time();
    private final Dog dog = new Dog("Jack", time);
    private final Human human = new Human("Nobody", time, dog);

    private final File dogImg = new File("src\\main\\resources\\funnyDog.jpg");
    private final File dogEating = new File("src\\main\\resources\\eatingDog.jpg");
    private final File dogHunting = new File("src\\main\\resources\\noDog.jpg");
    private final File badImg = new File("src\\main\\resources\\kerduk.jpg");
    private final File funnyHuman = new File("src\\main\\resources\\funnyHuman.jpg");
    private final File funnyHumanWaiting = new File("src\\main\\resources\\waitingHuman.jpg");
    private final File funnyHumanHunting = new File("src\\main\\resources\\funnyHunting.jpg");
    private final File frustratedHuman = new File("src\\main\\resources\\frustratedHuman.jpg");
    private final File activeBonfire = new File("src\\main\\resources\\activeBonfire.jpg");
    private final File activeBonfireMeat = new File("src\\main\\resources\\activeBonfireMeat.jpg");
    private final File extinctBonfire = new File("src\\main\\resources\\extinctBonfire.jpg");
    private final File gameOverPic = new File("src\\main\\resources\\gameover.jpg");

    private final JTextArea textArea;
    private final JTextArea statusTextAreaHuman;
    private final JTextArea statusTextAreaMeats;

    private final JLabel dogLabel;
    private final JLabel humanLabel;
    private final JLabel bonfireLabel;

    private final JPanel northPanel = new JPanel();
    private JPanel southPanel = new JPanel();

    private final JButton spend1hour = new JButton("Spend_1_hour");
    private final JButton makeFire = new JButton("Make_Fire");
    private final JButton feedDog = new JButton("Feed_Dog");
    private final JButton fryOneMeet = new JButton("Fry_One_Meet");
    private final JButton addWood = new JButton("Add_Wood");
    private final JButton exit = new JButton("EXIT");
    private final JButton restart = new JButton("Restart");
    private final JButton hunting = new JButton("Hunting");

    private JLabel iconRedactor(File file)
    {
        log.info(this.getClass().getName() + " method : 'JLabel iconRedactor(File file)'");
        Image picture;
        JLabel jLabel = null;
        try
        {
            picture = ImageIO.read(file);
            picture = picture.getScaledInstance(200, 200, 1);
            jLabel = new JLabel(new ImageIcon(picture));
        }
        catch (IOException e)
        {
            log.error(this.getClass().getName() + " method : 'JLabel iconRedactor(File file)'");
            e.printStackTrace();
        }
        return jLabel;
    }

    private void setActiveBonfirePic()
    {
        log.info(this.getClass().getName() + " method : 'setActiveBonfirePic()'");
        try
        {
            Image bonfire = ImageIO.read(activeBonfire);
            bonfire = bonfire.getScaledInstance(200, 200, 1);
            bonfireLabel.setIcon(new ImageIcon(bonfire));
        }
        catch (IOException e)
        {
            log.error(this.getClass().getName() + " method : 'setActiveBonfirePic()'");
            e.printStackTrace();
        }
    }

    private void setActiveBonfireMeatPic()
    {
        log.info(this.getClass().getName() + " method : 'BonfireMeatPic()'");
        try
        {
            Image bonfire = ImageIO.read(activeBonfireMeat);
            bonfire = bonfire.getScaledInstance(200, 200, 1);
            bonfireLabel.setIcon(new ImageIcon(bonfire));
        }
        catch (IOException e)
        {
            log.error(this.getClass().getName() + " method : 'BonfireMeatPic()'");
            e.printStackTrace();
        }
    }

    private void setExtinctBonfirePic()
    {
        log.info(this.getClass().getName() + " method : 'setExtinctBonfirePic()'");
        try
        {
            Image bonfire = ImageIO.read(extinctBonfire);
            bonfire = bonfire.getScaledInstance(200, 200, 1);
            bonfireLabel.setIcon(new ImageIcon(bonfire));
        }
        catch (IOException e)
        {
            log.error(this.getClass().getName() + " method : 'setExtinctBonfirePic()'");
            e.printStackTrace();
        }
    }

    private void setFunnyHumanHuntingPic()
    {
        log.info(this.getClass().getName() + " method : 'setFunnyHumanHuntingPic()'");
        try
        {
            Image hunting = ImageIO.read(funnyHumanHunting);
            hunting = hunting.getScaledInstance(200, 200, 1);
            humanLabel.setIcon(new ImageIcon(hunting));
        }
        catch (IOException e)
        {
            log.error(this.getClass().getName() + " method : 'setFunnyHumanHuntingPic() -> " +
                    "ImageIO.read(funnyHumanHunting);'");
            e.printStackTrace();
        }
    }

    private void setWaitingHumanPic()
    {
        log.info(this.getClass().getName() + " method : 'setHuntingPic()'");
        try
        {
            Image hunting = ImageIO.read(funnyHumanWaiting);
            hunting = hunting.getScaledInstance(200, 200, 1);
            humanLabel.setIcon(new ImageIcon(hunting));
        }
        catch (IOException e)
        {
            log.error(this.getClass().getName() + " method : 'setHuntingPic()'");
            e.printStackTrace();
        }
    }

    private void setUsualHumanPic()
    {
        log.info(this.getClass().getName() + " method : 'setUsualHumanPic()'");
        try
        {
            Image funnyHuman = ImageIO.read(this.funnyHuman);
            funnyHuman = funnyHuman.getScaledInstance(200, 200, 1);
            humanLabel.setIcon(new ImageIcon(funnyHuman));
        }
        catch (IOException e)
        {
            log.error(this.getClass().getName() + " method : 'setUsualHumanPic()'");
            e.printStackTrace();
        }
    }

    private void setEatingDogPic()
    {
        log.info(this.getClass().getName() + " method : 'setEatingDogPic()'");
        try
        {
            Image dog = ImageIO.read(dogEating);
            dog = dog.getScaledInstance(200, 200, 1);
            dogLabel.setIcon(new ImageIcon(dog));
        }
        catch (IOException e)
        {
            log.error(this.getClass().getName() + " method : 'setEatingDogPic()'");
            e.printStackTrace();
        }
    }

    private synchronized void setHuntingDogPic()
    {
        log.info(this.getClass().getName() + " method : 'setHuntingDogPic()'");
        while (human.getDog().isEating())
        {
            setWaitingHumanPic();
            try
            {
                wait();
            }
            catch (InterruptedException e)
            {
                log.error(this.getClass().getName() + " method : 'setHuntingDogPic() -> wait();'");
                e.printStackTrace();
            }
        }
        try
        {
            Image dog = ImageIO.read(dogHunting);
            dog = dog.getScaledInstance(200, 200, 1);
            dogLabel.setIcon(new ImageIcon(dog));
        }
        catch (IOException e)
        {
            log.error(this.getClass().getName() + " method : 'setHuntingDogPic()'");
            e.printStackTrace();
        }
    }

    private synchronized void setUsualDogPic()
    {
        log.info(this.getClass().getName() + " method : 'setUsualDogPic()'");
        try
        {
            Image dog = ImageIO.read(dogImg);
            dog = dog.getScaledInstance(200, 200, 1);
            dogLabel.setIcon(new ImageIcon(dog));
        }
        catch (IOException e)
        {
            log.error(this.getClass().getName() + " method : 'setUsualDogPic()'");
            e.printStackTrace();
        }
        notifyAll();
    }

    private void gameOverIconRedactor()
    {
        log.info(this.getClass().getName() + " method : 'gameOverIconRedactor()'");
        try
        {
            Image gameOverPicture = ImageIO.read(gameOverPic);
            gameOverPicture = gameOverPicture.getScaledInstance(200, 200, 1);
            bonfireLabel.setIcon(new ImageIcon(gameOverPicture));
        }
        catch (IOException e)
        {
            log.error(this.getClass().getName() + " method : 'gameOverIconRedactor() -> ImageIO.read(gameOverPic);'");
            e.printStackTrace();
        }

        try
        {
            Image dogPicture = ImageIO.read(badImg);
            dogPicture = dogPicture.getScaledInstance(200, 200, 1);
            dogLabel.setIcon(new ImageIcon(dogPicture));
        }
        catch (IOException e)
        {
            log.error(this.getClass().getName() + " method : 'gameOverIconRedactor() -> ImageIO.read(badImg);'");
            e.printStackTrace();
        }

        try
        {
            Image humanPicture = ImageIO.read(frustratedHuman);
            humanPicture = humanPicture.getScaledInstance(200, 200, 1);
            humanLabel.setIcon(new ImageIcon(humanPicture));
        }
        catch (IOException e)
        {
            log.error(this.getClass().getName() + " method : 'gameOverIconRedactor() -> ImageIO.read(frustratedHuman);'");
            e.printStackTrace();
        }
        southPanel.setVisible(false);
        southPanel = new JPanel();
        southPanel.add(restart);
        southPanel.add(exit);
        frame.getContentPane().add(BorderLayout.SOUTH, southPanel);
        refreshInfo();
    }

    private synchronized void timeCheck()
    {
        log.info(this.getClass().getName() + " method : 'timeCheck()'");
        if (!human.getBonfire().isBurning(time))
        {
            setExtinctBonfirePic();
        }
        if (!dog.isAlive(time))
        {
            textArea.append("The dog died of hunger!");
            gameOverIconRedactor();
        }
    }

    private synchronized void refreshInfo()
    {
        log.info(this.getClass().getName() + " method : 'refreshInfo()'");
        statusTextAreaHuman.setText(human.toString());
        statusTextAreaMeats.setText(human.printMeats());
    }

    private void spend1hourButtonAction()
    {
        spend1hour.addActionListener(e ->
        {
            log.info(this.getClass().getName() + " method : 'buttonsActions() -> spend1hour.addActionListener'");
            time.addMinutes(60);
            textArea.append("Was spend 1 hour!\n");
            timeCheck();
            refreshInfo();
        });
    }

    private void makeFireButtonAction()
    {
        makeFire.addActionListener(e ->
        {
            log.info(this.getClass().getName() + " method : 'buttonsActions() -> makeFire.addActionListener'");
            if (!human.getBonfire().isBurning() && human.getBonfire().getCapacity() > 0)
            {
                time.addMinutes(30);
                human.makeFire();
                setActiveBonfirePic();
                textArea.append("Bonfire is burning now!\n");
                timeCheck();
                refreshInfo();
            }
            else if (human.getBonfire().isBurning())
            {
                textArea.append("Bonfire is already burning!\n");
            }
            else
            {
                textArea.append("Add more wood to bonfire!\n");
            }
        });
    }

    private void addWoodButtonAction()
    {
        addWood.addActionListener(e ->
        {
            log.info(this.getClass().getName() + " method : 'buttonsActions() -> addWood.addActionListener'");
            time.addMinutes(10);
            human.addWoodInFire();
            textArea.append("Bonfire capacity is " + human.getBonfire().getCapacity() + "\n");
            timeCheck();
            refreshInfo();
        });
    }

    private void feedDogButtonAction()
    {
        feedDog.addActionListener(e ->
        {
            log.info(this.getClass().getName() + " method : 'buttonsActions() -> feedDog.addActionListener'");
            Runnable r = () ->
            {
                if (human.getMeats().isEmpty())
                {
                    textArea.append("You need  to go hunting!\n");
                }
                else
                {
                    human.getDog().setEating(true);
                    Meat pollMeat = human.getMeats().poll();
                    refreshInfo();
                    setEatingDogPic();
                    textArea.append("The Dog is eating!\n");
                    for (int i = 0; i < 3; i++)
                    {
                        try
                        {
                            time.addMinutes(5);
                            TimeUnit.MILLISECONDS.sleep(1500);
                        }
                        catch (InterruptedException interruptedE)
                        {
                            log.error(this.getClass().getName() + " method : 'buttonsActions() -> " +
                                    "feedDog.addActionListener -> TimeUnit.SECONDS.sleep(1);'");
                            interruptedE.printStackTrace();
                        }
                        textArea.append("The Dog is eating!\n");
                    }
                    human.feedingADog(pollMeat);
                    human.getDog().setEating(false);
                    setUsualDogPic();
                    timeCheck();
                    refreshInfo();
                }
            };
            Thread eatingTread = new Thread(r);
            eatingTread.setName("Eating Tread");
            eatingTread.start();
        });
    }

    private void exitButtonAction()
    {
        exit.addActionListener(e ->
        {
            log.info(this.getClass().getName() + " method : 'buttonsActions() -> exit.addActionListener'");
            frame.dispose();
        });
    }

    private void restartButtonAction()
    {
        restart.addActionListener(e ->
        {
            log.info(this.getClass().getName() + " method : 'buttonsActions() -> restart.addActionListener'");
            frame.dispose();
            new GUI();
        });
    }

    private void fryOneMeetButtonAction()
    {
        fryOneMeet.addActionListener(e ->
        {
            log.info(this.getClass().getName() + " method : 'buttonsActions() -> fryOneMeet.addActionListener'");
            Runnable r = () ->
            {
                if (human.getBonfire().isBurning() && !human.getMeats().isEmpty() && human.checkRowMeats())
                {
                    southPanel.setVisible(false);
                    Meat rowMeat = human.getRowMeat();
                    setActiveBonfireMeatPic();
                    refreshInfo();
                    for (int i = 0; i < 5; i++)
                    {
                        textArea.append("You are frying meat!\n");
                        try
                        {
                            TimeUnit.SECONDS.sleep(1);
                        }
                        catch (InterruptedException interruptedException)
                        {
                            log.error(this.getClass().getName() + " method : 'buttonsActions() -> " +
                                    "fryOneMeet.addActionListener -> TimeUnit.SECONDS.sleep(1);'");
                            interruptedException.printStackTrace();
                        }
                        time.addMinutes(4);
                    }
                    human.fryOneMeat(rowMeat);
                    setActiveBonfirePic();
                    timeCheck();
                    refreshInfo();
                    textArea.append("You fried meat.\n");
                    southPanel.setVisible(true);
                }
                else if (human.getMeats().isEmpty())
                {
                    textArea.append("You need to go hunting!\n");
                }
                else if (!human.getBonfire().isBurning())
                {
                    textArea.append("You need to make a fire.\n");
                }
                else
                {
                    textArea.append("You don't have row meat!\n");
                }
            };
            Thread fryOneMeatThread = new Thread(r);
            fryOneMeatThread.setName("fryOneMeatThread");
            fryOneMeatThread.start();
        });
    }

    private void huntingButtonAction()
    {
        hunting.addActionListener(e ->
        {
            log.info(this.getClass().getName() + " method : 'buttonsActions() -> hunting.addActionListener'");
            Runnable r = () ->
            {
                southPanel.setVisible(false);
                setHuntingDogPic();
                setFunnyHumanHuntingPic();
                for (int i = 0; i < 5; i++)
                {
                    textArea.append("You are Hunting!\n");
                    try
                    {
                        time.addMinutes(10);
                        timeCheck();
                        refreshInfo();
                        TimeUnit.MILLISECONDS.sleep(1500);
                    }
                    catch (InterruptedException interruptedE)
                    {
                        log.error(this.getClass().getName() + " method : 'buttonsActions() -> hunting.addActionListener ->" +
                                "TimeUnit.SECONDS.sleep(2);'");
                        interruptedE.printStackTrace();
                    }
                }
                textArea.append(human.hunting() + '\n');
                setUsualHumanPic();
                setUsualDogPic();
                timeCheck();
                refreshInfo();
                southPanel.setVisible(true);
            };
            Thread huntingThread = new Thread(r);
            huntingThread.setName("Hunting Thread");
            huntingThread.start();
        });
    }

    private void buttonsActions()
    {
        log.info(this.getClass().getName() + " method : 'buttonsActions()'");
        spend1hourButtonAction();
        makeFireButtonAction();
        addWoodButtonAction();
        feedDogButtonAction();
        exitButtonAction();
        restartButtonAction();
        fryOneMeetButtonAction();
        huntingButtonAction();
    }

    public GUI()
    {
        Font no_name_font = new Font("No name", Font.BOLD, 10);

        log.info(this.getClass().getName() + " constructor : 'GUI()'");
        statusTextAreaHuman = new JTextArea("Logging:\n", 1, 8);
        statusTextAreaHuman.setFont(no_name_font);
        JScrollPane scrollPaneHuman = new JScrollPane(statusTextAreaHuman);

        statusTextAreaMeats = new JTextArea("Logging:\n", 1, 8);
        statusTextAreaMeats.setFont(no_name_font);
        JScrollPane scrollPaneMeats = new JScrollPane(statusTextAreaMeats);

        refreshInfo();

        textArea = new JTextArea("Logging:\n", 8, 2);
        textArea.setFont(new Font("No name", Font.PLAIN, 14));
        JScrollPane scrollPaneTextArea = new JScrollPane(textArea);

        dogLabel = iconRedactor(dogImg);
        humanLabel = iconRedactor(funnyHuman);
        bonfireLabel = iconRedactor(extinctBonfire);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 600);
        frame.setLocationRelativeTo(null);

        buttonsActions();

        northPanel.add(dogLabel);
        northPanel.add(bonfireLabel);
        northPanel.add(humanLabel);

        southPanel.add(spend1hour);
        southPanel.add(makeFire);
        southPanel.add(feedDog);
        southPanel.add(fryOneMeet);
        southPanel.add(addWood);
        southPanel.add(hunting);

        frame.getContentPane().add(BorderLayout.NORTH, northPanel);
        frame.getContentPane().add(BorderLayout.CENTER, scrollPaneTextArea);
        frame.getContentPane().add(BorderLayout.SOUTH, southPanel);
        frame.getContentPane().add(BorderLayout.WEST, scrollPaneHuman);
        frame.getContentPane().add(BorderLayout.EAST, scrollPaneMeats);

        frame.setVisible(true);
    }
}
