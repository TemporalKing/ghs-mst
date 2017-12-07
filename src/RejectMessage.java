

public class RejectMessage implements Message{

    private int id;
    
    public RejectMessage(int id) {
        this.id = id;
    }

    public void execute(GallagerHumbletSpira instance) {
        if(instance.neighbours_status.get(id) == GallagerHumbletSpira.UNKNOWN) {
            instance.neighbours_status.put(id, GallagerHumbletSpira.NOT_IN_MST);
        }
        instance.test();
    }

}
