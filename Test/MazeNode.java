class MazeNode
{
    private final int x_pos;
    private final int y_pos;

    private MazeNode west;
    private MazeNode east;
    private MazeNode north;
    private MazeNode south;

    
    public boolean visited;
    public MazeNode parent;

    public MazeNode(int x_pos, int y_pos)
    {
        this.x_pos = x_pos;
        this.y_pos = y_pos;

        this.west = null;
        this.east = null;
        this.north = null;
        this.south = null;

        this.visited = false;
        this.parent = null;
    }

    public int getX_pos()
    {
        return x_pos;
    }

    public int getY_pos()
    {
        return y_pos;
    }

    public MazeNode[] getNeighbours()
    {
        return new MazeNode[]{west, east, north, south};
    }


    public void setWest(MazeNode west)
    {
        this.west = west;
    }

    public void setEast(MazeNode east)
    {
        this.east = east;
    }

    public void setNorth(MazeNode north)
    {
        this.north = north;
    }

    public void setSouth(MazeNode south)
    {
        this.south = south;
    }

    public int getConnections()
    {
        int count = 0;

        for (MazeNode n : getNeighbours())
        {
            if (n != null)
            {
                count++;
            }
        }

        return count;
    }
}
