import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;
import redis.clients.jedis.Tuple;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Text {

    public static void main(String[] args) {

        //1创建jedis对象
        Jedis jedis = new Jedis("192.168.174.188", 6379);
        //指定密码
        jedis.auth("admin");
        //测试连接
        System.out.println(jedis.ping());

        System.out.println("String类型=================");
        jedis.set("name", "你好！");
        System.out.println(jedis.get("name"));
        jedis.mset("a", "aaa", "b", "bbb");
        System.out.println(jedis.mget("a", "b"));



        System.out.println("Hash类型=================");
        jedis.hset("items001", "id", "001");
        jedis.hset("items001", "title", "哇哈哈");
        jedis.hset("items001", "price", "300");




        Map<String, String> map = new HashMap<String, String>();
        map.put("id", "1002");
        map.put("title", "哈根达斯");
        map.put("price", "3000");

        jedis.hmset("items002", map);

        List<String> hmget = jedis.hmget("items002", "id", "title", "price");
        System.out.println(hmget);

        System.out.println("List类型=================");

        jedis.lpush("list001", "a", "b", "c");
        System.out.println(jedis.lrange("list001", 0, -1));
        jedis.rpush("list001", "b", "b", "c", "d", "e", "f");
        System.out.println(jedis.lrange("list001", 0, -1));

        System.out.println(jedis.lpop("list001"));
        System.out.println(jedis.rpop("list001"));
        System.out.println(jedis.lrange("list001", 0, -1));

        System.out.println("set类型=============");

        jedis.sadd("set01", "a", "a", "b", "e", "s", "g");
        Set<String> set = jedis.smembers("set01");
        System.out.println(set);
        System.out.println("中奖号码：" + jedis.spop("set01"));

        jedis.srem("set01", "a");
        set = jedis.smembers("set01");
        System.out.println(set);

        System.out.println("=========zset==========");

        jedis.zadd("zset01", 10000, "哇哈哈");
        Map<String, Double> stringDoubleMap = new HashMap<String, Double>();
        stringDoubleMap.put("哈根达斯", 300.0);
        stringDoubleMap.put("冰水", 1000.0);
        stringDoubleMap.put("脉动", 1300.0);
        stringDoubleMap.put("芬达", 4000.0);


        jedis.zadd("zset01", stringDoubleMap);

        Set<String> zset = jedis.zrange("zset01", 0, -1);
        System.out.println("正序" + zset);
        zset = jedis.zrevrange("zset01", 0, -1);
        System.out.println("倒序" + zset);

        Set<Tuple> tuples = jedis.zrangeWithScores("zset01", 0, -1);
        for (Tuple tuple : tuples) {
            System.out.println(tuple.getElement() + "---" + tuple.getScore());
        }

        System.out.println("=========end==========");

        Set<String> set1 = jedis.keys("*");
        for (String s : set1) {
            System.out.println(s);
        }

        System.out.println(jedis.expire("zset01", 1000));


        System.out.println("=========事务==========");
        Transaction transaction = jedis.multi();
        transaction.set("v", "v");
        transaction.set("l", "l");
        transaction.set("m", "m");
        transaction.incr("v");

        transaction.exec();
        //transaction.discard()//取消
        jedis.close();
    }

}