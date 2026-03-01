<template>
  <!-- 外层容器用于等比例缩放控制 -->
  <div class="record-card-wrapper">
    <div ref="scalerRef" class="record-card-scaler">
      <div class="p_10 t_l f_0 v_b record-card-container">
        <div class="playlog_top_container p_r">
          <img :src="diffImage" class="playlog_diff v_b">
          <div class="sub_title t_c f_r f_11 sub_left">
            <span class="red f_b v_b f_11 f_11_sub">曲目 {{ trackNumber }}</span>　<span class="v_b f_11 f_11_sub">{{ playDate }}</span>
          </div>
          <div class="clearfix"></div>
        </div>
        <div :class="containerClass">
          <div class="basic_block m_5 p_5 p_l_10 f_13 break"><img :src="clearImage" class="w_80 f_r p_t_2">{{ musicName }}</div>
          <div class="p_r f_0">
            <img loading="lazy" :src="musicImage" class="music_img m_5 m_r_0 f_l">
            <img v-if="isDx" src="/src/assets/record/music_dx.png" class="playlog_music_kind_icon">
            <img v-else src="/src/assets/record/music_standard.png" class="playlog_music_kind_icon">
            <div class="playlog_result_block m_t_5 f_l">
              <div class="playlog_achievement_label_block">
                <img src="/src/assets/record/achievement.png" class="achi_image">
              </div>
              <img v-if="isNewRecord" src="/src/assets/record/newrecord.png" class="playlog_achievement_newrecord">
              <div class="playlog_achievement_txt t_r">{{ achievementParts.integer }}<span class="f_20">.{{ achievementParts.decimal }}</span><span class="f_20">%</span></div>
              <img :src="scoreRankImage" class="playlog_scorerank">
              <img :src="scoreLineImage" class="playlog_scoreline f_r">
              <img src="/src/assets/playlog/line_02.png" alt="line" class="line_image" />
              <div class="playlog_result_innerblock basic_block p_5 f_13">
                <div class="playlog_score_block playlog_score_block_star f_0">
                  <img src="/src/assets/playlog/deluxscore.png" class="w_80">
                  <div class="white p_r_5 f_15 f_r">{{ deluxScore }}</div>
                  <img v-if="dxStar > 0" :src="`/src/assets/record/dxstar_${dxStar}.png`" class="playlog_deluxscore_star">
                </div>
                <img v-if="comboType >= 0" :src="comboImage" class="h_35 m_5 f_l">
                <img v-if="syncType >= 0" :src="syncImage" class="h_35 m_5 f_l">
                <img v-if="list >= 0" :src="listImage" class="h_20 m_5 f_l">
                <div class="clearfix"></div>
              </div>
              <div class="f_0">
                <div v-if="kaleidxHealth" class="f_l">
                  <img src="/src/assets/playlog/icon_kaleidxscope.png" class="h_30">
                  <img src="/src/assets/playlog/icon_life.png" class="w_96 m_b_3">
                  <span :class="['kaleidx-health', isKaleidxHealthZero ? 'red' : 'white']">{{ kaleidxHealth }}</span>
                </div>
                <form action="#" method="get" accept-charset="utf-8" class="f_r">
                  <input type="hidden" name="idx" :value="recordIdx">
                  <button type="submit" class="f_0"><img src="/src/assets/record/btn_detail.png" class="w_84"></button>
                </form>
              </div>
              <div class="clearfix"></div>
            </div>
          </div>
          <div class="clearfix"></div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { computed, onMounted, onUnmounted, ref, watch } from 'vue';

const props = defineProps({
  trackNumber: {
    type: String,
    required: true
  },
  playDate: {
    type: String,
    required: true
  },
  levelId: {
    type: Number,
    required: true
  },
  musicName: {
    type: String,
    required: true
  },
  musicImage: {
    type: String,
    required: true
  },
  isDx: {
    type: Boolean,
    required: false,
    default: false
  },
  isNewRecord: {
    type: Boolean,
    required: false,
    default: false
  },
  achievement: {
    type: String,
    required: true
  },
  scoreType: {
    type: Number,
    required: true
  },
  scoreLineType: {
    type: Number,
    required: true
  },
  deluxScore: {
    type: String,
    required: true
  },
  dxStar: {
    type: Number,
    required: false,
    default: 0
  },
  comboType: {
    type: Number,
    required: true
  },
  syncType: {
    type: Number,
    required: true
  },
  recordIdx: {
    type: String,
    required: true
  },
  list: {
    type: Number,
    required: false,
    default: 3,
  },
  kaleidxHealth: {
    type: String,
    required: false,
    default: ""
  },
});

// 定义缩放容器的ref引用
const scalerRef = ref<HTMLDivElement | null>(null);

// 计算缩放比例并应用
const setCardScale = () => {
  if (!scalerRef.value) return;
  
  // 获取屏幕宽度（兼容写法）
  const screenWidth = window.innerWidth || document.documentElement.clientWidth;
  // 基准宽度：480px，小于480px时等比例缩小，大于等于时不缩放
  const scaleRatio = screenWidth > 480 ? 1 : screenWidth / 480;
  
  // 应用缩放样式
  scalerRef.value.style.transform = `scale(${scaleRatio})`;
  scalerRef.value.style.transformOrigin = 'top left'; // 固定缩放原点，避免偏移
};

// 监听窗口大小变化
onMounted(() => {
  // 初始化执行一次缩放
  setCardScale();
  // 添加窗口大小变化监听
  window.addEventListener('resize', setCardScale);
});

// 组件卸载时移除监听，避免内存泄漏
onUnmounted(() => {
  window.removeEventListener('resize', setCardScale);
});

// 可选：监听组件重新渲染（比如props变化）时重新计算缩放
watch([() => props.trackNumber, () => props.musicName], () => {
  setCardScale();
}, { immediate: false });

const diffImage = computed(() => {
  const diffMap = {
    0: '/src/assets/record/diff_basic.png',
    1: '/src/assets/record/diff_advanced.png',
    2: '/src/assets/record/diff_expert.png',
    3: '/src/assets/record/diff_master.png',
    4: '/src/assets/record/diff_remaster.png',
    10: '/src/assets/record/diff_utage.png'
  };
  return diffMap[props.levelId] || diffMap[3];
});

const clearImage = computed(() => {
  return '/src/assets/record/clear.png';
});

const scoreRankImage = computed(() => {
  const rankMap = {
    13: '/src/assets/playlog/sssplus.png',
    12: '/src/assets/playlog/sss.png',
    11: '/src/assets/playlog/ssplus.png',
    10: '/src/assets/playlog/ss.png',
    9: '/src/assets/playlog/splus.png',
    8: '/src/assets/playlog/s.png',
    7: '/src/assets/playlog/a.png',
    6: '/src/assets/playlog/aa.png',
    5: '/src/assets/playlog/aaa.png',
    4: '/src/assets/playlog/b.png',
    3: '/src/assets/playlog/bb.png',
    2: '/src/assets/playlog/bbb.png',
    1: '/src/assets/playlog/c.png',
    0: '/src/assets/playlog/d.png'
  };
  return rankMap[props.scoreType] || rankMap[0];
});

const scoreLineImage = computed(() => {
  return `/src/assets/line_0${props.scoreLineType}.png`;
});

const comboImage = computed(() => {
  const comboMap = {
    1: '/src/assets/record/fc.png',
    2: '/src/assets/record/fcplus.png',
    3: '/src/assets/record/ap.png',
    4: '/src/assets/record/applus.png'
  };
  return comboMap[props.comboType] || '/src/assets/record/fc_dummy.png';
});

const syncImage = computed(() => {
  const syncMap = {
    1: '/src/assets/record/fs.png',
    2: '/src/assets/record/fsplus.png',
    3: '/src/assets/record/fsd.png',
    4: '/src/assets/record/fsdplus.png',
    5: '/src/assets/record/sync.png'
  };
  return syncMap[props.syncType] || '/src/assets/record/fs_dummy.png';
});
const listImage = computed(() => {
  const syncMap = {
    1: '/src/assets/playlog/1st.png',
    2: '/src/assets/playlog/2nd.png'
  };
  return syncMap[props.list] || '';
});
const achievementParts = computed(() => {
  const parts = props.achievement.split('.');
  return {
    integer: parts[0],
    decimal: parts[1] || ''
  };
});

const containerClass = computed(() => {
  const classMap = {
    0: 'music_basic_score_back',
    1: 'music_advanced_score_back',
    2: 'music_expert_score_back',
    3: 'music_master_score_back',
    4: 'music_remaster_score_back',
    10: 'music_utage_score_back'
  };
  return classMap[props.levelId] || 'music_master_score_back';
});

const isKaleidxHealthZero = computed(() => {
  if (!props.kaleidxHealth) return false;
  const leftPart = props.kaleidxHealth.split('/')[0];
  return leftPart.startsWith('0');
});
</script>

<script lang="ts">
export default {
  name: 'Record_card'
};
</script>

<style scoped>
/* 新增外层包装器 - 关键修复 */
.record-card-wrapper {
  width: 100%;
  max-width: 480px;
  margin: 0 auto 10px;
  overflow: hidden; /* 截断溢出内容 */
  position: relative;
}

/* 移动端卡片间距调整 */
@media (max-width: 480px) {
  .record-card-wrapper {
    margin-bottom: 5px;
  }
}

/* 核心：等比例缩放容器 - 移除vw相关的CSS，由JS控制 */
.record-card-scaler {
  width: 480px; /* 基准宽度 */
  transform-origin: top left; /* 缩放原点，保证布局不变 */
  transition: transform 0s; /* 消除缩放动画 */
}

/* 基础容器：保持原有设置 */
.record-card-container {
  width: 480px; /* 设计基准宽度 */
  box-sizing: border-box;
}

/* 移除原有依赖vw的@media规则 */

/* 原有样式完全保留 */
.p_10 {
  padding: 10px;
}
.h_30 {
  height: 30px;
  float: left;
  padding: 2px;
  top: 5px;
}
.v_b {
  vertical-align: bottom;
}

.t_l {
  text-align: left;
}

.f_0 {
  font-size: 0;
  line-height: 0;
}

.w_96 {
  width: 83px;
}
.m_b_3 {
  padding-top: 7px;
}
.playlog_top_container {
  height: 34px;
  line-height: 35px;
}

.playlog_diff {
  height: 24px;
  padding: 0 2px 0 0;
}

.sub_title {
  width: 230px;
  height: 30px;
  line-height: 19px;
  background: url(/src/assets/record/info_base_01.png) bottom no-repeat;
  background-size: contain;
  padding-top: 10px;
  padding-left: 10px;
  box-sizing: border-box;
}

.red {
  color: red;
}

.f_b {
  font-weight: bold;
}

.f_r {
  float: right;
}

.f_11 {
  font-size: 11px;
  line-height: 11px;
}
.f_11_sub {
  margin-top: 11px;
  top: 40px;
  padding-left: 25px;
}
.clearfix::after {
  content: '';
  display: table;
  clear: both;
}
.p_t_2 {
  margin-bottom: 6px;
}
.music_basic_score_back {
  position: relative;
  margin: 2px 0 0 0;
  padding: 4px;
  background: no-repeat 0 5px, #6fe163;
  border: 2px solid #000;
  box-shadow: 1px 3px 0px #025235;
  border-radius: 5px;
}
.line_image {
  width: 440px;
  height: auto;
  position: absolute;
  padding-top: 48px;
  right: 1px;
  z-index: 1;
}

.music_advanced_score_back {
  position: relative;
  margin: 2px 0 0 0;
  padding: 4px;
  background: no-repeat 0 5px, #f8df3a;
  border: 2px solid #c7450c;
  box-shadow: 1px 3px 0px #c7450c;
  border-radius: 5px;
}

.music_expert_score_back {
  position: relative;
  margin: 2px 0 0 0;
  padding: 4px;
  background: no-repeat 0 5px, #ff828e;
  border: 2px solid #c02138;
  box-shadow: 1px 3px 0px #c02138;
  border-radius: 5px;
}

.music_master_score_back {
  position: relative;
  margin: 2px 0 0 0;
  padding: 4px;
  background: no-repeat 0 5px, #9f51dc;
  border: 2px solid #67148d;
  box-shadow: 1px 3px 0px #67148d;
  border-radius: 5px;
}

.music_remaster_score_back {
  position: relative;
  margin: 2px 0 0 0;
  padding: 4px;
  background: no-repeat 0 5px, #dbaaff;
  border: 2px solid #8c2cd5;
  box-shadow: 1px 3px 0px #8c2cd5;
  border-radius: 5px;
}

.music_utage_score_back {
  position: relative;
  margin: 2px 0 0 0;
  padding: 4px;
  background: no-repeat 0 5px, #ff6ffd;
  border: 2px solid #d00bb1;
  box-shadow: 1px 3px 0px #d00bb1;
  border-radius: 5px;
}

.basic_block {
  background-color: #fff;
  box-shadow: 1px 3px 0px rgba(0, 0, 0, 0.4);
  border-radius: 5px;
}

.f_20 {
  font-size: 18px;
}

.m_5 {
  margin: 5px;
}

.p_5 {
  padding: 5px;
}

.p_l_10 {
  padding-left: 10px;
}

.f_13 {
  font-size: 13px;
  line-height: 13px;
}

.break {
  word-break: break-all;
}

.w_80 {
  width: 80px;
}

.p_r {
  position: relative;
}

.achi_image {
  width: 100px;
}

.music_img {
  border: 1px solid #000;
  position: relative;
  z-index: 2;
}

.m_r_0 {
  margin-right: 0;
}

.f_l {
  float: left;
}

.playlog_music_kind_icon {
  position: absolute;
  top: 0px;
  left: 0;
  height: 16px;
  z-index: 3;
}

.m_t_5 {
  margin-top: 5px;
}

.playlog_result_block {
  width: 250px;
  overflow: hidden;
}

.playlog_achievement_label_block {
  width: 100px;
  padding: 3px 15px;
  background-color: rgba(76, 76, 76, 0.5);
  border-radius: 0 10px 10px 0;
}

.playlog_achievement_newrecord {
  position: absolute;
  top: 30px;
  right: 100px;
  height: 12px;
}

.playlog_achievement_txt {
  position: absolute;
  top: 43px;
  right: 110px;
  width: 170px;
  font-size: 29px;
  color: #fff;
  text-align: right;
  text-shadow: black 1px 1px 0, black -1px -1px 0, black -1px 1px 0, black 1px -1px 0, black 0px 1px 0, black 0 -1px 0, black -1px 0 0, black 1px 0 0;
}

.f_20 {
  font-size: 20px;
  line-height: 1.2rem;
}

.playlog_scorerank {
  position: absolute;
  top: 10px;
  right: 5px;
  width: 90px;
}

.playlog_scoreline {
  position: static;
  width: 380px;
  margin: 48px 0 0 0px;
}

.playlog_result_innerblock {
  position: static;
  height: 80px;
  margin: 58px 0 3px 5px;
}

.playlog_score_block_star {
  border-radius: 5px 5px 0 5px;
}
.sub_left {
  position: absolute;
  top: 5px;
  left: 50%;
}
.playlog_score_block {
  height: 29px;
  padding: 5px;
  background: #424242;
  box-shadow: 1px 1px 0px rgba(0, 0, 0, 0.4);
  border-radius: 5px;
}

.white {
  color: white;
}

.p_r_5 {
  padding-right: 5px;
}

.f_15 {
  font-size: 15px;
  line-height: 15px;
}

.playlog_deluxscore_newrecord {
  position: absolute;
  top: 95px;
  right: 110px;
  height: 12px;
}

.playlog_deluxscore_star {
  position: absolute;
  top: 116px;
  right: 5px;
  height: 20px;
}

.h_35 {
  height: 35px;
}
.h_20 {
  padding-top: 10px;
  padding-left: 10px;
  height: 30px;
}

.w_84 {
  width: 84px;
}

.playlog_result_block form button {
  background: transparent;
  border: none;
  padding: 0;
  cursor: pointer;
  float : right;
}

.kaleidx-health {
  font-size: 10px;
  right: 103px;
  position: absolute;
  vertical-align: middle;
  display: inline-block;
  padding-top: 17px;
}
</style>