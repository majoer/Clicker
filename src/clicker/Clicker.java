package clicker;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.net.URL;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.LookAndFeel;
import javax.swing.SpinnerNumberModel;

public class Clicker extends JFrame {

    private Thread th = null;
    private Changelog cl;

    public Clicker() {
        setTitle("AutoClicker v2.0 by Mats J");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setSize(300, 150);
        cl = new Changelog(getTitle(), this);

        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        c.gridy = 0;
        c.gridx = 1;
        c.weightx = 0;
        c.weighty = 0;
        c.fill = GridBagConstraints.NONE;
        c.anchor = GridBagConstraints.NORTHEAST;

        JButton changelog = new JButton();
        changelog.setIcon(new ImageIcon(this.getClass().getResource("/res/history24.gif")));
        changelog.setPreferredSize(new Dimension(24, 24));
        changelog.setSize(new Dimension(24, 24));
        changelog.setMinimumSize(new Dimension(24, 24));
        changelog.setMaximumSize(new Dimension(24, 24));
        changelog.setBorderPainted(false);
        changelog.setContentAreaFilled(false);
        changelog.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                cl.setVisible(true);
                cl.update(getSize(), getLocationOnScreen());
            }
        });
        add(changelog, c);

        c.insets = new Insets(2, 0, 0, 0);
        c.gridx = 0;
        c.fill = GridBagConstraints.BOTH;
        c.gridwidth = 2;
        c.weightx = 1;
        c.weighty = 1;
        JTabbedPane jtp = new JTabbedPane();
        jtp.add("Clicker", new ClickerPanel(jtp));
        jtp.add("Holder", new HolderPanel(jtp));
        add(jtp, c);

    }

    public static void main(String[] args) throws AWTException, InterruptedException {
        new Clicker().setVisible(true);
    }

}
