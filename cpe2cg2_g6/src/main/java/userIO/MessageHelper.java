package userIO;

import java.util.LinkedList;
import java.util.Queue;

public class MessageHelper {
    private static Queue<String> messageQueue = new LinkedList<>();
    
    public static void send(String message){
        messageQueue.add(message);
    }
    
    public static String receive(){
       return messageQueue.poll();
    }
}
