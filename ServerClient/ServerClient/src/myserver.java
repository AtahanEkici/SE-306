//  en üstte paketler //
import java.awt.Dimension;
import java.io.IOException;
import static java.lang.Integer.parseInt;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.regex.Pattern;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;



	public class myserver extends JFrame 
        {

		private static final long serialVersionUID = 1L;
		private final JTextArea msgArea = new JTextArea();
		private DatagramSocket socket;
                private int Money = 1000;

		   public myserver() 
		   {
		      super("Message Server");
		      super.add(new JScrollPane(msgArea));
		      super.setSize(new Dimension(450, 350));
		      super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		      super.setVisible(true);
		      msgArea.setEditable(false);

		      try
                      {
		         socket = new DatagramSocket(3667);

		      } catch (SocketException ex) 
                      {
                         msgArea.append("\n Bağlantı Hatası: "+ex+" \n ");
		         System.exit(1);
		      }
                      
                      msgArea.append("Total Money: "+getMoney()+" \n ");
		   }

		   public void readyToReceivePacket() 
                   {
		      while (true) 
                      {
		         try {
		            byte buffer[] = new byte[2048];
		            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
		            socket.receive(packet);
                            
                            
		            showMsg(" \n " + " \n Data: "
		                    + new String(packet.getData())+"\n");
                            
		            sendPacket(packet);
                            
                            
		         } catch (IOException ex) 
                         {
		            showMsg(ex.getMessage());
		         }
		      }
		   }

		   public void sendPacket(DatagramPacket packetReceived) 
                   {
		      
		      try 
                      {
		         DatagramPacket packet;
                          packet = new DatagramPacket(packetReceived.getData(),
                                  packetReceived.getLength(),
                                  packetReceived.getAddress(),
                                  packetReceived.getPort());
		         socket.send(packet);
		         
		      } catch (IOException ex) 
                      {
                        msgArea.append(" \n "+ex.toString()+" \n ");
                        msgArea.append(" \n Typing Money will return the Total Money Amount \n ");
		      }
		   }
                   
                   public int getMoney()
                   {
                       return Money;
                   }
                   
                   public void withdraw(int m)
                   {
                       if(Money < m)
                       {
                           msgArea.append(" \n Not enough Credits!! \n");
                       }
                       else
                       {
                           Money = Money - m;
                           msgArea.append("\n Current Money: "+getMoney()+"\n");
                       }
                   }
                   
                    public void deposit(int m)
                   {
                       Money = Money + m;
                       msgArea.append("\n Deposited Amount: "+m+" \n Current Money: "+getMoney()+"\n");
                   }
                   
                   public void Listener()
                   {
                       while (true) 
                      {
		         try {
		            byte buffer[] = new byte[128];
		            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
		            socket.receive(packet);
                            
		            //showMsg(" \n " + " \n Data: "+ new String(packet.getData())+"\n");
                            
                          String temp = new String(packet.getData());
                           
                          if(temp.contains("withdraw") == true)
                          {
                             temp = temp.replaceAll("\\D+","");
                             int m = Integer.parseInt(temp);
                             withdraw(m);
                             //msgArea.append(" \n Successfully withdrawn \n ");
                          }
                          
                          else if(temp.contains("deposit") == true)
                          {
                              temp = temp.replaceAll("\\D+","");
                              int m = Integer.parseInt(temp);
                              deposit(m);
                              sendPacket(packet); 
                          }
                          
                          else if(temp.contains("Money"))
                          {
                              
                              msgArea.append("\n  Total Money: "+getMoney()+"\n"); 
                              sendPacket(packet); 
                          }
                          
                          else
                          {
                              msgArea.append("\n Hata \n");
                              sendPacket(packet); 
                          }
		         } catch (IOException | NumberFormatException  ex) 
                         {
		            showMsg(" \n Wrong Input \n ");
		         }
		      }
                   }
                   
                  

		   public  void showMsg(final String msg) 
                   {
		      SwingUtilities.invokeLater(() -> {
                          msgArea.append(msg);
                      });
		   }
		}
		


