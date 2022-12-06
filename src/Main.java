public class Main
{
    public static void main(String[] args)
    {

        // Load maze
        Maze maze = new Maze("Mazes/tiny.png");

        maze.loadMaze();

        maze.createNodes(); //TODO: return node_map for solving

        maze.printNodeMap(); // debug method: check entrance node position

        maze.printNodeConnections();

    }
}
