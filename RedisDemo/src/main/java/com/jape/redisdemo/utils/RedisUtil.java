package com.jape.redisdemo.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.*;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.connection.stream.RecordId;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Component
public class RedisUtil {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

//=============================公共方法============================

    /**
     * 正则表达式获取匹配的所有key
     */
    public Set<String> getKeysByRegx(String regx) {
        return redisTemplate.keys(regx);
    }

    /**
     * 根据key批量删除一个或多个
     */
    public long batchDel(String... keys) {
        List<String> strings = Arrays.asList(keys);
        return redisTemplate.delete(strings);
    }

    /**
     * 判断key是否存在
     *
     * @param key 键
     * @return true存在 false不存在
     */
    public boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 根据key 获取过期时间
     *
     * @param key 不能为null
     * @return 时间(秒) 返回0代表为永久有效
     */
    public long getExpire(String key) {
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }

    /**
     * 指定缓存失效时间
     *
     * @param key  键
     * @param time 时间(秒,-1永不过期)
     * @return true成功 false失败
     */
    public boolean setExpire(String key, long time) {
        try {
            if (time > 0) {
                redisTemplate.expire(key, time, TimeUnit.SECONDS);
            } else if (time == -1) {
                redisTemplate.persist(key);
            } else {
                return false;
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


//============================String数据类型操作=============================

    /**
     * String缓存获取
     *
     * @param key 键
     * @return 值
     */
    public Object getString(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * String缓存放入，带过期时间（秒）
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒) time要大于0 如果time小于等于0 将设置无限期
     * @return true成功 false失败
     */
    public boolean setString(String key, Object value, long... time) {
        try {
            if (time.length > 0 && time[0] > 0) {
                redisTemplate.opsForValue().set(key, value, time[0], TimeUnit.SECONDS);
            } else {
                redisTemplate.opsForValue().set(key, value);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * String不存在则存入新值，带过期时间（秒）
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒) time要大于0 如果time小于等于0 将设置无限期
     * @return true成功 false失败
     */
    public boolean setStringNx(String key, Object value, long time) {
        //如果不存在，则设置
        Boolean absent = redisTemplate.opsForValue().setIfAbsent(key, value, time, TimeUnit.SECONDS);
        //如果存在，则设置
        //Boolean present = redisTemplate.opsForValue().setIfPresent(key, value, time, TimeUnit.SECONDS);
        return absent;
    }

    /**
     * String递增（减）
     *
     * @param key   键
     * @param delta （数学符号Δ）要增加几(负数即减几)
     * @return 计算后结果
     */
    public long incString(String key, long delta) {
        return redisTemplate.opsForValue().increment(key, delta);
    }

    /**
     * 位图放入操作，不放默认是false
     *
     * @param key    键
     * @param offset 偏移量
     * @param value  布尔值
     * @return 失败为null
     */
    public boolean setBit(String key, int offset, boolean value) {
        Boolean bit = redisTemplate.opsForValue().setBit(key, offset, value);
        return bit;
    }

    /**
     * 获取位图key的特定位置比特位
     *
     * @param key    键
     * @param offset 偏移量
     * @return value
     */
    public boolean getBit(String key, int offset) {
        Boolean bit = redisTemplate.opsForValue().getBit(key, offset);
        return bit;
    }


//============================Hash数据类型操作=============================

    /**
     * Hash放入对象（反射）
     *
     * @param key 键
     * @param obj 值对象
     * @return true 成功 false 失败
     */
    public boolean setHashObj(String key, Object obj) {
        Map<String, Object> map = ReflexUtil.obj2Map(obj);
        try {
            redisTemplate.opsForHash().putAll(key, map);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Hash获取Key对应的所有键值,并返回对应的类实例（反射）
     *
     * @param key   键
     * @param clazz 预转化成的类
     * @return 对应的多个键值
     */
    public Object getHashObj(String key, Class<?> clazz) {
        Map<Object, Object> map = redisTemplate.opsForHash().entries(key);
        Object obj = ReflexUtil.map2Obj(map, clazz);
        return obj;
    }

    /**
     * 向一张hash表中放入数据,如果不存在将创建
     *
     * @param key   键
     * @param item  项
     * @param value 值
     * @return true 成功 false失败
     */
    public boolean setHashItem(String key, String item, Object value) {
        try {
            redisTemplate.opsForHash().put(key, item, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


//============================Set数据类型操作==============================

    /**
     * 根据key获取Set中的所有值
     *
     * @param key 键
     * @return 整个Set
     */
    public Set<Object> getSet(String key) {
        return redisTemplate.opsForSet().members(key);
    }

    /**
     * 将数据放入set缓存
     *
     * @param key    键
     * @param values 值 可以是多个
     * @return 成功个数
     */
    public long addToSet(String key, Object... values) {
        return redisTemplate.opsForSet().add(key, values);
    }

    /**
     * 移除值Set中为的value
     *
     * @param key    键
     * @param values 值 可以是多个
     * @return 移除的个数
     */
    public long removeFromSet(String key, Object... values) {
        return redisTemplate.opsForSet().remove(key, values);
    }

    /**
     * 根据value从一个set中查询,是否存在
     *
     * @param key   键
     * @param value 值
     * @return true 存在 false不存在
     */
    public boolean hasValueInSet(String key, Object value) {
        return redisTemplate.opsForSet().isMember(key, value);
    }

    /**
     * 获取set缓存的长度
     *
     * @param key 键
     * @return Set大小
     */
    public long getSetSize(String key) {
        return redisTemplate.opsForSet().size(key);
    }

    /**
     * 计算多个Set的交集
     *
     * @param keys 键
     * @return 交集Set
     */
    public Set<Object> getSetsIntersect(String... keys) {
        return redisTemplate.opsForSet().intersect(Arrays.asList(keys));
    }

    /**
     * 计算多个Set的并集
     *
     * @param keys 键
     * @return 并集Set
     */
    public Set<Object> getSetsUnion(String... keys) {
        return redisTemplate.opsForSet().union(Arrays.asList(keys));
    }

    /**
     * 计算多个Set的补集
     *
     * @param keys 键
     * @return 补集Set
     */
    public Set<Object> getSetsDiff(String... keys) {
        return redisTemplate.opsForSet().difference(Arrays.asList(keys));
    }


//============================ZSet(SortedSet)有序集合数据类型操作==============================

    /**
     * 获取ZSet中索引范围内的值(有序)
     *
     * @param key 键
     * @return 整个Set
     */
    public Set<Object> getZSetByIndexRange(String key, long start, long end) {
        Set<Object> range = redisTemplate.opsForZSet().range(key, start, end);
        //降序
        Set<Object> reverseRange = redisTemplate.opsForZSet().reverseRange(key, start, end);
        //带分数
        Set<ZSetOperations.TypedTuple<Object>> tuples = redisTemplate.opsForZSet().rangeByScoreWithScores(key, start, end);
        return range;
    }

    /**
     * 获取ZSet中指定分数范围的值(有序)
     *
     * @param key 键
     * @return 整个Set
     */
    public Set<Object> getZSetByScoreRange(String key, double minScore, double maxScore) {
        Set<Object> range = redisTemplate.opsForZSet().rangeByScore(key, minScore, maxScore);
        //降序
        Set<Object> reverseRange = redisTemplate.opsForZSet().reverseRangeByScore(key, minScore, maxScore);
        //带分数
        Set<ZSetOperations.TypedTuple<Object>> tuples = redisTemplate.opsForZSet().rangeByScoreWithScores(key, minScore, maxScore);
        return range;
    }

    /**
     * 将数据放入Zset缓存
     *
     * @param key   键
     * @param value 值 可以是多个
     * @param score 排序分数
     * @return true 成功
     */
    public boolean addToZSet(String key, Object value, double score) {
        return redisTemplate.opsForZSet().add(key, value, score);
    }

    /**
     * 移除值ZSet中为的value
     *
     * @param key    键
     * @param values 值 可以是多个
     * @return 移除的个数
     */
    public long removeFromZSet(String key, Object... values) {
        return redisTemplate.opsForZSet().remove(key, values);
    }

    /**
     * ZSet分数递增（减）
     *
     * @param key   键
     * @param value 项
     * @param delta （数学符号Δ）要增加几(负数即减几)
     * @return 计算后的分数
     */
    public double incZSetScore(String key, Object value, double delta) {
        return redisTemplate.opsForZSet().incrementScore(key, value, delta);
    }

    /**
     * ZSet中查询value的排名
     *
     * @param key   键
     * @param value 项
     * @return 计算后的分数
     */
    public double getZSetRank(String key, Object value) {
        return redisTemplate.opsForZSet().rank(key, value);
    }

    /**
     * 获取Zset成员数
     *
     * @param key 键
     * @return ZSet大小
     */
    public long getZSetCard(String key) {
        return redisTemplate.opsForZSet().zCard(key);
    }


//============================List数据类型操作==============================

    /**
     * 获取list中指定索引的值
     *
     * @param key   键
     * @param index 索引  index>=0时， 0 表头，1 第二个元素，依次类推；index<0时，-1，表尾，-2倒数第二个元素，依次类推
     * @return 索引值
     */
    public Object getListByIndex(String key, long index) {
        return redisTemplate.opsForList().index(key, index);
    }

    /**
     * 获取list指定索引范围的内容
     *
     * @param key   键
     * @param start 开始
     * @param end   结束  0 到 -1代表所有值
     * @return List结果
     */
    public List<Object> getListByIndexRange(String key, long start, long end) {
        return redisTemplate.opsForList().range(key, start, end);
    }

    /**
     * list右部（尾）放入项目值
     *
     * @param key    键
     * @param values 多个值
     * @return true 成功
     */
    public boolean addToListRight(String key, Object... values) {
        try {
            redisTemplate.opsForList().rightPushAll(key, values);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * list右部（尾）移出项目值
     *
     * @param key 键
     * @return 项目value
     */
    public Object moveOutFromListRight(String key) {
        return redisTemplate.opsForList().rightPop(key);
    }

    /**
     * list左部（头）放入项目值
     *
     * @param key    键
     * @param values 多个值
     * @return true 成功
     */
    public boolean addToListLeft(String key, Object... values) {
        try {
            //redisTemplate.opsForList().leftPushAll(key, Arrays.asList(values));
            redisTemplate.opsForList().leftPushAll(key, values);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * list左部（头）移出项目值
     *
     * @param key 键
     * @return 项目value
     */
    public Object moveOutFromListLeft(String key) {
        return redisTemplate.opsForList().leftPop(key);
    }

    /**
     * 修改list中的相应索引的某条数据
     *
     * @param key   键
     * @param index 索引
     * @param value 值
     * @return true 成功
     */
    public boolean updateListByIndex(String key, long index, Object value) {
        try {
            redisTemplate.opsForList().set(key, index, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 移除N个值为value的项目
     *
     * @param key   键
     * @param count 移除多少个( >0 从头删n个;0 所有;<0 从尾删n个 )
     * @param value 值
     * @return 移除的个数
     */
    public long removeListByValue(String key, long count, Object value) {
        return redisTemplate.opsForList().remove(key, count, value);
    }

    /**
     * 获取list缓存的长度
     *
     * @param key 键
     * @return list的长度
     */
    public long getListSize(String key) {
        return redisTemplate.opsForList().size(key);
    }

//============================Geo地理位置数据类型操作(Redis 3.2)=================================
    /*
     geoadd：添加地理位置的坐标。
     geopos：获取地理位置的坐标。
     geodist：计算两个位置之间的距离。
     georadius：根据用户给定的经纬度坐标来获取指定范围内的地理位置集合。
     georadiusbymember：根据储存在位置集合里面的某个地点获取指定范围内的地理位置集合。
     geohash：返回一个或多个位置对象的 geohash 值。
     */

    /**
     * Geo放入成员位置
     *
     * @param key       键
     * @param longitude 经度
     * @param latitude  纬度
     * @param member    成员名
     * @return true 成功
     */
    public boolean addToGeo(String key, double longitude, double latitude, String member) {
        try {
            Point point = new Point(longitude, latitude);
            redisTemplate.opsForGeo().add(key, point, member);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Geo得到多个成员的经纬度
     *
     * @param key     键
     * @param members 成员名
     * @return 经纬点List
     */
    public List<Point> getGeoMembersPos(String key, String... members) {
        return redisTemplate.opsForGeo().position(key, members);
    }

    /**
     * Geo得到两个个成员的距离
     *
     * @param key     键
     * @param member1 成员名1
     * @param member2 成员名2
     * @return 成员的距离（米）
     */
    public double getGeoMembersDist(String key, String member1, String member2) {
        Distance distance = redisTemplate.opsForGeo().distance(key, member1, member2);
        double normalizedValue = distance.getNormalizedValue();
        double value = distance.getValue();
        Metric metric = distance.getMetric();
        return value;
    }

    /**
     * Geo获取成员附近的成员名
     *
     * @param key    键
     * @param member 成员名
     * @param radius 半径
     * @return 成员名List
     */
    public Object getGeoMemRadius(String key, String member, double radius) {
        GeoResults<RedisGeoCommands.GeoLocation<Object>> geoLocationGeoResults = redisTemplate.opsForGeo().radius(key, member, radius);
        List<Object> collect = geoLocationGeoResults.getContent().stream()
                .map(geoResult -> geoResult.getContent().getName())
                .collect(Collectors.toList());
        return collect;
    }

    /**
     * Geo获取经纬点附近的成员名
     *
     * @param key     键
     * @param centerX 经度
     * @param centerY 纬度
     * @param radius  半径
     * @return 成员名List
     */
    public Object getGeoPointRadius(String key, double centerX, double centerY, double radius) {
        Circle within = new Circle(centerX, centerY, radius);
        GeoResults<RedisGeoCommands.GeoLocation<Object>> geoLocationGeoResults = redisTemplate.opsForGeo().radius(key, within);
        List<Object> collect = geoLocationGeoResults.getContent().stream()
                .map(geoResult -> geoResult.getContent().getName())
                .collect(Collectors.toList());
        return collect;
    }

    /**
     * Geo移除成员
     *
     * @param key     键
     * @param members 成员
     * @return 移除成功数
     */
    public long removeGeoMembers(String key, String members) {
        return redisTemplate.opsForGeo().remove(key, members);
    }


//============================Stream消息队列数据类型操作(Redis 5.0)========================================================
    /*
    Redis Stream 提供了消息的持久化和主备复制功能，可以让任何客户端访问任何时刻的数据，并且能记住每一个客户端的访问位置，还能保证消息不丢失。
    消息队列相关命令：
        XADD - 添加消息到末尾
        XTRIM - 对流进行修剪，限制长度
        XDEL - 删除消息
        XLEN - 获取流包含的元素数量，即消息长度
        XRANGE - 获取消息列表，会自动过滤已经删除的消息
        XREVRANGE - 反向获取消息列表，ID 从大到小
        XREAD - 以阻塞或非阻塞方式获取消息列表
    消费者组相关命令：
        XGROUP CREATE - 创建消费者组
        XREADGROUP GROUP - 读取消费者组中的消息
        XACK - 将消息标记为"已处理"
        XGROUP SETID - 为消费者组设置新的最后递送消息ID
        XGROUP DELCONSUMER - 删除消费者
        XGROUP DESTROY - 删除消费者组
        XPENDING - 显示待处理消息的相关信息
        XCLAIM - 转移消息的归属权
        XINFO - 查看流和消费者组的相关信息；
        XINFO GROUPS - 打印消费者组的信息；
        XINFO STREAM - 打印流信息
     */

    /**
     * Stream生产消息
     *
     * @param key 键
     * @param map 消息体
     * @return 消息放入ID
     */
    public String addToStream(String key, Map<String, Object> map) {
        RecordId recordId = redisTemplate.opsForStream().add(key, map);
        return recordId.getValue();
    }


//============================HyperLogLog基数计算数据类型操作(Redis 2.8.9)========================================================
    /*
    1 PFADD key element [element ...]
        添加指定元素到 HyperLogLog 中。
    2 PFCOUNT key [key ...]
        返回给定 HyperLogLog 的基数估算值。
    3 PFMERGE destkey sourcekey [sourcekey ...]
        将多个 HyperLogLog 合并为一个 HyperLogLog
     */


//============================pub/sub发布订阅类型操作========================================================
    /*
    1	PUBLISH channel message
    将信息发送到指定的频道。

    2	SUBSCRIBE channel [channel ...]
    订阅给定的一个或多个频道的信息。
    3	UNSUBSCRIBE [channel [channel ...]]
    指退订给定的频道。

    4	PSUBSCRIBE pattern [pattern ...]
    订阅一个或多个符合给定模式的频道。
    5	PUNSUBSCRIBE [pattern [pattern ...]]
    退订所有给定模式的频道。

    6	PUBSUB subcommand [argument [argument ...]]
    查看订阅与发布系统状态。
     */
}
