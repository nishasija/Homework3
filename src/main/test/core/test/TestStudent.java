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

public class TestStudent {
	
	private IAdmin admin;
	private IInstructor instructor;
	private IStudent student;
	
	@Before
    public void setup() {
		this.admin = new Admin();
        this.instructor = new Instructor();
        this.student = new Student();
    }
	
	//base case for valid registration
	@Test 
	public void validRegistration() {
		this.admin.createClass("Test", 2017, "Instructor", 15);
		this.student.registerForClass("Student", "Test", 2017);
		assertTrue(this.student.isRegisteredFor("Student", "Test", 2017));
	}
	
	//test case for class that does not exist
	@Test
	public void invalidRegistration() {
		this.student.registerForClass("Student", "Test", 2017);
		assertFalse(this.student.isRegisteredFor("Student", "Test", 2017));
	}

	//test case for full capacity
	@Test
	public void invalidCapacity() {
		this.admin.createClass("Test", 2017, "Instructor", 1);
		this.student.registerForClass("Student", "Test", 2017);
		assertFalse(this.student.isRegisteredFor("Student", "Test", 2017));
	}
	
	//base case for drop class
	@Test
	public void testDropClass() {
		this.admin.createClass("Test", 2017, "Instructor", 15);
		this.student.registerForClass("Student", "Test", 2017);
		this.student.dropClass("Student", "Test", 2017);
		assertFalse(this.student.isRegisteredFor("Student", "Test", 2017));
	}
	
	//base case for submit hw
	@Test
	public void testSubmitHW() {
		this.admin.createClass("ECS161", 2017, "Instructor", 15);
		this.student.registerForClass("Student", "ECS161", 2017);
		this.instructor.addHomework("Instructor", "ECS161", 2017, "HW1");
		this.student.submitHomework("Student", "HW1", "answer", "ECS161", 2017);
		assertTrue(this.student.hasSubmitted("Student", "HW1", "ECS161", 2017));
	}
	
	//test case for unassigned hw 
	@Test
	public void testSubmitHW2() {
		this.admin.createClass("ECS161", 2017, "Instructor", 15);
		this.student.registerForClass("Student", "ECS161", 2017);
		this.student.submitHomework("Student", "HW1", "answer", "ECS161", 2017);
		assertFalse(this.student.hasSubmitted("Student", "HW1", "ECS161", 2017));
	}
	
	//test case for student not registered 
	@Test
	public void testSubmitHW3() {
		this.admin.createClass("ECS161", 2017, "Instructor", 15);
		this.instructor.addHomework("Instructor", "ECS161", 2017, "HW1");
		this.student.submitHomework("Student", "HW1", "answer", "ECS161", 2017);
		assertFalse(this.student.hasSubmitted("Student", "HW1", "ECS161", 2017));
	}
	
	//test case for different year
	@Test
	public void testSubmitHW4() {
		this.admin.createClass("ECS161", 2018, "Instructor", 15);
		this.instructor.addHomework("Instructor", "ECS161", 2018, "HW1");
		this.student.registerForClass("Student", "ECS161", 2017);
		this.student.submitHomework("Student", "HW1", "answer", "ECS161", 2017);
		assertFalse(this.student.hasSubmitted("Student", "HW1", "ECS161", 2017));
	}
}
