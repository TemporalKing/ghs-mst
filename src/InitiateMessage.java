
import java.util.Iterator;

public class InitiateMessage implements Message {

    private int L;
    private int F;
    private int S;
    private int id; //ID of sender
    private int counter;
    
    public InitiateMessage(int id, int L, int F, int S) {
        this.id = id;
        this.L = L;
        this.F = F;
        this.S = S;
    }

    public int getId() {
        return id;
    }
    
    public void execute(GallagerHumbletSpira instance) {
        instance.FN = F;
        instance.SN = S;
        instance.in_branch = id;
        instance.LN = L;
        instance.check_queue();
        
        instance.best_edge = new Edge(Edge.EDGE_NIL, Integer.MAX_VALUE);
        instance.find_count=0;
        for (Edge adjacent_edge : instance.edges)
        {
            if(adjacent_edge.getDst() != instance.in_branch && adjacent_edge.getStatus() == Edge.IN_MST) {
                instance.sendInitiate(adjacent_edge.getDst(), L, F, S);
                if (S == GallagerHumbletSpira.STATUS_FIND) {
                    instance.find_count++;
                }
            }
        }
        
        if(S == GallagerHumbletSpira.STATUS_FIND) {
            instance.test();
        }
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
