<template>
  <div class="record-card-wrapper" @click="handleClick">
    <div ref="scalerRef" class="record-card-scaler">
      <div class="w_450 m_15 p_r f_0">
        <div :class="[scoreBackClass, 'pointer', 'p_3']">
          <form action="#" method="get" accept-charset="utf-8">
            <img :src="diffImage" class="h_20 f_l">
            <div class="clearfix"></div>
            <div class="music_lv_block f_r t_c f_14">{{ level_String }}</div>
            <div class="music_name_block t_l f_13 break">{{ music_name }}</div>
            <div class="score_icons_container">
              <div class="music_score_block w_112 t_r f_12">{{ score }}%</div>
              <div v-if="dx_score" class="music_score_block w_150 t_r f_12">
                <img src="/src/assets/record/deluxscore.png" class="v_b">
                {{ dx_score }}
              </div>
              <div class="icons_group">
                <img v-if="scoreType == 13" src="/src/assets/record/music_icon_sssp.png">
                <img v-if="scoreType == 12" src="/src/assets/record/music_icon_sss.png">
                <img v-if="scoreType == 11" src="/src/assets/record/music_icon_ssp.png">
                <img v-if="scoreType == 10" src="/src/assets/record/music_icon_ss.png">
                <img v-if="scoreType == 9" src="/src/assets/record/music_icon_sp.png">
                <img v-if="scoreType == 8" src="/src/assets/record/music_icon_s.png">
                <img v-if="scoreType == 7" src="/src/assets/record/music_icon_aaa.png">
                <img v-if="scoreType == 6" src="/src/assets/record/music_icon_aa.png">
                <img v-if="scoreType == 5" src="/src/assets/record/music_icon_a.png">
                <img v-if="scoreType == 4" src="/src/assets/record/music_icon_bbb.png">
                <img v-if="scoreType == 3" src="/src/assets/record/music_icon_bb.png">
                <img v-if="scoreType == 2" src="/src/assets/record/music_icon_b.png">
                <img v-if="scoreType == 1" src="/src/assets/record/music_icon_c.png">
                <img v-if="scoreType == 0" src="/src/assets/record/music_icon_d.png">

                <img v-if="comboType === 0" src="/src/assets/record/music_icon_back.png">
                <img v-if="comboType === 1" src="/src/assets/record/music_icon_fc.png">
                <img v-if="comboType === 2" src="/src/assets/record/music_icon_fcp.png">
                <img v-if="comboType === 3" src="/src/assets/record/music_icon_ap.png">
                <img v-if="comboType === 4" src="/src/assets/record/music_icon_app.png">
                <img v-if="syncType === 0" src="/src/assets/record/music_icon_back.png">
                <img v-if="syncType === 1" src="/src/assets/record/music_icon_fs.png">
                <img v-if="syncType === 2" src="/src/assets/record/music_icon_fsp.png">
                <img v-if="syncType === 3" src="/src/assets/record/music_icon_fdx.png">
                <img v-if="syncType === 4" src="/src/assets/record/music_icon_fdxp.png">
                <img v-if="syncType === 5" src="/src/assets/record/music_icon_sync.png">

              </div>
            </div>

            <input type="hidden" name="idx" value="a17b34e2b6d63b67bdddcd1fa77cae53324d6fc82652c53a1ff4c738d279343f5ff9eb775b6f47242505255e8d1df68fa130d4a1dc65fd0726ac7ab217bdf24acMHphyjR81WoAapAVgmdrqwnm8hFH6wl3Wxgq6xd864=">
          </form>
        </div>
        <img v-if="type === 1" src="/src/assets/record/music_dx.png" class="music_kind_icon ">
        <img v-if="type === 0" src="/src/assets/record/music_standard.png" class="music_kind_icon ">
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { computed, onMounted, onUnmounted, ref, watch, defineEmits } from 'vue';

const props = defineProps({
  level_id: {
    type: Number,
    required: true
  },
  level_String: {
    type: String,
    required: true
  },
  score: {
    type: Number,
    required: true
  },
  music_name: {
    type: String,
    required: true
  },
  dx_score: {
    type: String,
    required: false,
    default: ''
  },
  comboType: {
    type: Number,
    required: true
  },
  syncType: {
    type: Number,
    required: true
  },
  scoreType: {
    type: Number,
    required: true
  },
  type: {
    type: Number,
    required: true
  },
  chart_id: {
    type: [String, Number],
    required: false,
    default: null
  }
});

const emit = defineEmits(['click']);

const handleClick = () => {
  console.log('Record_Simple handleClick called');
  emit('click', { 
    chart_id: props.chart_id, 
    level_id: props.level_id, 
    music_name: props.music_name,
    score: props.score,
    dx_score: props.dx_score,
    comboType: props.comboType,
    syncType: props.syncType,
    scoreType: props.scoreType,
    type: props.type
  });
};

const diffImage = computed(() => {
  const diffMap = {
    0: '/src/assets/record/diff_basic.png',
    1: '/src/assets/record/diff_advanced.png',
    2: '/src/assets/record/diff_expert.png',
    3: '/src/assets/record/diff_master.png',
    4: '/src/assets/record/diff_remaster.png',
    10: '/src/assets/record/diff_utage.png'
  };
  return diffMap[props.level_id] || diffMap[3];
});

const scoreBackClass = computed(() => {
  const classMap = {
    0: 'music_basic_score_back',
    1: 'music_advanced_score_back',
    2: 'music_expert_score_back',
    3: 'music_master_score_back',
    4: 'music_remaster_score_back',
    10: 'music_utage_score_back'
  };
  return classMap[props.level_id] || 'music_master_score_back';
});

const scoreGrade = computed(() => {
  const gradeMap = {
    0: 'D',
    1: 'C',
    2: 'B',
    3: 'BB',
    4: 'BBB',
    5: 'A',
    6: 'AA',
    7: 'AAA',
    8: 'S',
    9: 'S+',
    10: 'SS',
    11: 'SS+',
    12: 'SSS',
    13: 'SSS+'
  };
  return gradeMap[props.scoreType] || 'D';
});

const scalerRef = ref<HTMLDivElement | null>(null);

const setCardScale = () => {
  if (!scalerRef.value) return;
  
  const screenWidth = window.innerWidth || document.documentElement.clientWidth;
  const scaleRatio = screenWidth > 480 ? 1 : screenWidth / 480;
  
  scalerRef.value.style.transform = `scale(${scaleRatio})`;
  scalerRef.value.style.transformOrigin = 'top left';
};

onMounted(() => {
  setCardScale();
  window.addEventListener('resize', setCardScale);
});

onUnmounted(() => {
  window.removeEventListener('resize', setCardScale);
});

watch([() => props.music_name], () => {
  setCardScale();
}, { immediate: false });
</script>

<script lang="ts">
export default {
  name: 'Record_Simple'
};
</script>

<style scoped>
.p_r {
  position: relative;
}

.m_15 {
  margin: 15px;
}

.w_450 {
  padding-top: 7px;
  width: 470px;
  padding-bottom: 57px;
  transition: transform 0.3s ease;
}

.record-card-wrapper {
  width: 100%;
  max-width: 480px;
  margin: 0;
  max-height: 120px;
  overflow: hidden;
  position: relative;
}

@media (max-width: 480px) {
  .record-card-wrapper {
    margin-bottom: 0;
  }
}

.record-card-scaler {
  width: 480px;
  transform-origin: top left;
  transition: transform 0s;
}

@media (max-width: 470px) {
  .w_450 {
    transform-origin: top left;
    transform: scale(calc(100vw / 470));
    margin: 0;
    width: 470px;
    max-width: 100vw;
  }
}

.f_0 {
  font-size: 0;
  line-height: 0;
}

.music_basic_score_back {
  position: relative;
  background: url(/src/assets/record/back_music_basic.png) no-repeat 0 5px, #6fe163;
  border: 2px solid #000;
  width: 430px;
  box-shadow: 1px 3px 0px #025235;
  border-radius: 5px;
}

.music_advanced_score_back {
  position: relative;
  background: url(/src/assets/record/back_music_advanced.png) no-repeat 0 5px, #f8df3a;
  border: 2px solid #c7450c;
  width: 430px;
  box-shadow: 1px 3px 0px #c7450c;
  border-radius: 5px;
}

.music_expert_score_back {
  position: relative;
  background: url(/src/assets/record/back_music_expert.png) no-repeat 0 5px, #ff828e;
  border: 2px solid #c02138;
  width: 430px;
  box-shadow: 1px 3px 0px #c02138;
  border-radius: 5px;
}

.music_master_score_back {
  position: relative;
  background: url(/src/assets/record/back_music_master.png) no-repeat 0 5px, #c27ff4;
  border: 2px solid #67148d;
  width: 430px;
  box-shadow: 1px 3px 0px #67148d;
  border-radius: 5px;
}

.music_remaster_score_back {
  position: relative;
  background: url(/src/assets/record/back_music_remaster.png) no-repeat 0 5px, #e5ddea;
  border: 2px solid #8c2cd5;
  width: 430px;
  box-shadow: 1px 3px 0px #8c2cd5;
  border-radius: 5px;
}

.music_utage_score_back {
  position: relative;
  background: url(/src/assets/record/back_music_utage.png) no-repeat 0 5px, #ff6ffd;
  border: 2px solid #d00bb1;
  width: 430px;
  box-shadow: 1px 3px 0px #d00bb1;
  border-radius: 5px;
}

.f_l {
  float: left;
}

.h_20 {
  height: 20px;
}

.music_lv_block {
  position: absolute;
  top: 18px;
  right: 3px;
  width: 45px;
  height: 26px;
  padding: 17px 0 0 0;
  background: url(/src/assets/record/back_music_level.png);
  border-radius: 5px;
}
.w_150 {
  width: 160px;
}
.music_name_block {
  width: calc(90% - 25px);
  margin: 5px 10% 0 0;
  padding: 5px;
  float: left;
  margin-top: 13px;
  background: #313131;
  border-radius: 5px;
  color: #fff;
}

.music_score_block {
  height: 17px;
  margin: 5px 5px 0 0;
  padding: 3px;
  padding-top: 5px;
  background: rgba(255, 255, 255, 0.9);
  border-radius: 5px;
}

.v_b {
  vertical-align: bottom;
}

.music_kind_icon {
  position: absolute;
  top: 0px;
  right: 30px;
  height: 18px;
}

.p_3 {
  padding: 3px;
}

.pointer {
  cursor: pointer;
}

.clearfix::after {
  content: '';
  display: table;
  clear: both;
}

.t_c {
  text-align: center;
}

.f_14 {
  font-size: 14px;
  line-height: 14px;
}

.t_l {
  text-align: left;
}

.f_13 {
  font-size: 13px;
  line-height: 13px;
}

.break {
  word-break: break-all;
}

.w_112 {
  width: 112px;
}

.t_r {
  text-align: right;
}

.f_12 {
  font-size: 12px;
  line-height: 12px;
}

.w_190 {
  width: 190px;
}

.h_30 {
  height: 30px;
}

.f_r {
  float: right;
}

.music_score_grade {
  margin: 10px 5px 0 0;
  padding: 5px 10px;
  background: rgba(255, 255, 255, 0.9);
  border-radius: 5px;
  font-weight: bold;
}

.score_icons_container {
  display: flex;
  align-items: center;
  width: 100%;
  flex-shrink: 1;
  overflow: hidden;
}

.icons_group {
  display: flex;
  align-items: center;
  flex-shrink: 1;
  margin-left: auto;
  flex-wrap: nowrap;
}
.icons_group img {
  margin-left: 2px;
  flex-shrink: 1;
  width: auto;
  max-width: none;
  height: 30px;
  flex-basis: auto;
}

.music_score_block .v_b {
  width: auto;
  max-width: none;
  height: auto;
  margin-right: 2px;
  flex-shrink: 0;
}

.music_score_block {
  flex-shrink: 1;
  margin-right: 5px;
  min-width: 0;
  display: flex;
  align-items: center;
  height: auto;
  padding: 2px;
}

.w_112 {
  width: auto;
  min-width: 70px;
}

.w_150 {
  width: auto;
  min-width: 100px;
}

.f_12 {
  font-size: 12px;
  line-height: 12px;
}
</style>