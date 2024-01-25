import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
//  ─────────────────────────────────────────────────────────┘

/// Program by EtianKrižman 89201173 2023/24 ® ─ │ └ ┘ ┌ ┐
// forgive me for I wanted to make this code more readable to my silly brain by adding <br/>

/*
┌────────────────────────────TO-DO───────────────────────────┐
│ - count execution time                                     │
│ - add more sample pictures                                 │
│ - limit selectable file type                               │
│ - implement image preview                                  │
│ - window with output image                                 │
│ - rebuild the UI with GridLayout                           │
│ - iron out bugs                                            │
│ - make JTable thinner (possible?)                          │
-  report graphs: R studio, https://r-graph-gallery.com/ , ggplot2
└────────────────────────────TO-DO───────────────────────────┘
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
    static float[][] kernel =  new float[][] { // DEFAULT KERNEL IS IDENTITY
        {0, 0, 0},
        {0, 1, 0},
        {0, 0, 0}
    };
    static boolean enableTable = false;


    public GUI(){ // creating the very elegant sqaure of gaze

        System.out.println(
                """
                        ┌──────────────────────────────────┐
                        │ Kernel image processing program. │
                        └──────────────────────────────────┘
                        > You may select an image and a\s
                        kernel to be used, if not,\s
                        identity kernel will be used.
                """);

//      ───adding some silliness───────────────────────────────────────┐
        // le magestic, le one and only, J. Frame Kernely
        JFrame frame = new JFrame("Image process kerneling 🌽");
        ImageIcon icon = new ImageIcon("cat2.jpeg");
        frame.setIconImage(icon.getImage());
        frame.setSize(1000, 300);
//      ───────────────────────────────────────────────────────────────┘

//      ─────────────────────────JPannelli──────────────────────────────────────────────────┐
        //GridBagLayout <- ??
        // Frame is
        JPanel panel1 = new JPanel();
        JPanel panel2 = new JPanel();
        JPanel panel3 = new JPanel();
        JLabel p1 = new JLabel("Select kernel: ");
        JLabel p2 = new JLabel("Select image: ");
        JLabel p3 = new JLabel("Select run mode: ");
//      ────────────────────────────────────────────────────────────────────────────────────┘

//      ───────────────────🔘───────────────────────────────────────────────────────────────────────────┐

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

        // JTable
        DefaultTableModel tableModel = new DefaultTableModel(3, 3);
        JTable matrixTable = new JTable(tableModel);

        // DROPDOWN MENU KERNEL
        JComboBox<String> kernelMode = new JComboBox<>(
                new String[]{" ", "Custom","Sharpen", "Box blur", "Gaussian blur", "Edge detection"});
        kernelMode.addActionListener((e) -> {
            GUI.selectedKernel = (String) kernelMode.getSelectedItem();
            if (GUI.selectedKernel != "Custom"){GUI.enableTable = false;} else {GUI.enableTable = true;}
            matrixTable.setEnabled(enableTable);

            System.out.println("Selected kernel: " + selectedKernel);
        });
//      ────────────────────────────────────────────────────────────────────────────────────────────────┘


//      ──────────────────────────────────────────────────────────┐
        // JTable TEST

        runButton.addActionListener(e -> {
            // test
            System.out.println("Selected kernel:");
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    Object value = matrixTable.getValueAt(i, j);
                    System.out.print(value + " ");
                }
                System.out.println();
            }
        });
//      ──────────────────────────────────────────────────────────┘


// BUTTON ACTIONLISTENERS

            // IMAGE SELECTOR ───────────────────────────────────────────────────────────────────┐

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
// ───────────────────────────────────────────────────────────────────┐

            // RUN BUTTON
        runButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                    // SELECT KERNEL
                switch (GUI.selectedKernel){
                    case "Custom":

                        for (int i = 0; i < 3; i++) {
                            for (int j = 0; j < 3; j++) {
                                Object value = matrixTable.getValueAt(i,j);
                                GUI.kernel[i][j] = Float.parseFloat(value.toString());
                            }
                        }

                        break;
                    case "Sharpen":
                        GUI.kernel = new float[][] {
                                { 0, -1, 0},
                                {-1, 5, -1},
                                { 0, -1, 0}
                        };
                        break;
                    case "Box blur":
                        GUI.kernel = new float[][] {
                                {1,1,1},
                                {1,1,1},
                                {1,1,1}
                        };
                        for (int i = 0; i < GUI.kernel.length; i++) {
                            for (int j = 0; j < GUI.kernel[i].length; j++) GUI.kernel[i][j] = GUI.kernel[i][j] * (float)(1.0/9);
                        }

                        break;
                    case "Gaussian blur":
                        GUI.kernel = new float[][] {
                                {1, 2, 1},
                                {2, 4, 2},
                                {1, 2, 1}
                        };
                        for (int i = 0; i < GUI.kernel.length; i++) {
                            for (int j = 0; j < GUI.kernel[i].length; j++) GUI.kernel[i][j] = GUI.kernel[i][j] * (float)(1.0/16);
                        }

                        break;
                    case "Edge detection":
                        GUI.kernel = new float[][] {
                                {-1, -1, -1},
                                {-1,  8, -1},
                                {-1, -1, -1}
                        };
                        break;
                    default:
                        System.out.println("Kernel not selected.");
                }

                    // Setting the value of the JTable withhhh the
                    // values of the selected kernel - if default was not used
                    // such that is not empty and is prettier
                for (int i = 0; i < kernel.length; i++) {
                    for (int ii = 0; ii < kernel.length; ii++) {
                        matrixTable.setValueAt(kernel[i][ii], i, ii);

                    }
                }


                switch(GUI.selectedOption) {
                    case "Sequential":
                        seq.kerneling(fileName, directory, kernel);
                        break;
                    case "Parallel":
                        par.kerneling(fileName, directory, kernel);
                        break;
                    case "Distributed":
                        distr.kerneling(fileName, directory, kernel);
                        break;
                    default:
                        System.out.println("Run mode not selected.");
                }

            }
        });
//      ───────────────────────────────────────────────────────────────────────────────────────────────┘


        //  IMAGE PREVIEW ─────────────────────────────────────────────┐
        BufferedImage image;
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
        frame.setLocation(280,260);
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
