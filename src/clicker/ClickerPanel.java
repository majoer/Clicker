/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clicker;

import java.awt.AWTException;
import java.awt.GridLayout;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.SpinnerNumberModel;

/**
 *
 * @author Mats
 */
public class ClickerPanel extends JPanel implements ActionListener, Runnable {

    private JTabbedPane parent;
    private Thread thread;
    private boolean stopped;
    private JSpinner tts, delay, count;

    public ClickerPanel(JTabbedPane parent) {
        this.parent = parent;
        setLayout(new GridLayout(4, 2));

        JLabel ttsl = new JLabel("Time to start");
        ttsl.setToolTipText("Time to start in milliseconds.");
        add(ttsl);
        add(tts = new JSpinner(new SpinnerNumberModel(3000, 0, 50000, 1000)));

        JLabel numC = new JLabel("Number of clicks");
        numC.setToolTipText("Number of clicks to be performed");
        add(numC);
        add(count = new JSpinner(new SpinnerNumberModel(1, 1, 5000, 1)));

        JLabel tbc = new JLabel("Time between clicks");
        tbc.setToolTipText("Time between clicks in milliseconds.");
        add(tbc);
        add(delay = new JSpinner(new SpinnerNumberModel(10, 10, 1000, 10)));

        JButton b = new JButton("Start");
        b.addActionListener(this);
        add(b);

        JButton s = new JButton("Stop");
        s.addActionListener(this);
        add(s);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Stop":
                stopped = true;
                thread.interrupt();
                break;
            case "Start":
                thread = new Thread(this);
                thread.start();
                break;
        }
    }

    @Override
    public void run() {
        stopped = false;
        parent.setEnabled(false);
        try {
            Thread.sleep((int) tts.getValue());

            Robot r = new Robot();
            int i = 0;
            while (i++ <= (int) count.getValue() && !stopped) {
                r.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                r.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                Thread.sleep((int) delay.getValue());
            }
        } catch (InterruptedException | AWTException ex) {
        } finally {
            stopped = true;
            parent.setEnabled(true);
        }
    }
}
