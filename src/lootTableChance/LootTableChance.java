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
public class LootTableChance {

    public static void main(final String[] args) {
        int min = -1, max = -1;
        boolean rolls = false;
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
        final int option = JOptionPane.showConfirmDialog(frame, pane, "Please fill all the fields", 0, 1);
        if (option == 0) {
            double totalSum = 0;
            final FileDialog fd = new FileDialog(frame, "Choose the loot table", 0);
            fd.setDirectory("C:\\");
            fd.setFile("*.json");
            fd.setVisible(true);
            final Stack<String> weights = new Stack<>();
            try {
                final Scanner scan = new Scanner(new File(fd.getDirectory() + fd.getFile()));
                while (scan.hasNextLine()) {
                    String nextLine = scan.nextLine();
                    if (nextLine.contains("weight")) {
                        nextLine = nextLine.replaceAll("[^0-9]+", "");
                        weights.push(nextLine);
                    }                      
                    if (nextLine.contains("rolls") && !nextLine.contains("bonus")) {
                        rolls = false;
                        min = -1;max = -1;
                        while (!rolls) {
                            if (nextLine.contains("min")) {
                                System.out.println(nextLine);
                                min = Integer.parseInt(nextLine.replaceAll("[^1-9]+", ""));
                            }
                            if (nextLine.contains("max")) {
                                System.out.println(nextLine);
                                max = Integer.parseInt(nextLine.replaceAll("[^1-9]+", ""));
                            }
                            if (min < 0 || max < 0) {
                                nextLine = scan.nextLine();
                            } else {
                                rolls = true;
                            }
                        }
                    }
                }
            } catch (FileNotFoundException ex) {
                Logger.getLogger(LootTableChance.class.getName()).log(Level.SEVERE, null, ex);
            }
            while (!weights.empty()) {
                totalSum += Integer.parseInt(weights.pop());
            }
            System.out.println("min: "+min+" max: "+max);
            final double chance = 100 * Math.floor(Double.parseDouble(weightin.getText()) + Double.parseDouble(quality.getText()) * Double.parseDouble(playerluckin.getText())) / totalSum;
            final double actual_chance = ((max + min) / 2) * Math.pow(chance, 1) * Math.pow(1 - (chance / 100), ((max + min) / 2) - 1);
            final DecimalFormat df = new DecimalFormat("#.##");
            JOptionPane.showMessageDialog(pane, "The weight sum of the " + fd.getFile() + " loot table is: " + (int) totalSum + "\n The chance of getting this item in a single roll is: " + df.format(chance) + "%" + "\n the chance to recieve this item as a drop is: " + df.format(actual_chance) + "%");
            System.exit(0);
        } else {
            System.exit(1);
        }
    }
}
