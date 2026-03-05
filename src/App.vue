<script setup lang="ts">
import { ref } from 'vue';
import Index from './components/Index.vue';
import HomeSubMenu from './components/views/HomeSubMenu.vue';
import Playlog from "./components/views/Playlog.vue";
import PlayData from "./components/views/PlayData.vue";

const currentMenu = ref('home');
const recordSubItem = ref('playlog');
const musicItem = ref<any>(null);

const handleMenuChange = (menuId: string, subItemId?: string, item?: any) => {
  currentMenu.value = menuId;
  if (menuId === 'record' && subItemId) {
    recordSubItem.value = subItemId;
    musicItem.value = item || null;
  }
};

const handleMusicSelect = (item: any) => {
  console.log('handleMusicSelect in App.vue called with:', item);
  currentMenu.value = 'record';
  recordSubItem.value = 'music';
  musicItem.value = item;
};
</script>

<template>
  <div class="index-container">
    <!-- 背景图片 -->
    <img src="./assets/back_base.png" alt="Background" class="background-image" />
    
    <div class="header-container">
      <div class="logo-wrapper">
        <img src="./assets/logo.png" alt="Logo" class="logo" />
      </div>
      <img src="./assets/btn_setting.png" alt="Setting" class="setting-btn" />
    </div>
    <Index :active-menu="currentMenu" @menu-change="handleMenuChange" />
    
    <!-- Home 子菜单 -->
    <HomeSubMenu v-if="currentMenu === 'home'" @menu-change="handleMenuChange" @music-select="handleMusicSelect" />
    <Playlog v-if="currentMenu === 'record'" :initial-item="recordSubItem" :music-item="musicItem"/>
    <PlayData v-if="currentMenu === 'playerdata'"/>

    <!-- Footer -->
    <footer>
      <div class="footer-content">
        <div class="animation-container">
          <div class="animation-wrapper">
            <img src="./assets/back_area_dx.png" alt="back_area_dx" class="animation-image" />
            <img src="./assets/back_area_dx.png" alt="back_area_dx" class="animation-image" />
            <img src="./assets/back_area_dx.png" alt="back_area_dx" class="animation-image" />
          </div>
        </div>
        <div class="footer-extension">
          <svg xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" viewBox="0 0 480 50" preserveAspectRatio="none" style="height:50px; position: relative; z-index: 1;">
            <path d="M0,0 v25 q5,5 10,0 t10,0 t10,0 t10,0 t10,0 t10,0 t10,0 t10,0 t10,0 t10,0 t10,0 t10,0 t10,0 t10,0 t10,0 t10,0 t10,0 t10,0 t10,0 t10,0 t10,0 t10,0 t10,0 t10,0 t10,0 t10,0 t10,0 t10,0 t10,0 t10,0 t10,0 t10,0 t10,0 t10,0 t10,0 t10,0 t10,0 t10,0 t10,0 t10,0 t10,0 t10,0 t10,0 t10,0 t10,0 t10,0 t10,0 t10,0 v-25 Z" fill="#7cbc29"></path>
          </svg>
        </div>
        <img src="./assets/line_01.png" alt="line" class="line_image" />
      </div>
    </footer>
  </div>
</template>

<style scoped>
.logo {
  width: 260px;
  padding: 5px;
  box-sizing: border-box;
}

.header-container {
  position: relative;
  width: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
}

.logo-wrapper {
  display: flex;
  justify-content: center;
  width: 100%;
}

.setting-btn {
  position: absolute;
  right: 0;
  top: 50%;
  transform: translateY(-10%);
  width: 34px;
  height: auto;
  cursor: pointer;
  z-index: 10;
}

.index-container {
  min-height: 100vh;
  position: relative;
  overflow: hidden;
  display: flex;
  flex-direction: column;
  width: 480px;
  margin: 0 auto;
  background-color: transparent;
  border-radius: 8px;
  padding: 0;
  box-shadow: none;
  box-sizing: border-box;
}

.background-image {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: auto;
  object-fit: contain;
  z-index: -1;
  transform: translateY(+14%);
}

.animation-container {
  width: 480px;
  height: auto;
  margin: 0 auto;
  overflow: hidden;
  position: relative;
}

.animation-wrapper {
  display: flex;
  width: 300%;
  height: auto;
  animation: scroll 30s linear infinite;
}

.animation-image {
  width: 33.333%;
  height: auto;
  object-fit: contain;
}

@keyframes scroll {
  0% {
    transform: translateX(0);
  }
  100% {
    transform: translateX(-33.333%);
  }
}

footer {
  width: 100%;
  margin-top: 10px;
  flex: 1;
}

.footer-content {
  width: 480px;
  margin: 0 auto;
  position: relative;
}

.footer-content::before {
  content: '';
  position: absolute;
  top: 80%;
  left: 0;
  right: 0;
  bottom: 0;
  background: linear-gradient(to bottom, #72ae26 0%, #55ab38 100%);
  z-index: -1;
}

.footer-extension {
  width: 480px;
  min-height: 150px;
  margin: 0 auto;
  background-color: #55ab38;
}

.line_image {
  width: 100%;
  height: auto;
  margin-top: 10px;
}
</style>
