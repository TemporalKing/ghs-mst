run-ring-alt:
	java -Djava.security.policy=my.policy -Djava.rmi.server.hostname=127.0.0.1 GallagerHumbletSpira_main network-topology.txt-ring1 node-location.txt > ring-alt.data

run-ring-inc:
	java -Djava.security.policy=my.policy -Djava.rmi.server.hostname=127.0.0.1 GallagerHumbletSpira_main network-topology.txt-ring2 node-location.txt > ring-inc.data

run-ring-complex-1:
	java -Djava.security.policy=my.policy -Djava.rmi.server.hostname=127.0.0.1 GallagerHumbletSpira_main network-topology.txt-graph1 node-location.txt-graph1 > ring-complex.data

run-ring-large:
	java -Djava.security.policy=my.policy -Djava.rmi.server.hostname=127.0.0.1 GallagerHumbletSpira_main network-topology.txt-large node-location.txt > ring-large.data

run-grid:
	java -Djava.security.policy=my.policy -Djava.rmi.server.hostname=127.0.0.1 GallagerHumbletSpira_main network-topology.txt-4x4 node-location.txt > grid.data

clean:
	rm *.class

default:
	javac *.java
