package lootTableChance;

import java.text.DecimalFormat;
import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Scanner;
import java.io.File;
import java.util.Stack;
import java.awt.*;
import javax.swing.*;

/**
 * @author D2N13L
 */
public class LootTableChance
{
    public static void main(final String[] args)
    {
        final JFrame frame = new JFrame();
        final JPanel pane = new JPanel();
        final JTextField weightin = new JTextField(5);
        final JTextField quality = new JTextField(5);
        final JTextField playerluckin = new JTextField(5);
        pane.setLayout(new GridLayout(0, 1, 3, 3));
        final JLabel label = new JLabel("Write the weight and quality of an item, and then write your luck. \n");
        label.setFont(new Font("Arial", 1, 15));
        pane.add(label);
        pane.add(new JLabel("What's the weight of the item?"));
        pane.add(weightin);
        pane.add(new JLabel("What's the quality of the item?"));
        pane.add(quality);
        pane.add(new JLabel("What's your luck?"));
        pane.add(playerluckin);

        final int option = JOptionPane.showConfirmDialog(frame,pane,"Please fill all the fields",0,1);
        if (option == 0)
        {
            int totalSum = 0;
            final FileDialog fd = new FileDialog(frame,"Choose the loot table",0);
            fd.setDirectory("C:\\");
            fd.setFile("*.json");
            fd.setVisible(true);
            final Stack<String> weights = new Stack<>();
            try
            {
                final Scanner scan = new Scanner(new File(fd.getDirectory() + fd.getFile()));
                while (scan.hasNextLine())
                {
                    String nextLine = scan.nextLine();
                    if (nextLine.contains("weight"))
                    {
                        nextLine = nextLine.replaceAll("[^0-9]+", "");
                        weights.push(nextLine);
                    }
                }
            } catch (FileNotFoundException ex)
            {
                Logger.getLogger(LootTableChance.class.getName()).log(Level.SEVERE, null, ex);
            }
            while (!weights.empty())
            {
                totalSum += Integer.parseInt(weights.pop());
            }
            final double chance = 100 * Math.floor(Integer.parseInt(weightin.getText()) + Integer.parseInt(quality.getText()) * Double.parseDouble(playerluckin.getText())) / totalSum;
            final DecimalFormat df = new DecimalFormat("#.##");
            JOptionPane.showMessageDialog(pane, "The weight sum of the " + fd.getFile() + " loot table is:" + totalSum + "\n The chance of getting this item is: " + df.format(chance) + "%");
            System.exit(0);
        } else
        {
            System.exit(1);
        }
    }
}
