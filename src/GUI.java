import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.BrokenBarrierException;

import static javax.swing.BorderFactory.createEmptyBorder;

//  ─────────────────────────────────────────────────────────┘
/// Program by EtianKrižman 89201173 2023/24 ® ─ │ └ ┘ ┌ ┐
// forgive me for I wanted to make this code more readable to my silly ass by adding <br/>



/*
┌────────────────────────────TO-DO──────────────────────────────────────────────┐
│ - una class per la window (GUI)                                               │
│ - una class per il programma                                                  │
│ - button linked to action listener that opens Java swing file JFile chooser   │
└────────────────────────────TO-DO──────────────────────────────────────────────┘
*/


//====================================================================================================================┐

public class GUI implements ActionListener {

    public GUI(){ // creating the very elegant sqaure of gaze



//      ───adding some silliness───────────────────────────────────────┐
        // le magestic, le one and only, J. Frame Kernely
        JFrame frame = new JFrame("Image process kerneling 🌽");
        ImageIcon icon = new ImageIcon("cat.png");
        frame.setIconImage(icon.getImage());
        frame.setSize(400, 300);
//      ───────────────────────────────────────────────────────────────┘

//      ─────────────────────────JPannelli──────────────────────────────────────────────────┐
        JPanel panel1 = new JPanel(new BorderLayout());
        JPanel panel2 = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        JPanel panel3 = new JPanel(new BorderLayout());
        //panel1.setSize(400, 300);
        panel1.setBorder(createEmptyBorder(30, 30, 10, 30));

        //panel.setLayout(new GridLayout(0,1 ));
        //panel.setBackground(Color.WHITE);
//      ────────────────────────────────────────────────────────────────────────────────────┘

//      ───────────────────🔘────────────────────────────────────┐
        JLabel label = new JLabel("Number");
        JButton button = new JButton("0");
        button.addActionListener(this);

        JFileChooser jf = new JFileChooser("c:");
        jf.showSaveDialog(null);
//      ─────────────────────────────────────────────────────────┘

//      ───adding to panel───────────────────────────────────────┐
        panel2.add(button, BorderLayout.SOUTH);
        panel1.add(label);
        frame.add(panel1);
        frame.add(panel2);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //frame.pack();
        frame.setVisible(true);
//      ─────────────────────────────────────────────────────────┘
    }


    public static void main(String[] args) {

       new GUI();

    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
