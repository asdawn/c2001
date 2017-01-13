package net.c2001.base;

/*
 * Though all objects should be net.c2001.base.Ojbect, here we use 
 * {@link java.lang.Object} in case.
 */
import java.lang.Object;

/**
 * Message provider used in this library. There are pairs of methods, all
 * methods with an {@link Object} parameter are designed for debugging, though 
 * not strictly limited.
 * 
 * @author Lin Dong
 *
 */
public interface MessageProvider extends Messages{
}