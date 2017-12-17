
public class TerminateMessage implements Message {
	
	private int id;
	private int counter;
	
	public TerminateMessage(int id)
	{
		this.id = id;
	}

	@Override
	public void execute(GallagerHumbletSpira instance) {
		// TODO Auto-generated method stub
		for (Edge adjacent_edge : instance.edges)
		{
			int dest = adjacent_edge.getDst();
			if (dest != instance.in_branch && adjacent_edge.getStatus() == Edge.IN_MST)
			{
				instance.sendTerminate(dest);
			}
		}
		instance.print_in_branches();
	}

	@Override
	public int getId() {
		// TODO Auto-generated method stub
		return id;
	}

	@Override
	public int getMessageCounter() {
		// TODO Auto-generated method stub
		return counter;
	}

	@Override
	public void setMessageCounter(int counter) {
		// TODO Auto-generated method stub
		this.counter = counter;
	}
	
}
