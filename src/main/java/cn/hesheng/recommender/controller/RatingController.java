package cn.hesheng.recommender.controller;

import cn.hesheng.recommender.lenskit.Session;
import cn.hesheng.recommender.lenskit.exception.ResourceNotFoundException;
import cn.hesheng.recommender.model.Ratings;
import cn.hesheng.recommender.repository.RatingRepository;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;


/**
 * http://localhost:8080/swagger-ui.html
 */
@RequestMapping("/api")
@RestController
@Slf4j
public class RatingController {

    @Autowired
    private RatingRepository repository;

    @Autowired
    private Session session;

    /**
     * 添加
     * @param ratings 信息
     * @return 添加成功后的id
     */
//    @ApiOperation(value="创建投票", notes="根据投票对象创建用户")
//    @ApiImplicitParam(name = "ratings", value = "投票实体", required = true, dataType = "Ratings")
    @PostMapping("/users")
    public Object saveUser(@RequestBody Ratings ratings){
        Ratings saveUser = repository.save(ratings);
        return saveUser;
    }

    /**
     * 获取指定和一组用户 相关项目的投票
     * @param ratings
     */
    @PostMapping("/getCurrentItemRatings")
    public void getCurrentItemRatings(@RequestBody Ratings ratings){
        long itemId = 256L;
        Set<Long> users = new HashSet<Long>();
        try {
            Map<Long, Double> currentItemRatings = session.getCurrentItemRatings(itemId, users);
        } catch (ResourceNotFoundException e) {
            log.error(e.getMessage(),e);
        }
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
        repository.deleteById(id);
    }

}