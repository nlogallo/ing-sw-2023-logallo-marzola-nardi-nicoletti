package it.polimi.ingsw.utils;

import java.io.*;

/**
 * This is a util class that create a stream of bytes from a NetworkMessage or vice versa. It is necessary only when it uses the Socket TCP.
 */
public class ByteHandler {
    /**
     * This method creates a stream of bytes from the NetworkMessage
     * @param networkMessage is the NetworkMessage to send
     * @return the bytes stream
     */
    public byte[] writeBytesFromMessage(NetworkMessage networkMessage){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = null;
        try {
            objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(networkMessage);
        } catch (IOException e) {
            System.out.println("--Fatal Error-- Message could not be converted in bytes");
        }
        return byteArrayOutputStream.toByteArray();
    }

    /**
     * This method create a NetworkMessage from a stream of bytes
     * @param bytesMessage is the stream passed via Socket TCP
     * @return the NetworkMessage
     */
    public NetworkMessage readMessageFromBytes(byte[] bytesMessage){
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytesMessage);
        ObjectInputStream objectInputStream = null;
        try {
            objectInputStream = new ObjectInputStream(byteArrayInputStream);
            return (NetworkMessage) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("--Fatal Error-- Message could not be read from bytes");
        }
        return new NetworkMessage();
    }

}
