package comp3350.grs.tests.objects;

import junit.framework.TestCase;

public class CourseTest extends TestCase
{
	public CourseTest(String arg0)
	{
		super(arg0);
	}

	public void testCourse1()
	{
		Course course;

		System.out.println("\nStarting testCourse");

		course = new Course("12345", "Software Development");
		assertNotNull(course);
		assertTrue("12345".equals(course.getCourseID()));
		assertTrue("Software Development".equals(course.getCourseName()));

		System.out.println("Finished testCourse");
	}
}