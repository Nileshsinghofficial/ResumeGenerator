package ResumeBuilder;

import com.itextpdf.text.*;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;

public class GUIResumeBuilder {
    private JFrame frame;
    private JPanel panel;
    private JTextField nameField;
    private JTextField addressField;
    private JTextField contactField;
    private JTextField emailField;
    private JTextField skillsField;
    private JTextField workExperienceField;
    private JTextField collegeField;
    private JLabel photoLabel;
    private String selectedImagePath;

    public GUIResumeBuilder() {
        frame = new JFrame("Resume Builder");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBackground(new Color(0x123456));
        frame.setForeground(new Color(255, 255, 255));
        frame.setSize(700, 500);
        frame.setLocationRelativeTo(null);
        frame.setContentPane(createUIComponents());
        frame.setVisible(true);
    }

    private JPanel createUIComponents() {
        panel = new JPanel();
        panel.setLayout(null);

        int xStart = 30;
        int yStart = 15;
        int labelWidth = 150;
        int fieldWidth = 200;
        int height = 25;
        int gap = 20;

        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setBounds(xStart, yStart, labelWidth, height);
        panel.add(nameLabel);

        nameField = new JTextField();
        nameField.setBounds(xStart + labelWidth + gap, yStart, fieldWidth, height);
        panel.add(nameField);

        JLabel addressLabel = new JLabel("Address:");
        addressLabel.setBounds(xStart, yStart + (height + gap), labelWidth, height);
        panel.add(addressLabel);

        addressField = new JTextField();
        addressField.setBounds(xStart + labelWidth + gap, yStart + (height + gap), fieldWidth, height);
        panel.add(addressField);

        JLabel contactLabel = new JLabel("Contact:");
        contactLabel.setBounds(xStart, yStart + 2 * (height + gap), labelWidth, height);
        panel.add(contactLabel);

        contactField = new JTextField();
        contactField.setBounds(xStart + labelWidth + gap, yStart + 2 * (height + gap), fieldWidth, height);
        panel.add(contactField);

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setBounds(xStart, yStart + 3 * (height + gap), labelWidth, height);
        panel.add(emailLabel);

        emailField = new JTextField();
        emailField.setBounds(xStart + labelWidth + gap, yStart + 3 * (height + gap), fieldWidth, height);
        panel.add(emailField);

        JLabel skillsLabel = new JLabel("Skills:");
        skillsLabel.setBounds(xStart, yStart + 4 * (height + gap), labelWidth, height);
        panel.add(skillsLabel);

        skillsField = new JTextField();
        skillsField.setBounds(xStart + labelWidth + gap, yStart + 4 * (height + gap), fieldWidth, height);
        panel.add(skillsField);

        JLabel workExperienceLabel = new JLabel("Work Experience:");
        workExperienceLabel.setBounds(xStart, yStart + 5 * (height + gap), labelWidth, height);
        panel.add(workExperienceLabel);

        workExperienceField = new JTextField();
        workExperienceField.setBounds(xStart + labelWidth + gap, yStart + 5 * (height + gap), fieldWidth, height);
        panel.add(workExperienceField);

        JLabel collegeLabel = new JLabel("College/University:");
        collegeLabel.setBounds(xStart, yStart + 6 * (height + gap), labelWidth, height);
        panel.add(collegeLabel);

        collegeField = new JTextField();
        collegeField.setBounds(xStart + labelWidth + gap, yStart + 6 * (height + gap), fieldWidth, height);
        panel.add(collegeField);

        photoLabel = new JLabel();
        photoLabel.setBounds(xStart + 2 * (fieldWidth + gap), yStart, fieldWidth, 150);
        panel.add(photoLabel);

        JButton selectImageButton = new JButton("Select Photo");
        selectImageButton.setBounds(xStart + 2 * (fieldWidth + gap), yStart + 150 + gap, fieldWidth, height);
        panel.add(selectImageButton);

        selectImageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter("*.IMAGE", "jpg", "png");
                fileChooser.addChoosableFileFilter(filter);
                int rs = fileChooser.showSaveDialog(null);
                if (rs == JFileChooser.APPROVE_OPTION) {
                    File selectedImage = fileChooser.getSelectedFile();
                    selectedImagePath = selectedImage.getAbsolutePath();
                    displaySelectedImage(selectedImagePath);
                }
            }
        });

        JButton generateButton = new JButton("Generate Resume");
        generateButton.setBounds(xStart, yStart + 9 * (height + gap), labelWidth + fieldWidth + gap, height);
        panel.add(generateButton);

        generateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generatePDFResume();
            }
        });

        return panel;
    }

    private void displaySelectedImage(String imagePath) {
        ImageIcon imageIcon = new ImageIcon(imagePath);
        java.awt.Image image = imageIcon.getImage();
        java.awt.Image newImage = image.getScaledInstance(150, 150, java.awt.Image.SCALE_SMOOTH);
        photoLabel.setIcon(new ImageIcon(newImage));
    }

    private void generatePDFResume() {
        try {
            // Load the custom font
            BaseFont seguePrintFont = BaseFont.createFont("C:\\Users\\Nilesh Singh\\IdeaProjects\\Resume\\src\\ResumeBuilder\\segoeprint.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);

            Document document = new Document();
            String outputFileName = "Resume.pdf";
            PdfWriter.getInstance(document, new FileOutputStream(outputFileName));
            document.open();

            // Set the background color of the document
            document.add(new Paragraph("", FontFactory.getFont(FontFactory.HELVETICA, 9, Font.NORMAL, new BaseColor(234,45,56))));

            Font titleFont = new Font(seguePrintFont, 28, Font.ITALIC, new BaseColor(0x123456));
            Font contentFont = new Font(seguePrintFont, 14, Font.ITALIC, new BaseColor(0x123456));
            Font contentFonts = new Font(seguePrintFont, 22,Font.ITALIC, new BaseColor(0x12345));

            Paragraph title = new Paragraph("Resume", titleFont);
            title.setAlignment(Paragraph.ALIGN_CENTER);
            if (selectedImagePath != null) {
                Image photoImage = Image.getInstance(selectedImagePath);
                photoImage.scaleAbsolute(70, 70);
                document.add(photoImage);
            }

            document.add(title);
            document.add(new Paragraph("************************************************************************",contentFont));
            document.add(new Paragraph("PROFILE NAME ", contentFonts));
            document.add(new Paragraph(" ",contentFont));
            document.add(new Paragraph("Name: " + nameField.getText(), contentFont));
            document.add(new Paragraph("---------------------------------------------------------", contentFont));

            document.add(new Paragraph("CONTACT DETAILS", contentFonts));
            document.add(new Paragraph(" ",contentFont));
            document.add(new Paragraph("Email: " + emailField.getText(), contentFont));
            document.add(new Paragraph("Contact: " + contactField.getText(), contentFont));
            document.add(new Paragraph("Address: " + addressField.getText(), contentFont));
            document.add(new Paragraph( "---------------------------------------------------------", contentFont));

            document.add(new Paragraph("SKILLS", contentFonts));
            document.add(new Paragraph(" ",contentFont));
            document.add(new Paragraph("Skills: " + skillsField.getText(), contentFont));
            document.add(new Paragraph("---------------------------------------------------------", contentFont));

            document.add(new Paragraph("QUALIFICATIONS", contentFonts));
            document.add(new Paragraph(" ",contentFont));
            document.add(new Paragraph("Collage/University: "+collegeField.getText(), contentFont));
            document.add(new Paragraph( "---------------------------------------------------------", contentFont));

            document.add(new Paragraph("WORK EXPERIENCE", contentFonts));
            document.add(new Paragraph(" ",contentFont));
            document.add(new Paragraph(" Work Exp: " + workExperienceField.getText(), contentFont));
            document.add(new Paragraph("---------------------------------------------------------", contentFont));

            document.add(new Paragraph("REFERENCES", contentFonts));
            document.add(new Paragraph(" "));
            document.add(new Paragraph("Available upon request", contentFont));
            document.close();

            JOptionPane.showMessageDialog(frame, "Resume generated successfully!");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frame, "Error generating resume: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new GUIResumeBuilder();
            }
        });
    }
}
