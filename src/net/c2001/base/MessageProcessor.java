package net.c2001.base;

/**
 * Message processor used in this library. There are pairs of methods, all
 * methods with an {@link Object} parameter are designed for debugging, though 
 * not strictly limited. <br>
 * Note: both message provider and processor implement the same interface
 * {@link Messages}, this assures any message type will have both providers
 * and processors.
 *  
 * @author Lin Dong
 *
 */
public interface MessageProcessor extends Messages{

}
