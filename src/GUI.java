import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
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
│ - start actually working on convolution algorithm                             │
│ - implement image preview                                                     │
└────────────────────────────TO-DO──────────────────────────────────────────────┘
*/


//====================================================================================================================┐

public class GUI implements ActionListener {


    ParKerneling par = new ParKerneling();
    DistrKerneling distr = new DistrKerneling();
    SeqKerneling seq = new SeqKerneling();
    private static String selectedOption = "";
    private static String selectedKernel = "";

    static String directory = "\\kerneling\\";
    static String fileName = "cat2.jpeg";
    static int[][] kernel;
    static boolean enableTable = true;


    public GUI(){ // creating the very elegant sqaure of gaze

        System.out.println("┌──────────────────────────────────┐\n" +
                "│ Kernel image processing program. │\n" +
                "└──────────────────────────────────┘\n" +
                "> You may select an image and a \n" +
                "kernel to be used, if not, \n" +
                "default values will be used.\n");

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
        JPanel panel2 = new JPanel();
        JPanel panel3 = new JPanel();
        JLabel p1 = new JLabel("Select kernel: ");
        JLabel p2 = new JLabel("Select image: ");
        JLabel p3 = new JLabel("Select run mode: ");


//      ────────────────────────────────────────────────────────────────────────────────────┘

//      ───────────────────🔘───────────────────────────────────────────────┐

        JButton customKernel = new JButton("Select ");
        JButton selectImage = new JButton("Select image");
        JButton runButton = new JButton("Run");

            // DROPDOWN MENU MODE
        JComboBox<String> runMode = new JComboBox<>(
                new String[]{" ","Sequential", "Parallel", "Distributed"});
        runMode.addActionListener((e) -> {
            GUI.selectedOption = (String) runMode.getSelectedItem();
            System.out.println("Selected option: " + selectedOption);
        });

        DefaultTableModel tableModel = new DefaultTableModel(3, 3);
        JTable matrixTable = new JTable(tableModel);

        // DROPDOWN MENU KERNEL
        JComboBox<String> kernelMode = new JComboBox<>(
                new String[]{"Custom","Sharpen", "Box blur", "Gaussian blur", "Edge detection"});
        kernelMode.addActionListener((e) -> {
            GUI.selectedKernel = (String) kernelMode.getSelectedItem();
            if (GUI.selectedKernel == "Custom"){GUI.enableTable = true;} else {GUI.enableTable = false;}
            matrixTable.setEnabled(enableTable);
            System.out.println("Selected kernel: " + selectedKernel);
        });

        // JTable for custom kernel

        runButton.addActionListener(e -> {
            // test
            System.out.println("Custom kernel:");
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    Object value = matrixTable.getValueAt(i, j);
                    System.out.print(value + " ");
                }
                System.out.println();
            }
        });




//      ────────────────────────────────────────────────────────────────────┘


        // BUTTON ACTIONLISTENERS ───────────────────────────────────────────────────────────┐

            // IMAGE SELECTOR

        selectImage.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                FileDialog fileDialog = new FileDialog((Frame) null, "Select an Image");

                fileDialog.setVisible(true);

                // get directory and file name
                GUI.directory = fileDialog.getDirectory();
                GUI.fileName = fileDialog.getFile();

                // If a file was selected
                if (fileName != null) {
                    // Process the selected file
                    System.out.println("Selected file: " + directory + fileName);

            }}
        });

            // RUN BUTTON
        runButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                switch (GUI.selectedKernel){
                    case "Custom":
                        //kernel = ;
                        break;
                    case "Sharpen":
                        GUI.kernel = new int[][] {
                                { 0, -1, 0},
                                {-1, 5, -1},
                                {0, -1, 0}
                        };
                        break;
                    case "Box blur":
                        GUI.kernel = new int[][] {
                                {1,1,1},
                                {1,1,1},
                                {1,1,1}
                        };
                        break;
                    case "Gaussian blur":
                        GUI.kernel = new int[][] {
                                {1, 2, 1},
                                {2, 4, 2},
                                {1, 2, 1}
                        };
                        break;
                    case "Edge detection":
                        GUI.kernel = new int[][] {
                                {-1, -1, -1},
                                {-1, 8, -1},
                                {-1, -1, -1}
                        };
                        break;

                    default:
                        System.out.println("Achievement unlocked: How did we get here?");
                }

                for (int i = 0; i < kernel.length; i++) {
                    for (int ii = 0; ii < kernel.length; ii++) {
                        matrixTable.setValueAt(kernel[i][ii], i, ii);

                    }
                }



                switch(GUI.selectedOption) {
                    case "Sequential":
                        seq.test(fileName, directory, kernel);
                        break;
                    case "Parallel":
                        par.test(fileName, directory, kernel);
                        break;
                    case "Distributed":
                        distr.test(fileName, directory, kernel);
                        break;
                    default:
                        System.out.println("Run mode not selected.");
                }

                // SELECT KERNEL



            }
        });







//      ─────────────────────────────────────────────────────────────────────────────────────┘

        //  IMAGE PREVIEW ─────────────────────────────────────────────┐
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File("cat2.jpeg"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        JLabel imagePreview = new JLabel(new ImageIcon(image));
//      ───────────────────────────────────────────────────────────────┘

//      ───Panel shenanigans 😒──────────────────────────────────┐

        panel1.add(p1, BorderLayout.NORTH);
        panel1.add(customKernel, BorderLayout.SOUTH);
        panel1.add(kernelMode, BorderLayout.WEST);
        panel1.add(matrixTable,BorderLayout.WEST);

        panel2.add(p2, BorderLayout.NORTH);
        panel2.add(selectImage, BorderLayout.SOUTH);
        panel2.add(imagePreview, BorderLayout.SOUTH);

        panel3.add(p3, BorderLayout.NORTH);
        panel3.add(runMode, BorderLayout.WEST);
        panel3.add(runButton, BorderLayout.EAST);


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
