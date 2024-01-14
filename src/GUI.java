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
│ - una class per il programma sequential                                       │
│ - una class per il programma parallel                                         │
│ - una class per il programma distributed                                      │
│ - start actually working on convolution algorithm                             │
│ - implement image preview                                                     │
└────────────────────────────TO-DO──────────────────────────────────────────────┘
*/


//====================================================================================================================┐

public class GUI implements ActionListener {

    SeqKerneling seq = new SeqKerneling();
    ParKerneling par = new ParKerneling();
    DistrKerneling distr = new DistrKerneling();
    private static String selectedOption;

    public GUI(){ // creating the very elegant sqaure of gaze



//      ───adding some silliness───────────────────────────────────────┐
        // le magestic, le one and only, J. Frame Kernely
        JFrame frame = new JFrame("Image process kerneling 🌽");
        ImageIcon icon = new ImageIcon("cat2.jpeg");
        frame.setIconImage(icon.getImage());
        frame.setSize(700, 300);
//      ───────────────────────────────────────────────────────────────┘

//      ─────────────────────────JPannelli──────────────────────────────────────────────────┐
        //GridBAgLayout
        JPanel panel1 = new JPanel();
        //panel1.setSize(350,250);
        JPanel panel2 = new JPanel();
        //panel2.setSize(350,250);
        JPanel panel3 = new JPanel();
        //panel3.setSize(700,50);
        JLabel p1 = new JLabel("Select kernel: ");
        JLabel p2 = new JLabel("Select image: ");
        JLabel p3 = new JLabel("Select run mode: ");
        //panel1.setSize(400, 300);
        //panel1.setBorder(createEmptyBorder(30, 30, 10, 30));

//      ────────────────────────────────────────────────────────────────────────────────────┘

//      ───────────────────🔘──────────────────────────────────────────────┐

        JButton customKernel = new JButton("Select ");
        JButton selectImage = new JButton("Select image");
        JButton runButton = new JButton("Run");
        // DROPDOWN MENU
        JComboBox<String> runMode = new JComboBox<>(
                new String[]{" ","Sequential", "Parallel", "Distributed"});
        runMode.addActionListener((e) -> {
            GUI.selectedOption = (String) runMode.getSelectedItem();
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
                FileDialog fileDialog = new FileDialog((Frame) null, "Select an Image");

                // Show the file dialog
                fileDialog.setVisible(true);

                // Get the selected directory and file name
                String directory = fileDialog.getDirectory();
                String fileName = fileDialog.getFile();

                // If a file was selected
                if (fileName != null) {
                    // Process the selected file
                    System.out.println("Selected file: " + directory + fileName);


            }}
        });

        runButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                switch(selectedOption) {
                    case "Sequential":
                        seq.test();
                        break;
                    case "Parallel":
                        par.Unimplemented();
                        break;
                    case "Distributed":
                        distr.Unimplemented();
                        break;
                    default:
                        System.out.println("Run mode not selected.");
                }


            }
        });




//      ───────────────────────────────────────────────────────────────────┘

//      ───Panel shenanigans 😒──────────────────────────────────┐

        panel1.add(p1, BorderLayout.NORTH);
        panel1.add(customKernel, BorderLayout.SOUTH);

        panel2.add(selectImage, BorderLayout.SOUTH);
        panel2.add(p2, BorderLayout.NORTH);
        panel2.add(imagePreview, BorderLayout.SOUTH);

        panel3.add(p3, BorderLayout.NORTH);
        panel3.add(runMode, BorderLayout.WEST);
        panel3.add(runButton, BorderLayout.EAST);


        //frame.setLayout(new GridLayout(2,1));

        /*GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.anchor = GridBagConstraints.NORTH;
        panel1.add(info, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        panel1.add(customKernel, constraints);
*/

        frame.add(panel1, BorderLayout.WEST);
        frame.add(panel2, BorderLayout.EAST);
        frame.add(panel3, BorderLayout.SOUTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //frame.pack();
        frame.setLocation(400,260);
        frame.setVisible(true);
        UIManager.put("swing.boldMetal", Boolean.FALSE);
//      ─────────────────────────────────────────────────────────┘
    }


    public static void main(String[] args) {

       new GUI();

    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
