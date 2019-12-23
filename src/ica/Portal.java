package ica;
import java.util.HashMap;

public class Portal extends MetaAgent {
    /**
     * The variable routing queue will be a HashMap mapping the
     * agent names to their blocking queue.
     */
    private HashMap<String, UserAgent> routing = new HashMap<>();

    public Portal(String name, Portal portal) {
        super(name, portal);
        this.name = name;
        this.portal = portal;

        if (portal != null)
            routing = portal.getRoutingTable();
    }

    public Portal getPortal() {
        return portal;
    }

    public String getName() {
        return name;
    }

    public HashMap<String, UserAgent> getRoutingTable() {
        return routing;
    }

    public void setRoutingTable(HashMap<String, UserAgent> routing) {
        this.routing = routing;
    }

    public UserAgent getKeyAgent(String key)
    {
        if (routing.containsKey(key))
            return routing.get(key);
        return null;
    }


    public void sync(Portal portal) {
        HashMap<String, UserAgent> newRoutingTable = new HashMap<>();
        newRoutingTable.putAll(this.routing);
        newRoutingTable.putAll(portal.getRoutingTable());
        portal.setRoutingTable(newRoutingTable);
     }


    public void setPortal(Portal portal) {
        this.portal = portal;
    }

    public void msgHandler(Message msg) {
        routing.get(msg.getReceiver());
    }

    /**
     * Adds UserAgents to the Portals routing table.
     * @param agent UserAgent being passed in. Allows adding to the routing table.
     */
    public void addAgent(UserAgent agent) {
        if (!routing.containsKey(agent.getName()) && agent.getPortal() == this) {
            routing.put(agent.getName(), agent);
        }
        if (this.portal != null)
        {
            sync(this.portal);
        }
    }


    // ABOVE WE HAVE ADD AGENT, HERE WE COULD ALSO HAVE REMOVE AGENT, ADD MONITOR, REMOVE MONITOR...ETC.
}