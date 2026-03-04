<script setup lang="ts">
import {ref, onMounted, watch} from "vue";
import Record_card from "../been/Record_card.vue";
import PlaylogDetail from "../been/Playlog_Detail.vue";

import { api } from "../../utils/api";
import type {PlaylogRecord} from '../../types/playlog';
import Music from "../sub_views/Music.vue";

const recordSubItems = [
  { id: 'playlog', name: 'menu_sub_record_playlog' },
  { id: 'music', name: 'menu_sub_record_music' },
  { id: 'course', name: 'menu_sub_record_course' },
  { id: 'sub_record2', name: 'menu_sub_record' }
];

const props = defineProps<{
  initialItem?: string
  musicItem?: any
}>();

const selectedItem = ref('playlog');
const selectedRecord = ref<number | null>(null);
const selectedRecordId = ref<string>('');
const scrollPositions = ref<Record<string, number>>({});
const records = ref([]);
const playlogDetail = ref<PlaylogRecord | null>(null);
const isLoading = ref(false);
const isDetailLoading = ref(false);
const localMusicItem = ref(props.musicItem || null);

const handleItemClick = (id: string) => {
  if (id !== 'sub_record2') {
    if (selectedItem.value === 'music' && selectedRecord.value !== null) {
      selectedRecord.value = null;
      selectedRecordId.value = '';
      playlogDetail.value = null;
    }
    console.log(selectedItem.value)
    if (selectedItem.value === 'music') {
      loadPlaylogData();
    }
    selectedItem.value = id;
  }
};

const handleRecordClick = (index: number) => {
  scrollPositions.value[selectedItem.value] = window.scrollY || document.documentElement.scrollTop;
  selectedRecord.value = index;
  selectedRecordId.value = records.value[index].recordIdx;
};

const handleBack = () => {
  const position = scrollPositions.value[selectedItem.value] || 0;
  selectedRecord.value = null;
  selectedRecordId.value = '';
  playlogDetail.value = null;
  setTimeout(() => {
    window.scrollTo({ top: position, behavior: 'smooth' });
  }, 100);
};

const handleMusicBack = () => {
  localMusicItem.value = null;
};

const loadPlaylogData = async () => {
  // if (selectedItem.value !== 'playlog') return;

  isLoading.value = true;
  try {
    const data = await api.get('/api/playlog');
    records.value = data.records;
  } catch (error) {
    console.error('Failed to fetch playlog data:', error);
  } finally {
    isLoading.value = false;
  }
};

const loadPlaylogDetail = async () => {
  if (!selectedRecordId.value) return;

  isDetailLoading.value = true;
  try {
    const response = await api.getPlaylogDetail(selectedRecordId.value);
    const playlogData: any = {
        recordId: response.recordId,
        playData: {
          fast: response.fast,
          late: response.late,
          notes: {
            tap: [response.tap_cpfect, response.tap_pfect, response.tap_great, response.tap_god || 0, response.tap_mis || 0],
            hold: [response.hold_cpfect, response.hold_pfect, response.hold_great, response.hold_god || 0, response.hold_mis || 0],
            slide: [response.slide_cpfect, response.slide_pfect, response.slide_great, response.slide_god || 0, response.slide_mis || 0],
            touch: [response.touch_cpfect, response.touch_pfect, response.touch_great, response.touch_god || 0, response.touch_mis || 0],
            break: [response.break_cpfect, response.break_pfect, response.break_great, response.break_god || 0, response.break_mis || 0]
          },
          rating: response.rating,
          ratingKeep: response.ratingKeep,
          ratingChange: response.rating_change || 0,
          maxCombo: response.max_combo || '',
          maxSync: response.max_sync || '',
          characters: response.characters || [],
          chara_id: response.chara_id,
          chara_star: response.chara_star,
          chara_level:response.chara_level,
          play_together_name:response.play_together_name,
          play_together_diff:response.play_together_diff,
        }
      };
    
    playlogDetail.value = playlogData;
    console.log('Playlog detail set:', playlogDetail.value);
  } catch (error) {
    console.error('Failed to fetch playlog detail:', error);
  } finally {
    isDetailLoading.value = false;
  }
};

onMounted(() => {
  if (props.initialItem && ['playlog', 'music', 'course'].includes(props.initialItem)) {
    selectedItem.value = props.initialItem;
  }
  loadPlaylogData();

  if (props.musicItem) {
    selectedItem.value = 'music';
  }
});

watch(selectedRecordId, () => {
  if (selectedRecordId.value) {
    loadPlaylogDetail();
  }
});

watch(() => props.musicItem, (newVal) => {
  localMusicItem.value = newVal || null;
});
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
    <div v-if="selectedRecord === null" class="line_image">
      <div v-if="isLoading">加载中...</div>
      <div v-else>
        <Record_card 
          v-for="(record, index) in records"
          :key="index"
          v-bind="record"
          @click="handleRecordClick(index)"
        />
      </div>
    </div>
    
    <div v-else class="record-detail-view">
      <Record_card 
        v-bind="records[selectedRecord]"
      />
      <PlaylogDetail 
        v-if="playlogDetail"
        :playlog="playlogDetail"
        class="h_500"
      />
      <div v-if="isDetailLoading" class="loading">加载中...</div>
      <div class="t_c">
        <button type="button" @click="handleBack" class="f_0">
          <img src="/src/assets/playlog/btn_back.png" class="w_80 m_t_10">
        </button>
      </div>
    </div>
  </div>
  <div v-if="selectedItem === 'music'">
    <Music :music-item="localMusicItem" @back="handleMusicBack"/>
  </div>
</template>

<style scoped>
.playlog-sub-menu {
  margin-top: 20px;
  display: flex;
  justify-content: center;
}
.m_t_10 {
  margin-top: 140px;
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

.record-detail-view {
  width: 100%;
  max-width: 480px;
  margin: 0 auto;
}

.record-detail-view .record-card-wrapper {
  margin: 0 auto 20px;
}

.record-detail-view .record-card-scaler {
  transform: scale(1.2);
  transform-origin: top left;
}

@media (max-width: 480px) {
  .record-detail-view .record-card-scaler {
    transform: scale(1);
  }
}
.w_80 {
  width: 80px;
}
button {
  background: transparent;
  border: none;
  padding: 0;
  cursor: pointer;
}
button:focus {
  outline: none;
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
.loading {
  padding: 20px;
  text-align: center;
}
</style>
