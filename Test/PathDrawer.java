import java.awt.*;
import java.awt.image.BufferedImage;

public class PathDrawer
{
    public static BufferedImage drawPath(BufferedImage image, MazeNode[] path)
    {
        BufferedImage draw_layer = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2 = draw_layer.createGraphics();
        g2.drawImage(image, 0, 0, null);
        g2.setColor(Color.red);

        for (int i = 0; i < path.length -1; i++)
        {
            MazeNode current = path[i];
            MazeNode next = path[i + 1];
            g2.drawLine(current.getX_pos(), current.getY_pos(), next.getX_pos(), next.getY_pos());
        }

        return draw_layer;
    }
}
