import java.io.Serializable;

public interface Message extends Serializable {
    
    public void execute(GallagerHumbletSpira instance);
    
    public int getId();
}
