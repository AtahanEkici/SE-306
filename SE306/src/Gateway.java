import java.util.ArrayList;

public class Gateway {
	String ip; //Router's ip
	ArrayList<Integer> dist; //Distance between me and others
	ArrayList<Gateway> neighbor; //Routers as neighbors
	int size; //number of neighbours
	
	
	Gateway(String p)
	{
		ip = p;
		dist = new ArrayList<Integer>(); //initialize an empty distance array
		neighbor = new ArrayList<Gateway>(); //initialize an empty neighbor array
		size = 0;
	}
	
	public void addNeighbor(Gateway n, Integer d)
	{
		dist.add(d);
		neighbor.add(n);
		size++;
	}
	
	public Gateway getNeighbor(Integer index)
	{
		return neighbor.get(index);
	}
	
	public Integer getNeighborDist(Integer index)
	{
		return dist.get(index);
	}	
}