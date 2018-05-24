package cn.hesheng.recommender.repository;

import cn.hesheng.recommender.model.Ratings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 投票 数据操作类
 */
@Repository
public interface RatingRepository extends JpaRepository<Ratings,Long> {

}
