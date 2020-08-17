package cn.org.foggy.dto;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


@SuppressWarnings("ALL")
@Entity
@Table(name = "time_task")
public class TimeTask {

    @Id
    @GeneratedValue
    private Long id;

    private String taskTime;

    private String status;

    private String eventId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTaskTime() {
        return taskTime;
    }

    public void setTaskTime(String taskTime) {
        this.taskTime = taskTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    @Override
    public String toString() {
        return "TimeTask{" +
                "id=" + id +
                ", taskTime='" + taskTime + '\'' +
                ", status='" + status + '\'' +
                ", eventId='" + eventId + '\'' +
                '}';
    }
}
