package cn.hesheng.recommender.controller;

import cn.hesheng.recommender.model.Ratings;
import cn.hesheng.recommender.repository.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api")
@RestController
public class RatingController {

    @Autowired
    private RatingRepository repository;

    /**
     * 添加
     * @param ratings 信息
     * @return 添加成功后的id
     */
    @PostMapping("/users")
    public Object saveUser(@RequestBody Ratings ratings){
        Ratings saveUser = repository.save(ratings);
        return saveUser;
    }

    /**
     * 更新信息
     * @param ratings 信息
     * @param id id
     * @return 更新结果
     */
    @PutMapping("/users/{id}")
    public Object updateUserById(@RequestBody Ratings ratings,@PathVariable Long id){
        ratings.setId(id);
        repository.save(ratings);
        return id;
    }

    /**
     * 获取信息
     * @param id id
     * @return 信息
     */
    @GetMapping("/users/{id}")
    public Object findUserById(@PathVariable Long id){
        return repository.getOne(id);
    }

    /**
     * 删除信息
     * @param id id
     * @return 删除结果
     */
    @DeleteMapping("/users/{id}")
    public void deleteUserById(@PathVariable Long id){
        repository.delete(id);
    }

}