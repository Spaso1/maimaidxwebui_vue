package com.example.demo;

import com.example.demo.been.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

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

    @GetMapping("/playlog")
    public PlaylogData getPlaylog() {
        PlaylogData playlogData = new PlaylogData();
        playlogData.setRecords(generatePlaylogItems(3+new Random().nextInt(1)));
        return playlogData;
    }

    @GetMapping("/playlogDetail")
    public PlaylogRecord getPlaylogRecord(String recordId) {
        PlaylogRecord playlogRecord = generatePlaylogRecord();
        return playlogRecord;
    }

    @GetMapping("/music")
    public MusicData getMusic(@RequestParam(required = false) String genre,
                               @RequestParam(required = false) String word,
                               @RequestParam(required = false) String level,
                               @RequestParam(required = false) String version,
                               @RequestParam(required = false) String diff,
                              @RequestParam(required = false) String mode) {
        MusicData musicData = new MusicData();
        List<RatingItem> items = generateMusicItems(50);
        
        Map<String, List<RatingItem>> songsMap = new java.util.HashMap<>();
        List<String> genreOrder = new java.util.ArrayList<>();
        
        for (RatingItem item : items) {
            String genreLabel = getGenreLabel(item);
            if (!songsMap.containsKey(genreLabel)) {
                songsMap.put(genreLabel, new java.util.ArrayList<>());
                genreOrder.add(genreLabel);
            }
            songsMap.get(genreLabel).add(item);
        }
        
        musicData.setSongs(songsMap);
        musicData.setTotal(items.size());
        musicData.setGenreOrder(genreOrder);
        musicData.setMusicStatus(generateMusicStatus());
        return musicData;
    }

    @GetMapping("/score")
    public Score getScore(@RequestParam(required = true) String chart_id) {
        Score score = new Score();
        List<RatingItem> ratingItems = generateScoreItems(5);
        score.setArtist("BlackY feat. Risa Yuzuki");
        score.setTitle("Starry Colors");
        score.setImg_url("https://maimai.wahlap.com/maimai-mobile/img/Music/f38fc9d91ef0b72c.png");
        score.setRatingItems(ratingItems);
        score.setType("dx");
        if (new Random().nextBoolean()) {
            score.setType("standard");
        }
        score.setChart_id(Integer.parseInt(chart_id));
        return score;
    }
    private String getGenreLabel(RatingItem item) {
        Random random = new Random();
        switch (random.nextInt(3)) {
            case 0:
                return "例子1";
            case 1:
                return "例21";
            case 2:
                return "例子3";
            case 3:
                return "例子4";
            default:
                return "default";
        }
    }

    private PlaylogRecord generatePlaylogRecord() {
        PlaylogRecord playlogRecord = new PlaylogRecord();
        playlogRecord.setTap_cpfect(-1);
        playlogRecord.setTap_pfect(999);
        playlogRecord.setTap_great(1);
        playlogRecord.setSlide_cpfect(999);

        playlogRecord.setFast(1);
        playlogRecord.setLate(999);
        playlogRecord.setRating(19999);
        playlogRecord.setRating_change(0);
        playlogRecord.setMax_combo("9999 / 9999");
        playlogRecord.setMax_sync("9999 / 9999");
        List<Integer> chara_id = Arrays.asList(606107,606106,606106,606105,606104);
        List<Integer> chara_star = Arrays.asList(0,0,0,6,3);;
        List<Integer>  chara_level = Arrays.asList(9999,9999,0,0,0);;
        playlogRecord.setChara_id(chara_id);
        playlogRecord.setChara_level(chara_level);
        playlogRecord.setChara_star(chara_star);
        List<String> play_together_name = Arrays.asList("Wang Hongwen");
        playlogRecord.setPlay_together_name(play_together_name);
        List<Integer> play_together_diff = Arrays.asList(3);
        playlogRecord.setPlay_together_diff(play_together_diff);
        return playlogRecord;
    }


    private List<PlaylogItem> generatePlaylogItems(int count) {
        List<PlaylogItem> items = new ArrayList<>();
        Random random = new Random();
        String[] musicNames = {
            "胡蝶乃舞", "114514", "病み垢ステロイド", "インフェルノ", "Bad Apple!!",
            "砂の惑星", "深海少女", "ヒバナ", "ラブリーデイズ", "マーシャルマキシマイザー"
        };

        for (int i = 0; i < count; i++) {
            PlaylogItem item = new PlaylogItem();
            item.setTrackNumber(String.format("%02d", i + 1));
            item.setPlayDate("2026/02/28 22:14");
            item.setLevelId(4 - i);
            item.setMusicName(musicNames[random.nextInt(musicNames.length)]);
            item.setMusicImage("https://maimai.wahlap.com/maimai-mobile/img/Music/243cb5e190e6bc1c.png");
            item.setDx(i % 2 == 0);
            item.setNewRecord(i == 0);
            item.setAchievement(String.format("%.4f", 90 + random.nextDouble() * 10));
            item.setScoreType(13);
            item.setScoreLineType(2);
            item.setDeluxScore((2000 + random.nextInt(500)) + " / " + (2400 + random.nextInt(200)));
            item.setDxStar(random.nextInt(6));
            item.setComboType(random.nextInt(3));
            item.setSyncType(random.nextInt(3));
            item.setRecordIdx("4,1772288055");
            item.setList(random.nextInt(3) - 1);
            if (i == 1) {
                item.setKaleidxHealth("999 / 999");
            } else if (i == 2) {
                item.setKaleidxHealth("0 / 999");
            } else {
                item.setKaleidxHealth("");
            }
            items.add(item);
        }
        return items;
    }
    private List<RatingItem> generateScoreItems(int count) {
        List<RatingItem> items = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < count; i++) {
            RatingItem item = new RatingItem();
            item.setChart_id(i);
            item.setLevel_id(i);
            item.setLevel_String((random.nextInt(15)) + (random.nextBoolean() ? "+" : ""));
            item.setScore(95.0 + (random.nextInt(50000)/10000.0) );
            item.setMusic_name("s");
            if (random.nextInt(2)==1) {
                item.setMusic_name(null);
            }
            item.setDx_score((2000 + random.nextInt(800)) + " / " + (2500 + random.nextInt(300)));
            item.setCombo_type(random.nextInt(3));
            item.setSync_type(random.nextInt(6));
            item.setScore_type(1 + random.nextInt(15));
            item.setType(random.nextInt(2));
            item.setPlayCount(random.nextInt(100));
            item.setLastPlayDate("2025/04/30 21:10");
            items.add(item);
        }
        return items;
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
            item.setChart_id(i);
            item.setLevel_id(random.nextInt(5));
            item.setLevel_String((10 + random.nextInt(5)) + (random.nextBoolean() ? "+" : ""));
            item.setScore(95.0 + (random.nextInt(50000) / 10000.0) );
            item.setMusic_name(musicNames[random.nextInt(musicNames.length)]);
            item.setDx_score((2000 + random.nextInt(800)) + " / " + (2500 + random.nextInt(300)));
            item.setCombo_type(random.nextInt(3));
            item.setSync_type(random.nextInt(6));
            item.setScore_type(1 + random.nextInt(15));
            item.setType(random.nextInt(2));
            items.add(item);
        }
        return items;
    }

    private List<RatingItem> generateMusicItems(int count) {
        List<RatingItem> items = new ArrayList<>();
        Random random = new Random();
        String[] musicNames = {
            "病み垢ステロイド", "インフェルノ", "Bad Apple!!", "砂の惑星", "深海少女",
            "ヒバナ", "ラブリーデイズ", "マーシャルマキシマイザー", "ネクロファンタジア", "幻夢抱擁",
            "胡蝶乃舞", "114514", "深海少女", "ヒバナ", "ラブリーデイズ"
        };
        
        String[] genres = {"流行&动漫", "niconico＆VOCALOID™", "东方Project", "其他游戏", "舞萌", "音击/中二节奏"};

        for (int i = 0; i < count; i++) {
            RatingItem item = new RatingItem();
            item.setChart_id(i);
            item.setLevel_id(random.nextInt(5));
            item.setLevel_String((10 + random.nextInt(5)) + (random.nextBoolean() ? "+" : ""));
            item.setScore(95.0 + (random.nextInt(50000) / 10000.0) );
            item.setMusic_name(musicNames[random.nextInt(musicNames.length)]);
            item.setDx_score((2000 + random.nextInt(800)) + " / " + (2500 + random.nextInt(300)));
            item.setCombo_type(random.nextInt(3));
            item.setSync_type(random.nextInt(6));
            item.setScore_type(1 + random.nextInt(15));
            item.setType(random.nextInt(2));
            item.setGenre(genres[random.nextInt(genres.length)]);
            items.add(item);
        }
        return items;
    }

    private MusicStatus generateMusicStatus() {
        MusicStatus status = new MusicStatus();
        Random random = new Random();
        
        status.setClearCount(105 + random.nextInt(20));
        status.setsCount(104 + random.nextInt(20));
        status.setSpCount(104 + random.nextInt(20));
        status.setSsCount(103 + random.nextInt(20));
        status.setSspCount(102 + random.nextInt(20));
        status.setSssCount(101 + random.nextInt(20));
        status.setSsspCount(86 + random.nextInt(20));
        status.setFcCount(60 + random.nextInt(20));
        status.setFcpCount(30 + random.nextInt(20));
        status.setApCount(28 + random.nextInt(20));
        status.setAppCount(6 + random.nextInt(10));
        status.setSyncCount(83 + random.nextInt(20));
        status.setFsCount(34 + random.nextInt(20));
        status.setFspCount(34 + random.nextInt(20));
        status.setFdxCount(33 + random.nextInt(20));
        status.setFdxpCount(7 + random.nextInt(10));
        status.setDxStar1Count(104 + random.nextInt(20));
        status.setDxStar2Count(103 + random.nextInt(20));
        status.setDxStar3Count(102 + random.nextInt(20));
        status.setDxStar4Count(101 + random.nextInt(20));
        status.setDxStar5Count(86 + random.nextInt(20));
        status.setRandDollyCount(1239);
        
        return status;
    }
}
