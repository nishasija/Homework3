package core.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import core.api.IAdmin;
import core.api.IInstructor;
import core.api.IStudent;
import core.api.impl.Admin;
import core.api.impl.Instructor;
import core.api.impl.Student;

public class TestInstructor {
	
	private IAdmin admin;
	private IInstructor instructor;
	private IStudent student;
	
	@Before
    public void setup() {
		this.admin = new Admin();
        this.instructor = new Instructor();
        this.student = new Student();
    }

	//base test -- hw is added 
    @Test
    public void testValidHW() {
        this.admin.createClass("ECS161", 2017, "Instructor", 15);
    		this.instructor.addHomework("Instructor", "ECS161", 2017, "HW1");
    		assertTrue(this.instructor.homeworkExists("ECS161", 2017, "HW1"));
    }
    
    //test case for assigning hw for a class in different year 
    public void invalidYear() {
        this.admin.createClass("ECS161", 2017, "Instructor", 15);
		this.instructor.addHomework("Instructor", "ECS161", 2016, "HW1");
		assertFalse(this.instructor.homeworkExists("ECS161", 2017, "HW1"));
    }
    
    //test case for class that does not exist 
    public void invalidClass() {
    		this.instructor.addHomework("Instructor", "ECS161", 2016, "HW1");
		assertFalse(this.instructor.homeworkExists("ECS161", 2017, "HW1"));
    }
    
    //test case for a hw that does not exist   
    @Test
    public void testValidHW2() {
        this.admin.createClass("ECS161", 2017, "Instructor", 15);
		this.instructor.addHomework("Instructor", "ECS161", 2017, "HW1");
		assertFalse(this.instructor.homeworkExists("ECS161", 2017, "HW2"));
    }
    
    //test for when a different instructor assigns hw for the same class 
    @Test 
	public void testInstructor() {
        this.admin.createClass("ECS161", 2017, "Instructor", 15);
        this.instructor.addHomework("Instructor", "ECS161", 2017, "HW");
		this.instructor.addHomework("Instructor2", "ECS161", 2017, "HW");
		assertFalse(this.instructor.homeworkExists("ECS161", 2017, "HW"));
	}
    
    //base case for assigned grade 
    @Test 
    public void testGradedHW() {
    		this.admin.createClass("ECS161", 2017, "Instructor", 15);
		this.student.registerForClass("Student", "ECS161", 2017);
    		this.instructor.addHomework("Instructor", "ECS161", 2017, "HW1");
    		this.student.submitHomework("Student", "HW1", "answer", "ECS161", 2017);
    		this.instructor.assignGrade("Instructor1", "ECS161", 2017, "HW1", "Student", 90);
    		assertEquals(new Integer(90), this.instructor.getGrade("ECS161", 2017, "HW1", "Student"));
    }
    
    //test assigned grade for student who did not submit hw
    @Test 
    public void testGradedHW2() {
    		this.admin.createClass("ECS161", 2017, "Instructor", 15);
		this.student.registerForClass("Student", "ECS161", 2017);
    		this.instructor.addHomework("Instructor", "ECS161", 2017, "HW1");
    		this.instructor.assignGrade("Instructor1", "ECS161", 2017, "HW1", "Student", 90);
    		assertEquals(new Integer(90), this.instructor.getGrade("ECS161", 2017, "HW1", "Student"));
    }
    

    //test assigned grade for a student that does not exist
    @Test 
    public void testGradedHW3() {
    		this.admin.createClass("ECS161", 2017, "Instructor", 15);
    		this.instructor.addHomework("Instructor", "ECS161", 2017, "HW1");
    		this.instructor.assignGrade("Instructor1", "ECS161", 2017, "HW1", "Student", 90);
    		assertNull(this.instructor.getGrade("ECS161", 2017, "HW1", "Student"));
    }

}
