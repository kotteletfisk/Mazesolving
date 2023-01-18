import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MazesolverTest
{
    public static void main(String[] args)
    {
        // CLI program call: Mazesolver /input/path/maze.png /output/path/solved.png -m [method] ex: "bfs"

        /*
        Rules:
        Mazes must be in 1bit color (b/w)
        Only png format is tested
        Paths can only be 1 pixel wide
        Entrance and exit must be in top and bottom
        1 entrance and 1 exit allowed
        Outer wall of maze image must be black. No whitespace!

        Solving algorithms must implement the "SolvingAlgorithm" interface
        in order to be incorporated into the CLI version.

        MazeNodes have 2 public attributes meant to be used by solving algorithms for keeping track of node traversal,
        but do whatever.

        Have fun!
        */

        String input_path = "Mazes/tiny.png";
        String output_path = "Mazes/output.png";

        // Load maze
        Maze maze = new Maze(input_path);


        System.out.println("Loading maze...");

        try
        {
            maze.loadMaze();
        }

        catch (IOException e)
        {
            System.out.println("Input file not found");
            System.exit(1);
        }

        System.out.println("Creating nodes...");
        long time_start = System.currentTimeMillis();
        int node_count = maze.createNodes();
        System.out.println("Nodes created: " + node_count);
        float time_elapsed = System.currentTimeMillis() - time_start;
        System.out.println("Seconds elapsed: " + time_elapsed / 1000);

        // maze.printNodeMap(); // debug method: prints a 2D array with nodes represented as 1.

        // maze.printNodeConnections(); // debug method: goes through node array, and prints every node's amount of connections in sequence.
        // not very readable, but can be useful!


        // Find the shortest path
        SolvingAlgorithm solver = new BreadthFirstSearch(); // Your method here!


        System.out.println("Finding path...");
        time_start = System.currentTimeMillis();
        MazeNode[] path = solver.solve(maze);

        if (path == null)
        {
            System.out.println("Unable to find path between exit and entrance");
            System.exit(1);
        }

        System.out.println("Path found!");
        System.out.println("Nodes visited: " + solver.getTotal_visited());
        time_elapsed = System.currentTimeMillis() - time_start;
        System.out.println("Seconds elapsed: " + time_elapsed / 1000);


        // Draw path on image
        System.out.println("Drawing path...");
        BufferedImage drawn_image = PathDrawer.drawPath(maze.getMaze_img(), path);

        // Output image
        try
        {
            ImageIO.write(drawn_image, "png", new File(output_path));
        }

        catch (IOException e)
        {
            System.out.println("Failed to output image. Is the specified path correct?");
        }
    }
}
