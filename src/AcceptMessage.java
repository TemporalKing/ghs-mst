

public class AcceptMessage implements Message{

    private int id;
    
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

}
