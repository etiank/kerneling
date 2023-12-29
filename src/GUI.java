import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.text.Position;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
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
        ImageIcon icon = new ImageIcon("cat2.jpeg");
        frame.setIconImage(icon.getImage());
        frame.setSize(700, 300);
//      ───────────────────────────────────────────────────────────────┘

//      ─────────────────────────JPannelli──────────────────────────────────────────────────┐
        JPanel panel1 = new JPanel();
        JPanel panel2 = new JPanel();
        JPanel panel3 = new JPanel();
        JLabel p1 = new JLabel("Select kernel: ");
        JLabel p2 = new JLabel("Select image: ");
        JLabel p3 = new JLabel("Select run mode: ");
        //panel1.setSize(400, 300);
        //panel1.setBorder(createEmptyBorder(30, 30, 10, 30));

        //panel.setLayout(new GridLayout(0,1 ));
        //panel.setBackground(Color.WHITE);
//      ────────────────────────────────────────────────────────────────────────────────────┘

//      ───────────────────🔘──────────────────────────────────────────────┐
        JButton customKernel = new JButton("Select ");
        JButton selectImage = new JButton("Select image");
        JButton runButton = new JButton("Run");
        // DROPDOWN MENU
        JComboBox<String> runMode = new JComboBox<>(
                new String[]{"Sequential", "Parallel", "Distributed"});
        runMode.addActionListener((e) -> {
            String selectedOption = (String) runMode.getSelectedItem();
            System.out.println("Selected option: " + selectedOption);
        });

        //  IMAGE PREVIEW
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File("cat2.jpeg"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        JLabel imagePreview = new JLabel(new ImageIcon(image));

        // BUTTON ACTIONLISTENERS
        selectImage.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new java.awt.FileDialog((java.awt.Frame) null).setVisible(true);
            }
        });


//      ───────────────────────────────────────────────────────────────────┘

//      ───adding to panel───────────────────────────────────────┐
        panel1.add(p1, BorderLayout.NORTH);
        panel1.add(customKernel, BorderLayout.CENTER);
        panel2.add(p2, BorderLayout.NORTH);
        panel2.add(selectImage, BorderLayout.CENTER);
        panel2.add(imagePreview);
        panel3.add(p3, BorderLayout.NORTH);
        panel3.add(runMode, BorderLayout.WEST);
        panel3.add(runButton, BorderLayout.EAST);


        //frame.setLayout(new GridLayout(2,1));

        frame.add(panel1, BorderLayout.WEST);
        frame.add(panel2, BorderLayout.EAST);
        frame.add(panel3, BorderLayout.SOUTH);
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
