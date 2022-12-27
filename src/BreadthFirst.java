import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Queue;

public class BreadthFirst implements SolvingAlgorithm
{
    @Override
    public MazeNode[] solve(Maze maze)
    {
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

                else
                {
                    nodePath.addAll(path.get(current.parent));
                }
                nodePath.add(current);
                nodePath.trimToSize();
                path.put(current, nodePath);

                // If exit is found, stop search and return its path.
                if (current == exit)
                {
//                    System.out.println("Exit found!");
//                    System.out.println("total visited: " + total_visited);
//                    System.out.println(path.get(current));
                    MazeNode[] arr = new MazeNode[path.get(current).size()];
                    return path.get(current).toArray(arr);
                }

                // if node has unvisited neighbours, add them to queue
                boolean dead_end = true;
                for (MazeNode n : current.getNeighbours())
                {
                    if (n != null && !n.visited)
                    {
                        queue.add(n);
                        n.parent = current;
                        dead_end = false;
                    }
                }

                // if dead end, clean up path for a little ram efficiency
                if (dead_end)
                {
                    path.remove(current);
                }
            }
        }
        // Queue empty, but exit not found
        return null;
    }
}
