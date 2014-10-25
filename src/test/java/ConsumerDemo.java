import com.sfebiz.queue.Action;
import com.sfebiz.queue.ConsumerAdapter;
import com.sfebiz.queue.Message;
import com.sfebiz.queue.MessageListener;


public class ConsumerDemo {

	public static void main(String[] args) {
		new ConsumerAdapter()
		.setAccessKey("0tNtH7eVTLLZFcpU")
		.setConsumerId("CID_test1")
		.setSecretKey("xsLm7BtZlbEbCC0j8LCz3LcGIKpAll")
		.consum("topic1", "tag1", new MessageListener() {
			@Override
			public Action consume(Message message) {
				System.out.println(message);
				return Action.CommitMessage;
			}
		});
	}

}
