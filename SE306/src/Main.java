import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Main 
{
    private  static ArrayList<Gateway> allGateways = new ArrayList<>();
    public static void main(String[] args) throws IOException
    {

        // and add them to allGateways array
        for(int i=0; i<30; i++)
        {
            allGateways.add(new Gateway(String.valueOf(i)));
        }

        buildTable();
        showTable();
        
        System.out.println(shortestPath(0,15));
        System.out.println(shortestPath(21,17));

    }
    public static void buildTable() throws IOException
    {
        JFileChooser jfc = new JFileChooser(System.getProperty("user.home") +"/Desktop");
        File file = null;
        jfc.setFileFilter(new FileNameExtensionFilter("Text","txt"));
        int result = jfc.showSaveDialog(null);
       
        
         if(result == JFileChooser.APPROVE_OPTION)
        {
            file = jfc.getSelectedFile();
            JOptionPane.showMessageDialog(null,"Transaction Successfull","Success",JOptionPane.INFORMATION_MESSAGE);
        }
        else
        {
            JOptionPane.showMessageDialog(null,"File Selection error","ERROR!",JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
         
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;
        int i=0;
        while ((line = br.readLine()) != null) {
            String[] distance = line.split(",");
            Gateway currentGateway = allGateways.get(i) ;

            for(int j=0;j<30;j++){
                int value = Integer.parseInt(distance[j]);
                Gateway neighbourGateway;
                neighbourGateway = allGateways.get(j);
                currentGateway.addNeighbor(neighbourGateway,value);

            }
            allGateways.set(i,currentGateway);
            i++;
        }

    }


    public static void showTable()
    {
        ///// TODO: prints out the neighbor information for each router from 0 to 29
        for(Gateway currentGateway : allGateways)
        {
            System.out.print(currentGateway.ip + "->");
            for(int j=0;j<30;j++)
            {
                System.out.print( "|R" + currentGateway.getNeighbor(j).ip + "|"+"D"+currentGateway.getNeighborDist(j)+ "|\t\t");
            }
      System.out.println();
        }
    }

    public static Integer shortestPath(Integer routerA, Integer routerB)
    {
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        pq.add(routerA);
        int [] vertexMinDistances = new int[30];
        for (int i=0;i<30;i++){
            vertexMinDistances[i] = Integer.MAX_VALUE;
        }
        vertexMinDistances[routerA] = 0;
        while (!pq.isEmpty()){
            Integer routerU = pq.poll();

            for(int j=0;j<30;j++){
                int distance = allGateways.get(routerU).getNeighborDist(j);
                if(distance == 0)
                    continue;
                int distanceThroughU = vertexMinDistances[routerU] + distance;

                if (distanceThroughU < vertexMinDistances[j]) {
                    vertexMinDistances[j] = distanceThroughU;
                    pq.add(j);
                }
            }
        }
        return vertexMinDistances[routerB];
    }
}
