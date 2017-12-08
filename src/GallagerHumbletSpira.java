import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class GallagerHumbletSpira implements GallagerHumbletSpira_RMI{
    
    public static final int STATUS_FIND = 0;
    public static final int STATUS_FOUND = 1;
    public static final int STATUS_SLEEPING = -1;
    
    private int id;
    
    protected int LN; //Level of the current fragment it is part of
    protected int FN; //Name of the current fragment it is part of
    protected int SN; //State of the node,-1 = sleeping, 0 = find, 1 = found
    protected int in_branch; //ID of edge towards core
    protected int test_edge; //ID of edge checked whether other end in same fragment
    protected Edge best_edge; // candidate for MWOE
    protected int find_count; //Number of report messages expected
    
    protected Queue<Message> message_queue; //Message queue
    
    protected List<Edge> edges; //Keeps track of edges
    	
    public GallagerHumbletSpira(int id, List<Edge> edges) {
        this.id = id;
        this.edges = edges;
        this.message_queue = new LinkedList<Message>();
    }
    
    public void receiveMessage(Message m) {
        m.execute(this);
    }
    
    private void sendMessage(Message m) {
        //TODO
    }
    
    public void sendInitiate(int receiveID, int level, int name, int state) {
        sendMessage(new InitiateMessage(receiveID, level, name, state));
    }
    
    public void sendAccept(int receiveID) {
        sendMessage(new AcceptMessage(receiveID));
    }
    
    public void sendReject(int receiveID) {
        sendMessage(new RejectMessage(receiveID));
    }
    
    public void sendChangeRoot(int receiveID) {
        sendMessage(new ChangeRootMessage(receiveID));
    }
    
    public void sendReport(int receiveID, int best_wt) {
        sendMessage(new ReportMessage(receiveID, best_wt));
    }
    
    public void sendTest(int receiveID, int level, int name) {
        sendMessage(new TestMessage(receiveID, level, name));
    }
    
    public void sendConnect(int receiveID, int level) {
        sendMessage(new ConnectMessage(receiveID, level));
    }  

    public void wakeUp() {
        int min_edge_dst = Edge.getMWOE(edges).getDst();
        Edge.getMWOE(edges).setStatus(Edge.IN_MST);
        LN = 0;
        SN = STATUS_FOUND;
        find_count = 0;
        sendConnect(min_edge_dst, 0);
    }
    
    public void test() {
        //TODO
    }
    
    public void report() {
        if(find_count == 0 && test_edge == Edge.EDGE_NIL) {
            SN = STATUS_FOUND;
            sendReport(in_branch, best_edge.getWeight());
        }
    }
    
    public void change_root() {
        //TODO
    }
    
    public void halt() {
        //TODO
    }
    
    @SuppressWarnings("unused")
	private void println(String message)
    {
        String pidStr = "(" + this.id + ") ";
        System.err.println(pidStr + message);
    }
    
}
