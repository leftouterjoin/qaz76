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

	@Test
	public void test2() throws Exception {
		Twitter twitter = new TwitterFactory().getInstance();

		long[] followersIDs = twitter.getFollowersIDs(1).getIDs();

		for (long followers : followersIDs) {
			if (twitter.showFriendship(132737336, followers)
					.isTargetFollowingSource()
					&& !twitter.showFriendship(132737336, followers)
							.isSourceFollowingTarget()
					&& twitter.showFriendship(132737336, followers)
							.isSourceNotificationsEnabled()) {
				//twitter.createFriendship(followers);
			}
		}
	}
}
