<script setup lang="ts">
import { computed, ref, watch } from 'vue';
import { PlaylogRecord } from '../../types/playlog.ts';
import Rating from "./Rating.vue";

const props = defineProps<{
  playlog: PlaylogRecord;
}>();

const isLoading = ref(false);
const showMatching = ref(true);

const getNoteColor = (index: number) => {
  const colors = ['#ffbc09', '#ff9d00', '#f75ea3', '#2fca4c', '#8c8c8c'];
  return colors[index] || '#8c8c8c';
};

const getLocalImage = (path: string) => {
  const localPath = `/src/assets/rating/${path}`;
  return localPath;
};

const getNoteImage = (type: string) => {
  const images: Record<string, string> = {
    tap: 'tap.png',
    hold: 'hold.png',
    slide: 'slide.png',
    touch: 'touch.png',
    break: 'break.png',
    fast: 'fast.png',
    late: 'late.png',
    criticalperfect: 'criticalperfect.png',
    perfect: 'perfect.png',
    great: 'great.png',
    good: 'good.png',
    miss: 'miss.png',
    maxcombo: 'maxcombo.png',
    maxsync: 'maxsync.png',
    rating_keep: 'rating_keep.png',
    character: 'character.png',
    icon_star: 'icon_star.png',
  };
  return getLocalImage(images[type] || '');
};

const getDifficultyColor = (diff: number) => {
  const colors = {
    0: { bg: '#6fe163', border: '#025235', shadow: '#025235', back: '/src/assets/record/back_music_basic.png' },
    1: { bg: '#f8df3a', border: '#c7450c', shadow: '#c7450c', back: '/src/assets/record/back_music_advanced.png' },
    2: { bg: '#ff828e', border: '#c02138', shadow: '#c02138', back: '/src/assets/record/back_music_expert.png' },
    3: { bg: '#c27ff4', border: '#67148d', shadow: '#67148d', back: '/src/assets/record/back_music_master.png' },
    4: { bg: '#e5ddea', border: '#8c2cd5', shadow: '#8c2cd5', back: '/src/assets/record/back_music_remaster.png' },
  };
  return colors[diff] || colors[3];
};

const getDifficultyName = (diff: number) => {
  const names = ['BASIC', 'ADVANCED', 'EXPERT', 'MASTER', 'REMASTER'];
  return names[diff] || 'MASTER';
};

const getDifficultyImage = (diff: number) => {
  const images = {
    0: '/src/assets/record/diff_basic.png',
    1: '/src/assets/record/diff_advanced.png',
    2: '/src/assets/record/diff_expert.png',
    3: '/src/assets/record/diff_master.png',
    4: '/src/assets/record/diff_remaster.png',
  };
  return images[diff] || images[3];
};

const handleCharaError = (event: Event) => {
  (event.target as HTMLImageElement).src = getLocalImage('back_character.png');
};

const formatAchievement = (achievement: string) => {
  const parts = achievement.split('.');
  return {
    integer: parts[0],
    decimal: parts[1] || '00'
  };
};

</script>

<template>
  <div class="scale-container">

  <div v-if="isLoading" class="loading">
    加载中...
  </div>
  <div v-else class="gray_block">
    <div class="detail_header">
      <img :src="getLocalImage('detail.png')" class="detail_img">
      <div class="playlog_fl_block">
        <div class="fast_block">
          <img :src="getLocalImage('fast.png')" class="icon_img">
          <div class="icon_text">{{ playlog.playData.fast }}</div>
        </div>
        <div class="late_block">
          <img :src="getLocalImage('late.png')" class="icon_img2">
          <div class="icon_text2">{{ playlog.playData.late }}</div>
        </div>
      </div>
    </div>
    <div class="notes_section">
      <table class="playlog_notes_detail">
        <tbody>
        <tr>
          <th></th>
          <td class="t_c f_0 note"><img :src="getLocalImage('criticalperfect.png')" class="notePng"></td>
          <td class="t_c f_0 note"><img :src="getLocalImage('perfect.png')"  class="notePng"></td>
          <td class="t_c f_0 note"><img :src="getLocalImage('great.png')" class="notePng_s"></td>
          <td class="t_c f_0 note"><img :src="getLocalImage('good.png')" class="notePng_s"></td>
          <td class="t_c f_0 note"><img :src="getLocalImage('miss.png')" class="notePng_s"></td>
        </tr>
        <tr>
          <th class="f_0"><img :src="getLocalImage('tap.png')" class="notePng2"></th>
          <td class="t_c" :class="{ 'note-crossed': playlog.playData.notes.tap[0] === -1 }" :style="{ color: getNoteColor(0) }">{{ playlog.playData.notes.tap[0] }}</td>
          <td class="t_c" :class="{ 'note-crossed': playlog.playData.notes.tap[1] === -1 }" :style="{ color: getNoteColor(1) }">{{ playlog.playData.notes.tap[1] }}</td>
          <td class="t_c" :class="{ 'note-crossed': playlog.playData.notes.tap[2] === -1 }" :style="{ color: getNoteColor(2) }">{{ playlog.playData.notes.tap[2] }}</td>
          <td class="t_c" :class="{ 'note-crossed': playlog.playData.notes.tap[3] === -1 }" :style="{ color: getNoteColor(3) }">{{ playlog.playData.notes.tap[3] }}</td>
          <td class="t_c" :class="{ 'note-crossed': playlog.playData.notes.tap[4] === -1 }" :style="{ color: getNoteColor(4) }">{{ playlog.playData.notes.tap[4] }}</td>
        </tr>
        <tr>
          <th class="f_0"><img :src="getLocalImage('hold.png')"class="notePng2"></th>
          <td class="t_c" :class="{ 'note-crossed': playlog.playData.notes.hold[0] === -1 }" :style="{ color: getNoteColor(0) }">{{ playlog.playData.notes.hold[0] }}</td>
          <td class="t_c" :class="{ 'note-crossed': playlog.playData.notes.hold[1] === -1 }" :style="{ color: getNoteColor(1) }">{{ playlog.playData.notes.hold[1] }}</td>
          <td class="t_c" :class="{ 'note-crossed': playlog.playData.notes.hold[2] === -1 }" :style="{ color: getNoteColor(2) }">{{ playlog.playData.notes.hold[2] }}</td>
          <td class="t_c" :class="{ 'note-crossed': playlog.playData.notes.hold[3] === -1 }" :style="{ color: getNoteColor(3) }">{{ playlog.playData.notes.hold[3] }}</td>
          <td class="t_c" :class="{ 'note-crossed': playlog.playData.notes.hold[4] === -1 }" :style="{ color: getNoteColor(4) }">{{ playlog.playData.notes.hold[4] }}</td>
        </tr>
        <tr>
          <th class="f_0"><img :src="getLocalImage('slide.png')"class="notePng2"></th>
          <td class="t_c" :class="{ 'note-crossed': playlog.playData.notes.slide[0] === -1 }" :style="{ color: getNoteColor(0) }">{{ playlog.playData.notes.slide[0] }}</td>
          <td class="t_c" :class="{ 'note-crossed': playlog.playData.notes.slide[1] === -1 }" :style="{ color: getNoteColor(1) }">{{ playlog.playData.notes.slide[1] }}</td>
          <td class="t_c" :class="{ 'note-crossed': playlog.playData.notes.slide[2] === -1 }" :style="{ color: getNoteColor(2) }">{{ playlog.playData.notes.slide[2] }}</td>
          <td class="t_c" :class="{ 'note-crossed': playlog.playData.notes.slide[3] === -1 }" :style="{ color: getNoteColor(3) }">{{ playlog.playData.notes.slide[3] }}</td>
          <td class="t_c" :class="{ 'note-crossed': playlog.playData.notes.slide[4] === -1 }" :style="{ color: getNoteColor(4) }">{{ playlog.playData.notes.slide[4] }}</td>
        </tr>
        <tr>
          <th class="f_0"><img :src="getLocalImage('touch.png')"class="notePng2"></th>
          <td class="t_c" :class="{ 'note-crossed': playlog.playData.notes.touch[0] === -1 }" :style="{ color: getNoteColor(0) }">{{ playlog.playData.notes.touch[0] }}</td>
          <td class="t_c" :class="{ 'note-crossed': playlog.playData.notes.touch[1] === -1 }" :style="{ color: getNoteColor(1) }">{{ playlog.playData.notes.touch[1] }}</td>
          <td class="t_c" :class="{ 'note-crossed': playlog.playData.notes.touch[2] === -1 }" :style="{ color: getNoteColor(2) }">{{ playlog.playData.notes.touch[2] }}</td>
          <td class="t_c" :class="{ 'note-crossed': playlog.playData.notes.touch[3] === -1 }" :style="{ color: getNoteColor(3) }">{{ playlog.playData.notes.touch[3] }}</td>
          <td class="t_c" :class="{ 'note-crossed': playlog.playData.notes.touch[4] === -1 }" :style="{ color: getNoteColor(4) }">{{ playlog.playData.notes.touch[4] }}</td>
        </tr>
        <tr>
          <th class="f_0"><img :src="getLocalImage('break.png')"class="notePng2"></th>
          <td class="t_c" :class="{ 'note-crossed': playlog.playData.notes.break[0] === -1 }" :style="{ color: getNoteColor(0) }">{{ playlog.playData.notes.break[0] }}</td>
          <td class="t_c" :class="{ 'note-crossed': playlog.playData.notes.break[1] === -1 }" :style="{ color: getNoteColor(1) }">{{ playlog.playData.notes.break[1] }}</td>
          <td class="t_c" :class="{ 'note-crossed': playlog.playData.notes.break[2] === -1 }" :style="{ color: getNoteColor(2) }">{{ playlog.playData.notes.break[2] }}</td>
          <td class="t_c" :class="{ 'note-crossed': playlog.playData.notes.break[3] === -1 }" :style="{ color: getNoteColor(3) }">{{ playlog.playData.notes.break[3] }}</td>
          <td class="t_c" :class="{ 'note-crossed': playlog.playData.notes.break[4] === -1 }" :style="{ color: getNoteColor(4) }">{{ playlog.playData.notes.break[4] }}</td>
        </tr>
        </tbody>
      </table>
      <div class="rating_section">
        <div class="rating_img_block">
          <Rating :rating="playlog.playData.rating" />
        </div>
        <img v-if="playlog.playData.ratingChange === 0" :src="getLocalImage('rating_keep.png')" class="rating_keep_img">
        <div class="rating_change">
          <span>({{ playlog.playData.ratingChange >= 0 ? '+' : '' }}{{ playlog.playData.ratingChange }})</span>
        </div>
      </div>
      <div class="score_section">
        <div class="score_block">
          <img :src="getLocalImage('maxcombo.png')" class="score_icon">
          <div class="score_value">{{ playlog.playData.maxCombo }}</div>
        </div>
        <div class="score_block">
          <img :src="getLocalImage('maxsync.png')" class="score_icon">
          <div class="score_value">{{ playlog.playData.maxSync }}</div>
        </div>
      </div>
      <img src="/src/assets/line_01.png" alt="line" class="line_image" />
    </div>
    <div class="character_section">
      <img :src="getLocalImage('character.png')" class="character_img">
      <div class="playlog_chara_container" v-for="(charaId, index) in (playlog.playData.chara_id || playlog.playData.chara_id || []).slice(0, 5)" :key="index">
        <div class="playlog_chara_block">
          <div class="chara_cycle_trim">
            <img loading="lazy" :src="`https://cdn.godserver.cn/resource/static/coll/Chara/UI_Chara_${charaId}.png`" class="chara_cycle_img" @error="handleCharaError($event)">
          </div>
        </div>
        <div class="playlog_chara_star_block"><img :src="getLocalImage('icon_star.png')" class="v_b">×{{ (playlog.playData.chara_star || playlog.playData.chara_star || [])[index] || 0 }}</div>
        <div class="playlog_chara_lv_block f_13">等级{{ (playlog.playData.chara_level || playlog.playData.chara_level || [])[index] || 0 }}</div>
      </div>
    </div>
    <div v-if="playlog.playData.play_together_diff && playlog.playData.play_together_diff.length > 0" class="matching_section">
      <div class="see_through_block m_10 p_5 t_l f_0" id="matching">
        <span v-for="(diff, index) in playlog.playData.play_together_diff" :key="index" :class="['playlog_master_container', 'w_120', 'p_3', 'd_ib', 'f_0', { 'hidden': !showMatching }]" :style="{ background: getDifficultyColor(diff).bg, boxShadow: '1px 3px 0px ' + getDifficultyColor(diff).shadow, borderRadius: '10px' }">
          <img :src="getDifficultyImage(diff)" class="h_16">
          <img src="/src/assets/rating/icon_each.png" class="h_14 f_r">
          <div class="basic_block p_3 t_c f_12" :style="{ background: '#fff', boxShadow: '1px 3px 0px rgba(0, 0, 0, 0.4)', borderRadius: '5px' }">{{ playlog.playData.play_together_name[index] }}</div>
        </span>
        <span v-for="i in 3 - playlog.playData.play_together_diff.length" :key="'placeholder-' + i" :class="['playlog_master_container2', 'w_120', 'p_3', 'd_ib', 'f_0', { 'hidden': !showMatching }]">
          <img src="https://maimai.wahlap.com/maimai-mobile/img/icon_each.png" class="h_16 f_r">
          <div class="clearfix"></div>
          <div class="basic_block p_3 t_c f_11 s_1" style="background: #fff;height: 24px; box-shadow: 1px 3px 0px rgba(0, 0, 0, 0.4);">_</div>
        </span>
        <div class="f_r p_t_10 d_ib f_0" id="matchingCtrl">
          <img v-if="showMatching" src="/src/assets/btn_off.png" class="h_30 m_t_5 m_b_5" @click="showMatching = false">
          <img v-else src="/src/assets/btn_on.png" class="h_30 m_t_5 m_b_5" @click="showMatching = true">
        </div>
        <div class="clearfix"></div>
      </div>
    </div>
    <div>
      <img src="/src/assets/playlog/btn_myrecord.png" class="h_40 pointer">
      <button type="submit" class="music_master_btn pointer w_96 m_r_5 p_5 t_c f_12 f_b white">排行榜</button>
    </div>
  </div>
    <img src="/src/assets/playlog/line_02.png" alt="line" class="line_image2" />

  </div>

</template>

<style scoped>
/* 缩放容器基础样式 - 放在原有样式最上方 */
.scale-container {
  display: block;
  padding-left: 10px;
  padding-right: 10px;
  width: fit-content;
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
    transform: scale(0.66);
    /* 兼容写法（可选）：如果calc不生效，可手动设置固定比例 */
    /* transform: scale(0.8);  示例：80%缩放，根据需要调整 */
  }
}
@media (max-width: 360px) {
  .scale-container {
    /* 计算缩放比例 = 屏幕宽度 / 480，这里用动态计算值 */
    transform: scale(0.61);
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
/* 屏幕宽度>480px时不缩放（默认状态） */
@media (min-width: 481px) {
  .scale-container {
    transform: scale(0.95);
  }
}
.h_40 {
  height: 40px;
}

input, button, .basic_btn, .pointer {
  cursor: pointer;
  top: 502px;
  padding-left: 100px;
  position: absolute;
}

.gray_block {
  background-color: #eaeaea;
  box-shadow: 1px 3px 0px rgba(0, 0, 0, 0.4);
  border-radius: 5px;
  position: relative;
  min-height: 383px;
  padding-bottom: 5px;
  margin-bottom: 100px;
}
th {
  padding: 4px 5px;
  border: 1px solid #c0e5f7;
}
td {
  padding: 4px 5px;
  border: 1px solid #c0e5f7;
  font-size: 13px;
  text-align: right;
}
.music_master_btn {
  top: 270px;
  left: 200px;
  position: relative;
  background: radial-gradient(#c27ff4 50%, #a85ce0);
  text-shadow: #9e45e2 1px 1px 0, #9e45e2 -1px -1px 0, #9e45e2 -1px 1px 0, #9e45e2 1px -1px 0, #9e45e2 0px 1px 0, #9e45e2 0 -1px 0, #9e45e2 -1px 0 0, #9e45e2 1px 0 0;
  border: 2px solid #67148d;
  box-shadow: 1px 3px 0px #67148d;
  border-radius: 5px;
}
.p_5 {
  padding: 5px;
}
.m_r_5 {
  margin-right: 5px;
}
.m_r_5 {
  margin-right: 5px;
}

.w_96 {
  width: 96px;
}
.t_c {
  text-align: center;
}
.f_b {
  font-weight: bold;
}
.white {
  color: #fff;
}
.gray_block2 {
  background-color: #eaeaea;
  box-shadow: 1px 3px 0px rgba(0, 0, 0, 0.4);
  border-radius: 5px;
}
.notePng {
  max-width: 43px;
  max-height: 13px;
}
.notePng_s {
  max-height: 8px;
}
.notePng2 {
  height: 8px;
}
.note-crossed {
  position: relative;
  overflow: hidden;
}
.note-crossed::before {
  content: '';
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: linear-gradient(to top right, transparent 45%, #3e9cf5 50%, transparent 55%);
  pointer-events: none;
}
.detail_header {
  position: relative;
  padding: 5px;
}
.note {
  width: 48px;
}
.detail_img {
  position: absolute;
  top: 5px;
  left: 0;
}

.playlog_fl_block {
  position: absolute;
  top: 5px;
  right: 4px;
  width: 200px;
  height: 25px;
  box-shadow: 1px 2px 0px rgba(0, 0, 0, 0.4);
  background: #fff;
  border-radius: 15px;
}
.s_1 {
  text-align: center;
}
.fast_block, .late_block {
  position: absolute;
  top: 0;
}

.fast_block {
  left: 5px;
}

.late_block {
  right: 5px;
}
.f_12 {
  padding-top: 10px;
  padding-left: 6px;
  font-size: 12px;
}
.icon_img {
  position: absolute;
  top: 5px;
  left: 3px;
  height: 10px;
}
.icon_img2 {
  position: absolute;
  top: 5px;
  right: 60px;
  height: 10px;
}
.icon_text {
  position: absolute;
  top: 6px;
  left: 80px;
  text-align: right;
  font-size: 12px;
}
.icon_text2 {
  position: absolute;
  top: 6px;
  right: 10px;
  text-align: right;
  font-size: 13px;
}
.notes_section {
  position: relative;
  padding: 5px;
}
.line_image {
  position: relative;
  top: 200px;
  right: 10px;
}
.line_image2 {
  padding-top: 56px;
  top:450px;
}
.playlog_notes_detail {
  position: absolute;
  top: 30px;
  left: 6px;
  max-width: 301px;
  height: 149px;
  border: 2px solid #3e9cf5;
  border-radius: 5px;
  border-collapse: separate;
  border-spacing: 0;
  line-height: 14px;
  background: #fff;
}

.rating_section {
  position: absolute;
  top: 0;
  right: 0;
  width: 142px;
}

.rating_img_block {
  position: absolute;
  left: 0px;
  top: 27px;
  padding: 2px 10px 0px 10px;
}

.rating_keep_img {
  position: absolute;
  top: 62px;
  right: 5px;
  height: 20px;
}

.rating_change {
  position: absolute;
  top: 64px;
  right: 30px;
  font-size: 11px;
}

.score_section {
  position: relative;
  margin-top: 5px;
}

.score_block {
  position: absolute;
  top: 174px;
  width: 220px;
  height: 22px;
  padding: 5px;
  background: #424242;
  box-shadow: 1px 1px 0px rgba(0, 0, 0, 0.4);
  border-radius: 5px;
}

.score_block:first-child {
  left: 0;
}

.score_block:last-child {
  left: 50%;
  padding-left: 5px;
}

.score_icon {
  position: absolute;
  top: 4px;
  left: 4px;
  height: 23px;
}

.score_value {
  position: absolute;
  top: 4px;
  right: 4px;
  left: 30px;
  font-size: 15px;
  color: white;
  text-align: right;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

.divider_line {
  position: relative;
  margin-top: 5px;
  width: 450px;
}

.character_section {
  position: relative;
}

.character_img {
  position: absolute;
  top: 190px;
  left: 0;
}

.chara_list {
  position: relative;
  padding: 5px;
}

.chara_item {
  position: relative;
  display: inline-block;
  top: 100px;
  margin: 0 5px;
}

.chara_cycle_trim {
  width: 60px;
  height: 60px;
  border-radius: 50%;
  overflow: hidden;
  border: 2px solid #fff;
  box-shadow: 0 0 5px rgba(0, 0, 0, 0.5);
}

.chara_cycle_img {
  width: 100%;
  height: 100%;

  object-fit: cover;
}

.chara_star {
  position: absolute;
  top: 65px;
  left: 0;
  width: 100%;
  text-align: center;
  font-size: 12px;
}

.star_img {
  vertical-align: bottom;
}

.chara_level {
  position: absolute;
  top: 85px;
  left: 0;
  width: 100%;
  text-align: center;
  font-size: 13px;
}

.playlog_chara_container {
  position: relative;
  width: 19%;
  top: 220px;
  min-height: 105px;
  margin: 0 0 0 0;
  display: inline-block;
  background: url(/src/assets/rating/back_character.png) no-repeat 111px 15px;
  background-size: 70px;
}

.playlog_chara_block {
  margin: 18px 0px;
  display: inline-block;
}

.playlog_chara_star_block {
  position: absolute;
  top: -10px;
  left: -12px;
  scale: 0.4;
  font-size: 30px;
  background: rgba(255, 255, 255, 0.96);
  border-radius: 10px;
  display: flex;
  align-items: center;
  justify-content: center;
}

.playlog_chara_star_block img {
  vertical-align: middle;
  margin-right: 4px;
}
.f_13 {
  font-size: 13px;
}
.playlog_chara_lv_block {
  position: absolute;
  top: 86px;
  left: 11px;
  width: 70px;
  box-shadow: 1px 3px 0px rgba(0, 0, 0, 0.4);
  background: #4bbbf5;
  border-radius: 10px;
  color: white;
}
.matching_section {
  position: relative;
  top: 250px;
}
.see_through_block {
  background-color: rgba(255, 255, 255, 0.6);
  box-shadow: 1px 3px 0px rgba(0, 0, 0, 0.4);
  border: 2px solid #000;
  border-radius: 5px;
  height: 55px;
}
.playlog_master_container {
  position: relative;
  margin: 2px 0 0 0;
  height: 45px;
  padding: 4px;
  background: #9f51dc;
  box-shadow: 1px 3px 0px #67148d;
  border-radius: 10px;
}
.playlog_master_container2 {
  position: relative;
  margin: 2px 0 0 0;
  height: 45px;
  padding: 4px;
  background: #9f51dc;
  box-shadow: 1px 3px 0px #67148d;
  border-radius: 10px;
  background: #eaeaea; box-shadow: 1px 3px 0px #888; position: relative; top: -10px;
}
.basic_block {
  background-color: #fff;
  box-shadow: 1px 3px 0px rgba(0, 0, 0, 0.4);
  border-radius: 5px;
  margin-top: 5px;
  height: 15px;
}
.m_10 {
  margin-top: 10px;
}
.p_5 {
  padding: 5px;
}
.t_l {
  text-align: left;
  left: 2px;
}
.f_0 {
  font-size: 0;
  line-height: 0;
}
.w_120 {
  width: 120px;
}
.d_ib {
  display: inline-block;
}
.h_16 {
  height: 16px;
}
.h_14 {
  height: 14px;
}
.f_r {
  float: right;
}
.f_11 {
  font-size: 11px;
}
.f_13 {
  font-size: 13px;
}
.p_t_10 {
  padding-top: 10px;
}
.m_t_5 {
  margin-top: 5px;
}
.m_b_5 {
  margin-bottom: 5px;
}
.h_30 {
  height: 30px;
}
.clearfix::after {
  content: '';
  display: table;
  clear: both;
}
.hidden {
  display: none;
}
</style>
