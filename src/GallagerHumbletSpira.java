
public class GallagerHumbletSpira implements GallagerHumbletSpira_RMI{

	
	private void println(String message)
    {
        String pidStr = "(" + this.id + ") ";
        System.err.println(pidStr + message);
    }
}
