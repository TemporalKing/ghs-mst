
public class AcceptMessage implements Message{

    private int id;
    
    public AcceptMessage(int id) {
        this.id = id;
    }
    
    public void execute(GallagerHumbletSpira instance) {
        instance.test_edge = GallagerHumbletSpira.BEST_EDGE_NIL;
        
        if(instance.neighbours_weight.get(id) < instance.best_weight) {
            instance.best_edge = id;
            instance.best_weight = instance.neighbours_weight.get(id);
        }
        
        instance.report();
    }

}
