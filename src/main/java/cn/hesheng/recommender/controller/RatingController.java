package cn.hesheng.recommender.controller;

import cn.hesheng.recommender.model.Ratings;
import cn.hesheng.recommender.repository.RatingRepository;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * http://localhost:8080/swagger-ui.html
 */
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
//    @ApiOperation(value="创建投票", notes="根据投票对象创建用户")
//    @ApiImplicitParam(name = "ratings", value = "投票实体", required = true, dataType = "Ratings")
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
        repository.deleteById(id);
    }

}