package twilb;

import org.junit.Test;

import twitter4j.Twitter;
import twitter4j.TwitterFactory;

public class Test1 {
	@Test
	public void test1() throws Exception {
		Twitter twitter = new TwitterFactory().getInstance();
		twitter.updateStatus("twitter4jのテスト。");
	}
}
