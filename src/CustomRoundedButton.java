import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;

public class CustomRoundedButton extends JButton {
    private int arcWidth = 50;
    private int arcHeight = 50;

    private boolean roundTopLeft = true;
    private boolean roundTopRight = true;
    private boolean roundBottomLeft = true;
    private boolean roundBottomRight = true;

    public CustomRoundedButton(String text) {
        super(text);
        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorderPainted(false);
        setLayout(null);
    }

    public void setRoundTopLeft(boolean roundTopLeft) {
        this.roundTopLeft = roundTopLeft;
    }

    public void setRoundTopRight(boolean roundTopRight) {
        this.roundTopRight = roundTopRight;
    }

    public void setRoundBottomLeft(boolean roundBottomLeft) {
        this.roundBottomLeft = roundBottomLeft;
    }

    public void setRoundBottomRight(boolean roundBottomRight) {
        this.roundBottomRight = roundBottomRight;
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setColor(getBackground());
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        RoundRectangle2D.Double shape = new RoundRectangle2D.Double(
                0, 0, getWidth() - 1, getHeight() - 1, arcWidth, arcHeight);

        if (!roundTopLeft) {
            g2d.fill(new Rectangle2D.Double(0, 0, arcWidth, arcHeight));
        }

        if (!roundTopRight) {
            g2d.fill(new Rectangle2D.Double(getWidth() - arcWidth, 0, arcWidth, arcHeight));
        }

        if (!roundBottomLeft) {
            g2d.fill(new Rectangle2D.Double(0, getHeight() - arcHeight, arcWidth, arcHeight));
        }

        if (!roundBottomRight) {
            g2d.fill(new Rectangle2D.Double(getWidth() - arcWidth, getHeight() - arcHeight, arcWidth, arcHeight));
        }

        g2d.fill(shape);
        g2d.dispose();

        super.paintComponent(g);
    }



    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Custom Rounded Button Example");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(300, 200);

            CustomRoundedButton roundedButton = new CustomRoundedButton("Click me!");
            roundedButton.setRoundTopLeft(false);
            roundedButton.setRoundBottomRight(false);
            frame.add(roundedButton);

            frame.setVisible(true);
        });
    }
}
