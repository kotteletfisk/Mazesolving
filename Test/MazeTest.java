import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MazeTest
{
    BufferedImage maze;

    {
        try
        {
            maze = ImageIO.read(new File("Mazes/small.png"));
        }

        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

}

