import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

public class Text02 {

    public static void main(String[] args) {

        Jedis jedis = new Jedis("192.168.174.188", 6379);

        jedis.auth("admin");
        /*测试连接*/
        System.out.println(jedis.ping());


        /*
        * //订阅消息
        JedisPubSub sub = new JedisPubSub() {
            @Override
            public void onMessage(String channel, String message) {
                System.out.println("--->监听到消息:");
                System.out.println(channel+"-"+message);
            }
        };
        jedis.subscribe(sub,"hotnews");
        jedis.close();
        *
        * */
        JedisPubSub jedisPubSub = new JedisPubSub() {
            @Override
            public void onMessage(String channel, String message) {
                System.out.println("--->监听到消息:");
                System.out.println(channel + "-" + message);
            }
        };

        jedis.subscribe(jedisPubSub, "ch");
        jedis.close();
    }
}
