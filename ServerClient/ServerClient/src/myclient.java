

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;



	
	public class myclient extends JFrame
	{
		private static final long serialVersionUID = 1L;
		private final JTextField msgField=new JTextField();
		   private final JTextArea msgArea=new JTextArea();
		   private DatagramSocket socket; // ServerSocket fonksiyonu bu client'ı TCP yapar //

		   public myclient()
		   {
		      super("UDP Client"); // Server Socket olunca adı TCP Client olarak yenilenmeli  eğer karar verebilirsek o da//
		      super.add(msgField, BorderLayout.NORTH);
		      super.add(new JScrollPane(msgArea),
		         BorderLayout.CENTER);
		      super.setSize(new Dimension(450,350)); // Frame'in boyutunu belirler.(x,y) //
		      super.setVisible(true);
		      super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		      msgArea.setEditable(false);

		      try{
		         socket=new DatagramSocket();
		      }catch(SocketException ex)
			  {
		         System.exit(1);
		      }

		      msgField.addActionListener((ActionEvent evt) -> 
                      {
		         try
                         {
		            String msg=evt.getActionCommand();
		            
		            byte buff[]=msg.getBytes();
		            DatagramPacket packetSend=
		               new DatagramPacket(buff, buff.length,
		               InetAddress.getLocalHost(), 3667); // Bağlanılan adresin port bilgileri buraya girilir ancak LocaLHost sadece aynı bilgisayardaki komünikasyonu yöneetir.//
		            socket.send(packetSend);
		            
		         }catch(IOException ex)
                         {
		            showMsg(ex.getMessage());
		         }
		      });

		   }

		   public void showMsg(final String msg) 
                   {
		      SwingUtilities.invokeLater(() -> 
                      {
		         msgArea.append(msg);
		      });
		   }
	}

