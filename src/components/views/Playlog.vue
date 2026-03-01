<script setup lang="ts">
import {ref} from "vue";
import Record_card from "../../components/Record_card.vue";

const recordSubItems = [
  { id: 'playlog', name: 'menu_sub_record_playlog' },
  { id: 'music', name: 'menu_sub_record_playlog' },
  { id: 'course', name: 'menu_sub_record_course' },
  { id: 'sub_record2', name: 'menu_sub_record' }
];

const selectedItem = ref('playlog');

const handleItemClick = (id: string) => {
  selectedItem.value = id;
};
</script>

<template>
  <div class="playlog-sub-menu">
    <div class="sub-menu-container">
      <div
          v-for="(item) in recordSubItems"
          :key="item.id"
          class="sub-menu-item clickable"
          @click="handleItemClick(item.id)"
      >
        <img
            v-if="selectedItem === item.id"
            :src="`/src/assets/home_sub/icon_on.png`"
            :alt="`icon_on_${item.id}`"
            class="sub-menu-icon"
        />
        <img
            :src="`/src/assets/playlog/${item.name}.png`"
            :alt="item.name"
            class="sub-menu-image"
        />
      </div>
    </div>
  </div>
  <div v-if="selectedItem === 'playlog'">
    <img src="/src/assets/playlog/title_playlog.png" alt="line" class="line_image m_t_30" />
    <div class="line_image">
      <Record_card 
        trackNumber="01" 
        playDate="2026/02/28 22:14" 
        :levelId=3
        musicName="胡蝶乃舞" 
        musicImage="https://maimai.wahlap.com/maimai-mobile/img/Music/243cb5e190e6bc1c.png"
        :isDx="true" 
        :isNewRecord="true" 
        achievement="100.6152" 
        :scoreType=13
        :scoreLineType=2
        deluxScore="2,233 / 2,496" 
        :dxStar=1
        :comboType=1
        :syncType=2
        recordIdx="4,1772288055" 
      />
      <Record_card
          trackNumber="02"
          playDate="2026/02/28 22:14"
          :levelId=2
          musicName="胡蝶乃舞"
          musicImage="https://maimai.wahlap.com/maimai-mobile/img/Music/243cb5e190e6bc1c.png"
          :isDx="false"
          :isNewRecord="false"
          achievement="999.9999"
          :scoreType=13
          :scoreLineType=2
          deluxScore="2,233 / 2,496"
          :dxStar=5
          :comboType=0
          :syncType=0
          recordIdx="4,1772288055"
          :list=1
          kaleidx-health="999 / 999"
      />
      <Record_card
          trackNumber="02"
          playDate="2026/02/28 22:14"
          :levelId=10
          musicName="114514"
          musicImage="https://maimai.wahlap.com/maimai-mobile/img/Music/243cb5e190e6bc1c.png"
          :isDx="false"
          :isNewRecord="false"
          achievement="0.9999"
          :scoreType=13
          :scoreLineType=2
          deluxScore="2,233 / 2,496"
          :dxStar=5
          :comboType=0
          :syncType=0
          recordIdx="4,1772288055"
          :list=1
          kaleidx-health="0 / 999"
      />
    </div>
  </div>
</template>

<style scoped>
/* 基础样式保持不变，以下是修改和新增的关键样式 */
.playlog-sub-menu {
  margin-top: 20px;
  display: flex;
  justify-content: center;
}

.sub-menu-container {
  display: flex;
  align-items: center;
  position: relative;
  width: 100%;
  max-width: 480px;
}

.sub-menu-item {
  flex: 1;
  display: flex;
  flex-direction: column;
  align-items: center;
  position: relative;
  cursor: pointer;
}

.sub-menu-icon {
  position: absolute;
  top: -10px;
  width: 28px;
  height: 26px;
  z-index: 10;
}

.sub-menu-image {
  width: 100%;
  height: auto;
  max-width: 120px;
  max-height: 42px;
  margin-top: 5px;
}

/* 竖线分隔符 */
.sub-menu-item:not(:last-child)::after {
  content: '';
  position: absolute;
  right: 0;
  top: 50%;
  transform: translateY(-40%);
  width: 1px;
  height: 25px;
  background-color: rgb(72, 149, 25);
}
.line_image {
  width: 100%;
  height: auto;
  margin-top: 10px;
}
.m_t_30 {
  margin-top: 30px;
}
</style>