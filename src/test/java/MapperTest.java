import cn.org.foggy.TimeTaskApplication;
import cn.org.foggy.dto.TimeTask;
import cn.org.foggy.mapper.TimeTaskMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {TimeTaskApplication.class})
public class MapperTest {

    @Autowired
    TimeTaskMapper timeTaskMapper;

    @Test
    public void testSelect() {
        List<TimeTask> timeTasks = timeTaskMapper.selectTimeTaskList();
        for (TimeTask timeTask : timeTasks) {
            System.out.println(timeTask);
        }
    }
}
