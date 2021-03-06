package com.sohu.tv.mq.cloud.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.sohu.tv.mq.cloud.bo.ProducerTotalStat;
/**
 * 生产者总计统计dao
 * 
 * @author yongfeigao
 * @date 2018年9月12日
 */
public interface ProducerTotalStatDao {
    /**
     * 插入记录
     */
    @Options(useGeneratedKeys = true, keyProperty = "s.id")
    @Insert("insert into producer_total_stat(producer,client,percent90,percent99,"
            + "avg,count,stat_time,create_date,create_time) "
            + "values(#{s.producer},#{s.client},#{s.percent90},#{s.percent99},#{s.avg},#{s.count},#{s.statTime},"
            + "#{s.createDate},#{s.createTime})")
    public Integer insert(@Param("s")ProducerTotalStat producerTotalStat);
    
    /**
     * 根据日期查询producer记录
     */
    @Select("select * from producer_total_stat where create_date = #{createDate} and producer = #{producer}")
    public List<ProducerTotalStat> selectByDate(@Param("producer")String producer, @Param("createDate")int createDate);
    
    /**
     * 查看producer是否存在
     */
    @Select("select 1 from producer_total_stat where producer = #{producer} limit 1")
    public Boolean selectByProducer(@Param("producer")String producer);
    
    /**
     * 删除
     * @return
     */
    @Delete("delete from producer_total_stat where create_date=#{createDate}")
    public Integer delete(@Param("createDate")int createDate);
    
    /**
     * 查询有异常的记录
     * @param createDate
     * @return
     */
    @Select("select total.* from producer_total_stat total, producer_stat stat "
            + "where total.id = stat.total_id and total.create_date = #{createDate} and total.create_time >= #{createTime} "
            + "and stat.exception is not null")
    public List<ProducerTotalStat> selectExceptionList(@Param("createDate")int createDate, @Param("createTime")String createTime);
}
