import java.io.BufferedReader;
import java.io.FileReader;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;


@SuppressWarnings("deprecation")
public class GallagerHumbletSpira_main {

	/**
	 * @param args
	 * @throws RemoteException 
	 */
	public static void main(String[] args) throws RemoteException {
		
		// Localhost IP
		String localhost = System.getProperty("java.rmi.server.hostname");

		// read in network topology and nodes per host
		HashMap<Integer, Integer> nodeMap = new HashMap<Integer, Integer>();
		HashMap<Integer, String> ipMap = new HashMap<Integer, String>();
		HashMap<Integer, List<Edge>> topologyMap = new HashMap<Integer, List<Edge>>();
		
		try {
			FileReader nodeFile, ipFile, topologyFile;
			
			nodeFile = new FileReader("node-location.txt");
			ipFile = new FileReader("ip-identifier.txt");
			topologyFile = new FileReader("network-topology.txt");
			
			BufferedReader nodeReader, ipReader, topologyReader;
			
			nodeReader = new BufferedReader(nodeFile);
			ipReader = new BufferedReader(ipFile);
			topologyReader = new BufferedReader(topologyFile);
			
			
			
			String line;
			while ((line = nodeReader.readLine()) != null) {
				if (!line.contains("#"))
				{
					String data[] = line.split(" ");
					nodeMap.put(Integer.parseInt(data[0]), Integer.parseInt(data[1]));
				}
			}
			
			while ((line = ipReader.readLine()) != null) {
				if (!line.contains("#"))
				{
					String data[] = line.split(" ");
					ipMap.put(Integer.parseInt(data[0]), data[1]);
				}
			}
			
			while ((line = topologyReader.readLine()) != null) {
				if (!line.contains("#"))
				{
					String data[] = line.split(" ");
					
					int node1 = Integer.parseInt(data[0]);
					int node2 = Integer.parseInt(data[1]);
					int weight = Integer.parseInt(data[2]);
					
					List<Edge> newData1 = topologyMap.get(node1);
					List<Edge> newData2 = topologyMap.get(node2);
					
					if (newData1 == null)
						newData1 = new ArrayList <Edge>();
					if (newData2 == null)
						newData2 = new ArrayList <Edge>();
					
					Edge edge1, edge2;
					
					edge1 = new Edge(node2, weight);
					edge2 = new Edge(node1, weight);
					
					
					newData1.add(edge1);
					newData2.add(edge2);
					
					topologyMap.put(node1, newData1);
					topologyMap.put(node2, newData2);
				}
			}

			// Clean up
			ipReader.close();
			nodeReader.close();
			topologyReader.close();
			
			nodeFile.close();
			ipFile.close();
			topologyFile.close();
			
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		// Add security manager
		if (System.getSecurityManager() == null) {
            System.setSecurityManager(new RMISecurityManager());
        }
		
		// Create Registry
		try{
            LocateRegistry.createRegistry(1099);
        } catch (Exception e) {
            System.err.println("Could not create registry exception: " + e.toString()); 
            e.printStackTrace(); 
        } 
		
		// TODO Generate GallagerHumbletSpira Objects for the local system
		GallagerHumbletSpira helloWorld = null;
		for (Map.Entry<Integer, Integer> entry : nodeMap.entrySet())
		{
			int node_id = entry.getKey();
			int ip_id = entry.getValue();
			String ip = ipMap.get(ip_id);
			if (ip.equals(localhost))
			{
				// Create the node on the localhost
				List<Edge> edges = topologyMap.get(node_id);
				System.out.println("Node: " + node_id);
				
				HashMap<Integer, String> ip_LUT = new HashMap<Integer, String>();
				for (Edge e: edges)
				{
					System.out.println(e.toString());
					
					int dst = e.getDst();
					int dst_ip_id = nodeMap.get(dst);
					String dst_ip = ipMap.get(dst_ip_id);
					
					ip_LUT.put(dst, dst_ip);
				}
				GallagerHumbletSpira ghs = new GallagerHumbletSpira(node_id, edges, ip_LUT);
				if (node_id ==1)
					helloWorld = ghs;
			}
		}
		
		
		// Pause execution until entire network is up and running
		System.out.println("Press enter to continue");
        Scanner scan = new Scanner(System.in);
        scan.nextLine();
        scan.close();
        System.out.println("Finding MST using Gallager Humblet Spira Algorithm");
        
		// TODO Wake some random nodes
        if (helloWorld != null && helloWorld.SN == GallagerHumbletSpira.STATUS_SLEEPING)
        	helloWorld.wakeUp();
	}

}
