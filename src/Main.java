import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Main
{
    public static void main(String[] args)
    {
        // program call: Mazesolving /Mazes/small.png -m [method] -o /output/path/solved.png

        // Load maze
        Maze maze = new Maze("Mazes/small.png");

        System.out.println("Loading maze");
        maze.loadMaze();

        System.out.println("Creating nodes...");
        long time_start = System.currentTimeMillis();
        int node_count = maze.createNodes();
        System.out.println("Nodes created: " + node_count);
        double time_elapsed = System.currentTimeMillis() - time_start;
        System.out.println("Seconds elapsed: " + time_elapsed /1000);

       // maze.printNodeMap(); // debug method: prints a 2D array with nodes represented as 1.

      //  maze.printNodeConnections(); // debug method: goes through node array, and prints every nodes amount of connections

        // Find the shortest path
        SolvingAlgorithm bfs = new BreadthFirstSearch();

        System.out.println("Finding path...");
        time_start = System.currentTimeMillis();
        MazeNode[] path = bfs.solve(maze);
        System.out.println("Path found!");
        System.out.println("Nodes visited: " + bfs.getTotal_visited());
        time_elapsed = System.currentTimeMillis() - time_start;
        System.out.println("Seconds elapsed: " + time_elapsed /1000);


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
