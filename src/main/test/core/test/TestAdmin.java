package core.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import core.api.IAdmin;
import core.api.IStudent;
import core.api.impl.Admin;
import core.api.impl.Student;

public class TestAdmin {
	private IAdmin admin;
	private IStudent student;

    @Before
    public void setup() {
        this.admin = new Admin();
        this.student = new Student();
    }

    //the first is a base test
    @Test
    public void testMakeClass() {
        this.admin.createClass("Test", 2017, "Instructor", 15);
        assertTrue(this.admin.classExists("Test", 2017));
    }
    
    //testing for a different year 
    @Test
    public void testMakeClass2() {
        this.admin.createClass("Test", 2016, "Instructor", 15);
        assertFalse(this.admin.classExists("Test", 2016));
    }
    
    @Test
    public void testUnique() {
    		this.admin.createClass("ECS161", 2017, "Instructor", 15);
    		this.admin.createClass("ECS161", 2017, "Instructor2", 15);
    		assertEquals("Instructor", this.admin.getClassInstructor("ECS161", 2017));
    }
    
    @Test
    public void instructorCourseLimit() {
        this.admin.createClass("Test", 2017, "Instructor", 15);
        this.admin.createClass("Test1", 2017, "Instructor", 15);
        assertTrue(this.admin.classExists("Test1", 2017));
    }
    
    @Test
    public void instructorCourseLimit2() {
    		this.admin.createClass("Test", 2017, "Instructor", 15);
    		this.admin.createClass("Test1", 2017, "Instructor", 15);
    		this.admin.createClass("Test3", 2017, "Instructor", 15);
    		assertFalse(this.admin.classExists("Test3", 2017));
    }
    
    @Test
    public void invalidClassCapacity() {
		this.admin.changeCapacity("Test", 2017, 2);
		assertEquals(15,this.admin.getClassCapacity("Test", 2017));
    }
    
    
    @Test
    public void cornerCaseCapacity() {
    		this.admin.createClass("Test", 2017, "Instructor", -1);
    		assertFalse(this.admin.classExists("Test", 2017));	
    }
    
    @Test 
    public void zeroCapacity() {
    		this.admin.createClass("Test", 2017, "Instructor", 0);
    		assertFalse(this.admin.classExists("Test", 2017));
    }
    
    @Test 
    public void zeroCapacity2() {
    		this.admin.createClass("Test", 2017, "Instructor", 15);
    		this.admin.changeCapacity("Test", 2017, 0);
    		assertEquals(15,this.admin.getClassCapacity("Test", 2017));
    }
    
    @Test
    public void testCapacity() {
    		this.admin.createClass("Test", 2017, "Instructor", 1);
    		this.student.registerForClass("Student", "Test", 2017);
    		this.admin.changeCapacity("Test", 2017, 2);
    		assertEquals(2,this.admin.getClassCapacity("Test", 2017));
    }
    
    @Test
    public void testCapacity2() {
    		this.admin.createClass("Test", 2017, "Instructor", 15);
		this.student.registerForClass("Student", "Test", 2017);
		assertEquals(15,this.admin.getClassCapacity("Test", 2017));
	}
    
    public void testCapacity3() {
    		this.admin.createClass("Test", 2017, "Instructor", 1);
		this.student.registerForClass("Student1", "Test", 2017);
		this.student.registerForClass("Student2", "Test", 2017);
		this.admin.changeCapacity("Test", 2017, 2);
		assertEquals(2,this.admin.getClassCapacity("Test", 2017));
    }
    
}
