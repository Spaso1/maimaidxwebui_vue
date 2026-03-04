<script setup lang="ts">
import { onMounted, ref, computed } from 'vue'
import { api } from '../../utils/api.ts'
import type { Score } from '../../types/music'
import Record_Simple from "./Record_Simple.vue";
import Record_Simple_date from "./Record_Simple_date.vue";

const props = defineProps<{
  chartId: string | number
}>()

const scoreDetail = ref<Score | null>(null)
const isLoading = ref(true)

const getLevelClass = (levelId: number) => {
  const classes = ['music_basic_btn', 'music_advanced_btn', 'music_expert_btn', 'music_master_btn', 'music_remaster_btn']
  return classes[levelId] || 'music_basic_btn'
}

const getScoreRankImage = (scoreType: number) => {
  const rankMap = {
    13: '/src/assets/record/music_icon_sssp.png',
    12: '/src/assets/record/music_icon_sss.png',
    11: '/src/assets/record/music_icon_ssp.png',
    10: '/src/assets/record/music_icon_ss.png',
    9: '/src/assets/record/music_icon_sp.png',
    8: '/src/assets/record/music_icon_s.png',
    7: '/src/assets/record/music_icon_aaa.png',
    6: '/src/assets/record/music_icon_aa.png',
    5: '/src/assets/record/music_icon_a.png',
    4: '/src/assets/record/music_icon_bbb.png',
    3: '/src/assets/record/music_icon_bb.png',
    2: '/src/assets/record/music_icon_b.png',
    1: '/src/assets/record/music_icon_c.png',
    0: '/src/assets/record/music_icon_d.png'
  }
  return rankMap[scoreType] || '/src/assets/record/music_icon_blank.png'
}

const getComboImage = (comboType: number) => {
  const comboMap = {
    0: '/src/assets/record/music_icon_back.png',
    1: '/src/assets/record/music_icon_fc.png',
    2: '/src/assets/record/music_icon_fcp.png',
    3: '/src/assets/record/music_icon_ap.png',
    4: '/src/assets/record/music_icon_app.png'
  }
  return comboMap[comboType] || '/src/assets/record/music_icon_back.png'
}

const getSyncImage = (syncType: number) => {
  const syncMap = {
    0: '/src/assets/record/music_icon_back.png',
    1: '/src/assets/record/music_icon_fs.png',
    2: '/src/assets/record/music_icon_fsp.png',
    3: '/src/assets/record/music_icon_fdx.png',
    4: '/src/assets/record/music_icon_fdxp.png',
    5: '/src/assets/record/music_icon_sync.png'
  }
  return syncMap[syncType] || '/src/assets/record/music_icon_back.png'
}

const getDxStarImage = (dxScore: string) => {
  if (dxScore === '') {
    return `/src/assets/record/music_icon_blank.png`
  }

  const parts = dxScore.split('/')
  if (parts.length !== 2) {
    return `/src/assets/record/music_icon_blank.png`
  }

  const currentScore = parseInt(parts[0].replace(/,/g, ''))
  const maxScore = parseInt(parts[1].replace(/,/g, ''))

  if (maxScore === 0) {
    return `/src/assets/record/music_icon_blank.png`
  }

  const percentage = (currentScore / maxScore) * 100

  if (percentage >= 97) {
    return '/src/assets/music/music_icon_dxstar_5.png'
  }
  if (percentage >= 95) {
    return '/src/assets/music/music_icon_dxstar_4.png'
  }
  if (percentage >= 93) {
    return '/src/assets/music/music_icon_dxstar_3.png'
  }
  if (percentage >= 90) {
    return '/src/assets/music/music_icon_dxstar_2.png'
  }
  if (percentage >= 85) {
    return '/src/assets/music/music_icon_dxstar_1.png'
  }

  return `/src/assets/record/music_icon_blank.png`
}

const loadMusicDetail = async () => {
  try {
    const data = await api.getScore(String(props.chartId))
    scoreDetail.value = data
  } catch (error) {
    console.error('Failed to fetch music detail:', error)
  } finally {
    isLoading.value = false
  }
}

onMounted(() => {
  loadMusicDetail()
})
</script>

<template>
  <div class="scale-container">
    <img src="/src/assets/music/title_music.png" class="title m_10">

    <div v-if="isLoading" class="t_c p_20">
      加载中...
    </div>

    <div v-else-if="scoreDetail" class="f_0">
      <div class="basic_block m_15 m_t_0 p_5 t_l">
        <img :src="scoreDetail.img_url" loading="lazy" class="w_180 m_5 f_l">
        <div class="w_250 f_l t_l">
          <div class="m_10 m_t_5 t_r f_12 blue">
            <img src="/src/assets/record/music_dx.png" v-if="scoreDetail.type === 'dx'" class="f_l h_20">
            <img src="/src/assets/record/music_standard.png" v-if="scoreDetail.type === 'standard'" class="f_l h_20">
            <p class="f_r">舞萌</p>
          </div>
          <div class="m_5 f_15 break">{{ scoreDetail.title }}</div>
          <hr class="w_250">
          <div class="m_5 f_12 break">
            {{ scoreDetail.artist }}
          </div>
        </div>
        <div class="clearfix m_b_10"></div>
        <table class="music_detail_table t_c">
          <tbody>
          <tr v-for="item in scoreDetail.ratingItems" :key="item.chart_id">
            <td class="f_0">
              <div class="music_lv_back m_3 t_c f_14">
                <div class="t_1">{{ item.level_String }}</div>
              </div>
            </td>
            <td class="t_l f_0">
              <img v-if="item.music_name" :src="getScoreRankImage(item.score_type)" class="h_40">
              <img v-if="item.music_name" :src="getComboImage(item.combo_type)" class="h_40">
              <img v-if="item.music_name" :src="getSyncImage(item.sync_type)" class="h_40">
              <img v-if="item.music_name" :src="getDxStarImage(item.dx_score)" class="h_40 p_l_1">
            </td>
            <td>
              <button v-if="item.music_name" type="submit" :class="['music_basic_btn pointer w_96 m_r_5_1 p_5 t_c f_12 f_b white', getLevelClass(item.level_id)]">详细</button>
            </td>
            <td>
              <button v-if="item.music_name" type="submit" :class="['music_basic_btn pointer w_96 m_r_5 p_5 t_c f_12 f_b white', getLevelClass(item.level_id)]">排行榜</button>
            </td>
          </tr>
          </tbody>
        </table>
      </div>
      <div class="p_5">
        <template
            v-for="(item, index) in scoreDetail.ratingItems"
            :key="index"
        >
          <Record_Simple_date
              v-if="item.music_name && item.music_name.trim() !== ''"
              :level_id="item.level_id"
              :level_String="item.level_String"
              :score="item.score"
              :music_name="item.music_name"
              :dx_score="item.dx_score"
              :comboType="item.combo_type"
              :syncType="item.sync_type"
              :score-type="item.score_type"
              :type="item.type"
              :playCount="item.playCount"
              :lastPlayDate="item.lastPlayDate"
              class="pointer"
          />
        </template>
      </div>
    </div>
  </div>
</template>

<style scoped>
.p_20 {
  padding: 20px;
}
.t_c {
  text-align: center;
}

.title {
  max-width: 410px;
}
.m_10 {
  margin: 10px;
}
.t_1 {
  transform: translateY(+90%);
}
.basic_block {
  background-color: rgb(255, 255, 255);
  box-shadow: rgba(0, 0, 0, 0.4) 1px 3px 0px;
  border-radius: 5px;
  padding: 5px;
}
.p_3 {
  margin: 5px;
  height: 20px;
}
.m_15 {
  margin: 15px;
}
.p_5 {
  padding: 5px;
}
.clearfix::after {
  content: '';
  display: table;
  clear: both;
}
.f_r {
  text-align: right;
}
.m_5 {
  margin: 5px;
}
.p_5 {
  padding: 5px;
}
.f_b {
  font-weight: bold;
}
.p_l_1 {
  padding: 4px;
}
button {
  background: transparent;
  border: none;
  padding: 0;
  cursor: pointer;
}
.t_l {
  text-align: left;
}
.f_12 {
  font-size: 12px;
  line-height: 1.2rem;
}
.blue {
  color: rgb(20, 119, 230);
}
.m_3 {
  margin-top: 3px;
  text-align: center;
}
.f_15 {
  font-size: 15px;
  line-height: 1.5rem;
}
.f_14 {
  font-size: 14px;
  line-height: 1.2rem;
}

.break {
  word-break: break-all;
}
.music_detail_table {
  border-collapse: collapse;
  border-width: 1px;
  border-style: solid;
  border-color: rgb(222, 222, 222);
  border-image: initial;
}
.music_detail_table tr {
  border-bottom: 1px dashed rgb(222, 222, 222);
}
.music_detail_table td:nth-child(1) {
  width: 45px;
  border-collapse: collapse;
  padding: 3px;
}
.f_0 {
  font-size: 0px;
  line-height: 0;
}

.music_detail_table td:nth-child(4) {
  width: 100px;
}
.music_detail_table td:nth-child(3) {
  width: 80px;
}
.music_detail_table td:nth-child(2) {
  width: 200px;
}
.music_basic_btn {
  position: relative;
  text-shadow: rgb(34, 187, 91) 1px 1px 0px, rgb(34, 187, 91) -1px -1px 0px, rgb(34, 187, 91) -1px 1px 0px, rgb(34, 187, 91) 1px -1px 0px, rgb(34, 187, 91) 0px 1px 0px, rgb(34, 187, 91) 0px -1px 0px, rgb(34, 187, 91) -1px 0px 0px, rgb(34, 187, 91) 1px 0px 0px;
  box-shadow: rgb(2, 82, 53) 1px 3px 0px;
  background: radial-gradient(rgb(111, 225, 99) 50%, rgb(81, 206, 68));
  border-width: 2px;
  border-style: solid;
  border-color: rgb(0, 0, 0);
  border-image: initial;
  border-radius: 5px;
}
.music_advanced_btn {
  position: relative;
  text-shadow: rgb(251, 156, 45) 1px 1px 0px, rgb(251, 156, 45) -1px -1px 0px, rgb(251, 156, 45) -1px 1px 0px, rgb(251, 156, 45) 1px -1px 0px, rgb(251, 156, 45) 0px 1px 0px, rgb(251, 156, 45) 0px -1px 0px, rgb(251, 156, 45) -1px 0px 0px, rgb(251, 156, 45) 1px 0px 0px;
  box-shadow: rgb(199, 69, 12) 1px 3px 0px;
  background: radial-gradient(rgb(255, 214, 83) 50%, rgb(250, 180, 27));
  border-width: 2px;
  border-style: solid;
  border-color: rgb(199, 69, 12);
  border-image: initial;
  border-radius: 5px;
}
.music_expert_btn {
  position: relative;
  text-shadow: rgb(246, 72, 97) 1px 1px 0px, rgb(246, 72, 97) -1px -1px 0px, rgb(246, 72, 97) -1px 1px 0px, rgb(246, 72, 97) 1px -1px 0px, rgb(246, 72, 97) 0px 1px 0px, rgb(246, 72, 97) 0px -1px 0px, rgb(246, 72, 97) -1px 0px 0px, rgb(246, 72, 97) 1px 0px 0px;
  box-shadow: rgb(192, 33, 56) 1px 3px 0px;
  background: radial-gradient(rgb(255, 130, 142) 50%, rgb(245, 90, 112));
  border-width: 2px;
  border-style: solid;
  border-color: rgb(192, 33, 56);
  border-image: initial;
  border-radius: 5px;
}
.music_master_btn {
  position: relative;
  text-shadow: rgb(158, 69, 226) 1px 1px 0px, rgb(158, 69, 226) -1px -1px 0px, rgb(158, 69, 226) -1px 1px 0px, rgb(158, 69, 226) 1px -1px 0px, rgb(158, 69, 226) 0px 1px 0px, rgb(158, 69, 226) 0px -1px 0px, rgb(158, 69, 226) -1px 0px 0px, rgb(158, 69, 226) 1px 0px 0px;
  box-shadow: rgb(103, 20, 141) 1px 3px 0px;
  background: radial-gradient(rgb(194, 127, 244) 50%, rgb(168, 92, 224));
  border-width: 2px;
  border-style: solid;
  border-color: rgb(103, 20, 141);
  border-image: initial;
  border-radius: 5px;
}
.music_remaster_btn {
  position: relative;
  background: radial-gradient(#e5ddea 50%, #dccce6);
  text-shadow: #9e45e2 1px 1px 0, #9e45e2 -1px -1px 0, #9e45e2 -1px 1px 0, #9e45e2 1px -1px 0, #9e45e2 0px 1px 0, #9e45e2 0 -1px 0, #9e45e2 -1px 0 0, #9e45e2 1px 0 0;
  border: 2px solid #8c2cd5;
  box-shadow: 1px 3px 0px #8c2cd5;
  border-radius: 5px;
}
.music_lv_back {
  background: url("/src/assets/music/back_music_level.png");
  height: 43px;
}
.w_180 {
  width: 180px;
}
.w_250 {
  width: 250px;
}
.m_t_0 {
  margin-top: 0;
}
.white {
  color: white;
}
.m_r_5 {
  margin-right: 1px;
  padding-left: 20px;
  padding-right: 20px;
}
.m_r_5_1 {
  margin-right: 1px;
  padding-left: 25px;
  padding-right: 25px;
}
.f_l {
  float: left;
}
.h_20 {
  height: 20px;
}
.h_40 {
  height: 33px;
  padding: 0px;
  //transform: translateY(+50%);
}
.p_3 {
  padding: 3px;
}
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
</style>
