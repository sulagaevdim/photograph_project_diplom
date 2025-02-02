package ru.foto73.services;

import org.springframework.stereotype.Service;
import ru.foto73.model.Review;
import ru.foto73.repository.ReviewRepository;
import ru.foto73.repository.UserRepository;

import java.util.Comparator;
import java.util.List;

@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;

    public ReviewService(ReviewRepository reviewRepository, UserRepository userRepository) {
        this.reviewRepository = reviewRepository;
        this.userRepository = userRepository;
    }

    public List<Review> findAllReview(){
        List<Review> list = reviewRepository.findAll();
        list.sort(Comparator.comparing(Review::getPublicDate, Comparator.reverseOrder()));
        return list;
    }
    //подсчет средней оценки
    public String calcAverageRating(){
        if (reviewRepository.findAll().isEmpty()) return "0.00";
        double sum = 0;
        for(Review review : reviewRepository.findAll()){
            sum += review.getExellent();
        }
        return String.format("%.2f", sum/reviewRepository.findAll().size());
    }

    public boolean saveReview(Review review){
        reviewRepository.save(review);
        return true;
    }
}
