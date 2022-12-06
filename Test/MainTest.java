import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MainTest
{

    public static void main(String[] args)
    {
        BufferedImage maze;

        try
        {
            maze = ImageIO.read(new File("Mazes/small.png"));
            System.out.println(maze.getRGB(1,2));
        }

        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
