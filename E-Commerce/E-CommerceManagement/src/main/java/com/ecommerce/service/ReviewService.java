package com.ecommerce.service;

import com.ecommerce.entity.Review;
import com.ecommerce.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ReviewService {
	@Autowired
	private ReviewRepository reviewRepo;

	public Review addReview(Review review) {
		if (review.getProductId() == null || review.getCustomerId() == null || review.getRating() == null
				|| review.getRating() < 1 || review.getRating() > 5) {
			throw new RuntimeException("Invalid review details");
		}
		review.setStatus(false); // Pending
		return reviewRepo.save(review);
	}
	public List<Review> getAllReviews() {  // NEW
        return reviewRepo.findAll();
    }
	public List<Review> getApprovedReviewsByProduct(Integer productId) {
		return reviewRepo.findApprovedByProductId(productId);
	}

	public Optional<Review> getReviewById(Integer id) {
		return reviewRepo.findById(id);
	}

	public Review updateReview(Integer id, Review updated) {
		Review existing = reviewRepo.findById(id).orElseThrow(() -> new RuntimeException("Review not found"));
		if (updated.getRating() != null && updated.getRating() >= 1 && updated.getRating() <= 5) {
			existing.setRating(updated.getRating());
		}
		if (updated.getReviewText() != null) {
			existing.setReviewText(updated.getReviewText());
		}
		return reviewRepo.save(existing);
	}

	public void deleteReview(Integer id) {
		reviewRepo.deleteById(id);
	}

	public Review approveReview(Integer id) {
		Review r = reviewRepo.findById(id).orElseThrow(() -> new RuntimeException("Review not found"));
		r.setStatus(true);
		return reviewRepo.save(r);
	}

	public Review rejectReview(Integer id) {
		Review r = reviewRepo.findById(id).orElseThrow(() -> new RuntimeException("Review not found"));
		r.setStatus(false);
		return reviewRepo.save(r);
	}
}