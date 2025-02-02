package ru.foto73.services;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.foto73.model.FreeDay;

import java.time.LocalDate;
import java.util.List;


/**
 * Бин удаляет ежедневно в 00:00 дни из таблицы free-days, которые уже прошли
 */
@Service
public class DeleteExpiredDaysService {
    private final FreeDayService freeDayService;

    public DeleteExpiredDaysService(FreeDayService freeDayService) {
        this.freeDayService = freeDayService;
    }

    @Scheduled(cron = "0 0 0 * * *")  // Каждый день в 00:00
    public void runDailyTask() {
        System.out.println("Задача выполнена в 00:00!");
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
    }
}
