<script setup lang="ts">
import Rating from "../been/Rating.vue";
import {ref} from "vue";

const userdata = ref({
  iconUrl: 'https://maimai.wahlap.com/maimai-mobile/img/Icon/1a3e72174eb86393.png',
  name: 'Lin Biao爱吃黄豆',
  rating: 17110,
  trophy_title: 'ふかふか魔法のパン職人',
  trophy_url: '/src/assets/playdata/trophy_gold.png',
  course_rank: 'https://maimai.wahlap.com/maimai-mobile/img/course/course_rank_10hvsSHd90.png',
  class_rank: 'https://maimai.wahlap.com/maimai-mobile/img/class/class_rank_s_00ZqZmdpb8.png',
  star: 205,
  class_point: 6,
  class_point_max: 10 ,
  jewel: false,
  ticket: [{
      img_url:'https://maimai.wahlap.com/maimai-mobile/img/ticket/7b113dc41580bd5b.png',
      title:'旅行伙伴等级提升5倍票',
      num:2
    },
    {
      img_url:'https://maimai.wahlap.com/maimai-mobile/img/ticket/7b113dc41580bd5b.png',
      title:'中南海参观卷',
      num:5
    }
  ],
  gift:7566,
});
const activeDiff = ref(99);

// 新增：点击按钮的处理函数
const handleDiffClick = (value: number) => {
  activeDiff.value = value;
};

</script>

<template>
  <img src="/src/assets/playdata/title_playerdata.png" class="title m_10">
  <div class="see_through_block m_15 m_t_0 p_10 p_r t_l f_0">
    <div class="basic_block p_10 f_0">
      <img loading="lazy" :src="userdata.iconUrl" class="w_112 f_l">
      <div class="p_l_10">
        <div class="trophy_block trophy_Gold p_3 t_c f_0">
          <div class="trophy_inner_block f_13">
            <span>{{ userdata.trophy_title }}</span>
          </div>
        </div>
        <div class="m_b_5">
          <div class="name_block f_l f_16">{{userdata.name}}</div>
          <div class="f_r t_r f_0">
            <div class="">
              <Rating :rating="userdata.rating" />
            </div>
          </div>
          <img src="/src/assets/line_01.png" class="user_data_block_line">
          <img :src="userdata.course_rank" class="h_35 f_l">
          <img :src="userdata.class_rank" class="p_l_10 h_35 f_l">
          <div class="p_l_10 f_l f_14"><img src="/src/assets/rating/icon_star.png" class="h_30 m_3 v_m">×{{ userdata.star }}</div>
          <div class="clearfix"></div>
        </div>
      </div>
      <div class="clearfix"></div>
    </div>
    <div class="clearfix"></div>
    <div class="m_5 m_b_5 t_r f_12">当前版本的游玩次数：815<br>舞萌DX的累计游玩次数：1,640</div>

    <div>
      <div class="m_t_10 m_b_10 f_0">
        <button type="button" name="diff" value="99" class="p_r f_0" @click="handleDiffClick(99)">
          <img src="/src/assets/music/btn_music_all.png" class="w_71">
          <img v-if="activeDiff === 99" src="/src/assets/home_sub/icon_on.png" class="sub-menu-icon">
        </button>
        <button type="button" name="diff" value="0" class="p_r f_0" @click="handleDiffClick(0)">
          <img src="/src/assets/music/btn_music_basic.png" class="w_71">
          <img v-if="activeDiff === 0" src="/src/assets/home_sub/icon_on.png" class="sub-menu-icon">
        </button>
        <button type="button" name="diff" value="1" class="p_r f_0" @click="handleDiffClick(1)">
          <img src="/src/assets/music/btn_music_advanced.png" class="w_71">
          <img v-if="activeDiff === 1" src="/src/assets/home_sub/icon_on.png" class="sub-menu-icon">
        </button>
        <button type="button" name="diff" value="2" class="p_r f_0" @click="handleDiffClick(2)">
          <img src="/src/assets/music/btn_music_expert.png" class="w_71">
          <img v-if="activeDiff === 2" src="/src/assets/home_sub/icon_on.png" class="sub-menu-icon">
        </button>
        <button type="button" name="diff" value="3" class="p_r f_0" @click="handleDiffClick(3)">
          <img src="/src/assets/music/btn_music_master.png" class="w_71">
          <img v-if="activeDiff === 3" src="/src/assets/home_sub/icon_on.png" class="sub-menu-icon">
        </button>
        <button type="button" name="diff" value="4" class="p_r f_0" @click="handleDiffClick(4)">
          <img src="/src/assets/music/btn_music_remaster.png" class="w_71">
          <img v-if="activeDiff === 4" src="/src/assets/home_sub/icon_on.png" class="sub-menu-icon">
        </button>
      </div>

    </div>

    <div class="musiccount_block t_r f_l f_0">
      <div class="musiccount_img_block p_5 p_l_0 f_l"><img src="/src/assets/record/music_icon_sssp.png" class="icon_s"></div>
      <div class="musiccount_counter_block f_13_2">350/5,085</div>
    </div>
    <div class="musiccount_block t_r f_l f_0">
      <div class="musiccount_img_block t_c f_l"><img src="/src/assets/record/music_icon_app.png" class="icon_s"></div>
      <div class="musiccount_counter_block f_13_2">20/5,085</div>
    </div>
    <div class="clearfix"></div>
    <div class="musiccount_block t_r f_l f_0">
      <div class="musiccount_img_block p_5 p_l_0 f_l"><img src="/src/assets/record/music_icon_sss.png" class="icon_s"></div>
      <div class="musiccount_counter_block f_13_2">508/5,085</div>
    </div>
    <div class="musiccount_block t_r f_l f_0">
      <div class="musiccount_img_block t_c f_l"><img src="/src/assets/record/music_icon_ap.png" class="icon_s"></div>
      <div class="musiccount_counter_block f_13_2">45/5,085</div>
    </div>
    <div class="clearfix"></div>
    <div class="musiccount_block t_r f_l f_0">
      <div class="musiccount_img_block p_5 p_l_0 f_l"><img src="/src/assets/record/music_icon_ssp.png" class="icon_s"></div>
      <div class="musiccount_counter_block f_13_2">639/5,085</div>
    </div>
    <div class="musiccount_block t_r f_l f_0">
      <div class="musiccount_img_block t_c f_l"><img src="/src/assets/record/music_icon_fcp.png" class="icon_s"></div>
      <div class="musiccount_counter_block f_13_2">89/5,085</div>
    </div>
    <div class="clearfix"></div>
    <div class="musiccount_block t_r f_l f_0">
      <div class="musiccount_img_block p_5 p_l_0 f_l"><img src="/src/assets/record/music_icon_ss.png" class="icon_s"></div>
      <div class="musiccount_counter_block f_13_2">753/5,085</div>
    </div>
    <div class="musiccount_block t_r f_l f_0">
      <div class="musiccount_img_block t_c f_l"><img src="/src/assets/record/music_icon_fc.png" class="icon_s"></div>
      <div class="musiccount_counter_block f_13_2">436/5,085</div>
    </div>
    <div class="clearfix"></div>
    <div class="musiccount_block t_r f_l f_0">
      <div class="musiccount_img_block p_5 p_l_0 f_l"><img src="/src/assets/record/music_icon_sp.png" class="icon_s"></div>
      <div class="musiccount_counter_block f_13_2">904/5,085</div>
    </div>
    <div class="musiccount_block t_r f_l f_0">
      <div class="musiccount_img_block t_c f_l"><img src="/src/assets/record/music_icon_fdxp.png" class="icon_s"></div>
      <div class="musiccount_counter_block f_13_2">17/5,085</div>
    </div>
    <div class="clearfix"></div>
    <div class="musiccount_block t_r f_l f_0">
      <div class="musiccount_img_block p_5 p_l_0 f_l"><img src="/src/assets/record/music_icon_s.png" class="icon_s"></div>
      <div class="musiccount_counter_block f_13_2">1,012/5,085</div>
    </div>
    <div class="musiccount_block t_r f_l f_0">
      <div class="musiccount_img_block t_c f_l"><img src="/src/assets/record/music_icon_fdx.png" class="icon_s"></div>
      <div class="musiccount_counter_block f_13_2">48/5,085</div>
    </div>
    <div class="clearfix"></div>
    <div class="musiccount_block t_r f_l f_0">
      <div class="musiccount_img_block p_5 f_l"><img src="/src/assets/music/music_icon_clear.png" class="icon_s"></div>
      <div class="musiccount_counter_block f_13_2">1,189/5,085</div>
    </div>
    <div class="musiccount_block t_r f_l f_0">
      <div class="musiccount_img_block t_c f_l"><img src="/src/assets/record/music_icon_fsp.png" class="icon_s"></div>
      <div class="musiccount_counter_block f_13_2">110/5,085</div>
    </div>
    <div class="clearfix"></div>
    <div class="musiccount_block t_r f_l f_0">
      <div class="musiccount_img_block t_c f_l"><img src="/src/assets/music/music_icon_dxstar_5.png" class="icon_s"></div>
      <div class="musiccount_counter_block f_13_2">18/5,085</div>
    </div>
    <div class="musiccount_block t_r f_l f_0">
      <div class="musiccount_img_block t_c f_l"><img src="/src/assets/record/music_icon_fs.png" class="icon_s"></div>
      <div class="musiccount_counter_block f_13_2">124/5,085</div>
    </div>
    <div class="clearfix"></div>
    <div class="musiccount_block t_r f_l f_0">
      <div class="musiccount_img_block t_c f_l"><img src="/src/assets/music/music_icon_dxstar_4.png" class="icon_s"></div>
      <div class="musiccount_counter_block f_13_2">76/5,085</div>
    </div>
    <div class="musiccount_block t_r f_l f_0">
      <div class="musiccount_img_block t_c f_l"><img src="/src/assets/record/music_icon_sync.png" class="icon_s"></div>
      <div class="musiccount_counter_block f_13_2">1,084/5,085</div>
    </div>
    <div class="clearfix"></div>
    <div class="musiccount_block t_r f_l f_0">
      <div class="musiccount_img_block t_c f_l"><img src="/src/assets/music/music_icon_dxstar_3.png" class="icon_s"></div>
      <div class="musiccount_counter_block f_13_2">145/5,085</div>
    </div>
    <div class="clearfix"></div>
    <div class="musiccount_block t_r f_l f_0">
      <div class="musiccount_img_block t_c f_l"><img src="/src/assets/music/music_icon_dxstar_2.png" class="icon_s"></div>
      <div class="musiccount_counter_block f_13_2">350/5,085</div>
    </div>
    <div class="clearfix"></div>
    <div class="musiccount_block t_r f_l f_0">
      <div class="musiccount_img_block t_c f_l"><img src="/src/assets/music/music_icon_dxstar_1.png" class="icon_s"></div>
      <div class="musiccount_counter_block f_13_2">714/5,085</div>
    </div>
    <div class="clearfix"></div>
    <img src="/src/assets/playdata/chara_03.png" class="playerdata_back">
  </div>
  <img src="/src/assets/playdata/title_class.png" class="title m_10">
  <div class="town_block m_15 m_t_0 p_5 f_0 scale-container">
    <div class="p_t_10 p_r_10">
      <div class="d_ib t_c v_t w_180">
        <img src="/src/assets/playdata/icon_class_current.png" class="w_120 p_b_10 m_r_10">
        <img :src="userdata.class_rank" class="w_160 p_15 m_r_10">
      </div>
      <div class="d_ib t_c w_230 p_r">
        <div class="class_point_box d_ib p_10 p_r w_230">
          <img v-if="!userdata.jewel" src="/src/assets/playdata/icon_class_jewel_off.png" class="h_25">
          <img v-if="userdata.jewel" src="/src/assets/playdata/icon_class_jewel_on.png" class="h_25">

          <div class="p_t_10">
            <img class="p_r w_180" src="/src/assets/playdata/icon_class_geuge_base.png">
            <img class="class_geuge_meter p_a w_180" src="/src/assets/playdata/icon_class_geuge_meter.png" :style="{ clip: `rect(0, ${Math.round((userdata.class_point / userdata.class_point_max) * 180)}px, 62px, 0)` }">
            <img class="class_geuge_frame p_a w_180" src="/src/assets/playdata/icon_class_geuge_frame.png">
          </div>
        </div>
        <img src="/src/assets/playdata/icon_class_cp_count.png" class="p_r p_t_10 w_215">
        <div class="class_point_txt p_a t_c red f_20 w_230"><span class="f_29 f_b">{{ userdata.class_point }}</span><span class="f_20"> CP / {{ userdata.class_point_max }} </span><span class="f_13">CP</span></div>
      </div>
    </div>
    <img src="/src/assets/playdata/note_rankup.png" class="h_50 t_c_2 ">
  </div>
  <img src="/src/assets/playdata/title_ticket.png" class="title m_10">

  <div v-for="(item, index) in userdata.ticket" :key="index" class="p_r m_15 w_450 f_0 scale-container">
    <img src="/src/assets/playdata/ticket_free.png" class="w_450">
    <div class="ticket_title p_a p_5 t_c f_16 f_b blue">{{ item.title }}</div>
    <img :src="item.img_url" class="ticket_img p_a">
    <div class="ticket_txt p_a p_5 t_c f_13_2">持有{{ item.num }}张</div>
  </div>

  <img src="/src/assets/playdata/title_partneritem.png" class="title m_10">
  <div class="p_r m_15 m_b_5 w_450 f_0 scale-container">
    <img src="https://maimai.wahlap.com/maimai-mobile/img/intimate/intimateup_base.png" class="w_450">
    <div class="intimateup_txt p_a p_5 t_c f_13_2">持有{{userdata.gift}}个</div>
  </div>
  <div class="p_r w_450 t_r f_13">※超过9,999个将被丢弃</div>
</template>

<style scoped>
.m_10 {
  margin: 10px auto;
  text-align: center;
}
.see_through_block {
  background-color: rgba(255, 255, 255, 0.6);
  box-shadow: 1px 3px 0px rgba(0, 0, 0, 0.4);
  border: 2px solid #000;
  border-radius: 5px;
}
.p_r {
  position: relative;
}
.m_5 {
  margin: 5px;
}
.p_10 {
  padding: 10px;
}
.m_t_0 {
  margin-top: 0;
}
.m_15 {
  margin: 15px;
}
.t_l {
  text-align: left;
}
.f_0 {
  font-size: 0;
  line-height: 0;
}
.basic_block {
  background-color: #fff;
  box-shadow: 1px 3px 0px rgba(0, 0, 0, 0.4);
  border-radius: 5px;
}
.p_10 {
  padding: 10px;
}
.w_112 {
  width: 112px;
}
.p_l_10 {
  padding-left: 10px;
}
.trophy_Gold {
  background: url(/src/assets/playdata/trophy_gold.png) no-repeat;
}
.trophy_block {
  width: 268px;
  height: 25px;
  margin: 0 0 5px 0;
  white-space: nowrap;
  overflow: hidden;
}
.p_3 {
  padding: 3px;
}
.t_c {
  text-align: center;
}
.user_data_block_line {
  width: 280px;
  padding-top:5px;
  margin: 0 0 4px 0;
}
.f_l {
  float: left;
}
.clearfix {
  clear: both;
}
.w_71 {
  width: 69px;
}

@media (max-width: 481px) {
  .w_71 {
    width: 60px;
  }
}
@media (max-width: 401px) {
  .w_71 {
    width: 50px;
  }
}
.p_r {
  position: relative;
}
button[name="diff"] {
  background: transparent;
  border: none;
  padding: 0;
  cursor: pointer;
}
button[name="diff"] img {
  display: block;
}
.trophy_inner_block {
  width: 259px;
  height: 24px;
  margin: 0 2px;
  white-space: nowrap;
  overflow: hidden;
  color: #fff;
  text-shadow: black 1px 1px 0, black -1px -1px 0, black -1px 1px 0, black 1px -1px 0, black 0px 1px 0, black 0 -1px 0, black -1px 0 0, black 1px 0 0;
}
.f_13 {
  font-size: 13px;
  line-height: 1.2rem;
}
.m_b_5 {
  margin-bottom: 5px;
}
.name_block {
  width: 158px;
  height: 27px;
  margin: 3px 0;
  padding: 5px 3px 3px 3px;
  border: 1px solid #dedede;
  background-color: #fff;
  background-size: 34px;
  box-shadow: 1px 3px 0px rgba(0, 0, 0, 0.4);
  border-radius: 5px;
}
.f_l {
  float: left;
}
.f_16 {
  font-size: 16px;
  line-height: 1.2rem;
}
.rating_block {
  position: absolute;
  top: 7px;
  right: 9px;
  width: 73px;
  height: 21px;
  line-height: 21px;
  color: white;
  text-align: right;
  font-size: 16px;
}
.f_r {
  float: right;
}
.t_r {
  text-align: right;
}
.h_30 {
  height: 30px;
}
.h_35 {
  height: 35px;
}
.m_3 {
  margin: 3px;
}
.h_30 {
  height: 30px;
}
.v_m {
  vertical-align: middle;
}
.p_l_10 {
  padding-left: 10px;
}
.f_14 {
  font-size: 14px;
  line-height: 1.2rem;
}

.sub-menu-icon {
  position: absolute;
  top: -16px;
  right: 21px;
  width: 28px;
  height: 26px;
  z-index: 20;
}

.f_12{
  font-size: 12px;
  line-height: 1.2rem;
}
</style>

<style>

.musiccount_block {
  width: 200px;
  margin: 4px;
  border: 2px solid #26b3fc;
  box-shadow: 1px 3px 0px rgba(0, 0, 0, 0.4);
  border-radius: 5px;
}
@media (max-width: 481px) {
  .musiccount_block {
    width: 46%;
  }
}
.musiccount_img_block {
  width: 70px;
  height: 28px;
  background: #26b3fc;
}
.musiccount_counter_block {
  padding: 0 10px 0 0;
  background:  #f5feff;
  line-height: 28px;
  border-radius:3px;
}
.f_13_2 {
  padding: 2px;
  font-size: 12px;
  line-height: 1.5rem;
}
.t_r {
  text-align: right;
}
.icon_s {
  padding: 0px;
  height: 28px;
}
.playerdata_back {
  position: absolute;
  right: 11px;
  bottom: 3px;
  width: 137px;
}
.town_block {
  background: #fff url(/src/assets/playdata/back_town_01.png) bottom no-repeat;
  background-size: 485px;
  box-shadow: 1px 3px 0px rgba(0, 0, 0, 0.4);
  border-radius: 5px;
}

.p_5 {
  padding: 0px;
}
.m_t_0 {
  margin-top: 0;
}
.m_15 {
  margin: 15px;
}
.p_r_10 {
  padding-right: 10px;
}
.d_ib {
  display: inline-block;
}
.w_180 {
  width: 180px;
}
.v_t {
  vertical-align: top;
}
.t_c {
  text-align: center;
}
.p_b_10 {
  padding-bottom: 10px;
}
.m_r_10 {
  margin-right: 10px;
}
.w_120 {
  width: 120px;
}
.p_15 {
  padding: 15px;
}
.m_r_10 {
  margin-right: 10px;
}
.w_160 {
  width: 160px;
}
.p_r {
  position: relative;
}
.d_ib {
  display: inline-block;
}
.t_c {
  text-align: center;
}
.class_point_box {
  background-color: #ffeaea;
  border: 2px solid #fabebe;
  border-radius: 8px;
}
.p_r {
  position: relative;
}
.d_ib {
  display: inline-block;
}
.h_25 {
  height: 25px;
}
.p_t_10 {
  padding-top: 10px;
}
.p_r {
  position: relative;
}
.w_180 {
  width: 180px;
}
.class_geuge_meter, .class_geuge_frame {
  left: 34px;
}
.p_a {
  position: absolute;
}
.w_180 {
  width: 180px;
}
img {
  border: none;
  -webkit-touch-callout: none;
  -webkit-user-select: none;
  -moz-touch-callout: none;
  -moz-user-select: none;
  touch-callout: none;
  user-select: none;
}
.w_215 {
  width: 215px;
}
.class_point_txt {
  top: 165px;
}
.p_a {
  position: absolute;
}
.w_230 {
  width: 230px;
  padding-right: 20px;
}
.f_b {
  font-weight: bold;
}
.f_29 {
  font-size: 29px;
  line-height: 1.2rem;
}
.f_20 {
  font-size: 20px;
  line-height: 1.2rem;
}
.red {
  color: #e60000;
}
.p_10 {
  padding: 10px;
}
.h_50 {
  height: 50px;
}
.t_c_2 {
  display: block;
  margin: 10px auto;
  text-align: center;
  position: relative;
  left: 50%;
  transform: translateX(-50%);
}
</style>
<style>
.title {
  width: 410px;
  display: block;
  margin: 10px auto;
  text-align: center;
}
@media (max-width: 481px) {
  .title {
    width: 80vw;
    display: block;
    margin: 10px auto;
    text-align: center;
  }
}
.m_15 {
  margin: 15px;
}
.w_450 {
  width: 450px;
}
.ticket_title {
  top: 22px;
  left: 60px;
  width: 330px;
}
.p_a {
  position: absolute;
}
.ticket_img {
  top: 77px;
  left: 100px;
  width: 250px;
}
.p_a {
  position: absolute;
}
.ticket_txt {
  top: 183px;
  left: 60px;
  width: 330px;
}
.f_b {
  font-weight: bold;
}
.f_16 {
  font-size: 16px;
  line-height: 1.2rem;
}
.blue {
  color: #1477e6;
}
.intimateup_txt {
  top: 183px;
  left: 60px;
  width: 330px;
}
</style>

<style>
/* 缩放容器基础样式 - 放在原有样式最上方 */
.scale-container {
  display: block;
  transform-origin: top left; /* 固定缩放原点，避免偏移 */
}

/* 媒体查询：屏幕宽度≤480px时等比例缩放 */
@media (max-width: 450px) {
  .scale-container {
    /* 计算缩放比例 = 屏幕宽度 / 480，这里用动态计算值 */
    transform: scale(0.8);
    /* 兼容写法（可选）：如果calc不生效，可手动设置固定比例 */
    /* transform: scale(0.8);  示例：80%缩放，根据需要调整 */
  }
}
@media (max-width: 400px) {
  .scale-container {
    /* 计算缩放比例 = 屏幕宽度 / 480，这里用动态计算值 */
    transform: scale(0.72);
    /* 兼容写法（可选）：如果calc不生效，可手动设置固定比例 */
    /* transform: scale(0.8);  示例：80%缩放，根据需要调整 */
  }
}
@media (max-width: 380px) {
  .scale-container {
    /* 计算缩放比例 = 屏幕宽度 / 480，这里用动态计算值 */
    transform: scale(0.82);
    /* 兼容写法（可选）：如果calc不生效，可手动设置固定比例 */
    /* transform: scale(0.8);  示例：80%缩放，根据需要调整 */
  }
}
@media (max-width: 360px) {
  .scale-container {
    /* 计算缩放比例 = 屏幕宽度 / 480，这里用动态计算值 */
    transform: scale(0.7);
    /* 兼容写法（可选）：如果calc不生效，可手动设置固定比例 */
    /* transform: scale(0.8);  示例：80%缩放，根据需要调整 */
  }
}
@media (max-width: 300px) {
  .scale-container {
    /* 计算缩放比例 = 屏幕宽度 / 480，这里用动态计算值 */
    transform: scale(0.52);
    /* 兼容写法（可选）：如果calc不生效，可手动设置固定比例 */
    /* transform: scale(0.8);  示例：80%缩放，根据需要调整 */
  }
  
}
@media (max-width: 480px) {

}
/* 屏幕宽度>480px时不缩放（默认状态） */
@media (min-width: 481px) {
  .scale-container {
    transform: scale(1.0);
  }
}
</style>