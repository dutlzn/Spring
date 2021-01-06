package com.demo.ann;

@CourseInfoAnnotation(courseName = "java成神之路", courseTag = "Java", courseProfile = "进阶")
public class CourseTest {
    @PersonInfoAnnotation(name = "小明", language = {"C++", "Java", "Python"})
    private String author;
    @CourseInfoAnnotation(courseName = "Python成神之路", courseTag = "Python", courseProfile = "进阶", courseIndex = 100)
    public void getCourseInfo() {
        
    }
}
