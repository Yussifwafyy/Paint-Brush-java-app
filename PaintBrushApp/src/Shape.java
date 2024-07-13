import java.awt.Color;

public class Shape {
    int x1, y1, x2, y2;
    String mode;
    Color color;
    boolean isDotted;
    String lineType; // New field to store the line type

    public Shape(int x1, int y1, int x2, int y2, String mode, Color color, boolean isDotted, String lineType) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.mode = mode;
        this.color = color;
        this.isDotted = isDotted;
        this.lineType = lineType; // Store the line type
    }
}
