package net.c2001.cs;

import java.net.DatagramPacket;

/**
 * The server node. It coordinates the client nodes registered to it. 
 * @author Lin Dong
 *
 */
public interface Server extends Node{
	/**
	 * Kick a client out. This operation just screen a client.
	 * @param client index of a client.
	 * @return {@code true} on success, {@code false} on failure.
	 */
	public boolean kickOut(int client);
	
	/**
	 * Shutdown a client.
	 * @param client index of a client.
	 * @return {@code true} on success, {@code false} on failure.
	 */
	public boolean shutDown(int client);
	
	/**
	 * Allocate a unique id to the client.
	 */
	public boolean sendUID(int client);
	
	/**
	 * Send current time to client.
	 * @param client index of a client.
	 * @return {@code true} on success, {@code false} otherwise.
	 */
	public boolean sendTime(int client);

	/**
	 * Actions to perform after received an {@code REG}.
	 * @param packet {@link DatagramPacket}.
	 * @return {@code true} on success, {@code false} on failure.
	 */
	public boolean onReg(DatagramPacket packet);
	
	/**
	 * Actions to perform after received an {@code UNREG}.
	 * @param packet {@link DatagramPacket}.
	 * @return {@code true} on success, {@code false} on failure.
	 */
	public boolean onUnReg(DatagramPacket packet);
	
}
