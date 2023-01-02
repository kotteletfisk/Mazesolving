import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Queue;

public class BreadthFirst implements SolvingAlgorithm
{
    int total_visited = 0;
    @Override
    public MazeNode[] solve(Maze maze)
    {
        MazeNode entrance = maze.getEntrance();
        MazeNode exit = maze.getExit();
        MazeNode current;
//        HashMap<MazeNode, ArrayList<MazeNode>> path = new HashMap<>();

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

                // If exit is found, stop search and return its path.
                if (current == exit)
                {
                    // backtrack node path by its parents
                    ArrayList<MazeNode> path = new ArrayList<>();
                    while (current.parent != null)
                    {
                        path.add(current);

                        current = current.parent;
                    }

                    // add entrance node;
                    path.add(current);

                    path.trimToSize();
                    MazeNode[] output = new MazeNode[path.size()];
                    return path.toArray(output);
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
        // Queue empty, but exit not found
        return null;
    }

    public int getTotal_visited()
    {
        return total_visited;
    }
}
