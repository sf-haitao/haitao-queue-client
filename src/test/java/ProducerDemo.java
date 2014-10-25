import com.sfebiz.queue.ProducerAdapter;


public class ProducerDemo {

	public static void main(String[] args) {
		ProducerAdapter producer = new ProducerAdapter()
			.setProducerId("PID_haitao_logistics_sys")
			.setAccessKey("0tNtH7eVTLLZFcpU")
			.setSecretKey("xsLm7BtZlbEbCC0j8LCz3LcGIKpAll");
		producer.send("topic_haitao_logistics-status_prod", "test1", "suborder_1", "{xx}".getBytes());
	}

}
