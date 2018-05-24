package cn.hesheng.recommender.controller;

import cn.hesheng.recommender.model.Ratings;
import cn.hesheng.recommender.repository.RatingRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@Slf4j
public class HomeController {

    @Autowired
    private RatingRepository repository;

    @RequestMapping("/")
    public @ResponseBody String greeting() {
        List<Ratings> all = repository.findAll();
        log.info("list size {}",all.size());
        for(Ratings ratings : all){
            log.info(ratings.toString());
        }
        return "Hello World";
    }

}
