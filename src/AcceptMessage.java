

public class AcceptMessage implements Message{

    private int id;
    private int counter;
    
    public AcceptMessage(int id) {
        this.id = id;
    }
    
    public int getId() {
        return id;
    }
    
    public void execute(GallagerHumbletSpira instance) {
        instance.test_edge = Edge.EDGE_NIL;
        
        Edge senderEdge = Edge.getEdge(instance.edges, id);
        if(senderEdge.getWeight() < instance.best_edge.getWeight()) {
            instance.best_edge = senderEdge;
        }
        
        instance.report();
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
