<script setup lang="ts">
import {nextTick, ref} from "vue";
import PlayerData from "../sub_views/PlayerData.vue";

const homeSubItems = [
  { id: 'playerdata', name: 'menu_sub_playerdata_playerdata' },
  { id: 'playerdata_stamp', name: 'menu_sub_playerdata_stamp' },
  { id: 'photo_album', name: 'menu_sub_photo_album' },
  { id: 'sub_home2', name: 'menu_sub_playerdata' },
  { id: 'sub_home2', name: 'menu_sub_playerdata' }

];

const selectedItem = ref('playerdata');
const blockOffsets = ref<{[key: string]: number}>({});

const handleItemClick = (itemId: string) => {
  if (itemId !== 'sub_home2') {
  selectedItem.value = itemId;
  console.log(selectedItem.value)
  // 切换标签时重置吸顶状态
  resetStickyBlocks();
  // 重新计算位置
  nextTick(() => {
    calculateBlockOffsets();
  });
}
};
const resetStickyBlocks = () => {
  const blocks = document.querySelectorAll('.screw_block');
  blocks.forEach(block => {
    block.classList.remove('sticky-active');
  });
  stickyBlock.value = null;
};
const stickyBlock = ref<HTMLElement | null>(null);

// 计算所有screw block的偏移量
const calculateBlockOffsets = () => {
  blockOffsets.value = {};
  const blocks = document.querySelectorAll('.screw_block');

  blocks.forEach((block, index) => {
    const rect = block.getBoundingClientRect();
    // 存储相对于视口顶部的偏移量（加上滚动位置）
    blockOffsets.value[`block-${index}`] = window.scrollY + rect.top;
  });
};
</script>

<template>
  <div class="sub-menu-container">
    <div
        v-for="(item) in homeSubItems"
        :key="item.id"
        class="sub-menu-item"
        @click="handleItemClick(item.id)"
    >
      <img
          v-if="selectedItem === item.id"
          src="/src/assets/home_sub/icon_on.png"
          alt="icon_on"
          class="sub-menu-icon"
      />
      <img
          :src="`/src/assets/playdata/${item.name}.png`"
          :alt="item.name"
          class="sub-menu-image"
      />
    </div>
  </div>
  <PlayerData v-if="selectedItem==='playerdata'"/>
  <div v-if="selectedItem==='photo_album'">
    <img src="https://maimai.wahlap.com/maimai-mobile/img/title_photo_album.png" class="title m_10">
    <div class="container p_5 f_13">为了更好符合《中华人民共和国个人信息保护法》的规定，<br>目前相册功能暂不开放。</div>
    <div style="height: 300px"/>
  </div>
</template>

<style scoped>
.title {
  width: 410px;
}
.container {
  max-width: 380px;
  height: auto;
  padding: 5px;
  margin: 15px auto;
  position: relative;
  background-color: #fff;
  box-shadow: 0 0 0 2px #2e94f4, 0 0 0 6px #fff, 1px 8px 8px rgba(0, 0, 0, 0.2), 0 12px rgba(0, 0, 0, 0.2);
  border-radius: 10px;
}
.p_5 {
  padding: 5px;
}
.f_13 {
  font-size: 13px;
  line-height: 1.2rem;
}
.sub-menu-container {
  display: flex;
  align-items: center;
  position: relative;
  width: 100%;
  max-width: 480px;
}
/* 竖线分隔符 */
.sub-menu-item:not(:last-child)::after {
  content: '';
  position: absolute;
  right: 0;
  top: 50%;
  transform: translateY(-50%);
  width: 2px;
  height: 20px;
  background-color: rgb(216, 103, 93);
}
.sub-menu-image {
  width: 100%;
  height: auto;
  max-width: 120px;
  max-height: 42px;
  margin-top: 5px;
}
.sub-menu-icon {
  position: absolute;
  top: -10px;
  padding-left: 31px;
  width: 28px;
  height: 26px;
  z-index: 20;
}

</style>