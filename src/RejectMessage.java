

public class RejectMessage implements Message{

    private int id;
    private int counter;
    
    public RejectMessage(int id) {
        this.id = id;
    }
    
    public int getId() {
        return id;
    }

    public void execute(GallagerHumbletSpira instance) {
    	Edge senderEdge = Edge.getEdge(instance.edges, id);
        if(senderEdge.getStatus() == Edge.UNKNOWN) {
        	senderEdge.setStatus(Edge.NOT_IN_MST);
        }
        instance.test();
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
