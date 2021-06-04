package comp3350.grs.persistence;

import java.util.ArrayList;
import java.util.List;

import comp3350.grs.application.Main;
import comp3350.grs.objects.User;


public class DataAccessStub
{
	private String dbName;
	private String dbType = "stub";

	private ArrayList<User> users;

	public DataAccessStub(String dbName)
	{
		this.dbName = dbName;
	}

	public DataAccessStub()
	{
		this(Main.dbName);
	}

	public void open(String dbName)
	{
		Student student;
		Course course;
		SC mySC;

		students = new ArrayList<Student>();
		student = new Student("100", "Gary Chalmers", "Management");
		students.add(student);
		student = new Student("200", "Selma Bouvier", "University Centre");
		students.add(student);
		student = new Student("300", "Arnie Pye", "Frank Kennedy");
		students.add(student);
		student = new Student("400", "Mary Bailey", "Off Campus");
		students.add(student);

		courses = new ArrayList<Course>();
		course = new Course("COMP3010", "Distributed Computing");
		courses.add(course);
		course = new Course("COMP3020", "Human-Computer Interaction");
		courses.add(course);
		course = new Course("COMP3350", "Software Development");
		courses.add(course);
		course = new Course("COMP3380", "Databases");
		courses.add(course);

		scs = new ArrayList<SC>();
		mySC = new SC("100", "COMP3010", "Gary Chalmers", "Distributed Computing", "C+");
		scs.add(mySC);
		mySC = new SC("200", "COMP3010", "Selma Bouvier", "Distributed Computing", "A+");
		scs.add(mySC);
		mySC = new SC("100", "COMP3350", "Gary Chalmers", "Software Development", "A");
		scs.add(mySC);
		mySC = new SC("300", "COMP3350", "Arnie Pye", "Software Development", "B");
		scs.add(mySC);
		mySC = new SC("100", "COMP3380", "Gary Chalmers", "Databases", "A");
		scs.add(mySC);
		mySC = new SC("200", "COMP3380", "Selma Bouvier", "Databases", "B");
		scs.add(mySC);

		System.out.println("Opened " +dbType +" database " +dbName);
	}

	public void close()
	{
		System.out.println("Closed " +dbType +" database " +dbName);
	}

	public List<User> getAllUsers()
	{
		List<User> result=new ArrayList<User>();
		result.addAll(users);
		return result;
	}

	public User getUser(User user){
		int index= users.indexOf(user);
		User result=null;
		if (index>=0){
			result=users.get(index);
		}
		return result;
	}


	public void insertUser(User newUser)
	{
		users.add(newUser);
	}

	public void updateUser(User currentUser)
	{
		int index;
		
		index = users.indexOf(currentUser);
		if (index >= 0)
		{
			users.set(index, currentUser);
		}
	}

	public void deleteUser(User user)
	{
		int index;
		
		index = users.indexOf(user);
		if (index >= 0)
		{
			users.remove(index);
		}
	}


}
