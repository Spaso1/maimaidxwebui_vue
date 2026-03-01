package com.example.demo;

import com.example.demo.been.RatingData;
import com.example.demo.been.RatingItem;
import com.example.demo.been.UserPreview;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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

    @GetMapping("/rating")
    public RatingData getRating() {
        RatingData ratingData = new RatingData();
        ratingData.setNewSongs(generateRatingItems(10));
        ratingData.setBestSongs(generateRatingItems(10));
        ratingData.setNewCandidates(generateRatingItems(10));
        ratingData.setBestCandidates(generateRatingItems(10));
        return ratingData;
    }

    private List<RatingItem> generateRatingItems(int count) {
        List<RatingItem> items = new ArrayList<>();
        Random random = new Random();
        String[] musicNames = {
            "病み垢ステロイド", "インフェルノ", "Bad Apple!!", "砂の惑星", "深海少女",
            "ヒバナ", "ラブリーデイズ", "マーシャルマキシマイザー", "ネクロファンタジア", "幻夢抱擁"
        };

        for (int i = 0; i < count; i++) {
            RatingItem item = new RatingItem();
            item.setLevel_id(random.nextInt(5));
            item.setLevel_String((10 + random.nextInt(5)) + (random.nextBoolean() ? "+" : ""));
            item.setScore(95.0 + random.nextDouble() * 5.0);
            item.setMusic_name(musicNames[random.nextInt(musicNames.length)]);
            item.setDx_score((2000 + random.nextInt(800)) + " / " + (2500 + random.nextInt(300)));
            item.setComboType(random.nextInt(3));
            item.setSyncType(random.nextInt(6));
            item.setScore_type(1 + random.nextInt(15));
            item.setType(random.nextInt(2));
            items.add(item);
        }
        return items;
    }
}
