package leetcode;

import utils.Assertions;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author: kkyeer
 * @Description: 355. 设计推特
 * 设计一个简化版的推特(Twitter)，可以让用户实现发送推文，关注/取消关注其他用户，能够看见关注人（包括自己）的最近十条推文。你的设计需要支持以下的几个功能：
 * <p>
 * postTweet(userId, tweetId): 创建一条新的推文
 * getNewsFeed(userId): 检索最近的十条推文。每个推文都必须是由此用户关注的人或者是用户自己发出的。推文必须按照时间顺序由最近的开始排序。
 * follow(followerId, followeeId): 关注一个用户
 * unfollow(followerId, followeeId): 取消关注一个用户
 * <p>
 * 示例:
 * <p>
 * Twitter twitter = new Twitter();
 * <p>
 * // 用户1发送了一条新推文 (用户id = 1, 推文id = 5).
 * twitter.postTweet(1, 5);
 * <p>
 * // 用户1的获取推文应当返回一个列表，其中包含一个id为5的推文.
 * twitter.getNewsFeed(1);
 * <p>
 * // 用户1关注了用户2.
 * twitter.follow(1, 2);
 * <p>
 * // 用户2发送了一个新推文 (推文id = 6).
 * twitter.postTweet(2, 6);
 * <p>
 * // 用户1的获取推文应当返回一个列表，其中包含两个推文，id分别为 -> [6, 5].
 * // 推文id6应当在推文id5之前，因为它是在5之后发送的.
 * twitter.getNewsFeed(1);
 * <p>
 * // 用户1取消关注了用户2.
 * twitter.unfollow(1, 2);
 * <p>
 * // 用户1的获取推文应当返回一个列表，其中包含一个id为5的推文.
 * // 因为用户1已经不再关注用户2.
 * twitter.getNewsFeed(1);
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/design-twitter
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 * @Date:Created in  2020-4-13 18:39
 * @Modified By:
 */
class Twitter {
    private long index = 0;
    //    uid => [followee1,followee2]
    private Map<Integer, Set<Integer>> follows = new HashMap<>();

    //    uid => [{'20200101':1},{'20200202'}]
    private Map<Integer, List<Tweet>> tweets = new HashMap<>();

    /**
     * Initialize your data structure here.
     */
    public Twitter() {

    }

    private class Tweet{
        Tweet(Integer id,long index){
            this.index = index;
            this.id = id;
        }
        long index;
        Integer id;
    }

    /**
     * Compose a new tweet.
     */
    public void postTweet(int userId, int tweetId) {
        List<Tweet> tweetList = tweets.getOrDefault(userId, new LinkedList<>());
        tweetList.add(new Tweet(tweetId, index++));
        if (tweetList.size() > 10) {
            tweetList.remove(0);
        }
        tweets.put(userId, tweetList);
    }

    /**
     * Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent.
     */
    public List<Integer> getNewsFeed(int userId) {
        List<Tweet> result = new ArrayList<>();
        List<Tweet> self = tweets.get(userId);
        if (self != null) {
            result.addAll(self);
            Collections.reverse(result);
        }
        Set<Integer> followList = follows.get(userId);
        if (followList != null) {
            for (Integer follow : followList) {
                List<Tweet> tweetList = tweets.get(follow);
                if (tweetList != null) {
                    result.addAll(tweetList);
                    result.sort((o1,o2)-> (int) (o2.index-o1.index));
                    if (result.size() > 10) {
                        result = result.subList(0, 10);
                    }
                }
            }
        }
        return result.stream().map(t -> t.id).collect(Collectors.toList());
    }


    /**
     * Follower follows a followee. If the operation is invalid, it should be a no-op.
     */
    public void follow(int followerId, int followeeId) {
        if (followerId == followeeId) {
            return;
        }
        Set<Integer> followSet = follows.getOrDefault(followerId, new HashSet<>());
        followSet.add(followeeId);
        follows.put(followerId, followSet);
    }

    /**
     * Follower unfollows a followee. If the operation is invalid, it should be a no-op.
     */
    public void unfollow(int followerId, int followeeId) {
        Set<Integer> followSet = follows.get(followerId);
        if (followSet != null) {
            followSet.remove(followeeId);
        }

    }

    public static void main(String[] args) {
        Twitter twitter = new Twitter();
        twitter.postTweet(1, 5);
        Assertions.assertTrue(1 == twitter.getNewsFeed(1).size());
        Assertions.assertTrue(5 == twitter.getNewsFeed(1).get(0));
        twitter.follow(1, 2);
        twitter.postTweet(2, 6);
        Assertions.assertTrue(2 == twitter.getNewsFeed(1).size());
        Assertions.assertTrue(6 == twitter.getNewsFeed(1).get(0));
        Assertions.assertTrue(5 == twitter.getNewsFeed(1).get(1));
        twitter.unfollow(1, 2);
        Assertions.assertTrue(1 == twitter.getNewsFeed(1).size());
        Assertions.assertTrue(5 == twitter.getNewsFeed(1).get(0));

        twitter = new Twitter();
        twitter.postTweet(1, 5);
        twitter.postTweet(1, 3);
        Assertions.assertTrue(2 == twitter.getNewsFeed(1).size());
        Assertions.assertTrue(3 == twitter.getNewsFeed(1).get(0));
        Assertions.assertTrue(5 == twitter.getNewsFeed(1).get(1));
    }
}

/**
 * Your Twitter object will be instantiated and called as such:
 * Twitter obj = new Twitter();
 * obj.postTweet(userId,tweetId);
 * List<Integer> param_2 = obj.getNewsFeed(userId);
 * obj.follow(followerId,followeeId);
 * obj.unfollow(followerId,followeeId);
 */