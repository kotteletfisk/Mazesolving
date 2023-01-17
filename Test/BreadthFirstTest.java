import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.*;

public class BreadthFirstTest // implements SolvingAlgorithm
{

    // Inefficient deprecated version.
    @Test
    public void /*MazeNode[]*/ solve(/* Maze maze */)
    {
        Maze maze = new Maze("Mazes/small.png");

        try
        {
            maze.loadMaze();
        }

        catch (IOException e)
        {
            e.printStackTrace();
        }
        maze.createNodes();

        maze.printNodeMap();

        MazeNode entrance = maze.getEntrance();
        MazeNode exit = maze.getExit();
        MazeNode current;
        HashMap<MazeNode, ArrayList<MazeNode>> path = new HashMap<>();


        int total_visited = 0;

        Queue<MazeNode> queue = new ArrayDeque<>();

        // Traverse queue starting from entrance
        queue.add(entrance);
        while (!queue.isEmpty())
        {
            current = queue.poll();

            if (!current.visited) // why necessary??
            {
                current.visited = true;
                total_visited++;

                // node path = parent path + this
                ArrayList<MazeNode> nodePath = new ArrayList<>();
                if (path.get(current.parent) == null)
                {
                    nodePath.add(null);
                }
                 // need to test
                else
                {
                    nodePath.addAll(path.get(current.parent));
                }
                nodePath.add(current);
                path.put(current, nodePath);

                // If exit is found, stop search
                if (current == exit)
                {
                    System.out.println("Exit found!");
                    System.out.println("total visited: " + total_visited);
                    System.out.println(path.get(current));
                    break;
                }

                // if node has unvisited neighbours, add them to queue
                for (MazeNode n : current.getNeighbours())
                {
                    if (n != null && !n.visited)
                    {
                        queue.add(n);
                        n.parent = current;
                    }
                }
            }
        }
    }
}
