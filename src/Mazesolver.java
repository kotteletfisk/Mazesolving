import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Mazesolver
{
    public static void main(String[] args)
    {
        // program call: Mazesolver /path/to/input.png /output/path/solved.png -m [method]

        String help_info = "Welcome to Mazesolver!\n\n" +

                "Usage: Mazesolver /path/to/input.png /path/to/output.png (-m [solving method])\n\n" +

                "Path to input and output must always be specified. Paths can be relative or absolute.\n" +
                "Run the program with only -h, --help or without any arguments to show this information.\n\n" +

                "Current available solving methods:\n" +
                "bfs = Breadth first search\n\n" +

                "Rules:\n" +
                "Only png format is tested\n" +
                "Mazes must be in 1bit color (b/w)\n" +
                "Paths can only be 1 pixel wide\n" +
                "Entrance and exit must be in top and bottom\n" +
                "1 entrance and 1 exit allowed\n" +
                "Outer wall of maze image must be black. No whitespace!\n\n" +

                "Flags:\n" +
                "-m / --method  optional flag for path finding algorithm choice. if not specified, BFS is default.\n" +
                "-h / --help    Show this information only.";

        if (args.length < 1)
        {
            System.out.println(help_info);
        }

        else if (args.length == 1)
        {
            if (args[0].equalsIgnoreCase("-h") || args[0].equalsIgnoreCase("--help"))
            {
                System.out.println(help_info);
            }

            else
            {
                throw new IllegalArgumentException("Input and output path must be specified!");
            }
        }


        else if (args.length > 1)
        {
            String input_path = args[0];
            String output_path = args[1];

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

            // maze.printNodeConnections(); // debug method: goes through node array, and prints every node's amount of connections in sequence


            // Find the shortest path
            SolvingAlgorithm solver = new BreadthFirstSearch();

            if (args.length > 2)
            {
                if (args[2].equalsIgnoreCase("-m") || args[2].equalsIgnoreCase("--method"))
                {
                    if (args.length > 3)
                    {
                        switch (args[3].toLowerCase())
                        {
                            // example:
                            case "bfs":
                                solver = new BreadthFirstSearch();
                                break;


                            // Add new solving algo calls here

                            default:
                            {
                                System.out.println("Method not recognized. Using default.");
                                break;
                            }
                        }
                    }

                    else
                    {
                        System.out.println("Method flag used, but method not specified. Using default.");
                    }
                }
            }


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
}
