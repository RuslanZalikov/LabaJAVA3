import java.util.HashMap;
import java.util.Map;

/**
 * This class stores the basic state necessary for the A* algorithm to compute a
 * path across a map.  This state includes a collection of "open waypoints" and
 * another collection of "closed waypoints."  In addition, this class provides
 * the basic operations that the A* pathfinding algorithm needs to perform its
 * processing.
 **/
public class AStarState
{
    /** This is a reference to the map that the A* algorithm is navigating. **/
    private Map2D map;
    public HashMap<Location, Waypoint> openHM;
    public HashMap<Location, Waypoint> closeHM;

    /**
     * Initialize a new state object for the A* pathfinding algorithm to use.
     **/
    public AStarState(Map2D map)
    {
        if (map == null)
            throw new NullPointerException("map cannot be null");
        this.map = map;
        this.openHM = new HashMap<Location, Waypoint>();
        this.closeHM = new HashMap<Location, Waypoint>();
//        System.out.println("HelloWorld");
    }

    /** Returns the map that the A* pathfinder is navigating. **/
    public Map2D getMap()
    {
        return map;
    }

    /**
     * This method scans through all open waypoints, and returns the waypoint
     * with the minimum total cost.  If there are no open waypoints, this method
     * returns <code>null</code>.
     **/
    public Waypoint getMinOpenWaypoint(){
        if (this.openHM.isEmpty()) return null;
        float min = 100000000f;
        Location key = new Location();
        for (Waypoint point : this.openHM.values()){
            if (point.getRemainingCost() < min) {
                min = point.getRemainingCost();
                key = point.getLocation();
            }
        }
        return this.openHM.get(key);
    }

    /**
     * This method adds a waypoint to (or potentially updates a waypoint already
     * in) the "open waypoints" collection.  If there is not already an open
     * waypoint at the new waypoint's location then the new waypoint is simply
     * added to the collection.  However, if there is already a waypoint at the
     * new waypoint's location, the new waypoint replaces the old one <em>only
     * if</em> the new waypoint's "previous cost" value is less than the current
     * waypoint's "previous cost" value.
     **/
    public boolean addOpenWaypoint(Waypoint newWP)
    {
        // если вершин с такими координатами еще нет, добавляем вершину в hashmap
        if (!this.openHM.containsKey(newWP.getLocation())){
            this.openHM.put(newWP.getLocation(), newWP);
            return true;
        }
        // если вершина с такими координатами уже есть, мы заменяем ее на новую при условии, что
        // путь от старой вершины до старта больше, чем от новой вершины до старта
        else {
            if(this.openHM.get(newWP.getLocation()).getPreviousCost() > newWP.getPreviousCost()){
                this.openHM.put(newWP.getLocation(), newWP);
                return true;
            }
            else return false;
        }
    }


    /** Returns the current number of open waypoints. **/
    public int numOpenWaypoints()
    {
        return this.openHM.size();
    }


    /**
     * This method moves the waypoint at the specified location from the
     * open list to the closed list.
     **/
    public void closeWaypoint(Location loc)
    {
        this.closeHM.put(loc, this.openHM.remove(loc));
    }

    /**
     * Returns true if the collection of closed waypoints contains a waypoint
     * for the specified location.
     **/
    public boolean isLocationClosed(Location loc)
    {
        return this.closeHM.containsKey(loc);
    }
}
