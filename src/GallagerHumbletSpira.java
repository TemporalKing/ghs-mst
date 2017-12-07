import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class GallagerHumbletSpira implements GallagerHumbletSpira_RMI{

    private int id;
    
    protected int LN; //Level of the current fragment it is part of
    protected int FN; //Name of the current fragment it is part of
    protected int SN; //State of the node,-1 = sleeping, 0 = find, 1 = found
    protected int in_branch; //ID of edge towards core
    protected int test_edge; //ID of edge checked whether other end in same fragment
    protected int best_edge; //ID of candidate MOE, -1 if none
    protected int best_weight; //Weight of candidate MOE
    protected int find_count; //Number of report messages expected
    
    protected Queue<Message> message_queue; //Message queue
    
    protected HashMap<Integer,Integer> neighbours_status; //Keeps track of neighbours
    //-1 = not in MST, 0 = unknown, 1 = in MST
    
    protected final HashMap<Integer,Integer> neighbours_weight; //Node ID, weight
	
    public GallagerHumbletSpira(int id) {
        this.id = id;
        this.neighbours_status = new HashMap<Integer,Integer>();
        this.message_queue = new LinkedList<Message>();
        this.neighbours_weight = new HashMap<Integer,Integer>();
    }
    
    public void receiveMessage(Message m) {
        m.execute(this);
    }

    public void wakeUp() {
        //TODO
    }
    
    public void sendInitiate(int receiveID, int level, int name, int state) {
        //TODO
    }
    
    public void sendAccept(int receiveID) {
        //TODO
    }
    
    public void sendReject(int receiveID) {
        //TODO
    }
    
    public void test() {
        //TODO
    }
    
    public void report() {
        //TODO
    }
    
    private void println(String message)
    {
        String pidStr = "(" + this.id + ") ";
        System.err.println(pidStr + message);
    }
    
}
