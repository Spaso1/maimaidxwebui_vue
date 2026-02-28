package com.example.demo;

import com.example.demo.been.UserPreview;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ApiController {
    @GetMapping("/userpreview")
    public UserPreview getPreview() {
        UserPreview userPreview = new UserPreview();
        userPreview.setIconUrl("https://maimai.wahlap.com/maimai-mobile/img/Icon/1a3e72174eb86393.png");
        userPreview.setName("Lin Biao");
        userPreview.setTrophy_url("https://maimai.wahlap.com/maimai-mobile/img/trophy_gold.png");
        userPreview.setTrophy_title("ふかふか魔法のパン職人");
        userPreview.setRating(99999);
        userPreview.setStar(9999);
        userPreview.setChara("https://maimai.wahlap.com/maimai-mobile/img/Chara/ad98299474ffca1a.png");
        userPreview.setCourse_rank("https://maimai.wahlap.com/maimai-mobile/img/course/course_rank_10hvsSHd90.png");
        userPreview.setClass_rank("https://maimai.wahlap.com/maimai-mobile/img/class/class_rank_s_00ZqZmdpb8.png");
        userPreview.setRating_url("https://maimai.wahlap.com/maimai-mobile/img/rating_base_rainbow.png?ver=1.50");
        return userPreview;
    }
}
