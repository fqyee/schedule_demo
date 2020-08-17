package cn.org.foggy.mapper;

import cn.org.foggy.dto.TimeTask;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface TimeTaskMapper {

    @Select(value = "select * from time_task where status = '0'")
    List<TimeTask> selectTimeTaskList();

    @Update("update time_task set status = '1' where id = ${id}")
    int updateTimeTask(@Param("id") Long id);
}
