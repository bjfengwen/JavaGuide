package jdk8;

import java.io.Serializable;
import java.util.List;

/**
 * @Description
 * @Author fengwen
 * @Date 2020-11-04
 */
public class StudentDTO implements Serializable {

    public StudentDTO() {
    }
    public StudentDTO(Long id, String code, String name, String sex, Double scope,
                     List<Course> learningCources) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.sex = sex;
        this.scope = scope;
        this.learningCources = learningCources;
    }
    private Long id;
    /**
     * 学号 唯一标识
     */
    private String code;
    /**
     * 学生名字
     */
    private String name;
    /**
     * 性别
     */
    private String sex;
    /**
     * 分数
     */
    private Double scope;
    /**
     * 要学习的课程
     */
    private List<Course> learningCources;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Double getScope() {
        return scope;
    }

    public void setScope(Double scope) {
        this.scope = scope;
    }

    public List<Course> getLearningCources() {
        return learningCources;
    }

    public void setLearningCources(List<Course> learningCources) {
        this.learningCources = learningCources;
    }
}
