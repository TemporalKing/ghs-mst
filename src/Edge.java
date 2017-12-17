import java.util.List;


public class Edge {
	
	public static final int EDGE_NIL = -1;
    public static final int IN_MST = 1;
    public static final int NOT_IN_MST = 0;
    public static final int UNKNOWN = -1;
	
	private final int dst, weight;
	private int status = UNKNOWN;
	
	private int last_received = 0;
	private int last_sent = 0;
	
	Edge (int dst, int weight)
	{
		this.dst = dst;
		this.weight = weight;
	}
	
	public int getLastReceived()
	{
		return last_received;
	}
	
	public int getLastSent()
	{
		return last_sent;
	}
	
	public void incrementLastSent()
	{
		last_sent++;
	}
	
	public void incrementLastReceived()
	{
		last_received++;
	}
	
	public int getDst()
	{
		return dst;
	}
	
	public int getWeight()
	{
		return weight;
	}
	
	public int getStatus()
	{
		return status;
	}
	
	public void setStatus(int status)
	{
		this.status = status;
	}
	
	public String toString()
	{
		return String.format("Dst: %d\tWeight: %d" , getDst(), getWeight());
	}
	
	public static Edge getMWOE(List<Edge> edges)
	{
		Edge minEdge = new Edge(-1, Integer.MAX_VALUE);
		
		for (Edge e: edges)
		{
			if (e.getWeight() < minEdge.getWeight() && e.status == UNKNOWN)
			{
				minEdge = e;
			}
		}
		if (minEdge.getWeight() == Integer.MAX_VALUE)
			return null;
		return minEdge;
	}
	
	public static Edge getEdge(List<Edge> edges, int dst)
	{
		for (Edge e: edges)
		{
			if (e.getDst() == dst)
				return e;
		}
		System.out.println("getEdge() returned null");
		return null;
	}
}
