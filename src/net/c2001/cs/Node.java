package net.c2001.cs;

import java.net.DatagramPacket;


/**
 * A node as a client or a server. 	It is designed for computers in a LAN.
 * 
 * @author Lin Dong
 *
 */
public interface Node {	
	/**
	 * Send a packet.
	 * @param packet {@link DatagramPacket}.
	 * @return {@code true} on success, {@code false} on failure.
	 */
	public boolean sendPacket(DatagramPacket packet);
	
	/**
	 * Receive a packet.
	 * @return {@link DatagramPacket} on success, {@code null} on
	 * failure. <br>
	 * NOTE: maybe this will block current thread, see to the 
	 * implementation. 
	 * 
	 */
	public DatagramPacket receivePacket();
	
	/**
	 * Send an {@code PacketType.ACK} to the source of {@code packet}.
	 * @param packet {@link DatagramPacket}.
	 * @return {@code true} on success, {@code false} on failure.
	 */
	public boolean sendACK(DatagramPacket packet);
	
	/**
	 * Actions to perform after received an {@code PacketType.ACK}.
	 * @param packet {@link DatagramPacket}.
	 * @return {@code true} on success, {@code false} on failure.
	 */
	public boolean onACK(DatagramPacket packet);
	
	/**
	 * Send an {@code PacketType.ECHO} to the source of {@code packet}.
	 * @param packet {@link DatagramPacket}.
	 * @return {@code true} on success, {@code false} on failure.
	 */
	public boolean sendECHO(DatagramPacket packet);
	
	
	/**
	 * Actions to perform after received an {@code PacketType.ECHO}. Should
	 * return an {@code PacketType.ECHO}.
	 * @param packet {@link DatagramPacket}.
	 * @return {@code true} on success, {@code false} on failure.
	 */
	public boolean onEcho(DatagramPacket packet);
	
	/**
	 * Actions to perform after received a {@code PacketType.FAILURE}. 
	 * @param packet {@link DatagramPacket}.
	 * @return {@code true} on success, {@code false} on failure.
	 */
	public boolean onFailure(DatagramPacket packet);
	
	/**
	 * Actions to perform after received a {@code PacketType.WARNING}. 
	 * @param packet {@link DatagramPacket}.
	 * @return {@code true} on success, {@code false} on failure.
	 */
	public boolean onWarning(DatagramPacket packet);
	
	/**
	 * Actions to perform after received a {@code PacketType.TEXT}. 
	 * @param packet {@link DatagramPacket}.
	 * @return {@code true} on success, {@code false} on failure.
	 */
	public boolean onText(DatagramPacket packet);
	
}
