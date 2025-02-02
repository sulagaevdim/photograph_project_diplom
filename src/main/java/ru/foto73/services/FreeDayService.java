package ru.foto73.services;

import org.springframework.stereotype.Service;
import ru.foto73.model.FreeDay;
import ru.foto73.repository.FreeDayRepository;
import ru.foto73.repository.OrderRepository;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

@Service
public class FreeDayService {
    private final FreeDayRepository freeDayRepository;
    private final OrderRepository orderRepository;

    public FreeDayService(FreeDayRepository freeDayRepository, OrderRepository orderRepository) {
        this.freeDayRepository = freeDayRepository;
        this.orderRepository = orderRepository;
    }

    public String createFreeDay(FreeDay date){
        if (date.getDate() == null) return "Введите дату";
        for(FreeDay day : freeDayRepository.findAll()){
            if (day.getDate().equals(date.getDate())) return "Эта дата уже была добавлена ранее";
        }
        freeDayRepository.save(date);
        return "Добавлено";
    }
    public List<FreeDay> findAllFreeDays(){
        List<FreeDay> list = freeDayRepository.findAll();
        list.sort(Comparator.comparing(FreeDay::getDate)); //сортировка по дате
        return list;
    }
    public void deleteFreeDay(Long id) {
        freeDayRepository.deleteById(id);
    }
    public FreeDay findFreeDay(Long id) {
        return freeDayRepository.findById(id).get();
    }

    public void updateFreeDay(FreeDay freeDay){
        freeDayRepository.save(freeDay);
    }

    /** метод делает время определенного дня свободным, если Администратор удаляет запись*/
    public void liberationDayOfOrderId(Long orderId){
        LocalDate date = orderRepository.findById(orderId).get().getDate();
        String time = orderRepository.findById(orderId).get().getTime();
        for (FreeDay day : freeDayRepository.findAll()){
            if (day.getDate().equals(date)) {
                day.updateTimeFromNumber(time, true);
            }
        }
    }
}
