public interface SolvingAlgorithm
{
    MazeNode[] solve(Maze maze); // returns null if no path is found

    int getTotal_visited();
}
