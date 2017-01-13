package net.c2001.cs;

import java.net.DatagramPacket;

/**
 * The client node. It is controlled by the server node.
 * @author Lin Dong
 *
 */
public interface Client extends Node{
	
	/**
	 * Actions to perform after received a {@code KICKOUT}. Should
	 * return an {@code KICKOUT}.
	 * @param packet {@link DatagramPacket}.
	 * @return {@code true} on success, {@code false} on failure.
	 */
	public boolean onKickOut(DatagramPacket packet);
	
	/**
	 * Actions to perform after received a {@code SHUTDOWN}. Should
	 * return an {@code SHUTDOWN}.
	 * @param packet {@link DatagramPacket}.
	 * @return {@code true} on success, {@code false} on failure.
	 */
	public boolean onShutDown(DatagramPacket packet);
	
	/**
	 * Actions to perform after received an {@code UID}. Should
	 * return an {@code UID}.
	 * @param packet {@link DatagramPacket}.
	 * @return {@code true} on success, {@code false} on failure.
	 */
	public boolean onUID(DatagramPacket packet);
	
	/**
	 * Actions to perform after received an {@code TIME}. Should
	 * return an {@code TIME}.
	 * @param packet {@link DatagramPacket}.
	 * @return {@code true} on success, {@code false} on failure.
	 */
	public boolean onTime(DatagramPacket packet);
	
	/**
	 * Register to the server.
	 * @return {@code true} on success, {@code false} on failure.
	 */
	public boolean regToServer();
	
	/**
	 * Unregister to the server.
	 * @return {@code true} on success, {@code false} on failure.
	 */
	public boolean unregToServer();

}
