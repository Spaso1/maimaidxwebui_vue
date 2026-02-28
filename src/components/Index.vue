<script setup lang="ts">
import { ref, defineEmits, onMounted, onUnmounted } from 'vue';

const emit = defineEmits(['menu-change']);

const activeMenu = ref('home');
const topRow = ref<HTMLElement | null>(null);
const bottomRow = ref<HTMLElement | null>(null);

const menuItems = [
  { id: 'home', name: 'home' },
  { id: 'playerdata', name: 'playerdata' },
  { id: 'friend', name: 'friend' },
  { id: 'shop', name: 'shop' },
  { id: 'record', name: 'record' },
  { id: 'map', name: 'map' },
  { id: 'collection', name: 'collection' }
];

const handleMenuClick = (menuId: string) => {
  activeMenu.value = menuId;
  emit('menu-change', menuId);
};

const syncImageHeights = () => {
  if (!topRow.value || !bottomRow.value) return;
  
  const topImages = topRow.value.querySelectorAll('.menu-image');
  const bottomImages = bottomRow.value.querySelectorAll('.menu-image');
  
  if (topImages.length === 0) return;
  
  // 计算第一行图片的高度（取最大值）
  let maxHeight = 0;
  topImages.forEach(img => {
    const height = img.getBoundingClientRect().height;
    if (height > maxHeight) {
      maxHeight = height;
    }
  });
  
  // 将第二行图片的高度设置为与第一行一致
  bottomImages.forEach(img => {
    img.style.height = `${maxHeight}px`;
    img.style.width = 'auto';
  });
};

// 在组件挂载后同步高度
onMounted(() => {
  // 延迟执行，确保图片已经加载完成
  setTimeout(syncImageHeights, 100);
  
  // 监听窗口大小变化，重新同步高度
  window.addEventListener('resize', syncImageHeights);
});

// 在组件卸载时移除事件监听
onUnmounted(() => {
  window.removeEventListener('resize', syncImageHeights);
});
</script>

<template>
  <div class="wave-container">
    <!-- 顶部波浪（深蓝天蓝色，盖在容器顶部） -->
    <div class="wave wave-top"></div>
    <!-- 内容区域（浅蓝背景） -->
    <div class="wave-content">
      <div class="menu-container">
        <div class="menu-row top-row" ref="topRow">
          <div 
            v-for="item in menuItems.slice(0, 4)" 
            :key="item.id"
            class="menu-item"
            @click="handleMenuClick(item.id)"
          >
            <img 
              :src="`/src/assets/menu/menu_${item.name}${activeMenu === item.id ? '_on' : ''}.png`" 
              :alt="item.name"
              class="menu-image"
            />
          </div>
        </div>
        <div class="menu-row bottom-row" ref="bottomRow">
          <div 
            v-for="item in menuItems.slice(4)" 
            :key="item.id"
            class="menu-item"
            @click="handleMenuClick(item.id)"
          >
            <img 
              :src="`/src/assets/menu/menu_${item.name}${activeMenu === item.id ? '_on' : ''}.png`" 
              :alt="item.name"
              class="menu-image"
            />
          </div>
        </div>
      </div>
    </div>
    <!-- 底部波浪（深蓝天蓝色，盖在容器底部） -->
    <div class="wave wave-bottom"></div>
  </div>
</template>

<style scoped>
.wave-container {
  width: 100%;
  height: 170px;
  position: relative;
  /* 容器背景设为透明，让内容区的浅蓝作为主体色 */
  background: transparent;
  /* 隐藏溢出，避免波浪超出容器范围 */
  overflow: hidden;
}

/* 内容区：浅蓝底色，作为容器主体 */
.wave-content {
  width: 100%;
  height: 100%;
  background-color: rgb(126, 211, 255); /* 浅蓝 #7ed3ff */
  display: flex;
  align-items: center;
  justify-content: center;
  /* 上下留出波浪的高度，避免内容被波浪遮挡 */
  padding: 0 0;
  box-sizing: border-box;
}

.menu-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 6px;
  width: 100%;
  padding: 0 10px;
  box-sizing: border-box;
}

.menu-row {
  display: flex;
  gap: 6px;
  width: 100%;
  justify-content: center;
}

.menu-item {
  cursor: pointer;
  flex: 1;
  max-width: 112px;
  display: flex;
  justify-content: center;
}

.menu-image {
  width: 100%;
  height: auto;
  max-width: 112px;
}

/* 响应式设计：当屏幕宽度小于480px时 */
@media (max-width: 480px) {
  .menu-row {
    gap: 8px;
  }
  
  .menu-container {
    gap: 8px;
    padding: 0 8px;
  }
  
  .wave-container {
    height: 138px;
  }
}

/* 通用波浪样式：深蓝天蓝色 */
.wave {
  position: absolute;
  left: 0;
  width: 100%;
  height: 10px; /* 波浪高度，设置为25 */
  z-index: 2; /* 波浪层级高于内容区，作为边缘装饰 */
}

/* 顶部波浪：深蓝天蓝色，定位在容器最顶部 */
.wave-top {
  top: -6px;
  background: url("data:image/svg+xml,%3Csvg xmlns='http://www.w3.org/2000/svg' viewBox='0 0 100 25'%3E%3Cpath fill='rgb(83, 187, 242)' fill-opacity='1' d='M0,25L25,12.5C50,0,50,0,75,12.5L100,25L100,0L75,0C50,0,50,0,25,0L0,0Z'%3E%3C/path%3E%3C/svg%3E") repeat-x;
  background-size: 20px 15px; /* 波浪尺寸和高度匹配，每个区间长50，高25 */
}

/* 底部波浪：深蓝天蓝色，定位在容器最底部 */
.wave-bottom {
  bottom: 0;
  background: url("data:image/svg+xml,%3Csvg xmlns='http://www.w3.org/2000/svg' viewBox='0 0 100 25'%3E%3Cpath fill='rgb(83, 187, 242)' fill-opacity='1' d='M0,0L25,12.5C50,25,50,25,75,12.5L100,0L100,25L75,25C50,25,50,25,25,25L0,25Z'%3E%3C/path%3E%3C/svg%3E") repeat-x;
  background-size: 20px 15px; /* 波浪尺寸和高度匹配，每个区间长50，高25 */
}
</style>