package ru.foto73;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import ru.foto73.model.FreeDay;
import ru.foto73.model.User;
import ru.foto73.repository.OrderRepository;
import ru.foto73.repository.UserRepository;
import ru.foto73.services.FreeDayService;
import ru.foto73.services.UserService;

import java.time.LocalDate;
import java.util.List;

@SpringBootApplication
@EnableScheduling
public class PhotographApplication {
	private static UserService userService;
	private static UserRepository repository;
	private static OrderRepository orderRepository;
	private static FreeDayService freeDayService;

    public PhotographApplication(FreeDayService freeDayService, UserRepository repository, OrderRepository orderRepository, UserService userService) {
        this.repository = repository;
		this.userService = userService;
        this.orderRepository = orderRepository;
		this.freeDayService = freeDayService;

    }

    public static void main(String[] args) {
		SpringApplication.run(PhotographApplication.class, args);

		//Удаление прошедших свободных дней (после рестарта сервера)
		List<FreeDay> freeDays = freeDayService.findAllFreeDays();
		for (FreeDay day : freeDays){
			String[] thisday = day.getDate().toString().split("-");
			String[] today = LocalDate.now().toString().split("-");
			int thisDayInt = Integer.parseInt(thisday[0] + thisday[1] + thisday[2]);
			int todayInt = Integer.parseInt(today[0] + today[1] + today[2]);
			if (thisDayInt < todayInt) {
				freeDayService.deleteFreeDay(day.getId());
				System.out.println("Из таблицы (free-days) удален день " + day.getDate());
			}
		}


		//Проверка наличия админского аккаунта в БД, если его нет - создаём:
		if (repository.findByLogin("admin").isEmpty()) {
			User user = new User();
			user.setLogin("admin");
			user.setPassword("hashed_123");
			user.setFirstName("Наталья");
			user.setLastName("Сулагаева");
			user.setEmail("sulagaevanv@mail.ru");
			user.setRole("ADMIN");
			user.setSaleLevel(100);
			user.setPhoneNumber("+79041928517");
			repository.save(user);
		}


		//удаление пользователя по ID
		//repository.delete(repository.findById(12L).get());


		//показать всех пользователей в БД:
//		List<User> list = repository.findAll();
//		for (User us : list) {
//			System.out.println(us.toString());
//		}

		//удалить заказ из БД по ID:
		//orderRepository.deleteById(12L);

		//показать все заказы, имеющиеся в БД:
//		List<Order> list2 = orderRepository.findAll();
//		for (Order us : list2) {
//			System.out.println(us.toString());
//		}


	}

}
