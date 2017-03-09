/*
 * Created on 17 juin 2005
 *
 */
package fr.inria.atlanmod.appa.base;


/**
 * Abstract class for message contents.
 * The goal of this class is to represent the query part of a ResolverQuery.
 * @author sunye
 *
 */
public abstract class MessageContent {

    public static final String TypeTag = "type";
    public static final String KeyTag = "key";
    public static final String ClassNameTag = "className";
    public static final String ManagerPeerIDTag = "managerPeerID";
    public static final String AttributeTag = "attribute";
    public static final String AttributeNameTag = "name";
    public static final String AttributeSetTag = "attributeSet";
    
    
    public MessageContent() {

    }
    
    public abstract String getType();


    
    /**
     * Parses a message and intialize this message with it.
     * @param msg
     */
    abstract protected void parseContentMessage(String msg);

    
    
}
