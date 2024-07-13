import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class DrawPanel extends JPanel {
    private ArrayList<Shape> shapes;
    private PaintBrush paintBrush;
    private int startX, startY, endX, endY;
    private Shape tempShape;

    public DrawPanel(ArrayList<Shape> shapes, PaintBrush paintBrush) {
        this.shapes = shapes;
        this.paintBrush = paintBrush;

        setBackground(Color.WHITE);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                startX = e.getX();
                startY = e.getY();
                tempShape = null;  // Reset temporary shape
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                endX = e.getX();
                endY = e.getY();
                Color color = paintBrush.getCurrentMode().equals("Eraser") ? Color.WHITE : paintBrush.getCurrentColor();
                shapes.add(new Shape(startX, startY, endX, endY, paintBrush.getCurrentMode(), color, paintBrush.getLineType().equals("Dotted"), paintBrush.getLineType()));
                tempShape = null;  // Clear temporary shape
                repaint();
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (paintBrush.getCurrentMode().equals("Pencil")) {
                    endX = e.getX();
                    endY = e.getY();
                    shapes.add(new Shape(startX, startY, endX, endY, paintBrush.getCurrentMode(), paintBrush.getCurrentColor(), paintBrush.getLineType().equals("Dotted"), paintBrush.getLineType()));
                    startX = endX;
                    startY = endY;
                    repaint();
                } else {
                    endX = e.getX();
                    endY = e.getY();
                    Color color = paintBrush.getCurrentMode().equals("Eraser") ? Color.WHITE : paintBrush.getCurrentColor();
                    tempShape = new Shape(startX, startY, endX, endY, paintBrush.getCurrentMode(), color, paintBrush.getLineType().equals("Dotted"), paintBrush.getLineType());
                    repaint();
                }
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        for (Shape shape : shapes) {
            drawShape(g2d, shape);
        }

        if (tempShape != null) {
            drawShape(g2d, tempShape);
        }
    }

    private void drawShape(Graphics2D g2d, Shape shape) {
        g2d.setColor(shape.color);
        if (shape.lineType.equals("Dotted")) {
            float[] dash = {2f, 0f, 2f};
            BasicStroke bs = new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND, 1.0f, dash, 2f);
            g2d.setStroke(bs);
        } else {
            g2d.setStroke(new BasicStroke());
        }
        switch (shape.mode) {
            case "Line":
                g2d.drawLine(shape.x1, shape.y1, shape.x2, shape.y2);
                break;
            case "Rectangle":
                if (shape.lineType.equals("Solid")) {
                    g2d.fillRect(Math.min(shape.x1, shape.x2), Math.min(shape.y1, shape.y2), Math.abs(shape.x2 - shape.x1), Math.abs(shape.y2 - shape.y1));
                } else {
                    g2d.drawRect(Math.min(shape.x1, shape.x2), Math.min(shape.y1, shape.y2), Math.abs(shape.x2 - shape.x1), Math.abs(shape.y2 - shape.y1));
                }
                break;
            case "Oval":
                if (shape.lineType.equals("Solid")) {
                    g2d.fillOval(Math.min(shape.x1, shape.x2), Math.min(shape.y1, shape.y2), Math.abs(shape.x2 - shape.x1), Math.abs(shape.y2 - shape.y1));
                } else {
                    g2d.drawOval(Math.min(shape.x1, shape.x2), Math.min(shape.y1, shape.y2), Math.abs(shape.x2 - shape.x1), Math.abs(shape.y2 - shape.y1));
                }
                break;
            case "Pencil":
                g2d.drawLine(shape.x1, shape.y1, shape.x2, shape.y2);
                break;
            case "Eraser":
                g2d.setColor(Color.WHITE);
                g2d.fillRect(shape.x1, shape.y1, shape.x2 - shape.x1, shape.y2 - shape.y1);
                break;
        }
    }
}
