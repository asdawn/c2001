package net.c2001.cs;

/**
 * Header of packets.
 * @author Lin Dong
 *
 */
public enum PacketType {
	//Common
	
	/**
	 * Acknowledgment, return this after received a packet other than
	 * {@code PacketType.ACK} and {@code PacketType.ECHO}.
	 */
	ACK,
	/**
	 * ECHO, return a {@code PacketType.ECHO} after received a {@code PacketType.ECHO}.
	 */
	ECHO,
	/**
	 * Failure message.
	 */
	FAILURE,	
	/**
	 * Warning message.
	 */
	WARNING,
	/**
	 * Text message.
	 */
	TEXT,
	
	//Server	
	/**
	 * Kick a client out.
	 */
	KICK,
	/**
	 * Shutdown a client.
	 */
	CLOSE,
	/**
	 * Allocate a unique id to the client.
	 */
	UID,
	/**
	 * Send current time to client.
	 */
	TIME,
	
	//Client
	/**
	 * Register this client to server.
	 */
	REG,	
	/**
	 * Unregister this client.
	 */
	UNREG,
	
}
