import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class PaintBrush extends JFrame implements ActionListener {
    private JButton clearButton, undoButton;
    private JRadioButton lineButton, rectButton, ovalButton, pencilButton, eraserButton;
    private JRadioButton blackButton, redButton, greenButton, blueButton;
    private JRadioButton noneButton, solidButton, dottedButton;
    private Color currentColor = Color.BLACK;
    private ArrayList<Shape> shapes = new ArrayList<>();
    private String currentMode = "Line";
    private DrawPanel drawPanel;
    private String lineType = "None";

    public PaintBrush() {
        setTitle("Paint Brush");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel buttonPanel = new JPanel();
        clearButton = new JButton("Clear");
        undoButton = new JButton("Undo");

        clearButton.addActionListener(this);
        undoButton.addActionListener(this);

        buttonPanel.add(clearButton);
        buttonPanel.add(undoButton);

        lineButton = new JRadioButton("Line");
        rectButton = new JRadioButton("Rectangle");
        ovalButton = new JRadioButton("Oval");
        pencilButton = new JRadioButton("Pencil");
        eraserButton = new JRadioButton("Eraser");

        ButtonGroup shapeGroup = new ButtonGroup();
        shapeGroup.add(lineButton);
        shapeGroup.add(rectButton);
        shapeGroup.add(ovalButton);
        shapeGroup.add(pencilButton);
        shapeGroup.add(eraserButton);

        lineButton.setSelected(true);

        lineButton.addActionListener(this);
        rectButton.addActionListener(this);
        ovalButton.addActionListener(this);
        pencilButton.addActionListener(this);
        eraserButton.addActionListener(this);

        buttonPanel.add(lineButton);
        buttonPanel.add(rectButton);
        buttonPanel.add(ovalButton);
        buttonPanel.add(pencilButton);
        buttonPanel.add(eraserButton);

        noneButton = new JRadioButton("None");
        solidButton = new JRadioButton("Solid");
        dottedButton = new JRadioButton("Dotted");

        noneButton.setSelected(true);  // Default is none

        noneButton.addActionListener(this);
        solidButton.addActionListener(this);
        dottedButton.addActionListener(this);

        ButtonGroup lineTypeGroup = new ButtonGroup();
        lineTypeGroup.add(noneButton);
        lineTypeGroup.add(solidButton);
        lineTypeGroup.add(dottedButton);

        buttonPanel.add(noneButton);
        buttonPanel.add(solidButton);
        buttonPanel.add(dottedButton);

        // Adding color selection buttons
        blackButton = new JRadioButton("Black");
        redButton = new JRadioButton("Red");
        greenButton = new JRadioButton("Green");
        blueButton = new JRadioButton("Blue");

        blackButton.setSelected(true);  // Default color is black

        blackButton.addActionListener(this);
        redButton.addActionListener(this);
        greenButton.addActionListener(this);
        blueButton.addActionListener(this);

        ButtonGroup colorGroup = new ButtonGroup();
        colorGroup.add(blackButton);
        colorGroup.add(redButton);
        colorGroup.add(greenButton);
        colorGroup.add(blueButton);

        buttonPanel.add(blackButton);
        buttonPanel.add(redButton);
        buttonPanel.add(greenButton);
        buttonPanel.add(blueButton);

        add(buttonPanel, BorderLayout.NORTH);

        drawPanel = new DrawPanel(shapes, this);
        add(drawPanel, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            PaintBrush frame = new PaintBrush();
            frame.setVisible(true);
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == clearButton) {
            shapes.clear();
            drawPanel.repaint();
        } else if (e.getSource() == undoButton) {
            if (!shapes.isEmpty()) {
                shapes.remove(shapes.size() - 1);
                drawPanel.repaint();
            }
        } else if (e.getSource() == lineButton) {
            currentMode = "Line";
        } else if (e.getSource() == rectButton) {
            currentMode = "Rectangle";
        } else if (e.getSource() == ovalButton) {
            currentMode = "Oval";
        } else if (e.getSource() == pencilButton) {
            currentMode = "Pencil";
        } else if (e.getSource() == eraserButton) {
            currentMode = "Eraser";
        } else if (e.getSource() == blackButton) {
            currentColor = Color.BLACK;
        } else if (e.getSource() == redButton) {
            currentColor = Color.RED;
        } else if (e.getSource() == greenButton) {
            currentColor = Color.GREEN;
        } else if (e.getSource() == blueButton) {
            currentColor = Color.BLUE;
        } else if (e.getSource() == noneButton) {
            lineType = "None";
        } else if (e.getSource() == solidButton) {
            lineType = "Solid";
        } else if (e.getSource() == dottedButton) {
            lineType = "Dotted";
        }

        // Debugging: print the current color and mode
        System.out.println("Current Mode: " + currentMode);
        System.out.println("Current Color: " + currentColor);
        System.out.println("Line Type: " + lineType);
    }

    public String getCurrentMode() {
        return currentMode;
    }

    public Color getCurrentColor() {
        return currentColor;
    }

    public String getLineType() {
        return lineType;
    }
}
