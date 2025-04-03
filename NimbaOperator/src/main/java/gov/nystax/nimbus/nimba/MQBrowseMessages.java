import com.ibm.mq.MQEnvironment;
import com.ibm.mq.MQException;
import com.ibm.mq.MQGetMessageOptions;
import com.ibm.mq.MQMessage;
import com.ibm.mq.MQQueue;
import com.ibm.mq.MQQueueManager;

public class MQBrowseMessages {
    public static void main(String[] args) {
        String queueManagerName = "QM1"; // Change as needed
        String queueName = "MY.QUEUE";
        String channel = "CHANNEL.NAME";
        String hostname = "localhost";
        int port = 1414;

        MQQueueManager queueManager = null;
        MQQueue queue = null;

        try {
            // Set connection properties
            MQEnvironment.hostname = hostname;
            MQEnvironment.channel = channel;
            MQEnvironment.port = port;

            // Connect to Queue Manager
            queueManager = new MQQueueManager(queueManagerName);

            // Open the queue in BROWSE mode
            int openOptions = MQQueue.MQOO_BROWSE;
            queue = queueManager.accessQueue(queueName, openOptions);

            // Set browse options
            MQGetMessageOptions gmo = new MQGetMessageOptions();
            gmo.options = MQGetMessageOptions.MQGMO_BROWSE_FIRST; // First message

            MQMessage message = new MQMessage();

            while (true) {
                try {
                    queue.get(message, gmo);
                    byte[] messageData = new byte[message.getMessageLength()];
                    message.readFully(messageData);
                    System.out.println("Message: " + new String(messageData));

                    // Change to BROWSE_NEXT for subsequent messages
                    gmo.options = MQGetMessageOptions.MQGMO_BROWSE_NEXT;
                    message.clearMessage();
                } catch (MQException e) {
                    if (e.reasonCode == MQException.MQRC_NO_MSG_AVAILABLE) {
                        System.out.println("No more messages.");
                        break;
                    } else {
                        throw e;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (queue != null) queue.close();
                if (queueManager != null) queueManager.disconnect();
            } catch (MQException e) {
                e.printStackTrace();
            }
        }
    }
}
