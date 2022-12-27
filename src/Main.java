import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class Main
{
    public static void main(String[] args)
    {

        // Load maze
        Maze maze = new Maze("Mazes/combo400.png");

        System.out.println("Loading maze");
        maze.loadMaze();

        System.out.println("Creating nodes");
        int node_count = maze.createNodes();
        System.out.println("Nodes created: " + node_count);

       // maze.printNodeMap(); // debug method: check entrance node position

      //  maze.printNodeConnections();

        // Find shortest path
        SolvingAlgorithm bfs = new BreadthFirst();

        MazeNode[] path = bfs.solve(maze);
        System.out.println("Path found!");
        // System.out.println(Arrays.toString(path));


        // Draw path on image
        PathDrawer pd = new PathDrawer();

        BufferedImage drawn_image = pd.drawPath(maze.getMaze_img(), path);

        // Output image
        try
        {
            ImageIO.write(drawn_image, "png", new File("Mazes/solved.png"));
        }

        catch (IOException e)
        {
            System.out.println("Failed to output image");
            e.printStackTrace();
        }
    }
}
