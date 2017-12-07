
public class ChangeRootMessage implements Message {
    
    private int id;
    
    public ChangeRootMessage(int id) {
        this.id = id;
    }

    public void execute(GallagerHumbletSpira instance) {
        instance.change_root();
    }

}
