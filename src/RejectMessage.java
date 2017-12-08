

public class RejectMessage implements Message{

    private int id;
    
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

}
