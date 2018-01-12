import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class GallagerHumbletSpira extends UnicastRemoteObject implements GallagerHumbletSpira_RMI{
    
    private static final String naming = "Node-";
    public static final int STATUS_FIND = 0;
    public static final int STATUS_FOUND = 1;
    public static final int STATUS_SLEEPING = -1;
    
    // number of each type of message sent
    private int initiate_count = 0;
    private int report_count = 0;
    private int accept_count = 0;
    private int reject_count = 0;
    private int connect_count = 0;
    private int change_root_count = 0;
    private int test_count = 0;
    
    
    public int id; //TODO
    private HashMap<Integer, String> ip_LUT;
    
    protected int LN; //Level of the current fragment it is part of
    protected int FN; //Name of the current fragment it is part of
    protected int SN = STATUS_SLEEPING; //State of the node,-1 = sleeping, 0 = find, 1 = found
    protected int in_branch; //ID of edge towards core
    protected int test_edge; //ID of edge checked whether other end in same fragment
    protected Edge best_edge; // candidate for MWOE
    protected int find_count; //Number of report messages expected
    protected int init_count_received; //Number of init messages received.
    
    protected Queue<Message> message_queue; //Message queue
    
    protected List<Edge> edges; //Keeps track of edges
    	
    public GallagerHumbletSpira(int id, List<Edge> edges, HashMap<Integer, String> ip_LUT) throws RemoteException {
        this.id = id;
        this.edges = edges;
        this.message_queue = new LinkedList<Message>();
        this.ip_LUT = ip_LUT;
        
        this.best_edge = new Edge(Edge.EDGE_NIL, Integer.MAX_VALUE);
        
        bind();
    }
    
    /**
     * Binding the remote object (stub) in the local registry
     */
    private void bind() {
        try{
            Registry registry = LocateRegistry.getRegistry();
            registry.rebind(naming + id, this);
            System.err.println("Node " + id + " ready");
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
    }
    
    public synchronized void receiveMessage(Message m) {
    	println(String.format("Received message %d.%d of type %s", m.getId(), m.getMessageCounter(), m.getClass()));
    	
    	Edge sourceEdge = Edge.getEdge(edges, m.getId());
    
    	if (sourceEdge.getLastReceived()+1 == m.getMessageCounter())
    	{
    		sourceEdge.incrementLastReceived();
    		m.execute(this);
            check_queue();
    	}
    	else
    	{
    		println(String.format("Buffering message %d.%d", m.getId(), m.getMessageCounter()));
    		message_queue.add(m);
    	}
    }
    
    private void sendMessage(int destination, Message m) {
//        println(String.format("Level %d, Fragment Name %d, Status %d, In branch %d", LN, FN, SN, in_branch));
    	
        String destName = "//" + ip_LUT.get(destination) + ":1099/" + naming + destination;
        
        Edge destEdge = Edge.getEdge(edges, destination);
        destEdge.incrementLastSent();
        m.setMessageCounter(destEdge.getLastSent());
        
        println(String.format("Sent message %d.%d of type %s to %d", m.getId(), m.getMessageCounter(), m.getClass(), destination));
        
        try {
            GallagerHumbletSpira_RMI dest = (GallagerHumbletSpira_RMI) Naming.lookup(destName);
            
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                    	Thread.sleep((int)(Math.random()*1000));
                        dest.receiveMessage(m);
                    } catch (RemoteException | InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        } catch (MalformedURLException | RemoteException | NotBoundException e) {
            e.printStackTrace();
        }
        
        
    }
    
    public void sendInitiate(int receiveID, int level, int name, int state) {
    	initiate_count++;
        sendMessage(receiveID, new InitiateMessage(id, level, name, state));
    }
    
    public void sendAccept(int receiveID) {
    	accept_count++;
        sendMessage(receiveID, new AcceptMessage(id));
    }
    
    public void sendReject(int receiveID) {
    	reject_count++;
        sendMessage(receiveID, new RejectMessage(id));
    }
    
    public void sendChangeRoot(int receiveID) {
    	change_root_count++;
        sendMessage(receiveID, new ChangeRootMessage(id));
    }
    
    public void sendReport(int receiveID, int best_wt) {
    	report_count++;
        sendMessage(receiveID, new ReportMessage(id, best_wt));        
    }
    
    public void sendTest(int receiveID, int level, int name) {
    	test_count++;
        sendMessage(receiveID, new TestMessage(id, level, name));
    }
    
    public void sendConnect(int receiveID, int level) {
    	connect_count++;
        sendMessage(receiveID, new ConnectMessage(id, level));
    }  
    
    public void sendTerminate(int dest)
    {
    	sendMessage(dest, new TerminateMessage(this.id));
    }
    
    public void wakeUp() {        
    	println("Woke up");
        int min_edge_dst = Edge.getMWOE(edges).getDst();
        Edge.getMWOE(edges).setStatus(Edge.IN_MST);
        LN = 0;
        SN = STATUS_FOUND;
        find_count = 0;
        sendConnect(min_edge_dst, 0);
    }
    
    public void test() {
        if(Edge.getMWOE(edges).getWeight() != Integer.MAX_VALUE) {
            test_edge = Edge.getMWOE(edges).getDst();
            sendTest(test_edge, LN, FN);
        } else {
            test_edge = Edge.EDGE_NIL;
            report();
        }
    }
    
    public void report() {
    	println("fc: " + find_count);
//    	println("test_edge: " + test_edge);
    	if(find_count == 0 && test_edge == Edge.EDGE_NIL) {
            SN = STATUS_FOUND;
            while(report_count < init_count_received) {
                sendReport(in_branch, best_edge.getWeight());
            }
        }
    	check_queue();
    }
    
    public void change_root() {
    	println("Change Root");
    	
    	Edge temp = Edge.getEdge(edges, best_edge.getDst());

    	if (temp != null) {
    		if(temp.getStatus() == Edge.IN_MST) {
    		    sendChangeRoot(temp.getDst());
    		} else {
    		    sendConnect(temp.getDst(), LN);
    		    Edge.getEdge(edges, temp.getDst()).setStatus(Edge.IN_MST);
    		    temp.setStatus(Edge.IN_MST);
    		}
    	}    	
    }
    
    public void print_in_branches() {
        //TODO
    	synchronized(System.err){
    		println("*******************************");
    		println("Node " + id);
    		for (Edge adjacent_edge: edges)
    		{
    			if(adjacent_edge.getStatus() == Edge.IN_MST)
    				println (adjacent_edge.toString());
    		}
    		println("Message Queue Size: "+message_queue.size());
    		println("Accept Message Count: " + accept_count);
    		println("ChangeRoot Message Count: " + change_root_count);
    		println("Connect Message Count: " + connect_count);
    		println("Initiate Message Count: " + initiate_count);
    		println("Reject Message Count: " + reject_count);
    		println("Report Message Count: " + report_count);
    		println("Test Message Count: " + test_count);
    		println("################################");
    	}
    }
    
    public synchronized void check_queue()
    {
    	// Check queue
    	int queue_size = message_queue.size();
    	
    	for (int i = 0; i < queue_size; i++)
    	{
    		if (message_queue.size() != 0)
    		{
	    		Message m = message_queue.remove();
	    		Edge sourceEdge = Edge.getEdge(edges, m.getId());
	        	if (sourceEdge.getLastReceived()+1 >= m.getMessageCounter())
	        	{
	        		m.execute(this);
	        		if (sourceEdge.getLastReceived()+1 == m.getMessageCounter())
	        			sourceEdge.incrementLastReceived();
	        	}
	        	else
	        	{
	        		message_queue.add(m);
	        	}
    		}
    	}
    }
        
	public void println(String message)
    {
    	
        String pidStr = "(" + this.id + ") ";
//        synchronized(System.err){
//        	System.err.println(pidStr + message);
//        }
        synchronized(System.out){
            System.out.println(pidStr + message);
        }
    }
    
}
