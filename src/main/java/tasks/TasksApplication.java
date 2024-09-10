package tasks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import tasks.repository.RoleRepository;
import tasks.repository.UserRepository;

@SpringBootApplication
public class TasksApplication {

	@Autowired
	UserRepository userRepository;
	@Autowired
	RoleRepository roleRepository;

	public static void main(String[] args) {
		SpringApplication.run(TasksApplication.class, args);
	}

}
